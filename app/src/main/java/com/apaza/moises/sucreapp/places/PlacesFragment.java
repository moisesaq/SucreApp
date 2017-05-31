package com.apaza.moises.sucreapp.places;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apaza.moises.sucreapp.Injection;
import com.apaza.moises.sucreapp.R;
import com.apaza.moises.sucreapp.addplace.AddPlaceActivity;
import com.apaza.moises.sucreapp.data.Place;
import com.apaza.moises.sucreapp.data.PlaceRepositories;
import com.apaza.moises.sucreapp.data.PlacesServiceApiImpl;
import com.apaza.moises.sucreapp.placedetail.PlaceDetailActivity;
import com.apaza.moises.sucreapp.tools.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlacesFragment extends Fragment implements PlacesContract.View, PlacesAdapter.PlaceItemListener{

    private static final String TAG = PlacesFragment.class.getSimpleName();
    private static final int REQUEST_ADD_PLACE = 1;

    private PlacesContract.Presenter mPresenter;
    private PlacesAdapter mPlacesAdapter;

    @BindView(R.id.refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.places_list) RecyclerView recyclerView;
    @BindView(R.id.fab) FloatingActionButton fab;

    public static PlacesFragment newInstance(){
        return new PlacesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //PlacesServiceApiImpl placesServiceApi = new PlacesServiceApiImpl();
        mPlacesAdapter = new PlacesAdapter(new ArrayList<Place>(), this);
        mPresenter = new PlacesPresenter(this, Injection.providePlacesRepository());
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
                mPresenter.addNewPlace();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadPlaces(true);
                Utils.showToast("Refresh");
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadPlaces(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setProgressIndicator(final boolean active) {
        if(getView() == null)
            return;

        //refreshLayout.setRefreshing(false);
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
    public void showAddPlaces() {
        startActivityForResult(new Intent(getContext(), AddPlaceActivity.class), REQUEST_ADD_PLACE);
    }

    @Override
    public void showNoteDetailUi(String placeId) {
        Intent intent = new Intent(getContext(), PlaceDetailActivity.class);
        intent.putExtra(PlaceDetailActivity.EXTRA_PLACE_ID, placeId);
        startActivity(intent);
    }

    /*LISTENER ADAPTER*/
    @Override
    public void onPlaceClick(Place place) {
        mPresenter.openPlaceDetails(place);
    }
}
