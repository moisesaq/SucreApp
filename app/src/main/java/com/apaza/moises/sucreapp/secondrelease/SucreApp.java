package com.apaza.moises.sucreapp.secondrelease;

import android.app.Application;
import android.content.Context;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class SucreApp extends Application{

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        setupConfigFacebook();
        context = getApplicationContext();
    }

    private void setupConfigFacebook(){
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    public static Context getContext(){
        return context;
    }
}
