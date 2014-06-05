package com.shufudesing.drmb.Collections;

import android.util.Log;

import org.joda.time.DateTime;

/**
 * Created by Sam on 5/13/2014.
 */
public class Transaction implements Comparable<Transaction>{

    private final String TAG = "Transaction";
    @Override
    public int compareTo(Transaction t) {
        if(date.isBefore(t.getDate().toInstant())){
            return -1;
        }
        else{
            return 1;
        }
    }

    private String description, cat;
    private double amount;
    private DateTime date;

    public Transaction(String catName, Double amount, String description, DateTime date){
        this.cat = catName;
        this.description = description;
        this.amount = amount.doubleValue();
        Log.v(TAG, "Date: " + date);
        this.date = date;

    }

    public String getCatName() {
        return cat;
    }

    public void setCat(String catName) {
        this.cat = catName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }



}
