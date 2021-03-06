package com.apaza.moises.sucreapp.di.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.apaza.moises.sucreapp.data.ContractData;
import com.apaza.moises.sucreapp.data.DataManager;
import com.apaza.moises.sucreapp.data.PlaceRepositories;
import com.apaza.moises.sucreapp.data.PlacesRepository;
import com.apaza.moises.sucreapp.data.PlacesServiceApiImpl;
import com.apaza.moises.sucreapp.data.network.ContractAPI;
import com.apaza.moises.sucreapp.di.places.ComponentActivityPlaces;
import com.apaza.moises.sucreapp.ui.base.AppBase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = { ComponentActivityPlaces.class })
public class ModuleApp {

    @Singleton
    @Provides
    Context provideContext(AppBase appBase){
        return appBase.getApplicationContext();
    }

    @Singleton
    @Provides
    SharedPreferences provideSharedPreferences(AppBase appBase){
        return PreferenceManager.getDefaultSharedPreferences(appBase.getApplicationContext());
    }

    @Provides
    @Singleton
    static ContractData provideContractData(ContractAPI contractAPI){
        return new DataManager(contractAPI);
    }

    @Provides
    @Singleton
    static PlacesRepository providePlacesRepository(){
        return PlaceRepositories.getInstance(new PlacesServiceApiImpl());
    }
}
