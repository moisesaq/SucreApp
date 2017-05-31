package com.apaza.moises.sucreapp.addplace;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.apaza.moises.sucreapp.Injection;
import com.apaza.moises.sucreapp.R;
import com.apaza.moises.sucreapp.data.PlaceRepositories;
import com.apaza.moises.sucreapp.data.PlacesServiceApi;
import com.apaza.moises.sucreapp.data.PlacesServiceApiImpl;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkState;

public class AddPlaceFragment extends Fragment implements AddPlaceContract.View{

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
        mPresenter = new AddPlacePresenter(Injection.providePlacesRepository(), this, Injection.provideImageFile());
        FloatingActionButton fab = (FloatingActionButton)getActivity().findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.savePlace(etTitle.getText().toString(), etDescription.getText().toString());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_place, container, false);
        ButterKnife.bind(this, root);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_place, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.take_photo:
                try{
                    mPresenter.takePicture();
                }catch (IOException e){
                    if(getView() != null){
                        Snackbar.make(getView(), R.string.error_take_picture, Snackbar.LENGTH_SHORT).show();
                    }
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showEmptyPlaceError() {
        Snackbar.make(etTitle, R.string.place_cannot_be_empty, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showPlaceList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void openCamera(String saveTo) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getContext().getPackageManager()) != null){
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(saveTo));
            startActivityForResult(takePictureIntent, REQUEST_CODE_IMAGE_CAPTURE);
        }else{
            Snackbar.make(etTitle, R.string.cannot_connect_to_camera, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showImagePreview(@NonNull String url) {
        checkState(TextUtils.isEmpty(url), "imageUrl cannot be null or empty");
        mImageView.setVisibility(View.VISIBLE);

        Picasso.with(getContext())
                .load(url)
                .placeholder(R.mipmap.ic_lock_grey600_24dp)
                .error(R.mipmap.ic_launcher)
                .into(mImageView);
    }

    @Override
    public void showImageError() {
        Snackbar.make(etTitle, R.string.cannot_connect_to_camera, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_IMAGE_CAPTURE && Activity.RESULT_OK == resultCode){
            mPresenter.imageAvailable();
        }else{
            mPresenter.imageCaptureFailed();
        }
    }
}
