package com.shufudesing.drmb;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by Sam on 5/7/2014.
 */
public class MainView extends View{
    private ShapeDrawable mDrawable;

    public MainView(Context context) {
        super(context);

        DisplayMetrics screen = context.getResources().getDisplayMetrics();

        int width = 300;
        int height = 300;
        int x = (screen.widthPixels/2) - (width/2);
        int y = (screen.heightPixels/2) - height;

        mDrawable = new ShapeDrawable(new OvalShape());
        mDrawable.getPaint().setColor(0xff33CCFF);
        mDrawable.setBounds(x, y, x + width, y + height);
    }

    protected void onDraw(Canvas canvas) {
        mDrawable.draw(canvas);
    }
}
