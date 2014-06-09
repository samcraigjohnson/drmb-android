package com.shufudesing.drmb.Collections;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sam on 6/8/2014.
 */
public class SavingsGoal extends MeteorCollection{
    //user,active(boolean),goal(double),months(int),descr,completed,saved(double),date

    public SavingsGoal(){
        super();
    }

    public SavingsGoal(String docId, Map<String, Object> fields){
        super(docId, fields);
    }

    public double getAmountSaved(){
        return Double.parseDouble(mFields.get("saved").toString());
    }

    public double getGoal(){
        return Double.parseDouble(mFields.get("goal").toString());
    }

    public double getAmountLeft(){ return getGoal() - getAmountSaved();}

    public String getMonthsLeft(){
        //TODO calculate this with JodaTime
        return  mFields.get("months").toString();
    }

}
