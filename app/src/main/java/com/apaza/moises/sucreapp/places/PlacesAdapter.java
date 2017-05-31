package com.apaza.moises.sucreapp.places;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apaza.moises.sucreapp.R;
import com.apaza.moises.sucreapp.data.Place;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            mPlaceItemListener.onPlaceClick(getItem(getAdapterPosition()));
        }
    }

    public interface PlaceItemListener{
        void onPlaceClick(Place place);
    }
}
