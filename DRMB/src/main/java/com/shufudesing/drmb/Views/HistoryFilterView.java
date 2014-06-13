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
import com.shufudesing.drmb.Drawables.TextDrawable;

/**
 * Created by Sam on 6/13/2014.
 */
public class HistoryFilterView extends View {

    private final int X = 0, Y = 0;
    private ShapeDrawable filterRec;
    private TextDrawable filterText;

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
        filterRec = new ShapeDrawable(new RectShape());
        filterRec.getPaint().setColor(DrUTILS.GRAY);
        filterRec.setBounds(X, Y, screen.widthPixels, Y+DrUTILS.FILTER_BAR_SIZE);

        filterText = new TextDrawable("B Y  C A T E G O R Y", screen.widthPixels/2, DrUTILS.FILTER_BAR_SIZE/2, Color.WHITE);
        filterText.setTextAlign(Paint.Align.CENTER);
        filterText.setTextSize(50f);
    }

    protected void onDraw(Canvas c){
        filterRec.draw(c);
        filterText.draw(c);
    }


}
