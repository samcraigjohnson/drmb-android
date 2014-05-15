package com.shufudesing.drmb.Drawables;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

/**
 * Created by Sam on 5/14/2014.
 */
public class DrButtonDrawable extends Drawable {

    private final Paint paint;
    private String text;

    public DrButtonDrawable(String text){
        this.text = text;
        this.paint = new Paint();
    }
    @Override
    public void draw(Canvas canvas) {

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
