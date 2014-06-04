package com.shufudesing.drmb.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by Sam on 6/2/2014.
 */
public class PriceInputView extends EditText {

    private EditText input;
    private Paint p;

    public PriceInputView(Context c){
        super(c);
        init();
    }
    public PriceInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PriceInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    private void init(){
        p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.FILL);
        p.setStrokeWidth(10f);
    }

    public double getAmount(){
        return Double.parseDouble(this.getText().toString());
    }

    @Override
    public void onDraw(Canvas c){
        super.onDraw(c);
        c.drawRect(10,10,150,150, p);
    }
}
