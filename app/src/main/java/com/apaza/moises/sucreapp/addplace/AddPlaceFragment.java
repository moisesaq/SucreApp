package com.apaza.moises.sucreapp.addplace;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.apaza.moises.sucreapp.R;
import com.apaza.moises.sucreapp.data.PlaceRepositories;
import com.apaza.moises.sucreapp.data.PlacesServiceApi;
import com.apaza.moises.sucreapp.data.PlacesServiceApiImpl;

import butterknife.BindView;

public class AddPlaceFragment extends Fragment {

    public static final int REQUEST_CODE_IMAGE_CAPTURE = 10;
    private AddPlaceContract.Presenter mPresenter;
    @BindView(R.id.iv_image_place) protected ImageView mImageView;
    @BindView(R.id.et_title_place) protected EditText etTitle;
    @BindView(R.id.et_description_place) protected EditText etDescription;

    public static AddPlaceFragment newInstance(){
        return new AddPlaceFragment();
    }

    public AddPlaceFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PlacesServiceApiImpl placesServiceApi = new PlacesServiceApiImpl();
        //mPresenter = new AddPlacePresenter(this, PlaceRepositories.getInstance(placesServiceApi));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_place, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
