package com.apaza.moises.sucreapp.ui.places;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apaza.moises.sucreapp.R;
import com.apaza.moises.sucreapp.data.Place;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class PlacesFragment extends Fragment implements PlacesContract.FragmentView, PlacesAdapter.PlaceItemListener{

    private static final String TAG = PlacesFragment.class.getSimpleName();
    private static final int REQUEST_ADD_PLACE = 1;

    @Inject PlacesContract.FragmentPresenter mFragmentPresenter;
    @Inject PlacesAdapter mPlacesAdapter;

    @BindView(R.id.refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.places_list) RecyclerView recyclerView;
    @BindView(R.id.fab) FloatingActionButton fab;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
        mPlacesAdapter.setPlaceItemListener(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //PlacesServiceApiImpl placesServiceApi = new PlacesServiceApiImpl();
        //mPlacesAdapter = new PlacesAdapter(new ArrayList<Place>(), this);
        //mPresenter = new PlacesPresenter(this, Injection.providePlacesRepository());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_places, container, false);
        ButterKnife.bind(this, root);
        setUp();
        return root;
    }

    private void setUp(){
        recyclerView.setAdapter(mPlacesAdapter);
        int numColumns = getContext().getResources().getInteger(R.integer.num_places_columns);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numColumns));

        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragmentPresenter.addNewPlace();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mFragmentPresenter.loadPlaces(true);
                //Utils.showToast("Refresh");
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onStart(){
        super.onStart();
        mFragmentPresenter.onFragmentStarted();
    }

    @Override
    public void onResume() {
        super.onResume();
        mFragmentPresenter.loadPlaces(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("PLACESS", "request code " + requestCode);
        if (PlacesActivity.REQUEST_ADD_PLACE == requestCode && Activity.RESULT_OK == resultCode) {
            Snackbar.make(getView(), getString(R.string.successfully_saved_place_message), Snackbar.LENGTH_SHORT).show();
        }
    }

    /*LISTENER*/
    @Override
    public void setProgressIndicator(final boolean active) {
        if(getView() == null)
            return;

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(active);

            }
        });
    }

    @Override
    public void showPlaces(List<Place> places) {
        //mPlacesAdapter.addItems(places);
        mPlacesAdapter.replaceData(places);
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    /*LISTENER ADAPTER*/
    @Override
    public void onPlaceClick(Place place) {
        mFragmentPresenter.openPlaceDetail(place);
    }
}
