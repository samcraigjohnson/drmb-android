package com.shufudesing.drmb.Collections;

import android.util.Log;

import com.shufudesing.drmb.DrUTILS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sam on 5/9/2014.
 */
public class Budget extends MeteorCollection {

    private Map<String, Category> cats;
    private static final String TAG = "Budget";

    public Budget(String docId, Map<String, Object> fields){
        super(docId, fields);
        Log.v(TAG, "creating budget object");
        cats = new HashMap<String, Category>();
        updateCats();
    }

    public void updateFields(Map<String, Object> newFields){
        super.updateFields(newFields);
        updateCats();
    }

    public void updateCats(){
        int indx = 0;
        Map<String, Map<String, Object>> catMap = (Map<String, Map<String,Object>>) mFields.get("cats");
        Log.v(TAG, "updating cats");
        for(String name: DrUTILS.CAT_DB_NAMES){
            Map<String, Object> values = catMap.get(name);
            double bud = ((Double) values.get("amount")) - ((Double)values.get("save"));
            Log.v(TAG, "display amt: " + bud);
            Category nCat = new Category(name, bud);
            cats.put(name, nCat);
            indx++;
        }
    }

    public Double getTotal(){
        return (Double) mFields.get("total");
    }

    public Double getSave(){
        return (Double) mFields.get("save");
    }

    //gets a map of the categories {food : {amount: XXX, floor: XXX, save: XXX}, ...}
    public Map<String, Category> getCats(){
        return cats;
    }

}
