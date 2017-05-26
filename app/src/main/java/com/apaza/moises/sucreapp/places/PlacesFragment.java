package com.apaza.moises.sucreapp.places;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apaza.moises.sucreapp.R;
import com.apaza.moises.sucreapp.addplace.AddPlaceActivity;
import com.apaza.moises.sucreapp.data.Place;
import com.apaza.moises.sucreapp.placedetail.PlaceDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlacesFragment extends Fragment implements PlacesContract.View{

    private static final int REQUEST_ADD_PLACE = 1;

    private PlacesContract.Presenter mPresenter;
    private PlacesAdapter mPlacesAdapter;

    public static PlacesFragment newInstance(){
        return new PlacesFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlacesAdapter = new PlacesAdapter(new ArrayList<Place>(), mItemListener);
        //mPresenter = new
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    PlaceItemListener mItemListener = new PlaceItemListener() {
        @Override
        public void onPlaceClick(Place place) {
            mPresenter.openPlaceDetails(place);
        }
    };

    @Override
    public void setProgressIndicator(final boolean active) {
        if(getView() == null)
            return;

        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout)getView().findViewById(R.id.refresh_layout);

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(active);
            }
        });
    }

    @Override
    public void showPlaces(List<Place> places) {
        mPlacesAdapter.addItems(places);
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


    public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlaceViewHolder>{

        private List<Place> mPlaceList;
        private PlaceItemListener mPlaceItemListener;

        public PlacesAdapter(List<Place> places, PlaceItemListener itemListener){
            this.mPlaceList = places;
            mPlaceItemListener = itemListener;
        }
        @Override
        public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View placeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false);
            return new PlaceViewHolder(placeView, mPlaceItemListener);
        }

        @Override
        public void onBindViewHolder(PlaceViewHolder holder, int position) {
            Place place = mPlaceList.get(position);

            holder.mTitle.setText(place.getTitle());
            holder.mDescription.setText(place.getDescription());
        }

        @Override
        public int getItemCount() {
            if(mPlaceList.size() > 0)
                return mPlaceList.size();
            return 0;
        }

        public Place getItem(int position){
            return mPlaceList.get(position);
        }

        public void addItems(List<Place> places){
            mPlaceList.addAll(places);
            notifyDataSetChanged();
        }

        public class PlaceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            @BindView(R.id.tv_place_title) TextView mTitle;
            @BindView(R.id.tv_place_description) TextView mDescription;

            private PlaceItemListener mPlaceItemListener;

            public PlaceViewHolder(View view, PlaceItemListener listener){
                super(view);
                ButterKnife.bind(this, view);
                this.mPlaceItemListener = listener;
            }
            @Override
            public void onClick(View view) {
                mPlaceItemListener.onPlaceClick(getItem(getAdapterPosition()));
            }
        }
    }

    public interface PlaceItemListener{
        void onPlaceClick(Place place);
    }
}
