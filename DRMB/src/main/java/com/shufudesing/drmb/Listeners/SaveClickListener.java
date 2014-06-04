package com.shufudesing.drmb.Listeners;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Sam on 5/15/2014.
 */
public class SaveClickListener implements View.OnClickListener{

    private final String TAG = "SAVE CLICK LISTENER";
    private Context c;

    public SaveClickListener(Context c){
        super();
        this.c = c;
    }

    @Override
    public void onClick(View view) {
        Log.v(TAG, "Clicking save button!!!");
        LinearLayout theLayout;
    }
}
