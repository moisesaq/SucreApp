package com.apaza.moises.sucreapp;

import com.apaza.moises.sucreapp.data.PlaceRepositories;
import com.apaza.moises.sucreapp.data.PlacesRepository;
import com.apaza.moises.sucreapp.data.PlacesServiceApiImpl;
import com.apaza.moises.sucreapp.tools.ImageFile;
import com.apaza.moises.sucreapp.tools.ImageFileImpl;

public class Injection {

    public static ImageFile provideImageFile(){
        return new ImageFileImpl();
    }

    public static PlacesRepository providePlacesRepository(){
        return PlaceRepositories.getInstance(new PlacesServiceApiImpl());
    }
}
