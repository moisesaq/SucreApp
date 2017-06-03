package com.apaza.moises.sucreapp.di.app;

import com.apaza.moises.sucreapp.ui.base.AppBase;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = ModuleApp.class)
public interface ComponentApp {
    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(AppBase appBase);
        ComponentApp build();
    }

    //void inject(AppBase appBase);
}
