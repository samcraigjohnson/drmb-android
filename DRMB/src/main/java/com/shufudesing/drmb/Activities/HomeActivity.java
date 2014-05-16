package com.shufudesing.drmb.Activities;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.shufudesing.drmb.DrDDPManager;
import com.shufudesing.drmb.MyDDP;
import com.shufudesing.drmb.R;
import com.shufudesing.drmb.Views.CatsView;
import com.shufudesing.drmb.Views.MainView;


public class HomeActivity extends ActionBarActivity {

    private BroadcastReceiver mReceiver;
    private MainView bigCircle;
    private CatsView catsView;
    private LinearLayout layout;

    private final String TAG = "Home Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyDDP ddp = MyDDP.getInstance();
        Log.i(TAG, "oncreate...");


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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Log.v(TAG,  "adding transaction pressed");
            Intent intent = new Intent(this, AddTransactionActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
