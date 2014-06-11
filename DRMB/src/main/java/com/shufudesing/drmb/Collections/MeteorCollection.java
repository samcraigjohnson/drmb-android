package com.shufudesing.drmb.Collections;


import android.util.Log;
import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonObject;
import com.cedarsoftware.util.io.JsonWriter;
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
        JsonObject jo = new JsonObject();
        jo.put(DrUTILS.JSON_FIELDS, JsonWriter.objectToJson(mFields));
        jo.put(DrUTILS.JSON_DOC_ID, JsonWriter.objectToJson(mDocId));
        return jo.toString();
    }

    public void setFromJson(String json) throws IOException{
        Map collection = JsonReader.jsonToMaps(json);
        this.mFields = (Map) collection.get(DrUTILS.JSON_FIELDS);
        this.mDocId = (String) collection.get(DrUTILS.JSON_DOC_ID);
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
