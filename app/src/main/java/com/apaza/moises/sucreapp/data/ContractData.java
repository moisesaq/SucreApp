package com.apaza.moises.sucreapp.data;

import java.util.List;

import io.reactivex.Observable;

public interface ContractData{
    Observable<List<Place>> getPlaces();
}
