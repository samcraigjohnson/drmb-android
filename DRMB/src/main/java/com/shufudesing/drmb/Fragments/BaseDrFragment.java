package com.shufudesing.drmb.Fragments;

import android.app.Fragment;

/**
 * Created by Sam on 6/5/2014.
 */
public abstract class BaseDrFragment extends Fragment {
    protected boolean hasInfo = false;

    public void setHasInfo(boolean b){
        hasInfo = b;
    }

    public void checkChange(){
        if(hasInfo){
            updateInfo();
        }
    }

    public abstract void updateInfo();
}
