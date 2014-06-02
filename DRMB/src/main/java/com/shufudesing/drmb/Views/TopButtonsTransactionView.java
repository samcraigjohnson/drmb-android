package com.shufudesing.drmb.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.shufudesing.drmb.DrUTILS;
import com.shufudesing.drmb.Drawables.DrButtonDrawable;

/**
 * Created by Sam on 6/2/2014.
 */
public class TopButtonsTransactionView extends View implements View.OnTouchListener{

    private DrButtonDrawable income, expense;

    public TopButtonsTransactionView(Context context, AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics screen = context.getResources().getDisplayMetrics();
        int width = screen.widthPixels/2;

        income = new DrButtonDrawable("INCOME", false, 0, 0, width, DrUTILS.CAT_BTN_HEIGHT);
        income.setSelectedColor(DrUTILS.GREEN);
        expense = new DrButtonDrawable("EXPENSE", true, width, 0, width, DrUTILS.CAT_BTN_HEIGHT);

        this.setOnTouchListener(this);
    }


    @Override
    protected void onDraw(Canvas c){
        income.draw(c);
        expense.draw(c);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if(income.contains(x,y)){
            income.setSelected(true);
            expense.setSelected(false);
            //TODO change fields when income is selected
        }
        else if(expense.contains(x,y)){
            expense.setSelected(true);
            income.setSelected(false);
            //TODO change fields when expense is selected (remove categories)
        }
        return true;
    }
}
