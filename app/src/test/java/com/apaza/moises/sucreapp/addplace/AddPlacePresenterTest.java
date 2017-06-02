package com.apaza.moises.sucreapp.addplace;

import com.apaza.moises.sucreapp.data.Place;
import com.apaza.moises.sucreapp.data.PlacesRepository;
import com.apaza.moises.sucreapp.tools.ImageFile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddPlacePresenterTest {

    @Mock
    private PlacesRepository mPlacesRepository;

    @Mock
    private AddPlaceContract.View mAddPlaceView;

    @Mock
    private ImageFile mImageFile;

    private AddPlacePresenter mAddPlacePresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mAddPlacePresenter = new AddPlacePresenter(mPlacesRepository, mAddPlaceView, mImageFile);
    }

    @Test
    public void savePlaceToRepository_showSuccessMessageUi(){
        mAddPlacePresenter.savePlace("New Place Title", "Some Place Description");

        verify(mPlacesRepository).savePlace(any(Place.class));
        verify(mAddPlaceView).showPlaceList();
    }

    @Test
    public void saveNote_enptyNoteShowsErrorUi(){
        mAddPlacePresenter.savePlace("", "");

        verify(mAddPlaceView).showEmptyPlaceError();
    }

    @Test
    public void takePicture_CreateFileAndOpenCamera() throws IOException{
        mAddPlacePresenter.takePicture();

        verify(mImageFile).create(anyString(), anyString());
        verify(mImageFile).getPath();
        verify(mAddPlaceView).openCamera(anyString());
    }

    @Test
    public void imageAvailable_SavesImageAndUpdateUiWithThumbnail(){
        String imageUrl = "path/to/file";
        when(mImageFile.exists()).thenReturn(true);
        when(mImageFile.getPath()).thenReturn(anyString());

        mAddPlacePresenter.imageAvailable();

        verify(mAddPlaceView).showImagePreview(contains(imageUrl));
    }

    @Test
    public void noImageAvailable_ShowErrorUi(){
        mAddPlacePresenter.imageCaptureFailed();

        verify(mAddPlaceView).showImageError();
        verify(mImageFile).delete();
    }

    @After
    public void tearDown() throws Exception {

    }

}