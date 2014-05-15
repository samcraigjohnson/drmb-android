package com.shufudesing.drmb;

import android.content.BroadcastReceiver;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.shufudesing.drmb.Views.CatsView;
import com.shufudesing.drmb.Views.MainView;


public class HomeActivity extends ActionBarActivity {

    private BroadcastReceiver mReceiver;
    private MainView bigCircle;
    private CatsView catsView;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyDDP ddp = MyDDP.getInstance();
        Log.i("HomeActivity", "oncreate...");


        setContentView(R.layout.activity_home);
        layout = (LinearLayout) this.findViewById(R.id.linLayout);
        bigCircle = (MainView) this.findViewById(R.id.mainView);
        catsView = (CatsView) this.findViewById(R.id.catsView);
    }

    public MainView getBigCircle(){
        return bigCircle;
    }

    public CatsView getCatsView() { return catsView; }

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
