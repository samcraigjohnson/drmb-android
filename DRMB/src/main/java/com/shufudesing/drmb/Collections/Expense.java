package com.shufudesing.drmb.Collections;

import android.util.Log;

import com.shufudesing.drmb.Collections.MeteorCollection;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Sam on 5/8/2014.
 */
public class Expense extends MeteorCollection {

    public Expense(String docId, Map<String, Object> fields){
        super(docId, fields);
    }

    //returns the amount of the expense
    public List<Map<String, Object>> getSpendingList(){
        Log.i("EXPENSE", "Fields: " + mFields);

        return (List<Map<String,Object>>) mFields.get("spending");
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

}
