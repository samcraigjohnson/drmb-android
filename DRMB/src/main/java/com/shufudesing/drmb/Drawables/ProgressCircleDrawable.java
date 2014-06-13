package com.shufudesing.drmb.Drawables;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;

import com.shufudesing.drmb.DrUTILS;


/**
 * Created by Sam on 6/10/2014.
 */
public class ProgressCircleDrawable extends Drawable {

    private final Paint paint;
    private ShapeDrawable mDrawable, greenCircle;
    private ArcDrawable arcDrawable;
    private TextDrawable infoText, timeText, moneyText;
    private DateDrawable date;
    private boolean active;
    private int width, height;
    private String dateType;
    private final String TAG = "ProgressCircleDrawable";

    public ProgressCircleDrawable(int x, int y, String dateType, boolean active){
        paint = new Paint();
        this.active = active;
        width = DrUTILS.CIRCLE_SIZE;
        height = DrUTILS.CIRCLE_SIZE;
        int midX = (x+((DrUTILS.CIRCLE_SIZE)/2));
        this.dateType = dateType;

        mDrawable = new ShapeDrawable(new OvalShape());
        mDrawable.getPaint().setColor(DrUTILS.BLUE);
        mDrawable.setBounds(x, y, x + width, y + height);
        //Green Ring
        greenCircle = new ShapeDrawable(new OvalShape());
        greenCircle.getPaint().setColor(DrUTILS.GREEN);

        Rect bounds = new Rect(x-DrUTILS.RING_SIZE, y-DrUTILS.RING_SIZE, x + width+DrUTILS.RING_SIZE, y + height+DrUTILS.RING_SIZE);

        greenCircle.setBounds(bounds);
        //Red arc that shows progress
        arcDrawable = new ArcDrawable(bounds);
        //Text to date/money display information
        infoText = new TextDrawable("LEFT TO SPEND", midX, y+(height/2)+25, Color.WHITE);
        infoText.setTextAlign(Paint.Align.CENTER);
        infoText.setTextSize(DrUTILS.INFO_TEXT_SIZE);
        if(dateType.equals(DrUTILS.DAY)){
            timeText = new TextDrawable("TODAY", midX, y+(height/2)+80, Color.WHITE);
        }
        else {
            timeText = new TextDrawable("THIS " + dateType,midX, y + (height / 2) + 80, Color.WHITE);
        }
        timeText.setTextAlign(Paint.Align.CENTER);
        timeText.setTextSize(DrUTILS.INFO_TEXT_SIZE);

        moneyText = new TextDrawable("$0", midX, y+(height/3), Color.WHITE);
        moneyText.setTextAlign(Paint.Align.CENTER);
        moneyText.setTextSize(DrUTILS.MONEY_TEXT_SIZE);

        //Line to show current date and progress
        date = new DateDrawable(dateType, bounds);
    }

    public void draw(Canvas canvas) {
        greenCircle.draw(canvas);
        arcDrawable.draw(canvas);
        mDrawable.draw(canvas);
        moneyText.draw(canvas);
        infoText.draw(canvas);
        timeText.draw(canvas);
        if(active) {
            Log.v(TAG, "DateType: " + dateType + " active!");
            date.draw(canvas);
        }
    }

    public void setActive(){
        active = true;
    }

    public void setInactive() { active = false;}

    public void setLocation(int x, int y){
        mDrawable.setBounds(x, y, x + width, y + height);
        Rect bounds = new Rect(x-DrUTILS.RING_SIZE, y-DrUTILS.RING_SIZE, x + width+DrUTILS.RING_SIZE, y + height+DrUTILS.RING_SIZE);
        greenCircle.setBounds(bounds);
        //Red arc that shows progress
        arcDrawable = new ArcDrawable(bounds);
        date = new DateDrawable(dateType, bounds);
    }

    public void updateTime(){

        //date.updateTime(dateType);
        //this.invalidateSelf();
    }
    public void setMoneyText(String text){
        moneyText.setText("$"+text);
    }

    public void setSweep(float percent){
        arcDrawable.setSweep(percent);
    }

    @Override
    public void setAlpha(int i) {
        paint.setAlpha(i);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
