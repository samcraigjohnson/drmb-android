package com.shufudesing.drmb.Collections;

import android.util.Log;

import com.google.gson.Gson;
import com.shufudesing.drmb.Collections.MeteorCollection;


import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Sam on 5/8/2014.
 */
public class Expense extends MeteorCollection {

    private List<Transaction> transactions;
    private static final String TAG = "Expense";

    public Expense(){
        super();
        transactions = new ArrayList<Transaction>();
    }

    public Expense(String docId, Map<String, Object> fields){
        super(docId, fields);
        updateTransactions();
    }

    public void updateFields(Map<String, Object> newFields){
        super.updateFields(newFields);
        updateTransactions();
    }

    private void updateTransactions(){
        transactions = new ArrayList<Transaction>();
        //TODO parse dates
        for(Map<String,Object> trans: (List<Map<String,Object>>) mFields.get("spending")){
            Log.v(TAG, trans.get("cat")+":"+trans.get("amount")+":"+trans.get("description")+":"+trans.get("dateString"));
            DateTime date = new DateTime(trans.get("dateString").toString());
            Transaction t = new Transaction((String) trans.get("cat"), Double.parseDouble(trans.get("amount").toString()),
                    (String) trans.get("description"), date);
            transactions.add(t);
        }
    }
    //returns the amount of the expense
    public List<Transaction> getTransactions(){
        return transactions;
    }

    //returns whether or not this is the active expense
    public Boolean isActive(){
        return (Boolean) mFields.get("active");
    }

    //returns the category of the expense
    public String getUserId(){
        return (String) mFields.get("userId");
    }

    //returns the description of the expense
    public String getBudgetId(){
        return (String) mFields.get("budj");
    }

    //returns the date that the expense was made
    public Date getDate(){
        //TODO parse javascript date to get java date
        String scriptDate = (String) mFields.get("date");
        return null;
    }

    @Override
    public void setFromJson(String json) throws IOException{
        super.setFromJson(json);
        this.updateTransactions();
    }

}
