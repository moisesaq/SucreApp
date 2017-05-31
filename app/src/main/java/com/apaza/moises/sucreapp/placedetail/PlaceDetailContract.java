package com.apaza.moises.sucreapp.placedetail;

import android.support.annotation.NonNull;

public interface PlaceDetailContract {
    interface View{
        void setProgressIndicator(boolean active);
        void showMissingPlace();
        void hideTitle();
        void showTitle(String title);
        void showImage(String imageUrl);
        void hideImage();
        void hideDescription();
        void showDescription(String description);
    }

    interface Presenter{
        void openPlace(@NonNull String placeId);
    }
}
