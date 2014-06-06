package com.shufudesing.drmb.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.shufudesing.drmb.DrUTILS;
import com.shufudesing.drmb.Drawables.TextDrawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sam on 5/12/2014.
 */
public class CatsView extends View {

    private Map<String, ShapeDrawable> bars = new HashMap<String, ShapeDrawable>();
    private Map<String, ShapeDrawable> greenBars = new HashMap<String, ShapeDrawable>();
    private List<TextDrawable> catNames = new ArrayList<TextDrawable>();
    private static final String TAG = "CATS VIEW";

    public CatsView(Context context, AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics screen = context.getResources().getDisplayMetrics();

        int currX = 0;
        int padding = 10;
        int width = ((screen.widthPixels)/ DrUTILS.NUM_CATS) - padding;

        for(int inx = 0; inx < DrUTILS.NUM_CATS; inx++){
            ShapeDrawable newD = new ShapeDrawable(new RectShape());
            newD.getPaint().setColor(DrUTILS.RED);
            newD.setBounds(currX+ padding, 0, currX + width, DrUTILS.CAT_MAX_HEIGHT);
            bars.put(DrUTILS.CAT_DB_NAMES[inx], newD);

            ShapeDrawable greenB = new ShapeDrawable(new RectShape());
            greenB.getPaint().setColor(DrUTILS.GREEN);
            greenB.setBounds(currX + padding, 0, currX + width, DrUTILS.CAT_MAX_HEIGHT);
            greenBars.put(DrUTILS.CAT_DB_NAMES[inx], greenB);

            TextDrawable catName = new TextDrawable(DrUTILS.CAT_NAMES[inx], currX+padding + 5, DrUTILS.CAT_MAX_HEIGHT + 30, Color.WHITE);
            catNames.add(catName);
            currX += (width + padding);
        }
    }

    public void setHeight(String catName, double percent){
        ShapeDrawable toChange = bars.get(catName);
        Rect b = toChange.getBounds();
        int top = (int) ((1 - percent) * DrUTILS.CAT_MAX_HEIGHT);
        toChange.setBounds(b.left, top, b.right, DrUTILS.CAT_MAX_HEIGHT);
        this.invalidate();
    }

    protected void onDraw(Canvas canvas) {
        for(ShapeDrawable sd: greenBars.values()){
            sd.draw(canvas);
        }
        for(ShapeDrawable sd: bars.values()){
            sd.draw(canvas);
        }
        for(TextDrawable name: catNames){
            name.draw(canvas);
        }
    }

}
