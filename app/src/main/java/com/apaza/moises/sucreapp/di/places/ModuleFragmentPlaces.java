package com.apaza.moises.sucreapp.di.places;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.apaza.moises.sucreapp.data.Place;
import com.apaza.moises.sucreapp.data.PlacesRepository;
import com.apaza.moises.sucreapp.di.app.scope.ScopeFragment;
import com.apaza.moises.sucreapp.ui.places.PlacesActivity;
import com.apaza.moises.sucreapp.ui.places.PlacesAdapter;
import com.apaza.moises.sucreapp.ui.places.PlacesContract;
import com.apaza.moises.sucreapp.ui.places.PlacesFragment;
import com.apaza.moises.sucreapp.ui.places.PlacesFragmentPresenter;

import java.util.ArrayList;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module
public abstract class ModuleFragmentPlaces {

    @Binds
    @IntoMap
    @FragmentKey(PlacesFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> bindFragmentMainInjectorFactory(ComponentFragmentPlaces.Builder builder);

    @Provides
    @ScopeFragment
    static PlacesContract.FragmentPresenter providePlacesFragmentPresenter(PlacesContract.ActivityPresenter activityPresenter, PlacesContract.FragmentView placesFragmentView, PlacesRepository placesRepository){
        return new PlacesFragmentPresenter(activityPresenter, placesFragmentView, placesRepository);
    }

    @Provides
    @ScopeFragment
    static RecyclerView.LayoutManager provideLinearLayoutManager(PlacesActivity activity){
        return new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
    }

    @Provides
    @ScopeFragment
    static PlacesAdapter providePlacesAdapter(){
        return new PlacesAdapter(new ArrayList<Place>());
    }
}
