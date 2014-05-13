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
    private int y;

    public TextDrawable(String text, int y){
        this.text = text;
        this.paint = new Paint();
        this.y = y;

        paint.setColor(Color.WHITE);
        paint.setTextSize(24f);
        paint.setAntiAlias(true);
        paint.setFakeBoldText(true);
        //paint.setShadowLayer(6f, 0, 0, Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);

    }

    public void setTextSize(float size){
        paint.setTextSize(size);
    }

    public void setText(String text){
        this.text = text;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(text,canvas.getWidth()/2,y,paint);
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