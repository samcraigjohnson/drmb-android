package com.shufudesing.drmb.Collections;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shufudesing.drmb.DrUTILS;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sam on 5/9/2014.
 */
public class MeteorCollection {
    protected Map<String, Object> mFields;
    protected String mDocId;
    private final String TAG = "MeteorCollection";

    public MeteorCollection(){
        mDocId = "";
        mFields = new HashMap<String, Object>();
    }

    public MeteorCollection(String docId, Map<String, Object> fields){
        mFields = fields;
        mDocId = docId;
    }

    public Map<String, Object> getFields(){
        return mFields;
    }
    public String getDocId() { return mDocId; }

    public void updateFields(Map<String, Object> newFields){
        Log.v(TAG, newFields.toString());
        mFields = newFields;
    }

    public String getJsonRepresentation() throws IOException{
        Gson gson = new Gson();
        JsonObject jo = new JsonObject();
        jo.addProperty(DrUTILS.JSON_FIELDS, gson.toJson(mFields));
        jo.addProperty(DrUTILS.JSON_DOC_ID, gson.toJson(mDocId));
        return jo.toString();
    }

    public void setFromJson(Map<String, Object> map){
        this.mFields = (Map<String,Object>) map.get(DrUTILS.JSON_FIELDS);
        this.mDocId = (String) map.get(DrUTILS.JSON_DOC_ID);
    }

    @Override
    public String toString(){
        String s = "{";
        for(String key: mFields.keySet()){
            s += key + ": " + mFields.get(key).toString() + ", ";
        }
        s += "}";
        return s;
    }
}
