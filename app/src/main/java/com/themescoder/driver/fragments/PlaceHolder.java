package com.themescoder.driver.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.themescoder.driver.MainActivity;
import com.themescoder.driver.R;


public class PlaceHolder extends Fragment {


    public PlaceHolder() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity.refreshbutton.setVisibility(View.GONE);
        MainActivity.statusLayout.setVisibility(View.GONE);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place_holder, container, false);

        return view;
    }

}
