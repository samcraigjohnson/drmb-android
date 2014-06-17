package com.shufudesing.drmb.Fragments;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.shufudesing.drmb.Collections.Transaction;
import com.shufudesing.drmb.DrUTILS;
import com.shufudesing.drmb.MyDDP;
import com.shufudesing.drmb.R;
import com.shufudesing.drmb.TransactionListAdapter;
import com.shufudesing.drmb.Views.CategoryHistoryView;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends BaseDrFragment{
   // private Spinner spinner;
    private ListView transactionView;
    private List<Transaction> trans = new ArrayList<Transaction>();
    private BroadcastReceiver mReceiver;
    private final String TAG = "HistoryFragment";
    private int pos = DrUTILS.BY_CAT;
    private ViewGroup container;

    public HistoryFragment() {}
    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}
    @Override
    public void onAttach(Activity activity) {super.onAttach(activity);}
    @Override
    public void onDetach() {super.onDetach();}

    @Override
    public void onPause(){
        super.onPause();
        if(mReceiver != null){
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mReceiver);
            mReceiver = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        checkChange();
        this.container = container;
        // Inflate the layout for this fragment
        final View v =  inflater.inflate(R.layout.fragment_history, container, false);
        transactionView = (ListView) v.findViewById(R.id.transaction_list);


        ArrayAdapter<Transaction> listAdapter = new TransactionListAdapter(v.getContext(), trans);
        transactionView.setAdapter(listAdapter);

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals(DrUTILS.CHANGED_FILTER)){
                    //TODO change filter
                    Bundle b = intent.getExtras();
                    int pos = (Integer) b.get("POS");
                    Log.v(TAG, "switched to position: " + pos);
                    changePosition(v, pos);

                }
            }
        };

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mReceiver, new IntentFilter(DrUTILS.CHANGED_FILTER));

        return v;
    }

    @Override
    public void updateInfo() {
        trans = MyDDP.getInstance().getTransactionsBy(pos);
    }

    private void changePosition(View v, int pos){
        this.pos = pos;
        if(hasInfo){
            if(pos != DrUTILS.BY_CAT) {
                trans = MyDDP.getInstance().getTransactionsBy(pos);
                ArrayAdapter<Transaction> listAdapter = new TransactionListAdapter(v.getContext(), trans);
                transactionView.setAdapter(listAdapter);
            }
            else{
                container.removeView(transactionView);
                container.addView(new CategoryHistoryView(container.getContext()));
            }
        }
        else{
            Log.v(TAG, "no info");
        }
    }
}
