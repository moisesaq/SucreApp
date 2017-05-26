package com.apaza.moises.sucreapp.data;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlacesServiceApiImpl implements PlacesServiceApi{

    private static final int SERVICE_LATENCY_MILLIS = 2000;
    private static final Map<String, Place> PLACES_SERVICE_DATA = PlacesServiceApiEndPoint.getPersistedPlaces();

    @Override
    public void getAllPlaces(final PlacesServiceCallback<List<Place>> callback) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Place> places = new ArrayList<>(PLACES_SERVICE_DATA.values());
                callback.onLoaded(places);
            }
        }, SERVICE_LATENCY_MILLIS);
    }

    @Override
    public void getPlace(String placeId, PlacesServiceCallback<Place> callback) {

    }

    @Override
    public void savePlace(Place place) {

    }
}
