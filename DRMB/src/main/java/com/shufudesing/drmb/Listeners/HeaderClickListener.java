package com.shufudesing.drmb.Listeners;


import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.shufudesing.drmb.Collections.Category;
import com.shufudesing.drmb.Collections.Transaction;
import com.shufudesing.drmb.DrUTILS;
import com.shufudesing.drmb.Fragments.HistoryFragment;
import com.shufudesing.drmb.MyDDP;
import com.shufudesing.drmb.TransactionListAdapter;
import com.shufudesing.drmb.Views.HeaderView;

import java.util.List;


/**
 * Created by Sam on 6/17/2014.
 */
public class HeaderClickListener implements View.OnClickListener {

    private LinearLayout ll;
    private ListView transactions;
    private HistoryFragment fragment;
    private final String TAG = "HeaderClickListener";

    public HeaderClickListener(LinearLayout ll, ListView trans, HistoryFragment hf){
        this.ll = ll;
        this.transactions = trans;
        fragment = hf;
    }

    @Override
    public void onClick(View view) {
        HeaderView hv = (HeaderView) view;
        Category c = MyDDP.getInstance().getCat(DrUTILS.CAT_DB_NAMES[hv.position-1]);
        List<Transaction> trans = c.getTransactions();
        ll.removeView(transactions);

        Log.v(TAG, "Number of transactions: " + trans.size());

        ArrayAdapter<Transaction> listAdapter = new TransactionListAdapter(ll.getContext(), trans);
        transactions.setAdapter(listAdapter);

        ll.addView(transactions, hv.position+1);

        fragment.setCurrHeader(hv);
    }
}
