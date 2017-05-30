package com.apaza.moises.sucreapp.addplace;

import android.support.annotation.NonNull;

public interface AddPlaceContract {

    interface View{
        void showEmptyPlaceError();
        void showPlaceList();
        void openCamera(String saveTo);
        void showImagePreview(@NonNull String url);
        void showImageError();
    }

    interface Presenter{
        void savePlace(String title, String description);
        void takePicture() throws Exception;
        void imageAvailable();
        void imageCaptureFailed();
    }
}
