package com.shufudesing.drmb.Listeners;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Sam on 6/10/2014.
 */
public class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {

    private final String TAG = "MyGestureDetector";
    private final int SWIPE_MIN_DISTANCE = 10;
    private final int SWIPE_THRESHOLD_VELOCITY = 0;
    private final int SWIPE_MAX_OFF_PATH = 50;

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;
            // right to left swipe
            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Log.v(TAG, "SWIPING LEFT");
            }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Log.v(TAG, "SWIPING RIGHT");
            }
        } catch (Exception e) {
            Log.v(TAG, "Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }
}

