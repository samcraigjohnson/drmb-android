package com.shufudesing.drmb.Drawables;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.shufudesing.drmb.DrUTILS;

/**
 * Created by Sam on 5/14/2014.
 */
public class DrButtonDrawable extends Drawable {

    private final Paint paint, linePaint;
    private String text;
    private int x,y,width,height;
    private boolean selected;
    private RectF rectfBound;
    private final String TAG = "BUTTON DRAWABLE";

    public DrButtonDrawable(String text, boolean selected, int x, int y, int width, int height){
        this.text = text;
        this.paint = new Paint();
        this.linePaint = new Paint();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height= height;
        this.selected = selected;

        paint.setTextSize(DrUTILS.CAT_BTN_TEXT_SIZE);
        paint.setAntiAlias(true);
        paint.setFakeBoldText(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        updateColors();

        linePaint.setColor(Color.BLACK);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        rectfBound = new RectF(x,y,x+width,y+height);
    }

    public boolean isSelected(){
        return selected;
    }

    public void setSelected(boolean s){
        selected = s;
        updateColors();
    }

    private void updateColors(){
        if(selected)
            paint.setColor(DrUTILS.RED);
        else
            paint.setColor(DrUTILS.GRAY);
    }

    public boolean contains(float x, float y){
        return rectfBound.contains(x, y);
    }

    @Override
    public void draw(Canvas canvas) {
        double texty = y+(height/2)+(DrUTILS.CAT_BTN_TEXT_SIZE/2);
        canvas.drawText(text, x+width/2, (int)texty, paint);
        canvas.drawRect(x,y,x+width,y+height,linePaint);
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
