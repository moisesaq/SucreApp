package com.apaza.moises.sucreapp.di.places;

import com.apaza.moises.sucreapp.di.app.scope.ScopeActivity;
import com.apaza.moises.sucreapp.ui.places.PlacesActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ScopeActivity
@Subcomponent(modules = {ModuleActivityPlaces.class, ModuleFragmentPlaces.class})
public interface ComponentActivityPlaces extends AndroidInjector<PlacesActivity>{
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<PlacesActivity>{}
}
