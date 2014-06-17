package com.shufudesing.drmb;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.keysolutions.ddpclient.DDPClient;
import com.keysolutions.ddpclient.DDPListener;
import com.keysolutions.ddpclient.TokenAuth;
import com.keysolutions.ddpclient.UsernameAuth;
import com.keysolutions.ddpclient.android.DDPStateSingleton;
import com.shufudesing.drmb.Collections.Budget;
import com.shufudesing.drmb.Collections.Category;
import com.shufudesing.drmb.Collections.Expense;
import com.shufudesing.drmb.Collections.MeteorCollection;
import com.shufudesing.drmb.Collections.SavingsGoal;
import com.shufudesing.drmb.Collections.Transaction;
import com.shufudesing.drmb.Comparators.AmountComparator;
import com.shufudesing.drmb.Comparators.DateComparator;
import com.shufudesing.drmb.Offline.OfflineStack;
import com.shufudesing.drmb.Offline.SavedCall;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Sam on 5/6/2014.
 */
public class MyDDP extends DDPStateSingleton {

    private static final String mDRMBServer = "192.168.1.5";
    private static final Integer mDRMBPort = 3000;
    private static final String TAG = "MyDDP";
    private OfflineStack offlineStack;

    protected MyDDP(Context context) {
        super(context);
        Map<String, Expense> mExpenses = new ConcurrentHashMap<String, Expense>();
        offlineStack = new OfflineStack(context);
        offlineStack.setBudget(new Budget());
        offlineStack.setExpenses(mExpenses);
        offlineStack.setSavingsGoal(new SavingsGoal());
    }

    public static void initInstance(Context context){
        if (mInstance == null) {
            mInstance = new MyDDP(context);
        }
    }

    public static MyDDP getInstance() {
        return (MyDDP) mInstance;
    }

    public boolean isSavingsGoalActive(){
        return offlineStack.getSavingsGoal().getActive();
    }

    public List<Transaction> getTransactions(){
        return getTransactionsBy(DrUTILS.BY_DATE);
    }

    public List<Transaction> getTransactionsBy(int by){
        List<Transaction> transactions = new ArrayList<Transaction>();
        for(Expense e: offlineStack.getExpenses().values()){
            for(Transaction t: e.getTransactions()){
                transactions.add(t);
            }
        }

        if(by == DrUTILS.BY_HIGHTEST){
            Collections.sort(transactions, new AmountComparator());
            Collections.reverse(transactions);
        }
        else if(by == DrUTILS.BY_LOWEST){
            Collections.sort(transactions, new AmountComparator());
        }
        else{
            Collections.sort(transactions, new DateComparator());
        }

        return transactions;
    }

    /**
     * Call any methods that were saved while not connected
     * to the meteor server
     */
    public void callBackStackMethods(){
        Stack<SavedCall> calls = offlineStack.getCallStack();
        while(!calls.isEmpty()){
            SavedCall call = calls.pop();
            mDDP.call(call.getMethodName(), call.getArgs());
        }
        offlineStack.resetStack();
    }

    public double getPercentSaved(){
        return offlineStack.getSavingsGoal().getAmountSaved() / offlineStack.getSavingsGoal().getGoal();
    }

    public String getSavingAmountLeft(){
        return DrUTILS.formatDouble(offlineStack.getSavingsGoal().getAmountLeft());
    }

    public String getMonthsLeft(){
        Log.v(TAG, "recalling months left");
        return offlineStack.getSavingsGoal().getMonthsLeft();
    }

    public void saveStack(){
        offlineStack.writeData();
    }

    public void loadStack(){
        offlineStack.readData();
    }

    public Double getTotalSpent(String dateType){
        double total = 0;
        for(Expense e: offlineStack.getExpenses().values()){
            if(e.isActive()){
                total = e.getSpendingByDate(dateType);
            }
        }
        Log.v(TAG, "DateType: "+ dateType+" total spent:" + total);
        return new Double(total);
    }

    public void setUpCats(){
        clearCats();
        for(Expense e: offlineStack.getExpenses().values()) {
            if (e.isActive()) {
                for(Transaction t: e.getTransactions()){
                    offlineStack.getBudget().getCats().get(t.getCatName()).addSpending(t.getAmount());
                }
            }
        }
    }

    private void clearCats(){
        for(Category cat: offlineStack.getBudget().getCats().values()){
            cat.setSpent(0d);
        }
    }

    public Double getAmountLeft(String dateType){
        double left = getTotalBudget(dateType) - getTotalSpent(dateType)
                    - getSaveByDate(dateType);
        Log.v(TAG, "amount left: " + left + " datetype: " + dateType);
        return new Double(left);
    }



