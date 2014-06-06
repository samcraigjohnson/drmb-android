package com.shufudesing.drmb.Fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.shufudesing.drmb.Collections.Transaction;
import com.shufudesing.drmb.MyDDP;
import com.shufudesing.drmb.R;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends BaseDrFragment {
    private Spinner spinner;
    private ListView transactionView;
    private List<Transaction> trans = new ArrayList<Transaction>();

    public HistoryFragment() {}
    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}
    @Override
    public void onAttach(Activity activity) {super.onAttach(activity);}
    @Override
    public void onDetach() {super.onDetach();}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        checkChange();
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_history, container, false);
        spinner = (Spinner) v.findViewById(R.id.cat_spinner);
        transactionView = (ListView) v.findViewById(R.id.transaction_list);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(v.getContext(), R.array.cats_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_1, (List) trans);
        transactionView.setAdapter(listAdapter);
        return v;
    }

    @Override
    public void updateInfo() {
        trans = MyDDP.getInstance().getTransactions();
    }
}
