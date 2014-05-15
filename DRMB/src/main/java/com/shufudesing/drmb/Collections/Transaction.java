package com.shufudesing.drmb.Collections;

/**
 * Created by Sam on 5/13/2014.
 */
public class Transaction {

    private String description, cat;
    private double amount;
    private int date;

    public Transaction(String catName, Double amount, String description, Integer date){
        this.cat = catName;
        this.description = description;
        this.amount = amount.doubleValue();
        this.date = date.intValue();

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

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }



}
