package com.shufudesing.drmb.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.shufudesing.drmb.DrDDPManager;
import com.shufudesing.drmb.DrUTILS;
import com.shufudesing.drmb.Drawables.PolyDrawable;
import com.shufudesing.drmb.Fragments.BaseDrFragment;
import com.shufudesing.drmb.Fragments.BasePopupFragment;
import com.shufudesing.drmb.Fragments.HistoryFragment;
import com.shufudesing.drmb.Fragments.OutlookFragment;
import com.shufudesing.drmb.Listeners.DrawerItemClickListener;
import com.shufudesing.drmb.Fragments.OverallViewFragment;
import com.shufudesing.drmb.MyDDP;
import com.shufudesing.drmb.R;
import com.shufudesing.drmb.Views.CatsView;
import com.shufudesing.drmb.Views.MainView;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends ActionBarActivity {

    private BroadcastReceiver mReceiver;
    private DrawerLayout mDrawer;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mToggle;
    private final String TAG = "Home Activity";
    private BaseDrFragment[] fragments;
    private Fragment currentFragment;
    private int currPos, oldPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "oncreate...");
        setContentView(R.layout.activity_home);

        fragments = new BaseDrFragment[DrUTILS.DRAWER_ITEMS.length];
        BaseDrFragment mainFragment = new OverallViewFragment();
        BaseDrFragment historyFragment = new HistoryFragment();
        BaseDrFragment outlook = new OutlookFragment();
        fragments[DrUTILS.OVERVIEW] = mainFragment;
        fragments[DrUTILS.HISTORY_POS] = historyFragment;
        fragments[DrUTILS.OUTLOOK] = outlook;

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, fragments[DrUTILS.OVERVIEW], DrUTILS.DRAWER_ITEMS[DrUTILS.OVERVIEW])
                    .addToBackStack(DrUTILS.DRAWER_ITEMS[DrUTILS.OVERVIEW])
                    .commit();
            currentFragment = fragments[DrUTILS.OVERVIEW];
            currPos = DrUTILS.OVERVIEW;
        }

        mDrawer = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) this.findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, DrUTILS.DRAWER_ITEMS));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener(this));
        mDrawerList.setItemChecked(0, true);

        mToggle = new ActionBarDrawerToggle(this, mDrawer, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close){
            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
                getActionBar().setTitle("");
                invalidateOptionsMenu();
            }
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle("Dr. MB");
                invalidateOptionsMenu();
            }
        };

        mDrawer.setDrawerListener(mToggle);
        getActionBar().setTitle("");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setBackgroundDrawable(new ColorDrawable(DrUTILS.RED));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawer.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.v(TAG, "On resume home activity... loading stack...");

        MyDDP.getInstance().loadStack();
        MyDDP.getInstance().connectIfNeeded();
        for(BaseDrFragment bdf: fragments){
            //bdf.checkChange();
        }

        mReceiver = new DrDDPManager(MyDDP.getInstance(), this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MyDDP.getInstance().saveStack();
        if (mReceiver != null) {
            // unhook the receiver
            LocalBroadcastManager.getInstance(this)
                    .unregisterReceiver(mReceiver);
            mReceiver = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Log.v(TAG,  "adding transaction pressed");
            Intent intent = new Intent(this, AddTransactionActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void removeAll(){
        for(String tag: DrUTILS.DRAWER_ITEMS){
            Fragment f = getFragmentManager().findFragmentByTag(tag);
            if(f != null){
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Log.v(TAG, "removing : " + tag);
                ft.remove(f).commit();
            }
        }
    }

    public void selectItem(int pos){
        if(pos != currPos) {
            oldPos = Integer.valueOf(currPos);
            currPos = pos;
            Fragment fragment = null;
            String title = "";
            FragmentManager fManager = getFragmentManager();

            if (pos == DrUTILS.OVERVIEW) {
                //fManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                removeAll();
                fragment = fragments[DrUTILS.OVERVIEW];
                FragmentTransaction ft = fManager.beginTransaction();

                Log.v(TAG, "Adding overview back");
                ft.add(R.id.container, fragment, DrUTILS.DRAWER_ITEMS[DrUTILS.OVERVIEW])
                        .addToBackStack(DrUTILS.DRAWER_ITEMS[DrUTILS.OVERVIEW])
                        .commit();
            }
            else if (pos == DrUTILS.HISTORY_POS) {
                removeAll();
                fragment = fragments[DrUTILS.HISTORY_POS];

                fManager.beginTransaction()
                        .add(R.id.container, fragment, DrUTILS.DRAWER_ITEMS[DrUTILS.HISTORY_POS])
                        .addToBackStack(DrUTILS.DRAWER_ITEMS[DrUTILS.HISTORY_POS])
                        .commit();
                }
            else if (pos == DrUTILS.SETTINGS) {
                }
            else if (pos == DrUTILS.TIPS) {
                }
            else if (pos == DrUTILS.OUTLOOK) {
                    fragment = fragments[DrUTILS.OUTLOOK];
                    fManager.beginTransaction()
                            .add(R.id.container, fragment, DrUTILS.DRAWER_ITEMS[DrUTILS.OUTLOOK])
                            .addToBackStack(DrUTILS.DRAWER_ITEMS[DrUTILS.OUTLOOK])
                            .commit();
                }

            currentFragment = fragment;
            getActionBar().setTitle(title);
            mDrawerList.setItemChecked(pos, true);
        }
        Log.v(TAG, "Done ADDING FRAGMENT");
        mDrawer.closeDrawer(mDrawerList);
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getFragmentManager();
        if(fm.getBackStackEntryCount() > 0 && currPos != DrUTILS.OVERVIEW){
            if(currPos == DrUTILS.OUTLOOK){
                fm.popBackStackImmediate();
                currPos = Integer.valueOf(oldPos);
                //Fragment f = fm.findFragmentByTag(DrUTILS.DRAWER_ITEMS[DrUTILS.OUTLOOK]);
                //fm.beginTransaction().remove(f).commit();
            }
            else {
                selectItem(DrUTILS.OVERVIEW);
            }
        }
        else{
            super.onBackPressed();
        }
    }

    public void expensesUpdate(){
        fragments[DrUTILS.OVERVIEW].setHasInfo(true);
        fragments[DrUTILS.OVERVIEW].updateInfo();
        fragments[DrUTILS.HISTORY_POS].setHasInfo(true);
        fragments[DrUTILS.HISTORY_POS].updateInfo();
    }

    public void savingsGoalUpdate(){
        Log.v(TAG, "updating savings goal");
        ((OverallViewFragment)fragments[DrUTILS.OVERVIEW]).updateSavingsGoal();
    }

}
