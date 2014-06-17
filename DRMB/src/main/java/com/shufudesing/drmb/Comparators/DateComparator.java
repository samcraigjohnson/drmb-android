package com.shufudesing.drmb.Comparators;

import com.shufudesing.drmb.Collections.Transaction;

import java.util.Comparator;

/**
 * Created by Sam on 6/16/2014.
 */
public class DateComparator implements Comparator<Transaction>{

    @Override
    public int compare(Transaction t, Transaction t2) {
        if(t.getDate().isBefore(t.getDate().toInstant())){
            return 1;
        }
        else{
            return -1;
        }
    }
}
