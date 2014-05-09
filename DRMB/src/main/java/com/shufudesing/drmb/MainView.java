package com.shufudesing.drmb;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by Sam on 5/7/2014.
 */
public class MainView extends View{
    private ShapeDrawable mDrawable;
    private TextDrawable infoText;
    private TextDrawable moneyText;

    public MainView(Context context) {
        super(context);

        DisplayMetrics screen = context.getResources().getDisplayMetrics();

        int circle_size = 500;
        int width = circle_size;
        int height = circle_size;

        int x = (screen.widthPixels/2) - (width/2);
        int y = (screen.heightPixels/2) - height;

        mDrawable = new ShapeDrawable(new OvalShape());
        mDrawable.getPaint().setColor(0xff33CCFF);
        mDrawable.setBounds(x, y, x + width, y + height);

        infoText = new TextDrawable("LEFT TO SPEND", x+(width/3), y+(height/2));
        moneyText = new TextDrawable("$0", x+(width/4), y+(height/4));
        moneyText.setTextSize(50f);
    }

    public void setColor(int hex){
        mDrawable.getPaint().setColor(hex);
        this.invalidate();
    }

    public void setMoneyText(String text){
        moneyText.setText("$"+text);
        this.invalidate();
    }

    protected void onDraw(Canvas canvas) {
        mDrawable.draw(canvas);
        moneyText.draw(canvas);
        infoText.draw(canvas);
    }
}
