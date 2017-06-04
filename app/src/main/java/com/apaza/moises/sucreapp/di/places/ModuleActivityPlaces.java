package com.apaza.moises.sucreapp.di.places;

import android.app.Activity;

import com.apaza.moises.sucreapp.di.app.scope.ScopeActivity;
import com.apaza.moises.sucreapp.ui.places.PlacesActivity;
import com.apaza.moises.sucreapp.ui.places.PlacesActivityPresenter;
import com.apaza.moises.sucreapp.ui.places.PlacesContract;
import com.apaza.moises.sucreapp.ui.places.PlacesFragment;

import dagger.Binds;
import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {ComponentFragmentPlaces.class})
public abstract class ModuleActivityPlaces {

    @Binds
    abstract PlacesContract.ActivityView providePlacesContractActivity(PlacesActivity activity);

    @Binds
    @IntoMap
    @ActivityKey(PlacesActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindActivityPlacesInjectorFactory(ComponentActivityPlaces.Builder builder);

    @Provides
    @ScopeActivity
    static PlacesContract.ActivityPresenter provideActivityPresenter(PlacesContract.ActivityView activityView, Lazy<PlacesContract.FragmentView> fragmentViewLazy){
        return new PlacesActivityPresenter(activityView, fragmentViewLazy);
    }

    @Provides
    @ScopeActivity
    static PlacesContract.FragmentView providePlacesFragmentView(){
        return new PlacesFragment();
    }
}
