package com.apaza.moises.sucreapp.data;

import java.util.HashMap;
import java.util.Map;

public class PlacesServiceApiEndPoint {

    private final static Map<String, Place> DATA;
    static {
        DATA = new HashMap<>();
        addPlace("Place 1", "Description 1");
        addPlace("Place 2", "Description 2");
    }

    public static void addPlace(String title, String description){
        Place place = new Place(title, description);
        DATA.put(place.getId(), place);
    }

    public static Map<String, Place> getPersistedPlaces(){
        return DATA;
    }
}
