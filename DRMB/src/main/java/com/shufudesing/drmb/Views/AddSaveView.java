package com.shufudesing.drmb.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

import com.shufudesing.drmb.DrUTILS;
import com.shufudesing.drmb.Listeners.SaveClickListener;

/**
 * Created by Sam on 5/12/2014.
 */
public class AddSaveView extends LinearLayout{

    private Button save, add;

    public AddSaveView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        save = new Button(context);
        add = new Button(context);
        save.setText("Save");
        add.setText("Add");
        save.setTextColor(DrUTILS.GREEN);
        add.setTextColor(DrUTILS.RED);
        save.setOnClickListener(new SaveClickListener(context));
        this.setOrientation(LinearLayout.HORIZONTAL);
        this.addView(add);
        this.addView(save);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
        params.weight = 1;

        save.setLayoutParams(params);
        add.setLayoutParams(params);
    }

    @Override
    protected void onDraw(Canvas c){
        save.draw(c);
        add.draw(c);
    }

}
