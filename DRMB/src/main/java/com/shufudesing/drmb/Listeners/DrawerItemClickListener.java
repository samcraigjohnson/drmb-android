package com.shufudesing.drmb.Listeners;

import android.app.Fragment;
import android.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.shufudesing.drmb.Activities.HomeActivity;
import com.shufudesing.drmb.DrUTILS;
import com.shufudesing.drmb.R;
import com.shufudesing.drmb.Fragments.HistoryFragment;

/**
 * Created by Sam on 6/3/2014.
 */
public class DrawerItemClickListener implements ListView.OnItemClickListener {

    private final String TAG = "DrawerItemClickListener";
    private HomeActivity home;

    public DrawerItemClickListener(HomeActivity h){
        super();
        home = h;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
       home.selectItem(pos);
    }


}
