package com.shufudesing.drmb;

import java.sql.Struct;

/**
 * Created by Sam on 5/9/2014.
 */
public class DrUTILS {

    //Intent contants
    public static final String CHANGED_FILTER = "com.shufuinc.drmb.CHANGED_FILTER";

    //Circle constants
    public static final int CIRCLE_SIZE = 390;
    public static final int RING_SIZE = 35;
    public static final float INFO_TEXT_SIZE = 45f;
    public static final float MONEY_TEXT_SIZE = 75f;
    public static final float CAT_BTN_TEXT_SIZE = 50f;
    public static final int LINE_LENGTH = 30;
    public static final int SCREEN_OFFSET = 800;
    public static final int CIRCLE_OFFSET = 100 + CIRCLE_SIZE + RING_SIZE;

    public static final float ALT_MONEY_TEXT_SIZE = 90f;

    //savings view constants
    public static final int SAVINGS_BAR_SIZE = 225;
    public static final float SAVINGS_TEXT_SIZE = 45f;
    public static final float ALT_SAVINGS_TEXT_SIZE = 35f;

    //history filter bar
    public static final int FILTER_BAR_SIZE = 150;
    public static final int FILTER_TRI_OFFSET = 25;
    public static final String[] DISPLAY_FILTERS = {"B Y  C A T E G O R Y", "B Y  D A T E", "B Y  L O W E S T  A M O U N T", "B Y  H I G H E S T  A M O U N T"};
    public static final int BY_CAT = 0;
    public static final int BY_DATE = 1;
    public static final int BY_LOWEST = 2;
    public static final int BY_HIGHTEST = 3;
    public static final int CAT_HEADER_HEIGHT = 70;


    //popup contants
    public static final int POP_MARGIN = 100;
    public static final int X_TOP_OFFSET = 20;
    public static final int X_LINE_LENGTH = X_TOP_OFFSET + 50;

    //time constants
    public static final String MONTH = "MONTH";
    public static final String WEEK = "WEEK";
    public static final String DAY = "DAY";
    public static final String[] DATE_TYPES = {"DAY", "WEEK", "MONTH"};

    //App Color constants
    public static final int GREEN = 0xff58C45F;
    public static final int RED = 0xffFF7F78;
    public static final int BLUE = 0xff54BBFF;
    public static final int ORANGE = 0xffFFC32B;
    public static final int GRAY = 0xff343436;

    //Category bar constants
    public static final int NUM_CATS = 5;
    public static final int CAT_MAX_HEIGHT = 200;
    public static final String[] CAT_NAMES = {"FUN", "FOOD", "BILLS", "HOME", "TRANSIT"};
    public static final String[] CAT_DB_NAMES = {"fun", "food", "bills", "rent", "trans"};


    public static final int CAT_BTN_HEIGHT = 100;

    public static final String[] DRAWER_ITEMS = {"OVERVIEW", "HISTORY", "OUTLOOK", "TIPS", "SETTINGS"};
    //Positions for drawer items
    public static final int OVERVIEW = 0;
   // public static final int SAVED_LOCATIONS = 1;
    public static final int HISTORY_POS = 1;
    public static final int OUTLOOK = 2;
    public static final int TIPS = 3;
    public static final int SETTINGS = 4;

    //JSON
    public static final String JSON_BUDGET = "budget";
    public static final String JSON_CALL_STACK = "callStack";
    public static final String JSON_METHOD_NAME = "methodName";
    public static final String JSON_METHOD_ARGS= "methodArgs";
    public static final String JSON_FIELDS = "fields";
    public static final String JSON_DOC_ID = "docid";
    public static final String JSON_EXPENSES = "expenses";

    public static String formatDouble(Double d){
        String text = String.format("%.2f",d);
        if(d % 1 == 0){
            int leftInt = (int) d.doubleValue();
            text = String.valueOf(leftInt);
        }
        return text;
    }

}
