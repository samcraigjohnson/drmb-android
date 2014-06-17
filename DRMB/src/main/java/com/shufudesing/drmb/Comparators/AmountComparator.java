package com.shufudesing.drmb.Comparators;

import com.shufudesing.drmb.Collections.Transaction;
import com.shufudesing.drmb.Drawables.PolyDrawable;

import java.util.Comparator;

/**
 * Created by Sam on 6/16/2014.
 */
public class AmountComparator implements Comparator<Transaction> {
    @Override
    public int compare(Transaction t, Transaction t2) {
        if(t.getAmount() > t2.getAmount()){
            return 1;
        }
        else if(t.getAmount() < t2.getAmount()){
            return -1;
        }
        return 0;
    }
}
