package com.shufudesing.drmb.Fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.shufudesing.drmb.Collections.Category;
import com.shufudesing.drmb.DrUTILS;
import com.shufudesing.drmb.MyDDP;
import com.shufudesing.drmb.R;
import com.shufudesing.drmb.Views.CatsView;
import com.shufudesing.drmb.Views.MainView;
import com.shufudesing.drmb.Views.SavingsView;

import java.util.Map;

/**
 * Fragment that contains the main info circle, cats
 * bar graph, and savings graph
 *
 */
public class OverallViewFragment extends BaseDrFragment{
    private CatsView catsView;
    private MainView circleDash;
    private SavingsView savingsView;

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
        circleDash =  (MainView) v.findViewById(R.id.mainView);
        savingsView = (SavingsView) v.findViewById(R.id.savingsView);
        checkChange();
        return v;
    }

    @Override
    public void checkChange(){
        if(hasInfo){
            updateInfo();
            updateSavingsGoal();
        }
    }

    public void updateInfo(){
        for(String dateType: DrUTILS.DATE_TYPES) {
            Double left = MyDDP.getInstance().getAmountLeft(dateType);
            Double total = MyDDP.getInstance().getTotalBudget(dateType) - MyDDP.getInstance().getSaveByDate(dateType);
            double percent = (total - left) / total;
            Log.v(TAG, "DateType: " + dateType+ ", percent spent: " + percent + " left:" + left);
            circleDash.setPercent(dateType, new Float(percent));

            if(dateType.equals(circleDash.getActiveDateType())) {
                String newText = DrUTILS.formatDouble(left);
                circleDash.setMoneyText(newText);
            }
        }

        Map<String, Category> cats = MyDDP.getInstance().getCategories();
        for (Map.Entry<String, Category> cat : cats.entrySet()) {
            catsView.setHeight(cat.getKey(), cat.getValue().getPercentSpent());
        }

    }

    public void updateSavingsGoal(){
        if(MyDDP.getInstance().isSavingsGoalActive()) {
            savingsView.setSavingsInfo(MyDDP.getInstance().getSavingAmountLeft(), MyDDP.getInstance().getMonthsLeft());
            savingsView.setSavingsPercent(MyDDP.getInstance().getPercentSaved());
        }
    }
}
