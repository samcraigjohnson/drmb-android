package com.shufudesing.drmb.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.shufudesing.drmb.DrUTILS;
import com.shufudesing.drmb.Drawables.ArcDrawable;
import com.shufudesing.drmb.Drawables.TextDrawable;

/**
 * Created by Sam on 5/7/2014.
 */
public class MainView extends View{
    private ShapeDrawable mDrawable, greenCircle;
    private TextDrawable infoText, moneyText, timeText;
    private ArcDrawable arcDrawable;

    public MainView(Context context) {
        super(context);

        DisplayMetrics screen = context.getResources().getDisplayMetrics();

        int circle_size = 500;
        int width = circle_size;
        int height = circle_size;

        int x = (screen.widthPixels/2) - (width/2);
        int y = (screen.heightPixels/2) - height;

        mDrawable = new ShapeDrawable(new OvalShape());
        mDrawable.getPaint().setColor(DrUTILS.BLUE);
        mDrawable.setBounds(x, y, x + width, y + height);

        int gcSize = 20;
        greenCircle = new ShapeDrawable(new OvalShape());
        greenCircle.getPaint().setColor(DrUTILS.GREEN);
        greenCircle.setBounds(x-gcSize, y-gcSize, x + width+gcSize, y + height+gcSize);

        infoText = new TextDrawable("LEFT TO SPEND", y+(height/2)+25);
        infoText.setTextSize(50f);
        timeText = new TextDrawable("THIS MONTH", y+(height/2)+80);
        timeText.setTextSize(50f);
        moneyText = new TextDrawable("$0", y+(height/3));
        moneyText.setTextSize(90f);

        arcDrawable = new ArcDrawable(x-gcSize, y-gcSize, x+width+gcSize, y+height+gcSize);
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
        moneyText.draw(canvas);
        infoText.draw(canvas);
        timeText.draw(canvas);
    }
}
