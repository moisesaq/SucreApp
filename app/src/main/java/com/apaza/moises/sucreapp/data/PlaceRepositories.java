package com.apaza.moises.sucreapp.data;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class PlaceRepositories {

    private static PlaceRepositories repositories;
    private PlaceRepositories(){

    }

    public synchronized static PlaceRepositories getInstance(@NonNull PlacesServiceApi placesServiceApi){
        checkNotNull(placesServiceApi);
        if(repositories == null){
            repositories = new PlaceRepositories();
        }
        return repositories;
    }
}
