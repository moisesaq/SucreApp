package com.apaza.moises.sucreapp.placedetail;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apaza.moises.sucreapp.Injection;
import com.apaza.moises.sucreapp.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceDetailFragment extends Fragment implements PlaceDetailContract.View{

    public static final String ARGUMENT_PLACE_ID = "place_id";

    private PlaceDetailContract.Presenter mPresenter;

    @BindView(R.id.iv_image_place) protected ImageView mImageView;
    @BindView(R.id.tv_place_title) protected TextView tvTitle;
    @BindView(R.id.tv_place_description) protected TextView tvDescription;


    public PlaceDetailFragment() {
    }

    public static PlaceDetailFragment newInstance(String placeId){
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_PLACE_ID, placeId);
        PlaceDetailFragment fragment = new PlaceDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = new PlaceDetailPresenter(Injection.providePlacesRepository(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_place_detail, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onResume(){
        super.onResume();
        setUp();
    }

    private void setUp(){
        String placeId = getArguments().getString(ARGUMENT_PLACE_ID);
        mPresenter.openPlace(placeId);
    }

    @Override
    public void setProgressIndicator(boolean active) {
        if(active){
            tvTitle.setText("");
            tvDescription.setText(R.string.loading);
        }
    }

    @Override
    public void showMissingPlace() {
        tvTitle.setText("");
        tvDescription.setText(R.string.no_data);
    }

    @Override
    public void hideTitle() {
        tvTitle.setVisibility(View.GONE);
    }

    @Override
    public void showTitle(String title) {
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(title);
    }

    @Override
    public void showImage(String imageUrl) {
        mImageView.setVisibility(View.VISIBLE);
        Picasso.with(getContext())
                .load(imageUrl)
                .placeholder(R.mipmap.ic_lock_grey600_24dp)
                .error(R.mipmap.ic_launcher)
                .into(mImageView);
    }

    @Override
    public void hideImage() {
        mImageView.setImageDrawable(null);
        mImageView.setVisibility(View.GONE);
    }

    @Override
    public void hideDescription() {
        tvDescription.setVisibility(View.GONE);
    }

    @Override
    public void showDescription(String description) {
        tvDescription.setVisibility(View.VISIBLE);
        tvDescription.setText(description);
    }
}
