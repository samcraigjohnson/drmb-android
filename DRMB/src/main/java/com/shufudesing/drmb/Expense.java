package com.shufudesing.drmb;

import android.util.Log;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Sam on 5/8/2014.
 */
public class Expense {

    private Map<String, Object> mFields;
    private String mDocId;

    public Expense(String docId, Map<String, Object> fields){
        mFields = fields;
        mDocId = docId;
    }

    public Map<String, Object> getFields(){
        return mFields;
    }

    //returns the amount of the expense
    public List<Map<String, Object>> getSpendingList(){
        Log.i("EXPENSE", "Fields: " + mFields);

        return (List<Map<String,Object>>) mFields.get("spending");
    }

    public Boolean isActive(){
        return (Boolean) mFields.get("active");
    }

    //returns the category of the expense
    public String getCategory(){
        return (String) mFields.get("cat");
    }

    //returns the description of the expense
    public String getDescription(){
        return (String) mFields.get("description");
    }

    public Date getDate(){
        //TODO parse javascript date to get java date
        String scriptDate = (String) mFields.get("date");
        return null;
    }

}
