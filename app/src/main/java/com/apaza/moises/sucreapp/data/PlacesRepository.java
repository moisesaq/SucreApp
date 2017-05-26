package com.apaza.moises.sucreapp.data;

import android.support.annotation.NonNull;

import java.util.List;

public interface PlacesRepository {

    interface LoadPlacesCallback{
        void onPlacesLoaded(List<Place> places);
    }

    interface GetPlaceCallback{
        void onPlaceLoaded(Place place);
    }

    void getPlaces(@NonNull LoadPlacesCallback callback);

    void getPlace(@NonNull String placeId, @NonNull GetPlaceCallback callback);

    void savePlace(@NonNull Place place);

    void refreshData();
}