    public Map<String, Category> getCategories(){
        return offlineStack.getBudget().getCats();
    }

    public boolean addExpense(double amount, String cat, String description){
        Object[] methodArgs = new Object[3];
        methodArgs[0] = amount;
        methodArgs[1] = cat;
        methodArgs[2] = description.trim();
        if(isConnected() && isLoggedIn()) {
            mDDP.call("addExpense", methodArgs, new DDPListener() {
                @Override
                @SuppressWarnings("unchecked")
                public void onResult(Map<String, Object> jsonFields) {
                    Log.v(TAG, "Resulting json: " + jsonFields.toString());
                    if (jsonFields.get("msg").equals("result")) {
                        Map<String, Object> result = (Map<String, Object>) jsonFields
                                .get(DDPClient.DdpMessageField.RESULT);
                        Intent broadcastIntent = new Intent();
                        broadcastIntent.setAction(MESSAGE_METHODRESUlT);
                        broadcastIntent.putExtra(MESSAGE_EXTRA_RESULT,
                                mGSON.toJson(result));
                        LocalBroadcastManager.getInstance(
                                DrApplication.getAppContext()).sendBroadcast(
                                broadcastIntent);
                        Log.v(TAG, "sending intent");
                    } else if (jsonFields.containsKey("error")) {
                        Map<String, Object> error = (Map<String, Object>) jsonFields
                                .get(DDPClient.DdpMessageField.ERROR);
                        broadcastDDPError((String) error.get("message"));
                    }
                }
            });
            return true;
        }
        else{
            Log.v(TAG, "Logged in: " + isLoggedIn() + " Connected: " + isConnected());
            connectIfNeeded();
            offlineStack.saveCall("addExpense", methodArgs);
            return false;
        }
    }

    public Double getTotalBudget(String dateType){
        Calendar c = Calendar.getInstance();
        double total = offlineStack.getBudget().getTotal();
        if(dateType.equals(DrUTILS.MONTH))
            return total;
        else if(dateType.equals(DrUTILS.DAY)){
            int numDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            return total / numDays;
        }
        else{
            int numWeeks = c.getActualMaximum(Calendar.WEEK_OF_MONTH);
            return total / numWeeks;
        }
    }

    public Double getSaveByDate(String dateType){
        Calendar c = Calendar.getInstance();
        double save = offlineStack.getBudget().getSave();
        if(dateType.equals(DrUTILS.MONTH))
            return save;
        else if(dateType.equals(DrUTILS.DAY)){
            int numDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            return save / numDays;
        }
        else{
            int numWeeks = c.getActualMaximum(Calendar.WEEK_OF_MONTH);
            return save / numWeeks;
        }
    }

    @Override
    public void broadcastSubscriptionChanged(String collectionName,
                                             String changetype, String docId) {

        Log.i(TAG, "Broadcasting sub change: " + collectionName + ":" + changetype + ":" + docId);

        if (collectionName.equals("expenses")) {
            if (changetype.equals(DDPClient.DdpMessageType.ADDED)) {
                offlineStack.getExpenses().put(docId, new Expense(docId, getCollection(collectionName).get(docId)));
            } else if (changetype.equals(DDPClient.DdpMessageType.REMOVED)) {
                offlineStack.getExpenses().remove(docId);
            } else if (changetype.equals(DDPClient.DdpMessageType.CHANGED)) {
                offlineStack.getExpenses().get(docId).updateFields(getCollection(collectionName).get(docId));
            }
            setUpCats();
        }
        else if(collectionName.equals("budgets")){
            if (changetype.equals(DDPClient.DdpMessageType.ADDED)) {
                offlineStack.setBudget(new Budget(docId, getCollection(collectionName).get(docId)));
            } else if (changetype.equals(DDPClient.DdpMessageType.REMOVED)) {
                offlineStack.setBudget(new Budget());
            } else if (changetype.equals(DDPClient.DdpMessageType.CHANGED)) {
                offlineStack.getBudget().updateFields(getCollection(collectionName).get(docId));
            }
        }
        else if(collectionName.equals("savingsGoals")){
            if (changetype.equals(DDPClient.DdpMessageType.ADDED)) {
                offlineStack.setSavingsGoal(new SavingsGoal(docId, getCollection(collectionName).get(docId)));
            } else if (changetype.equals(DDPClient.DdpMessageType.REMOVED)) {
                offlineStack.setSavingsGoal(new SavingsGoal());
            } else if (changetype.equals(DDPClient.DdpMessageType.CHANGED)) {
                offlineStack.getSavingsGoal().updateFields(getCollection(collectionName).get(docId));
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
