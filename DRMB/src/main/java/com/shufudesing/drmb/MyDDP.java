package com.shufudesing.drmb;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;

import com.keysolutions.ddpclient.DDPClient;
import com.keysolutions.ddpclient.DDPListener;
import com.keysolutions.ddpclient.TokenAuth;
import com.keysolutions.ddpclient.UsernameAuth;
import com.keysolutions.ddpclient.android.DDPStateSingleton;
import com.shufudesing.drmb.Collections.Budget;
import com.shufudesing.drmb.Collections.Category;
import com.shufudesing.drmb.Collections.Expense;
import com.shufudesing.drmb.Collections.Transaction;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Sam on 5/6/2014.
 */
public class MyDDP extends DDPStateSingleton {

    private static final String mDRMBServer = "192.168.1.2";
    private static final Integer mDRMBPort = 3000;
    private static final String TAG = "MyDDP";

    private Map<String, Expense> mExpenses;
    private Budget mBudget;

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
        clearCats();
        for(Expense e: mExpenses.values()){
            if(e.isActive().booleanValue()){
                Log.v(TAG, "is active" + "num trans: " + e.getTransactions().size());
                for(Transaction t: e.getTransactions()){
                    Log.v(TAG, "amt:"+t.getAmount());
                    total += t.getAmount();
                    mBudget.getCats().get(t.getCatName()).addSpending(t.getAmount());
                }
            }
        }
        return new Double(total);
    }
    private void clearCats(){
        for(Category cat: mBudget.getCats().values()){
            cat.setSpent(0d);
        }
    }

    public Double getAmountLeft(String dateType){
        double left = 0;
        if(dateType.equals(DrUTILS.MONTH)){
            left = mBudget.getTotal().doubleValue() - getTotalSpent().doubleValue()
                    - mBudget.getSave().doubleValue();
        }

        return new Double(left);
    }

    public Map<String, Category> getCategories(){
        return mBudget.getCats();
    }

    public Double getTotalBudget(){
        return mBudget.getTotal();
    }

    public Double getDailyBudget(){
        Calendar c = Calendar.getInstance();
        int numDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        return getTotalBudget() / numDays;
    }

    public Double getWeeklyBudget(){
        Calendar c = Calendar.getInstance();
        int numWeeks = c.getActualMaximum(Calendar.WEEK_OF_MONTH);
        return getTotalBudget() / numWeeks;
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
            } else if (changetype.equals(DDPClient.DdpMessageType.CHANGED)) {
                mExpenses.get(docId).updateFields((Map<String, Object>) getCollection(collectionName).get(docId));
            }
        }
        else if(collectionName.equals("budgets")){
            if (changetype.equals(DDPClient.DdpMessageType.ADDED)) {
                mBudget = new Budget(docId, (Map<String, Object>) getCollection(collectionName).get(docId));
            } else if (changetype.equals(DDPClient.DdpMessageType.REMOVED)) {
                mBudget = null;
            } else if (changetype.equals(DDPClient.DdpMessageType.CHANGED)) {
                mBudget.updateFields((Map<String, Object>) getCollection(collectionName).get(docId));
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
