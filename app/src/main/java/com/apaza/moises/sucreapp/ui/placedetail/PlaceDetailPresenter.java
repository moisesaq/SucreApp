package com.apaza.moises.sucreapp.ui.placedetail;

import android.support.annotation.NonNull;

import com.apaza.moises.sucreapp.data.Place;
import com.apaza.moises.sucreapp.data.PlacesRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class PlaceDetailPresenter implements PlaceDetailContract.Presenter{

    private final PlacesRepository mPlacesRepository;
    private final PlaceDetailContract.View mPlaceDetailView;

    public PlaceDetailPresenter(@NonNull PlacesRepository placesRepository,
                                @NonNull PlaceDetailContract.View placeDetailView){
        mPlacesRepository = checkNotNull(placesRepository, "PlacesRepositoryCannot be null");
        mPlaceDetailView = checkNotNull(placeDetailView, "placeDetailView cannot be null");
    }

    @Override
    public void openPlace(@NonNull String placeId) {
        if(null == placeId || placeId.isEmpty()){
            mPlaceDetailView.showMissingPlace();
            return;
        }

        mPlaceDetailView.setProgressIndicator(true);
        mPlacesRepository.getPlace(placeId, new PlacesRepository.GetPlaceCallback() {
            @Override
            public void onPlaceLoaded(Place place) {
                mPlaceDetailView.setProgressIndicator(false);
                if(null == place){
                    mPlaceDetailView.showMissingPlace();
                }else{
                    showPlace(place);
                }
            }
        });
    }

    private void showPlace(Place place) {
        String title = place.getTitle();
        String description = place.getDescription();
        String imageUrl = place.getImageUrl();

        if(title != null && title.isEmpty()){
            mPlaceDetailView.hideTitle();
        }else {
            mPlaceDetailView.showTitle(title);
        }

        if(description != null && description.isEmpty()){
            mPlaceDetailView.hideDescription();
        }else{
            mPlaceDetailView.showDescription(description);
        }

        if(imageUrl != null){
            mPlaceDetailView.showImage(imageUrl);
        }else{
            mPlaceDetailView.hideImage();
        }
    }
}
