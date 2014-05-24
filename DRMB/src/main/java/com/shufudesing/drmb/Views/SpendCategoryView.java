package com.shufudesing.drmb.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.shufudesing.drmb.DrUTILS;
import com.shufudesing.drmb.Drawables.DrButtonDrawable;
import com.shufudesing.drmb.Drawables.TextDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 5/23/2014.
 */
public class SpendCategoryView extends View implements View.OnTouchListener{

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        for(DrButtonDrawable cat: cats){
            if(cat.contains(x,y)){
                if(!cat.isSelected()){
                    cat.setSelected(true);
                    this.invalidate();
                }
                else
                    break;
            }
            else if(cat.isSelected()){
                cat.setSelected(false);
            }
        }

        return true;
    }

    private List<DrButtonDrawable> cats = new ArrayList<DrButtonDrawable>();
    private TextDrawable categoryText;

    public SpendCategoryView(Context context, AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics screen = context.getResources().getDisplayMetrics();
        int width = screen.widthPixels/2;
        int row = 1;
        int indx = 1;
        boolean left = true;
        for(String name: DrUTILS.CAT_NAMES){
            DrButtonDrawable dbd = null;
            if(left) {
                dbd = new DrButtonDrawable(name, false, 0, row * DrUTILS.CAT_BTN_HEIGHT, width, DrUTILS.CAT_BTN_HEIGHT);
            }
            else{
                dbd = new DrButtonDrawable(name, false, width, row * DrUTILS.CAT_BTN_HEIGHT, width, DrUTILS.CAT_BTN_HEIGHT);
            }

            if(indx % 2 == 0){
                row++;
            }
            indx++;
            left = !left;
            cats.add(dbd);
        }

        categoryText = new TextDrawable("Category", 0, (int)(DrUTILS.CAT_BTN_TEXT_SIZE * 1.9), DrUTILS.GRAY);
        categoryText.setTextSize(DrUTILS.CAT_BTN_TEXT_SIZE);

        this.setOnTouchListener(this);
    }

    protected void onDraw(Canvas c){
        categoryText.draw(c);
        for(DrButtonDrawable db: cats){
            db.draw(c);
        }
    }
}
