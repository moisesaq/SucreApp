package com.apaza.moises.sucreapp.data;

import java.util.List;

public interface PlacesServiceApi {

    interface PlacesServiceCallback<T>{
        void onLoaded(T places);
    }

    void getAllPlaces(PlacesServiceCallback<List<Place>> callback);

    void getPlace(String placeId, PlacesServiceCallback<Place> callback);

    void savePlace(Place place);
}
