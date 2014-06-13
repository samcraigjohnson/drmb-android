package com.shufudesing.drmb.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.shufudesing.drmb.R;
import com.shufudesing.drmb.Views.BasePopupView;

/**
 * Created by Sam on 6/12/2014.
 */
public class BasePopupFragment extends BaseDrFragment {

    protected LinearLayout ll;

    public BasePopupFragment() { }
    @Override
    public void onCreate(Bundle savedInstanceState){super.onCreate(savedInstanceState);}
    @Override
    public void onResume(){
        super.onResume();
    }
    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.pop_up_layout, container, false);
        ll = (LinearLayout) v.findViewById(R.id.pop_up_ll);
        ll.addView(new BasePopupView(v.getContext()));
        return v;
    }


    @Override
    public void updateInfo() {

    }
}
