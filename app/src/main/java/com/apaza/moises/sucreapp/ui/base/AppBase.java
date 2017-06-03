package com.apaza.moises.sucreapp.ui.base;

import android.app.Activity;
import android.app.Application;

import com.apaza.moises.sucreapp.di.app.DaggerComponentApp;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasDispatchingActivityInjector;

public class AppBase extends Application implements HasDispatchingActivityInjector{

    @Inject DispatchingAndroidInjector<Activity> injector;

    @Override
    public void onCreate() {
        super.onCreate();
        setUpDependencyInject();
    }

    private void setUpDependencyInject(){
        DaggerComponentApp
                .builder()
                .application(this)
                .build();
                //.inject(this);
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return injector;
    }
}
