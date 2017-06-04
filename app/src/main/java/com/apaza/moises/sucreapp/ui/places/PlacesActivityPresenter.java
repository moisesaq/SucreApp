package com.apaza.moises.sucreapp.ui.places;

import com.apaza.moises.sucreapp.data.Place;

import javax.inject.Inject;

import dagger.Lazy;

public class PlacesActivityPresenter implements PlacesContract.ActivityPresenter{

    private final PlacesContract.ActivityView mActivityView;
    private final Lazy<PlacesContract.FragmentView> mFragmentView;

    private PlacesContract.FragmentPresenter fragmentPlacesPresenter;

    @Inject
    public PlacesActivityPresenter(PlacesContract.ActivityView activityView, Lazy<PlacesContract.FragmentView> fragmentViewLazy){
        mActivityView = activityView;
        mFragmentView = fragmentViewLazy;
    }

    @Override
    public void onActivityCreated() {
        mActivityView.addFragment(mFragmentView.get().getFragment());
    }

    @Override
    public void setFragmentPresenter(PlacesContract.FragmentPresenter fragmentPresenter) {
        fragmentPlacesPresenter = fragmentPresenter;
    }

    @Override
    public void onPlaceClicked(Place place) {
        mActivityView.startDetailPlaceActivity(place);
    }

    @Override
    public void openAddPlaceActivity() {
        mActivityView.startAddPlaceActivity();
    }
}
