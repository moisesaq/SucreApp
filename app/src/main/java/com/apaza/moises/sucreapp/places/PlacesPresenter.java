package com.apaza.moises.sucreapp.places;

import android.support.annotation.NonNull;

import com.apaza.moises.sucreapp.data.Place;
import com.apaza.moises.sucreapp.data.PlacesRepository;

public class PlacesPresenter implements PlacesContract.Presenter{

    private PlacesContract.View mPlacesView;
    private PlacesRepository mPlacesRepository;

    public PlacesPresenter(@NonNull PlacesContract.View placesView, @NonNull PlacesRepository placesRepository){
        mPlacesView = placesView;
        mPlacesRepository = placesRepository;
    }

    @Override
    public void loadPlaces(boolean forceUpdate) {
        mPlacesView.setProgressIndicator(true);
        if(forceUpdate){
            mPlacesRepository.refreshData();
        }


    }

    @Override
    public void addNewPlace() {

    }

    @Override
    public void openPlaceDetails(@NonNull Place requestPlace) {

    }
}
