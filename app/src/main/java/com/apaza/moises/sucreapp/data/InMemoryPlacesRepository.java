package com.apaza.moises.sucreapp.data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.google.common.collect.ImmutableList;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class InMemoryPlacesRepository implements PlacesRepository{

    private final PlacesServiceApi mPlacesServiceApi;

    @VisibleForTesting
    List<Place> mCachedPlaces;

    public InMemoryPlacesRepository(@NonNull PlacesServiceApi placesServiceApi){
        this.mPlacesServiceApi = checkNotNull(placesServiceApi);
    }

    @Override
    public void getPlaces(@NonNull final LoadPlacesCallback callback) {
        checkNotNull(callback);
        if(mCachedPlaces == null){
            mPlacesServiceApi.getAllPlaces(new PlacesServiceApi.PlacesServiceCallback<List<Place>>() {
                @Override
                public void onLoaded(List<Place> places) {
                    mCachedPlaces = ImmutableList.copyOf(places);
                    callback.onPlacesLoaded(mCachedPlaces);
                }
            });
        }else {
            callback.onPlacesLoaded(mCachedPlaces);
        }
    }

    @Override
    public void getPlace(@NonNull String placeId, @NonNull final GetPlaceCallback callback) {
        checkNotNull(placeId);
        checkNotNull(callback);

        mPlacesServiceApi.getPlace(placeId, new PlacesServiceApi.PlacesServiceCallback<Place>() {
            @Override
            public void onLoaded(Place places) {
                callback.onPlaceLoaded(places);
            }
        });
    }

    @Override
    public void savePlace(@NonNull Place place) {
        checkNotNull(place);
        mPlacesServiceApi.savePlace(place);
        refreshData();
    }

    @Override
    public void refreshData() {
        mCachedPlaces = null;
    }
}
