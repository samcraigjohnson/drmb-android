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
 * Fragment that contains the main info circle, cats
 * bar graph, and savings graph
 *
 */
public class OverallViewFragment extends BaseDrFragment{
    private CatsView catsView;
    private MainView circle;
    private final String TAG = "OverallViewFragment";

    public OverallViewFragment() { }
    @Override
    public void onCreate(Bundle savedInstanceState){super.onCreate(savedInstanceState);}
    @Override
    public void onResume(){ super.onResume();}
    @Override
    public void onPause(){ super.onPause();}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_overall_view_fragment, container, false);
        catsView = (CatsView) v.findViewById(R.id.catsView);
        circle =  (MainView) v.findViewById(R.id.mainView);
        Log.v(TAG, "VIEW INFLATED");
        checkChange();
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
