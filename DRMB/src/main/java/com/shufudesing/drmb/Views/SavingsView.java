package com.shufudesing.drmb.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.shufudesing.drmb.DrUTILS;
import com.shufudesing.drmb.Drawables.TextDrawable;

/**
 * Created by Sam on 6/6/2014.
 */
public class SavingsView extends View {

    private TextDrawable savingsText;
    private ShapeDrawable blueBar;
    private ShapeDrawable greenBar;

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
        double width = screen.widthPixels;
        float actionBarHeight = 0;
        if (getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }

        savingsText = new TextDrawable("ON TRACK TO SAVE", 5, (int) actionBarHeight - 30, Color.WHITE);
        savingsText.setTextSize(20f);
        savingsText.setTextAlign(Paint.Align.LEFT);

        blueBar = new ShapeDrawable(new RectShape());
        blueBar.getPaint().setColor(DrUTILS.BLUE);
        blueBar.setBounds(10, (int) DrUTILS.INFO_TEXT_SIZE + 10, 250, (int) (DrUTILS.INFO_TEXT_SIZE + 10 + (width * .66)));
    }

    @Override
    protected void onDraw(Canvas c){
        savingsText.draw(c);
        blueBar.draw(c);
    }
}
