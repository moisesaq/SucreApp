package com.apaza.moises.sucreapp.places;

import android.support.annotation.NonNull;

import com.apaza.moises.sucreapp.data.Place;

import java.util.List;

public class PlacesContract {

    interface View{
        void setProgressIndicator(boolean active);
        void showPlaces(List<Place> places);
        void showAddPlaces();
        void showNoteDetailUi(String placeId);
    }

    interface Presenter{
        void loadPlaces(boolean forceUpdate);
        void addNewPlace();
        void openPlaceDetails(@NonNull Place requestPlace);
    }
}
