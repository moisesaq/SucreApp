package com.apaza.moises.sucreapp;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class SucreApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        setupConfigFacebook();
    }

    private void setupConfigFacebook(){
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}
