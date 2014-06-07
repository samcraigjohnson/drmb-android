package com.shufudesing.drmb;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.keysolutions.ddpclient.DDPClient;
import com.keysolutions.ddpclient.android.DDPBroadcastReceiver;
import com.keysolutions.ddpclient.android.DDPStateSingleton;
import com.shufudesing.drmb.Activities.DrLogin;
import com.shufudesing.drmb.Activities.HomeActivity;
import com.shufudesing.drmb.Collections.Category;
import com.shufudesing.drmb.Fragments.OverallViewFragment;
import com.shufudesing.drmb.Offline.OfflineStack;
import com.shufudesing.drmb.Views.CatsView;
import com.shufudesing.drmb.Views.MainView;

import java.util.Map;

/**
 * Created by Sam on 5/8/2014.
 */
public class DrDDPManager extends DDPBroadcastReceiver{

    private HomeActivity mActivity;
    private static final String TAG = "DDPManager";

    public DrDDPManager(MyDDP ddp, Activity activity) {
        super(ddp, activity);
        /*manage the handling of method calls
        LocalBroadcastManager.getInstance(activity).registerReceiver(this,
                new IntentFilter(DDPStateSingleton.MESSAGE_METHODRESUlT));*/
        mActivity = (HomeActivity) activity;
    }

    @Override
    protected void onDDPConnect(DDPStateSingleton ddp) {
        Log.i(TAG, "Connected");
        MyDDP mDDP = MyDDP.getInstance();
        String resumeToken = mDDP.getResumeToken();
        if(resumeToken == null){
            Log.i(TAG, "starting login activity");
            Intent t = new Intent(mActivity, DrLogin.class);
            mActivity.startActivity(t);
        }
        else{
            Log.i(TAG, "loggin in with token");
            mDDP.login(resumeToken);
        }

    }
    @Override
    protected void onSubscriptionUpdate(String changeType,
                                        String subscriptionName, String docId) {

        Log.i(TAG, "Sub Changed: " + subscriptionName + ":"+changeType);
        if (subscriptionName.equals("spending") || subscriptionName.equals("expenses")) {
            if(changeType.equals(DDPClient.DdpMessageType.READY) || changeType.equals(DDPClient.DdpMessageType.CHANGED)) {
                mActivity.expensesUpdate();
            }
        }
    }
    @Override
    protected void onLogin() {
        Log.i(TAG, "Logged in");

        MyDDP mDDP = MyDDP.getInstance();
        mDDP.subscribe("userData", new Object[] {});
        mDDP.subscribe("budget", new Object[]{});
        mDDP.subscribe("spending", new Object[] {});
        mDDP.subscribe("savingsGoals", new Object[] {});

        mDDP.callBackStackMethods();
    }

    @Override
    protected void onLogout() {
        Log.i(TAG, "Logged out");
    }

    @Override
    protected void onError(String title, String msg){
        //TODO better error handling
        Log.e(TAG, title + " : " + msg);
    }
}
