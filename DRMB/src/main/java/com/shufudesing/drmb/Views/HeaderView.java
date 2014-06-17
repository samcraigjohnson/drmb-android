package com.shufudesing.drmb.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.shufudesing.drmb.DrUTILS;
import com.shufudesing.drmb.Drawables.TextDrawable;
import com.shufudesing.drmb.R;

/**
 * Created by Sam on 6/17/2014.
 */
public class HeaderView extends View{
    private TextDrawable headerText;
    private ShapeDrawable headerRect;
    private String text = "";
    public int position;

    public HeaderView(Context context) {
        super(context);
        init();
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.HeaderView,
                0, 0);

        try {
            text = a.getString(R.styleable.HeaderView_header_text);
            position = a.getInt(R.styleable.HeaderView_view_position, 0);
        } finally {
            a.recycle();
        }
        init();
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        DisplayMetrics screen = getContext().getResources().getDisplayMetrics();
        int width = screen.widthPixels;
        headerRect = new ShapeDrawable(new RectShape());
        headerRect.setBounds(0,0,width, DrUTILS.CAT_HEADER_HEIGHT);
        headerRect.getPaint().setColor(DrUTILS.BLUE);

        headerText = new TextDrawable(text, DrUTILS.CAT_HEADER_HEIGHT/2 + 10);
        headerText.setTextSize(40f);
    }

    @Override
    protected void onDraw(Canvas c){
        headerRect.draw(c);
        headerText.draw(c);
    }
}
