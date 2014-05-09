package com.shufudesing.drmb;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.keysolutions.ddpclient.DDPClient;
import com.keysolutions.ddpclient.android.DDPBroadcastReceiver;
import com.keysolutions.ddpclient.android.DDPStateSingleton;

/**
 * Created by Sam on 5/8/2014.
 */
public class DrDDPManager extends DDPBroadcastReceiver{

    private HomeActivity mActivity;
    private static final String TAG = "DDPManager";

    public DrDDPManager(MyDDP ddp, Activity activity) {
        super(ddp, activity);

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

        Log.i("DDPReceiver", "Sub Changed: " + subscriptionName + ":"+changeType);
        if (subscriptionName.equals("spending")) {
            if(changeType == DDPClient.DdpMessageType.READY){
                //TODO change this to left to spend not total spent
                String newText = MyDDP.getInstance().getTotalSpent().toString();
                mActivity.getBigCircle().setMoneyText(newText);
            }
            //updateExpenses();
        }
    }
    @Override
    protected void onLogin() {
        Log.i("DDPReceiver", "Logged in");

        MyDDP mDDP = MyDDP.getInstance();
        mDDP.subscribe("userData", new Object[] {});
        mDDP.subscribe("budget", new Object[]{});
        mDDP.subscribe("spending", new Object[] {});
        mDDP.subscribe("savingsGoals", new Object[] {});
    }
    @Override
    protected void onLogout() {
        Log.i("DDPReceiver", "Logged out");
    }

    @Override
    protected void onError(String title, String msg){
        //TODO better error handling
        Log.e(TAG, title + " : " + msg);
    }
}
