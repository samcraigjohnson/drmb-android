package com.shufudesing.drmb.Listeners;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Sam on 6/10/2014.
 */
public class AddGoalClickListener implements View.OnClickListener{

    private final String TAG = "SavingsGoalListener";
    private Context c;

    public AddGoalClickListener(Context c){
        super();
        this.c = c;
    }

    @Override
    public void onClick(View view) {
        Log.v(TAG, "Clicking save button!!!");
    }
}
