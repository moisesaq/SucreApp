package com.apaza.moises.sucreapp.di.places;

import com.apaza.moises.sucreapp.di.app.scope.ScopeFragment;
import com.apaza.moises.sucreapp.ui.places.PlacesFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ScopeFragment
@Subcomponent(modules = { ModuleFragmentPlaces.class })
public interface ComponentFragmentPlaces extends AndroidInjector<PlacesFragment>{
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<PlacesFragment>{}
}
