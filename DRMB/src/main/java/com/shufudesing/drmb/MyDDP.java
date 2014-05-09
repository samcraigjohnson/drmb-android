package com.shufudesing.drmb;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.keysolutions.ddpclient.DDPClient;
import com.keysolutions.ddpclient.DDPListener;
import com.keysolutions.ddpclient.EmailAuth;
import com.keysolutions.ddpclient.TokenAuth;
import com.keysolutions.ddpclient.UsernameAuth;
import com.keysolutions.ddpclient.android.DDPStateSingleton;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Sam on 5/6/2014.
 */
public class MyDDP extends DDPStateSingleton {

    private static final String mDRMBServer = "192.168.1.7";
    private static final Integer mDRMBPort = 3000;
    private static final String TAG = "DDP";

    private Map<String, Expense> mExpenses;

    protected MyDDP(Context context) {
        super(context);
        mExpenses = new ConcurrentHashMap<String, Expense>();
    }

    public static void initInstance(Context context){
        if (mInstance == null) {
            // Create the instance
            mInstance = new MyDDP(context);
        }
    }

    public static MyDDP getInstance() {
        // Return the instance
        return (MyDDP) mInstance;
    }

    public Double getTotalSpent(){
        double total = 0;
        Log.i(TAG, "Getting total spent: " + mExpenses.toString());

        for(Expense e: mExpenses.values()){
            if(e.isActive().booleanValue()){
                for(Map<String, Object> spendingMap: e.getSpendingList()){
                    total += Double.parseDouble((String)spendingMap.get("amount"));
                }
            }
        }
        return new Double(total);
    }

    @Override
    public void broadcastSubscriptionChanged(String collectionName,
                                             String changetype, String docId) {

        Log.i(TAG, "Broadcasting sub change: " + collectionName + ":" + changetype + ":" + docId);

        if (collectionName.equals("expenses")) {
            if (changetype.equals(DDPClient.DdpMessageType.ADDED)) {
                mExpenses.put(docId, new Expense(docId, (Map<String, Object>) getCollection(collectionName).get(docId)));
            } else if (changetype.equals(DDPClient.DdpMessageType.REMOVED)) {
                mExpenses.remove(docId);
            } else if (changetype.equals(DDPClient.DdpMessageType.UPDATED)) {
                //mExpenses.get(docId).updateFields();
            }
        }
        // do the broadcast after we've taken care of our parties wrapper
        super.broadcastSubscriptionChanged(collectionName, changetype, docId);
    }

    @Override
    public void createDDPCLient(){
        Log.i(TAG, "creating my ddp client.......");
        try {
            mDDP = new DDPClient(mDRMBServer, mDRMBPort);
        } catch (URISyntaxException e) {
            Log.e(TAG, "Invalid Websocket URL connecting to " + mDRMBServer
                    + ":" + mDRMBPort);
        }

        mDDP.addObserver(this);
        //mDDP.connect();
        //mDDPState = DDPSTATE.NotLoggedIn;
    }

    @Override
    public void login(String username, String password) {
        Object[] methodArgs = new Object[1];
        UsernameAuth userpass = new UsernameAuth(username, password);
        methodArgs[0] = userpass;

        if(mDDP.getState() == DDPClient.CONNSTATE.Disconnected) {
            Log.i(TAG, "MAKE SURE THE SERVER IS RUNNING DUMMY!");
        }

        Log.i(TAG, "calling login method: " + username + ":" + password);

        getDDP().call("login", methodArgs, new DDPListener() {
            @Override
            public void onResult(Map<String, Object> jsonFields) {
                Log.i("login", "results: " + jsonFields.toString());
                handleLoginResult(jsonFields);
            }
        });
    }

    @Override
    public void login(String token) {
        TokenAuth tokenAuth = new TokenAuth(token);
        Object[] methodArgs = new Object[1];
        methodArgs[0] = tokenAuth;
        getDDP().call("login", methodArgs,  new DDPListener() {
            @Override
            public void onResult(Map<String, Object> jsonFields) {
                Log.i("login with token", "results: " + jsonFields.toString());
                handleLoginResult(jsonFields);
            }
        });
    }


}
