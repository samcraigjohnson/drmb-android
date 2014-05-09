package com.shufudesing.drmb;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.keysolutions.ddpclient.android.DDPBroadcastReceiver;
import com.keysolutions.ddpclient.android.DDPStateSingleton;


public class HomeActivity extends ActionBarActivity {

    private BroadcastReceiver mReceiver;
    private MainView bigCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyDDP ddp = MyDDP.getInstance();
        Log.i("HomeActivity", "oncreate...");

        bigCircle = new MainView(this);
        setContentView(bigCircle);
    }

    public MainView getBigCircle(){
        return bigCircle;
    }

    @Override
    protected void onResume(){
        super.onResume();
        mReceiver = new DrDDPManager(MyDDP.getInstance(), this);
        MyDDP.getInstance().connectIfNeeded();
    }

    @Override
    public void onPause() {
        super.onPause();

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
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
