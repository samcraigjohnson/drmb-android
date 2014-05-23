package com.shufudesing.drmb.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.shufudesing.drmb.DrUTILS;
import com.shufudesing.drmb.Drawables.DrButtonDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 5/23/2014.
 */
public class SpendCategoryView extends View{

    private List<DrButtonDrawable> cats = new ArrayList<DrButtonDrawable>();

    public SpendCategoryView(Context context, AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics screen = context.getResources().getDisplayMetrics();
        int width = screen.widthPixels/2;
        int row = 0;
        int indx = 1;
        boolean left = true;
        for(String name: DrUTILS.CAT_NAMES){
            if(left) {
                DrButtonDrawable dbd = new DrButtonDrawable(name, false, 0, row * DrUTILS.CAT_BTN_HEIGHT, width, DrUTILS.CAT_BTN_HEIGHT);
            }
            else{
                DrButtonDrawable dbd = new DrButtonDrawable(name, false, width, row * DrUTILS.CAT_BTN_HEIGHT, width, DrUTILS.CAT_BTN_HEIGHT);
            }

            if(indx % 2 == 0){
                row++;
            }
            indx++;
            left = !left;
        }
    }

    protected void onDraw(Canvas c){
        for(DrButtonDrawable db: cats){
            db.draw(c);
        }
    }
}
