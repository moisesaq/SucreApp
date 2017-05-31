package com.apaza.moises.sucreapp.addplace;

import android.support.annotation.NonNull;

import com.apaza.moises.sucreapp.data.Place;
import com.apaza.moises.sucreapp.data.PlacesRepository;
import com.apaza.moises.sucreapp.tools.ImageFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddPlacePresenter implements AddPlaceContract.Presenter{

    @NonNull
    private final PlacesRepository mPlacesRepository;
    @NonNull
    private final AddPlaceContract.View mAddPlaceView;
    @NonNull
    private final ImageFile mImageFile;

    public AddPlacePresenter(@NonNull PlacesRepository placesRepository,
                             @NonNull AddPlaceContract.View addPlaceView,
                             @NonNull ImageFile imageFile){
        mPlacesRepository = placesRepository;
        mAddPlaceView = addPlaceView;
        mImageFile = imageFile;
    }

    @Override
    public void savePlace(String title, String description) {
        String imageUrl = null;
        if(mImageFile.exists()){
            imageUrl = mImageFile.getPath();
        }
        Place newPlace = new Place(title, description, imageUrl);
        if(newPlace.isEmpty()){
            mAddPlaceView.showEmptyPlaceError();
        }else{
            mPlacesRepository.savePlace(newPlace);
            mAddPlaceView.showPlaceList();
        }
    }

    @Override
    public void takePicture() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        mImageFile.create(imageFileName, ".jpg");
        mAddPlaceView.openCamera(mImageFile.getPath());
    }

    @Override
    public void imageAvailable() {
        if(mImageFile.exists()){
            mAddPlaceView.showImagePreview(mImageFile.getPath());
        }else{
            imageCaptureFailed();
        }
    }

    @Override
    public void imageCaptureFailed() {
        captureFailed();
    }

    private void captureFailed(){
        mImageFile.delete();
        mAddPlaceView.showImageError();
    }
}
