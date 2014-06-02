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
import com.shufudesing.drmb.Drawables.TextDrawable;

import java.util.Calendar;

/**
 * Created by Sam on 5/7/2014.
 */
public class MainView extends View{
    private ShapeDrawable mDrawable, greenCircle;
    private TextDrawable infoText, moneyText, timeText;
    private ArcDrawable arcDrawable;
    private DateDrawable date;

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics screen = context.getResources().getDisplayMetrics();
        int width = DrUTILS.CIRCLE_SIZE;
        int height = DrUTILS.CIRCLE_SIZE;

        int gcSize = DrUTILS.RING_SIZE;
        int y = gcSize + 40;
        int x = (screen.widthPixels/2) - (width/2);


        //Main Circle
        mDrawable = new ShapeDrawable(new OvalShape());
        mDrawable.getPaint().setColor(DrUTILS.BLUE);
        mDrawable.setBounds(x, y, x + width, y + height);
        //Green Ring
        greenCircle = new ShapeDrawable(new OvalShape());
        greenCircle.getPaint().setColor(DrUTILS.GREEN);

        Rect bounds = new Rect(x-gcSize, y-gcSize, x + width+gcSize, y + height+gcSize);

        greenCircle.setBounds(bounds);
        //Red arc that shows progress
        arcDrawable = new ArcDrawable(bounds);
        //Text to date/money display information
        infoText = new TextDrawable("LEFT TO SPEND", y+(height/2)+25);
        infoText.setTextSize(DrUTILS.INFO_TEXT_SIZE);
        timeText = new TextDrawable("THIS MONTH", y+(height/2)+80);
        timeText.setTextSize(DrUTILS.INFO_TEXT_SIZE);
        moneyText = new TextDrawable("$0", y+(height/3));
        moneyText.setTextSize(DrUTILS.MONEY_TEXT_SIZE);

        //Line to show current date and progress
        date = new DateDrawable(DrUTILS.MONTH, bounds);
    }


    public void updateTime(String measure){
        date.updateTime(measure);
        this.invalidate();
    }

    public void setColor(int hex){
        mDrawable.getPaint().setColor(hex);
        this.invalidate();
    }

    public void setMoneyText(String text){
        moneyText.setText("$"+text);
        this.invalidate();
    }

    public void setPercent(float percent){
        float angle = 360f * percent;
        Log.v("ARC", "Drawing with angle: " + angle);
        arcDrawable.setSweep(angle);
        this.invalidate();
    }

    protected void onDraw(Canvas canvas) {
        greenCircle.draw(canvas);
        arcDrawable.draw(canvas);
        mDrawable.draw(canvas);
        date.draw(canvas);
        moneyText.draw(canvas);
        infoText.draw(canvas);
        timeText.draw(canvas);
    }
}
