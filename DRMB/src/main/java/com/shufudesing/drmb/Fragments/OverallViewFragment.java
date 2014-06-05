package com.shufudesing.drmb.Fragments;



import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keysolutions.ddpclient.android.DDPBroadcastReceiver;
import com.shufudesing.drmb.Collections.Category;
import com.shufudesing.drmb.DrUTILS;
import com.shufudesing.drmb.MyDDP;
import com.shufudesing.drmb.R;
import com.shufudesing.drmb.Views.CatsView;
import com.shufudesing.drmb.Views.MainView;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class OverallViewFragment extends Fragment{

    private CatsView catsView;
    private MainView circle;
    private final String TAG = "OverallViewFragment";
    private boolean hasInfo = false;

    public OverallViewFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
    }

    public void onResume(){
        super.onResume();

    }

    public void onPause(){
        super.onPause();

    }
    public void setHasInfo(boolean b){
        hasInfo = b;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_overall_view_fragment, container, false);
        catsView = (CatsView) v.findViewById(R.id.catsView);
        circle =  (MainView) v.findViewById(R.id.mainView);
        Log.v(TAG, "VIEW INFLATED");
        if(hasInfo){
            updateInfo();
        }
        return v;
    }

    public void updateInfo(){
        Double left = MyDDP.getInstance().getAmountLeft(DrUTILS.MONTH);
        Double total = MyDDP.getInstance().getTotalBudget();
        double percent = (total.doubleValue() - left.doubleValue()) / total.doubleValue();
        Log.v(TAG, "percent spent: " + percent + "left:" + left.doubleValue());

        String newText = left.toString();
        Log.v(TAG, "AMOUNT OF MONEY LEFT TO SPEND THIS MONTH: " + newText);
        circle.setMoneyText(newText);
        circle.setPercent(new Float(percent));

        Map<String, Category> cats = MyDDP.getInstance().getCategories();
        for(Map.Entry<String, Category> cat: cats.entrySet()){
            catsView.setHeight(cat.getKey(), cat.getValue().getPercentSpent());
        }
    }
}
