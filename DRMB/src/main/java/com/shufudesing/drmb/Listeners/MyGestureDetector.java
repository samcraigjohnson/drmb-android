package com.shufudesing.drmb.Listeners;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.shufudesing.drmb.DrUTILS;
import com.shufudesing.drmb.R;
import com.shufudesing.drmb.Views.MainView;

/**
 * Created by Sam on 6/10/2014.
 */
public class MyGestureDetector extends GestureDetector.SimpleOnGestureListener{

    private final String TAG = "MyGestureDetector";
    private final int SWIPE_MIN_DISTANCE = 10;
    private final int SWIPE_THRESHOLD_VELOCITY = 0;
    private final int SWIPE_MAX_OFF_PATH = 50;
    private MainView mv;

    public MyGestureDetector(MainView v){
        super();
        mv = v;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.v(TAG, "FLINGING!!!!");
        try {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                Log.v(TAG, "off path");
            // right to left swipe
            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                if(!mv.getActiveDateType().equals(DrUTILS.WEEK)) {
                    Animation leftAnimation;
                    if(mv.getActiveDateType().equals(DrUTILS.MONTH)) {
                        leftAnimation = AnimationUtils.loadAnimation(mv.getContext(), R.anim.go_right);
                    }
                    else{
                        leftAnimation = AnimationUtils.loadAnimation(mv.getContext(), R.anim.way_left_go_right);
                    }
                    mv.swipeLeft();
                    leftAnimation.setAnimationListener(mv);
                    mv.startAnimation(leftAnimation);
                }
            }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                if(!mv.getActiveDateType().equals(DrUTILS.DAY)) {
                    Animation rightAnimation;
                    if(mv.getActiveDateType().equals(DrUTILS.MONTH)) {
                        rightAnimation = AnimationUtils.loadAnimation(mv.getContext(), R.anim.go_left);
                    }
                    else{
                        rightAnimation = AnimationUtils.loadAnimation(mv.getContext(), R.anim.way_right_go_left);
                    }
                    mv.swipeRight();
                    rightAnimation.setAnimationListener(mv);
                    mv.startAnimation(rightAnimation);
                }
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

