package com.shufudesing.drmb.Views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.shufudesing.drmb.DrUTILS;
import com.shufudesing.drmb.Drawables.PolyDrawable;
import com.shufudesing.drmb.Drawables.TextDrawable;

/**
 * Created by Sam on 6/13/2014.
 */
public class HistoryFilterView extends View implements View.OnTouchListener {

    private final int X = 0, Y = 0;
    private ShapeDrawable filterRec;
    private TextDrawable filterText;
    private PolyDrawable leftTri, rightTri;
    private int pos = 0;
    private int width;

    public HistoryFilterView(Context context) {
        super(context);
        init();
    }

    public HistoryFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HistoryFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        DisplayMetrics screen = getContext().getResources().getDisplayMetrics();
        width = screen.widthPixels;
        filterRec = new ShapeDrawable(new RectShape());
        filterRec.getPaint().setColor(DrUTILS.GRAY);
        filterRec.setBounds(X, Y, screen.widthPixels, Y+DrUTILS.FILTER_BAR_SIZE);

        filterText = new TextDrawable(DrUTILS.DISPLAY_FILTERS[pos], width/2, (DrUTILS.FILTER_BAR_SIZE/2) + (DrUTILS.FILTER_TRI_OFFSET/2), Color.WHITE);
        filterText.setTextAlign(Paint.Align.CENTER);
        filterText.setTextSize(50f);

        float[] ltx = {60f, 60f, DrUTILS.FILTER_TRI_OFFSET};
        float[] lty = { DrUTILS.FILTER_TRI_OFFSET, DrUTILS.FILTER_BAR_SIZE-DrUTILS.FILTER_TRI_OFFSET, DrUTILS.FILTER_BAR_SIZE/2};

        float[] rtx = {width-60f, width-60f, width-DrUTILS.FILTER_TRI_OFFSET};

        leftTri = new PolyDrawable(ltx, lty);
        rightTri = new PolyDrawable(rtx, lty);

        this.setOnTouchListener(this);
    }

    protected void onDraw(Canvas c){
        filterRec.draw(c);
        filterText.draw(c);

        leftTri.draw(c);
        rightTri.draw(c);
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            float x = event.getX();
            if(x < width*.33){
                pos--;
                if(pos < 0){
                    pos = DrUTILS.DISPLAY_FILTERS.length - 1;
                }
            }
            else if(x > width*.66){
                pos++;
                if(pos >= DrUTILS.DISPLAY_FILTERS.length){
                    pos = 0;
                }
            }

            filterText.setText(DrUTILS.DISPLAY_FILTERS[pos]);
            Log.v("ONTOUCH", "Calling correct width: " + (width - 140) + ": " + DrUTILS.DISPLAY_FILTERS[pos]);
            filterText.correctWidth(50f, width - 140);
            Intent t = new Intent(DrUTILS.CHANGED_FILTER);
            t.putExtra("POS", pos);
            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(t);

            this.invalidate();

        }
        return true;
    }
}
