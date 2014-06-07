package com.shufudesing.drmb.Collections;

import android.util.Log;

import com.cedarsoftware.util.io.JsonObject;
import com.cedarsoftware.util.io.JsonReader;
import com.google.gson.Gson;
import com.shufudesing.drmb.DrUTILS;

import java.io.IOException;
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

    public Budget() {
        super();
        cats = new HashMap<String, Category>();
    }
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
            double bud = (Double.parseDouble(values.get("amount").toString()) - (Double.parseDouble(values.get("save").toString())));
            Log.v(TAG, "display amt: " + bud);
            Category nCat = new Category(name, bud);
            cats.put(name, nCat);
            indx++;
        }
    }

    public Double getTotal(){
        return new Double(mFields.get("total").toString());
    }

    public Double getSave(){
        return Double.parseDouble(mFields.get("save").toString());
    }

    //gets a map of the categories {food : {amount: XXX, floor: XXX, save: XXX}, ...}
    public Map<String, Category> getCats(){
        return cats;
    }

    @Override
    public void setFromJson(String json) throws IOException{
        super.setFromJson(json);
        updateCats();
    }

}
