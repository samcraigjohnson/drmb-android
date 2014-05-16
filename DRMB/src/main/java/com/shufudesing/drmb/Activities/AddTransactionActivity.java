package com.shufudesing.drmb.Activities;

import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
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

/**
 * Created by Sam on 5/15/2014.
 */
public class AddTransactionActivity  extends ActionBarActivity{

    private final String TAG = "Add Transaction";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_transaction);
    }


    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
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
            Log.v(TAG, "adding transaction pressed");
            //TODO start intent to add transaction
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
