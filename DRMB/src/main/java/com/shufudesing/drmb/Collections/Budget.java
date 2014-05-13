package com.shufudesing.drmb.Collections;

import java.util.Map;

/**
 * Created by Sam on 5/9/2014.
 */
public class Budget extends MeteorCollection {

    public Budget(String docId, Map<String, Object> fields){
        super(docId, fields);
    }

    public Double getTotal(){
        return (Double) mFields.get("total");
    }

    public Double getSave(){
        return (Double) mFields.get("save");
    }

    //gets a map of the categories {food : {amount: XXX, floor: XXX, save: XXX}, ...}
    public Map<String, Map<String,Object>> getCats(){
        return (Map<String, Map<String,Object>>) mFields.get("cats");
    }

}
