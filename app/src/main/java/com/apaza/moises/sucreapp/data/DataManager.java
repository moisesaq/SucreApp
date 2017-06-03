package com.apaza.moises.sucreapp.data;

import com.apaza.moises.sucreapp.data.network.ContractAPI;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class DataManager implements ContractData{

    @Inject
    public DataManager(ContractAPI contractAPI){

    }

    @Override
    public Observable<List<Place>> getPlaces() {
        return null;
    }
}
