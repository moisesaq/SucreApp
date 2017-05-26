package com.apaza.moises.sucreapp.data;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class PlaceRepositories {

    private static PlacesRepository repository;
    private PlaceRepositories(){

    }

    public synchronized static PlacesRepository getInstance(@NonNull PlacesServiceApi placesServiceApi){
        checkNotNull(placesServiceApi);
        if(repository == null){
            repository = new InMemoryPlacesRepository(placesServiceApi);
        }
        return repository;
    }
}
