package com.apaza.moises.sucreapp.addplace;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apaza.moises.sucreapp.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddPlaceActivityFragment extends Fragment {

    public AddPlaceActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_place, container, false);
    }
}
