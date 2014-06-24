package com.shufudesing.drmb.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.shufudesing.drmb.DrUTILS;

/**
 * Created by Sam on 6/12/2014.
 */
public class BasePopupView extends View{

    private ShapeDrawable popRect;
    private final Paint linePaint;
    protected int leftX, topY, rightX, bottomY;

    public BasePopupView(Context context) {
        super(context);
        linePaint = new Paint();
        init();
    }

    public BasePopupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        linePaint = new Paint();
        init();
    }

    public BasePopupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        linePaint = new Paint();
        init();
    }

    protected void init(){
        this.setBackgroundColor(Color.TRANSPARENT);
        popRect = new ShapeDrawable(new RectShape());
        DisplayMetrics screen = getContext().getResources().getDisplayMetrics();
        leftX = DrUTILS.POP_MARGIN;
        topY = DrUTILS.POP_MARGIN;
        rightX = screen.widthPixels - DrUTILS.POP_MARGIN;
        bottomY = screen.heightPixels - (DrUTILS.POP_MARGIN*3);

        popRect.setBounds(leftX, topY, rightX, bottomY);
        popRect.getPaint().setColor(Color.WHITE);
        popRect.getPaint().setAlpha(240);

        linePaint.setColor(DrUTILS.GRAY);
        linePaint.setStrokeWidth(5f);
        linePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas c){
        popRect.draw(c);
        float lX = rightX - DrUTILS.X_LINE_LENGTH;
        float rX = rightX - DrUTILS.X_TOP_OFFSET;
        float tY = topY + DrUTILS.X_TOP_OFFSET;
        float bY = topY + DrUTILS.X_LINE_LENGTH;

        c.drawLine(rX, tY, lX, bY, linePaint);
        c.drawLine(lX, tY, rX, bY, linePaint);
    }
}
