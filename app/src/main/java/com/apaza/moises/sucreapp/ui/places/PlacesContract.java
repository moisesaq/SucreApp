package com.apaza.moises.sucreapp.ui.places;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.apaza.moises.sucreapp.data.Place;

import java.util.List;

public class PlacesContract {

    public interface ActivityView{
        void startAddPlaceActivity();
        void startDetailPlaceActivity(Place place);
        void addFragment(Fragment fragment);
    }

    public interface ActivityPresenter{
        void onActivityCreated();
        void setFragmentPresenter(FragmentPresenter fragmentPresenter);
        void onPlaceClicked(Place place);
        void openAddPlaceActivity();
    }

    public interface FragmentView {
        void setProgressIndicator(boolean active);
        void showPlaces(List<Place> places);
        Fragment getFragment();
    }

    public interface FragmentPresenter {
        void onFragmentStarted();
        void loadPlaces(boolean forceUpdate);
        void addNewPlace();
        void openPlaceDetail(@NonNull Place requestPlace);
    }
}
