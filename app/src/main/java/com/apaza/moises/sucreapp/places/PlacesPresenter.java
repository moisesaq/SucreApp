package com.apaza.moises.sucreapp.places;

import android.support.annotation.NonNull;

import com.apaza.moises.sucreapp.data.Place;
import com.apaza.moises.sucreapp.data.PlacesRepository;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class PlacesPresenter implements PlacesContract.Presenter{

    private PlacesContract.View mPlacesView;
    private PlacesRepository mPlacesRepository;

    public PlacesPresenter(@NonNull PlacesContract.View placesView, @NonNull PlacesRepository placesRepository){
        mPlacesView = checkNotNull(placesView);
        mPlacesRepository = checkNotNull(placesRepository);
    }

    @Override
    public void loadPlaces(boolean forceUpdate) {
        mPlacesView.setProgressIndicator(true);
        if(forceUpdate){
            mPlacesRepository.refreshData();
        }

        mPlacesRepository.getPlaces(new PlacesRepository.LoadPlacesCallback() {
            @Override
            public void onPlacesLoaded(List<Place> places) {
                mPlacesView.setProgressIndicator(false);
                mPlacesView.showPlaces(places);
            }
        });
    }

    @Override
    public void addNewPlace() {
        mPlacesView.showAddPlaces();
    }

    @Override
    public void openPlaceDetails(@NonNull Place requestPlace) {
        checkNotNull(requestPlace, "place can not be null");
        mPlacesView.showNoteDetailUi(requestPlace.getId());
    }
}
