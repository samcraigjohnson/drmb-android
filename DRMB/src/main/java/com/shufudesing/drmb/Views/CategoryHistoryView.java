package com.shufudesing.drmb.Views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.shufudesing.drmb.Collections.Transaction;
import com.shufudesing.drmb.DrUTILS;
import com.shufudesing.drmb.Drawables.TextDrawable;
import com.shufudesing.drmb.MyDDP;
import com.shufudesing.drmb.TransactionListAdapter;

import java.util.List;

/**
 * Created by Sam on 6/17/2014.
 */
public class CategoryHistoryView extends LinearLayout {


    private ListView arrayItems;

    public CategoryHistoryView(Context context) {
        super(context);
        init();
    }

    public CategoryHistoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CategoryHistoryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        arrayItems = new ListView(getContext());

        ArrayAdapter<Transaction> listAdapter = new TransactionListAdapter(getContext(), MyDDP.getInstance().getTransactions());
        arrayItems.setAdapter(listAdapter);
        LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 0, 3f);
        this.setLayoutParams(params);
        this.setOrientation(VERTICAL);
        this.addView(new HeaderView(getContext()));
        this.addView(arrayItems);
    }

    public void setTransactions(List<Transaction> trans){
        ArrayAdapter<Transaction> listAdapter = new TransactionListAdapter(getContext(), trans);
        arrayItems.setAdapter(listAdapter);
    }
}
