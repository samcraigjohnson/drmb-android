package com.shufudesing.drmb.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.shufudesing.drmb.DrUTILS;
import com.shufudesing.drmb.Drawables.TextDrawable;

/**
 * Created by Sam on 6/6/2014.
 */
public class SavingsView extends View {

    private TextDrawable savingsText, saveInfoText;
    private ShapeDrawable blueBar;
    private ShapeDrawable greenBar;
    private String dollarsLeft, timeLeft;
    private double width;

    public SavingsView(Context c){
        super(c);
        init();
    }
    public SavingsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SavingsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    private void init(){
        TypedValue tv = new TypedValue();
        DisplayMetrics screen = getContext().getResources().getDisplayMetrics();

        width = screen.widthPixels;
        double height = (DrUTILS.INFO_TEXT_SIZE + 10 + DrUTILS.SAVINGS_BAR_SIZE)/2;
        int initHeight = (int) DrUTILS.ALT_SAVINGS_TEXT_SIZE+30;

        float actionBarHeight = 0;
        dollarsLeft = "0";
        timeLeft = "0";
        if (getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }

        savingsText = new TextDrawable("ON TRACK TO SAVE", 5, (int) actionBarHeight - 50, Color.WHITE);
        savingsText.setTextSize(DrUTILS.ALT_SAVINGS_TEXT_SIZE);
        savingsText.setTextAlign(Paint.Align.LEFT);

        saveInfoText = new TextDrawable("$"+dollarsLeft+" and " + timeLeft + " months left!", (int)(width*.66)/2, (int)height, Color.WHITE);
        saveInfoText.setTextSize(DrUTILS.SAVINGS_TEXT_SIZE);
        saveInfoText.setTextAlign(Paint.Align.CENTER);
        saveInfoText.setBounds(0, initHeight, (int) (width * .66), DrUTILS.SAVINGS_BAR_SIZE + initHeight);
        saveInfoText.correctWidth((int)(width*.66));

        blueBar = new ShapeDrawable(new RectShape());
        blueBar.getPaint().setColor(DrUTILS.BLUE);
        blueBar.setBounds(0, initHeight, (int) (width * .66), DrUTILS.SAVINGS_BAR_SIZE + initHeight);

        greenBar = new ShapeDrawable(new RectShape());
        greenBar.getPaint().setColor(DrUTILS.GREEN);
        greenBar.setBounds(0, initHeight, (int) (width * .66),DrUTILS.SAVINGS_BAR_SIZE + initHeight);
    }

    @Override
    protected void onDraw(Canvas c){
        //savingsText.draw(c);
        blueBar.draw(c);
        greenBar.draw(c);
        saveInfoText.draw(c);
    }

    public void setSavingsPercent(double percent){
        Log.v("SGV", "Setting green bar with percent: " + percent);
        Rect bounds = greenBar.getBounds();
        double newWidth = bounds.right * percent;
        greenBar.setBounds(bounds.left, bounds.top, (int) newWidth, bounds.bottom);
        this.invalidate();
    }

    public void setSavingsInfo(String dollars, String time){
        this.dollarsLeft = dollars;
        this.timeLeft = time;

        saveInfoText.setText("$"+dollarsLeft+" and " + timeLeft + " months left!");
        saveInfoText.correctWidth((int)(width*.66));
        this.invalidate();
    }
}
