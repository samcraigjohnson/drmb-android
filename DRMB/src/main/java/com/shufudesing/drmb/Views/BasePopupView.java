package com.shufudesing.drmb.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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

    public BasePopupView(Context context) {
        super(context);
        init();
    }

    public BasePopupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BasePopupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        this.setBackgroundColor(Color.TRANSPARENT);
        popRect = new ShapeDrawable(new RectShape());
        DisplayMetrics screen = getContext().getResources().getDisplayMetrics();
        popRect.setBounds(DrUTILS.POP_MARGIN, DrUTILS.POP_MARGIN, screen.widthPixels - DrUTILS.POP_MARGIN, screen.heightPixels - (DrUTILS.POP_MARGIN*3));
        popRect.getPaint().setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas c){
        popRect.draw(c);
    }
}
