package com.apaza.moises.sucreapp.ui.places;

import android.support.annotation.NonNull;

import com.apaza.moises.sucreapp.data.Place;
import com.apaza.moises.sucreapp.data.PlacesRepository;

import java.util.List;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

public class PlacesFragmentPresenter implements PlacesContract.FragmentPresenter {

    private final PlacesContract.ActivityPresenter mActivityPresenter;
    private final PlacesContract.FragmentView mPlacesFragmentView;
    private final PlacesRepository mPlacesRepository;

    @Inject
    public PlacesFragmentPresenter(PlacesContract.ActivityPresenter activityPresenter, @NonNull PlacesContract.FragmentView placesFragmentView, @NonNull PlacesRepository placesRepository){
        mActivityPresenter = activityPresenter;
        mPlacesFragmentView = checkNotNull(placesFragmentView);
        mPlacesRepository = checkNotNull(placesRepository);
    }

    /*PRESENTER LISTENER*/
    @Override
    public void onFragmentStarted() {
        setPresenter();
    }

    private void setPresenter(){
        mActivityPresenter.setFragmentPresenter(this);
    }

    @Override
    public void loadPlaces(boolean forceUpdate) {
        mPlacesFragmentView.setProgressIndicator(true);
        if(forceUpdate){
            mPlacesRepository.refreshData();
        }

        mPlacesRepository.getPlaces(new PlacesRepository.LoadPlacesCallback() {
            @Override
            public void onPlacesLoaded(List<Place> places) {
                mPlacesFragmentView.setProgressIndicator(false);
                mPlacesFragmentView.showPlaces(places);
            }
        });
    }

    @Override
    public void addNewPlace() {
        mActivityPresenter.openAddPlaceActivity();
    }

    @Override
    public void openPlaceDetail(@NonNull Place requestPlace) {
        checkNotNull(requestPlace, "place can not be null");
        mActivityPresenter.onPlaceClicked(requestPlace);
    }
}
