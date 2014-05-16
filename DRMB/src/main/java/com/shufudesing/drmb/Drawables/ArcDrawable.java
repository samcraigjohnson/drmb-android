package com.shufudesing.drmb.Drawables;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import com.shufudesing.drmb.DrUTILS;

/**
 * Created by Sam on 5/12/2014.
 */
public class ArcDrawable extends Drawable {

    private float startAngle, sweepAngle;
    private final Paint paint;
    private RectF oval;

    public ArcDrawable(Rect r){
        this.paint = new Paint();

        oval = new RectF(r);
        startAngle = -90f;
        sweepAngle = 90f;

        paint.setColor(DrUTILS.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }

    public void setSweep(float sweep)
    {
        this.sweepAngle = sweep;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawArc(oval, startAngle, sweepAngle, true, paint);
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
        return PixelFormat.OPAQUE;
    }
}
