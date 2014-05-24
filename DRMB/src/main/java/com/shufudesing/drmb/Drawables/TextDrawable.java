package com.shufudesing.drmb.Drawables;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;


/**
 * Created by Sam on 5/8/2014.
 */
public class TextDrawable extends Drawable{

    private String text;
    private final Paint paint;
    private int x,y;

    public TextDrawable(String text, int y){
        this.text = text;
        this.paint = new Paint();
        this.y = y;
        this.x = -1;

        paint.setColor(Color.WHITE);
        paint.setTextSize(24f);
        paint.setAntiAlias(true);
        paint.setFakeBoldText(true);
        //paint.setShadowLayer(6f, 0, 0, Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);

    }

    public TextDrawable(String text, int x, int y, int color){
        this.text = text;
        this.paint = new Paint();
        this.y = y;
        this.x = x;

        paint.setColor(color);
        paint.setTextSize(24f);
        paint.setAntiAlias(true);
        paint.setFakeBoldText(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.LEFT);
    }

    public void setTextSize(float size){
        paint.setTextSize(size);
    }

    public void setText(String text){
        this.text = text;
    }

    public void setTextAlign(Paint.Align a){
        paint.setTextAlign(a);
    }

    @Override
    public void draw(Canvas canvas) {
        if(x == -1)
            canvas.drawText(text,canvas.getWidth()/2,y,paint);
        else
            canvas.drawText(text,x,y,paint);
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
