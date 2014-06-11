package com.shufudesing.drmb.Collections;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sam on 6/8/2014.
 */
public class SavingsGoal extends MeteorCollection{
    //user,active(boolean),goal(double),months(int),descr,completed,saved(double),date

    private final String TAG = "SavingsGoal";
    private boolean active;

    public SavingsGoal(){
        super();
        active = false;
    }

    public SavingsGoal(String docId, Map<String, Object> fields){
        super(docId, fields);
        active = true;
    }

    public boolean getActive(){
        return active;
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
        DateTime date = new DateTime(mFields.get("dateString").toString());
        DateTime now = new DateTime();
        Period p = new Period(date, now, PeriodType.months());
        int duration = Integer.valueOf(mFields.get("months").toString());
        int months = duration - p.getMonths();
        Log.v(TAG, "Months to go: " + months);
        return String.valueOf(months);
    }

}
