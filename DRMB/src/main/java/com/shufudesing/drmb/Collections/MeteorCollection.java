package com.shufudesing.drmb.Collections;

import android.util.Log;

import java.util.Map;

/**
 * Created by Sam on 5/9/2014.
 */
public class MeteorCollection {
    protected Map<String, Object> mFields;
    protected String mDocId;

    public MeteorCollection(String docId, Map<String, Object> fields){
        mFields = fields;
        mDocId = docId;
    }

    public Map<String, Object> getFields(){
        return mFields;
    }

    public void updateFields(Map<String, Object> newFields){
        Log.i("Collection", newFields.toString());
        mFields = newFields;
    }
}
