package com.shufudesing.drmb.Drawables;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.shufudesing.drmb.DrUTILS;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Sam on 5/15/2014.
 */
public class DateDrawable extends Drawable{

    private RectF oval;
    private int sx,sy,ex,ey;
    private int tx, ty;
    private String time, text;
    private final Paint paint;
    private final Paint textPaint;
    private final String TAG = "Date Drawable";

    public DateDrawable(String time, Rect ov){
        this.paint = new Paint();
        this.textPaint = new Paint();

        oval = new RectF(ov);
        this.time = time;
        sx = (int)oval.centerX();
        ex = (int)oval.centerX();
        ey = (int)oval.centerY();
        sy = (int)oval.centerY();

        updateTime(time);

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);
        paint.setAntiAlias(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setPathEffect(new CornerPathEffect(10));

        textPaint.setColor(Color.WHITE);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(35f);

    }

    public void updateTime(String time){
        this.time = time;
        Calendar c = Calendar.getInstance();
        double angle = 0;

        if(time.equals(DrUTILS.MONTH)){
            int maxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            int today = c.get(Calendar.DAY_OF_MONTH);
            angle = (((double)today)/((double)maxDay) * 360) - 90d;
            text = new SimpleDateFormat("MMM d").format(c.getTime());
        }

        int sRadius = (int) (DrUTILS.CIRCLE_SIZE + DrUTILS.RING_SIZE)/2 - DrUTILS.LINE_LENGTH;
        int eRadius = (int) (DrUTILS.CIRCLE_SIZE + DrUTILS.RING_SIZE)/2 + DrUTILS.LINE_LENGTH;

        Log.v(TAG, "Radii:" + sRadius+":"+eRadius);

        double radians = angle * (Math.PI/180d);
        Log.v(TAG, "Radians:" + radians);
        sx += (int) (sRadius * Math.cos(radians));
        sy += (int) (sRadius * Math.sin(radians));

        ex += (int) (eRadius * Math.cos(radians));
        ey += (int) (eRadius * Math.sin(radians));

        updateText(angle);
        Log.v(TAG, "ovalwidth:" + oval.width()/2+ "sx:sy ex:ey"+  sx+":"+sy+":"+ex+":"+ey);
    }

    private void updateText(double angle){
        if(angle < 0){
            tx = (int) oval.right;
            ty = (int) oval.top;
            textPaint.setTextAlign(Paint.Align.LEFT);
        }
        else if(angle < 90){
            tx = (int) oval.right;
            ty = (int) oval.bottom;
            textPaint.setTextAlign(Paint.Align.LEFT);
        }
        else if(angle < 180){
            tx = (int) oval.left;
            ty = (int) oval.bottom;
            textPaint.setTextAlign(Paint.Align.RIGHT);
        }
        else {
            tx = (int) oval.left;
            ty = (int) oval.top;
            textPaint.setTextAlign(Paint.Align.CENTER);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Path p  = new Path();
        p.moveTo(sx,sy);
        p.lineTo(ex,ey);
        p.lineTo(tx-30,ty);
        canvas.drawPath(p, paint);
        canvas.drawText(text, tx, ty, textPaint);
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
