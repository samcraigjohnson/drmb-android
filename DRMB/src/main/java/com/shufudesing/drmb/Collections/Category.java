package com.shufudesing.drmb.Collections;

/**
 * Created by Sam on 5/13/2014.
 */
public class Category {

    private String name;
    private double budget;
    private double spent;

    public Category(String name, double budget)
    {
        this.name = name;
        this.budget = budget;
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
