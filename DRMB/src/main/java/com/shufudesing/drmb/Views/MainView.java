package com.shufudesing.drmb.Views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.shufudesing.drmb.R;
import com.shufudesing.drmb.DrUTILS;
import com.shufudesing.drmb.Drawables.ProgressCircleDrawable;
import com.shufudesing.drmb.Listeners.MyGestureDetector;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sam on 5/7/2014.
 */
public class MainView extends View implements Animation.AnimationListener{
    private Map<String, ProgressCircleDrawable> circles;
    private String activeCircle = DrUTILS.MONTH;
    private final String TAG = "CircleView";
    private GestureDetector gDetect;
    private int leftX, rightX, width, x, y;
    private DisplayMetrics screen;
    private boolean isLeft = false;


    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);

        circles = new HashMap<String, ProgressCircleDrawable>();
        screen = context.getResources().getDisplayMetrics();
        width = DrUTILS.CIRCLE_SIZE;
        int gcSize = DrUTILS.RING_SIZE;
        y = gcSize + 40;
        x = (screen.widthPixels/2) - (width/2) + DrUTILS.SCREEN_OFFSET;
        Log.v(TAG, "Screen width: " + screen.widthPixels + " initx: " + x);
        leftX = x - DrUTILS.CIRCLE_OFFSET;
        rightX = x + DrUTILS.CIRCLE_OFFSET;

        //Month Circle
        circles.put(DrUTILS.MONTH, new ProgressCircleDrawable(x,y,DrUTILS.MONTH, true));
        //Day Circle
        circles.put(DrUTILS.DAY, new ProgressCircleDrawable(leftX, y, DrUTILS.DAY, false));
        //Week Circle
        circles.put(DrUTILS.WEEK, new ProgressCircleDrawable(rightX, y, DrUTILS.WEEK, false));

        gDetect = new GestureDetector(context, new MyGestureDetector(this));
        OnTouchListener gListen = new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return gDetect.onTouchEvent(event);
            }
        };

        this.setOnTouchListener(gListen);


    }

    public void setMoneyText(String dateType, String text){
        circles.get(dateType).setMoneyText(text);
        this.invalidate();
    }

    public String getActiveDateType(){
        return activeCircle;
    }

    /*
     * Should be given either DrUTILS.DAY / .MONTH/ .WEEK
     */
    private void setActive(String dateType){
        circles.get(activeCircle).setInactive();
        activeCircle = dateType;
        //circles.get(dateType).setLocation(x, y);
        circles.get(dateType).setActive();
    }

    public void setPercent(String dateType, float percent){
        float angle = 360f * percent;
        Log.v(TAG, "DateType: " + dateType + ", Percent: " + percent);
        circles.get(dateType).setSweep(angle);
        this.invalidate();
    }

    protected void onDraw(Canvas canvas) {
        for(ProgressCircleDrawable pcd: circles.values()){
            pcd.draw(canvas);
        }
    }

    public void updateTime(){
        circles.get(activeCircle).updateTime();
        //this.invalidate();
    }

    public void swipeRight(){
        if(activeCircle.equals(DrUTILS.MONTH)){
            setActive(DrUTILS.DAY);
        }
        else if(activeCircle.equals(DrUTILS.WEEK)) {
            setActive(DrUTILS.MONTH);
        }
    }

    public void swipeLeft(){
        if(activeCircle.equals(DrUTILS.MONTH)){
            setActive(DrUTILS.WEEK);
        }
        else if(activeCircle.equals(DrUTILS.DAY)) {
            setActive(DrUTILS.MONTH);
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        this.invalidate();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
