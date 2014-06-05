package com.shufudesing.drmb;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import net.danlew.android.joda.ResourceZoneInfoProvider;

/**
 * Created by Sam on 5/8/2014.
 */
public class DrApplication extends Application {
    /**
     * Android application context
     */
    private static Context sContext = null;

    /**
     * Saves current party ID to share between fragments/activities *
     */
    private static String sSelectedPartyId = null;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DrApplication.sContext = getApplicationContext();
        // Initialize the singletons so their instances
        // are bound to the application process.
        Log.i("Application", "initializing singletons...");
        initSingletons();
        ResourceZoneInfoProvider.init(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    /**
     * Initializes any singleton classes
     */
    protected void initSingletons() {
        // Initialize App DDP State Singleton
        MyDDP.initInstance(DrApplication.sContext);
    }

    /**
     * Gets application context
     *
     * @return Android application context
     */
    public static Context getAppContext() {
        return DrApplication.sContext;
    }
}