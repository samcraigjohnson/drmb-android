package com.shufudesing.drmb.Drawables;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import com.shufudesing.drmb.DrUTILS;

/**
 * Created by Sam on 6/16/2014.
 */
public class PolyDrawable extends Drawable {
    private final Paint paint;
    private Path path;
    private boolean isLeft;

    public PolyDrawable(float[] xs, float[] ys){
        paint = new Paint();

        paint.setColor(DrUTILS.GREEN);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        path = new Path();
        path.moveTo(xs[0], ys[0]);

        for(int i = 1; i < xs.length; i++){
            path.lineTo(xs[i], ys[i]);
        }
        path.close();
    }

    @Override
    public void draw(Canvas c){
        c.drawPath(path, paint);
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
