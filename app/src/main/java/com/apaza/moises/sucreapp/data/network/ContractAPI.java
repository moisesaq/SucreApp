package com.apaza.moises.sucreapp.data.network;

import com.apaza.moises.sucreapp.data.Place;

import java.util.List;

import io.reactivex.Observable;

public interface ContractAPI {

    Observable<List<Place>> getPlaces();
}
