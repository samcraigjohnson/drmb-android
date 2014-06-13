package com.shufudesing.drmb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.shufudesing.drmb.Collections.Transaction;

import org.joda.time.format.DateTimeFormat;

import java.util.List;

/**
 * Created by Sam on 6/5/2014.
 */
public class TransactionListAdapter extends ArrayAdapter<Transaction>{

    private final Context context;
    private final List<Transaction> values;

    //TODO create custom transaction view
    public TransactionListAdapter(Context context, List<Transaction> values){
        super(context, R.layout.history_list_item, values);
        this.context = context;
        this.values = values;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.history_list_item, parent, false);
        TextView dateView = (TextView) rowView.findViewById(R.id.dateText);
        TextView descrView = (TextView) rowView.findViewById(R.id.descriptionText);
        TextView amtView = (TextView) rowView.findViewById(R.id.amountText);
        Transaction t = values.get(position);
        //Set based on values
        dateView.setText(DateTimeFormat.forPattern("MM/dd/yy").print(t.getDate()));
        descrView.setText(t.getDescription());
        amtView.setText("$"+DrUTILS.formatDouble(t.getAmount()));

        return rowView;
    }
}
