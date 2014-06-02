package com.shufudesing.drmb.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.shufudesing.drmb.DrDDPManager;
import com.shufudesing.drmb.MyDDP;
import com.shufudesing.drmb.R;
import com.shufudesing.drmb.Views.CatsView;
import com.shufudesing.drmb.Views.MainView;
import com.shufudesing.drmb.Views.SpendCategoryView;

/**
 * Created by Sam on 5/15/2014.
 */
public class AddTransactionActivity  extends ActionBarActivity{

    private final String TAG = "Add Transaction";
    private EditText amount,location,description;
    private SpendCategoryView categoryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_transaction);
        amount = (EditText) this.findViewById(R.id.amount);
        location = (EditText) this.findViewById(R.id.location);
        description = (EditText) this.findViewById(R.id.description);
        categoryView = (SpendCategoryView) this.findViewById(R.id.categoryView);
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
        inflater.inflate(R.menu.add_transaction, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void submitExpense(){
        double amountVal = Double.parseDouble(amount.getText().toString());
        String descripVal = description.getText().toString();
        String locationVal = location.getText().toString();
        String category = categoryView.getSelectedCategory();
        Log.v(TAG, amountVal+":"+descripVal+":"+locationVal+":"+category);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.submit_transaction) {
            submitExpense();
        }

        return super.onOptionsItemSelected(item);
    }
}
