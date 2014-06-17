package com.shufudesing.drmb.Fragments;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.shufudesing.drmb.Collections.Category;
import com.shufudesing.drmb.Collections.Transaction;
import com.shufudesing.drmb.DrUTILS;
import com.shufudesing.drmb.Listeners.HeaderClickListener;
import com.shufudesing.drmb.MyDDP;
import com.shufudesing.drmb.R;
import com.shufudesing.drmb.TransactionListAdapter;
import com.shufudesing.drmb.Views.HeaderView;
import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends BaseDrFragment{
   // private Spinner spinner;
    private ListView transactionView;
    private List<Transaction> trans = new ArrayList<Transaction>();
    private List<HeaderView> headers = new ArrayList<HeaderView>();
    private BroadcastReceiver mReceiver;
    private final String TAG = "HistoryFragment";
    private int pos = DrUTILS.BY_CAT;
    private ViewGroup container;
    private HeaderView currHeader;

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
    public void onResume(){
        super.onResume();
        if(mReceiver == null){
            setReceiver();
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
        LinearLayout ll = (LinearLayout) v.findViewById(R.id.history_linear);

        headers.add((HeaderView)v.findViewById(R.id.fun_header));
        headers.add((HeaderView)v.findViewById(R.id.food_header));
        headers.add((HeaderView)v.findViewById(R.id.bills_header));
        headers.add((HeaderView)v.findViewById(R.id.home_header));
        headers.add((HeaderView)v.findViewById(R.id.transit_header));

        currHeader = headers.get(0);

        for(HeaderView hv: headers){
            hv.setOnClickListener(new HeaderClickListener(ll, transactionView, this));
        }
        ArrayAdapter<Transaction> listAdapter = new TransactionListAdapter(v.getContext(), trans);
        transactionView.setAdapter(listAdapter);
        setReceiver();
        return v;
    }

    private void setReceiver(){
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals(DrUTILS.CHANGED_FILTER)){
                    //TODO change filter
                    Bundle b = intent.getExtras();
                    int pos = (Integer) b.get("POS");
                    Log.v(TAG, "switched to position: " + pos);
                    changePosition(pos);

                }
            }
        };
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mReceiver, new IntentFilter(DrUTILS.CHANGED_FILTER));
    }

    @Override
    public void updateInfo() {
        if(pos != DrUTILS.BY_CAT) {
            trans = MyDDP.getInstance().getTransactionsBy(pos);
        }
        else{
            if(currHeader != null) {
                Category c = MyDDP.getInstance().getCat(DrUTILS.CAT_DB_NAMES[currHeader.position - 1]);
                trans = c.getTransactions();
            }
            else{
                Category c = MyDDP.getInstance().getCat(DrUTILS.CAT_DB_NAMES[0]);
                trans = c.getTransactions();
            }

        }
    }

    public void setCurrHeader(HeaderView hv){
        currHeader = hv;
    }

    private void changePosition(int pos){
        this.pos = pos;
        if(hasInfo){
            if(pos != DrUTILS.BY_CAT) {
                for(HeaderView hv: headers){
                    hv.setVisibility(View.GONE);
                }
                trans = MyDDP.getInstance().getTransactionsBy(pos);
                ArrayAdapter<Transaction> listAdapter = new TransactionListAdapter(container.getContext(), trans);
                transactionView.setAdapter(listAdapter);
            }
            else{
                for(HeaderView hv: headers){
                    hv.setVisibility(View.VISIBLE);
                }
                Category c = MyDDP.getInstance().getCat(DrUTILS.CAT_DB_NAMES[currHeader.position - 1]);
                trans = c.getTransactions();
                ArrayAdapter<Transaction> listAdapter = new TransactionListAdapter(container.getContext(), trans);
                transactionView.setAdapter(listAdapter);
               Log.v(TAG, "adding header view..");
            }
        }
        else{
            Log.v(TAG, "no info");
        }
    }
}
