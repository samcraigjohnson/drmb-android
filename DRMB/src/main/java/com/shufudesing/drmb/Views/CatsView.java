package com.shufudesing.drmb.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.shufudesing.drmb.DrUTILS;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 5/12/2014.
 */
public class CatsView extends View {

    private List<ShapeDrawable> bars = new ArrayList<ShapeDrawable>();
    private static final String TAG = "CATS VIEW";

    public CatsView(Context context) {
        super(context);

        DisplayMetrics screen = context.getResources().getDisplayMetrics();

        int currX = 0;
        int padding = 10;
        int width = ((screen.widthPixels)/ DrUTILS.NUM_CATS) - padding;
        int bottom = 100;

        Log.v(TAG, "width: "+ width + " NUMCATS: " + DrUTILS.NUM_CATS);

        for(int inx = 0; inx < DrUTILS.NUM_CATS; inx++){
            Log.v(TAG, "indx: " + inx);
            ShapeDrawable newD = new ShapeDrawable(new RectShape());
            newD.getPaint().setColor(DrUTILS.RED);
            newD.setBounds(currX+ padding, 10, currX + width, bottom);
            Log.v(TAG, "left: " + (currX) + ", top: 10, right: " + (currX+width) + ", bottom: " +bottom);
            bars.add(newD);
            currX += width;
        }
    }

    protected void onDraw(Canvas canvas) {
        for(ShapeDrawable sd: bars){
            sd.draw(canvas);
        }
    }

}
