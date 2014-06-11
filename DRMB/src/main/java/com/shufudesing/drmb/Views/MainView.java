package com.shufudesing.drmb.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.shufudesing.drmb.DrUTILS;
import com.shufudesing.drmb.Drawables.ArcDrawable;
import com.shufudesing.drmb.Drawables.DateDrawable;
import com.shufudesing.drmb.Drawables.ProgressCircleDrawable;
import com.shufudesing.drmb.Drawables.TextDrawable;

import java.util.Calendar;

/**
 * Created by Sam on 5/7/2014.
 */
public class MainView extends View{
    private ProgressCircleDrawable mDrawable, dDrawable, wDrawable;

    private String activeCircle = DrUTILS.MONTH;

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics screen = context.getResources().getDisplayMetrics();
        int width = DrUTILS.CIRCLE_SIZE;
        int height = DrUTILS.CIRCLE_SIZE;

        int gcSize = DrUTILS.RING_SIZE;
        int y = gcSize + 40;
        int x = (screen.widthPixels/2) - (width/2);

        int leftx = (int) (width*-DrUTILS.SIDE_PERCENT);
        int rightx = (int) (screen.widthPixels - (width*(1-DrUTILS.SIDE_PERCENT)));

        //Month Circle
        mDrawable = new ProgressCircleDrawable(x,y,DrUTILS.MONTH, true);

        //Day Circle
        dDrawable = new ProgressCircleDrawable(leftx, y, DrUTILS.DAY, false);

        //Week Circle
        wDrawable = new ProgressCircleDrawable(rightx, y, DrUTILS.WEEK, false);
    }

    public void setMoneyText(String text){
        mDrawable.setMoneyText(text);
        this.invalidate();
    }

    public String getActiveDateType(){
        return activeCircle;
    }

    public void setPercent(float percent){
        float mAngle = 360f * percent;
        mDrawable.setSweep(mAngle);
        this.invalidate();
    }

    protected void onDraw(Canvas canvas) {
        dDrawable.draw(canvas);
        mDrawable.draw(canvas);
        wDrawable.draw(canvas);
    }
}
