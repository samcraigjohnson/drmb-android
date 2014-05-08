package com.shufudesing.drmb;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.keysolutions.ddpclient.DDPClient;
import com.keysolutions.ddpclient.DDPListener;
import com.keysolutions.ddpclient.EmailAuth;
import com.keysolutions.ddpclient.UsernameAuth;
import com.keysolutions.ddpclient.android.DDPStateSingleton;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sam on 5/6/2014.
 */
public class MyDDP extends DDPStateSingleton {

    private static final String mDRMBServer = "192.168.1.7";
    private static final Integer mDRMBPort = 3000;
    private static final String TAG = "DDP";

    protected MyDDP(Context context) {
        super(context);
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

    @Override
    public void createDDPCLient(){
        try {
            Log.i("ddp", "creating ddp...");
            mDDP = new DDPClient(mDRMBServer, mDRMBPort);
            Log.i("ddp", "state: " + mDDP.getState());
        } catch (URISyntaxException e) {
            Log.e(TAG, "Invalid Websocket URL connecting to " + mDRMBServer
                    + ":" + mDRMBPort);
        }

        mDDP.addObserver(this);
        mDDP.connect();
        Log.i("ddp", "state: " + mDDP.getState());
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


}
