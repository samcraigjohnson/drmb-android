package com.shufudesing.drmb.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.shufudesing.drmb.DrUTILS;
import com.shufudesing.drmb.Drawables.ProgressCircleDrawable;
import com.shufudesing.drmb.Fragments.OverallViewFragment;
import com.shufudesing.drmb.Listeners.MyGestureDetector;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sam on 5/7/2014.
 */
public class MainView extends View{
    private Map<String, ProgressCircleDrawable> circles;
    private String activeCircle = DrUTILS.MONTH;
    private final String TAG = "CircleView";
    private GestureDetector gDetect;

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);

        circles = new HashMap<String, ProgressCircleDrawable>();
        DisplayMetrics screen = context.getResources().getDisplayMetrics();
        int width = DrUTILS.CIRCLE_SIZE;
        int gcSize = DrUTILS.RING_SIZE;
        int y = gcSize + 40;
        int x = (screen.widthPixels/2) - (width/2);

        int leftX = (int) (width*-DrUTILS.SIDE_PERCENT);
        int rightX = (int) (screen.widthPixels - (width*(1-DrUTILS.SIDE_PERCENT)));

        //Month Circle
        circles.put(DrUTILS.MONTH, new ProgressCircleDrawable(x,y,DrUTILS.MONTH, true));
        //Day Circle
        circles.put(DrUTILS.DAY, new ProgressCircleDrawable(leftX, y, DrUTILS.DAY, false));
        //Week Circle
        circles.put(DrUTILS.WEEK, new ProgressCircleDrawable(rightX, y, DrUTILS.WEEK, false));

        gDetect = new GestureDetector(context, new MyGestureDetector());
        OnTouchListener gListen = new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return gDetect.onTouchEvent(event);
            }
        };

        this.setOnTouchListener(gListen);

    }

    public void setMoneyText(String text){
        circles.get(activeCircle).setMoneyText(text);
        this.invalidate();
    }

    public String getActiveDateType(){
        return activeCircle;
    }

    /*
     * Should be given either DrUTILS.DAY / .MONTH/ .WEEK
     */
    public void setActive(String dateType){
        activeCircle = dateType;
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
}
