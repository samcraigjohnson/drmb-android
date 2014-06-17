package com.shufudesing.drmb.Collections;

import com.shufudesing.drmb.Comparators.DateComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sam on 5/13/2014.
 */
public class Category {

    private String name;
    private double budget;
    private double spent;
    private List<Transaction> trans;

    public Category(String name, double budget)
    {
        this.name = name;
        this.budget = budget;
        trans = new ArrayList<Transaction>();
    }

    public void setSpent(double val){
        spent = val;
    }
    public void addSpending(double val){
        spent += val;
    }

    public double getSpent(){
        return spent;
    }

    public List<Transaction> getTransactions(){
        Collections.sort(trans, new DateComparator());
        return trans;
    }

    public void addTransaction(Transaction t){
        trans.add(t);
    }

    public double getPercentSpent(){
        return spent / budget;
    }

    public String getName(){
        return name;
    }

    public double getBudget(){
        return budget;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setBudget(double budget){
        this.budget = budget;
    }

}
