package com.themescoder.androidstore.fragment;

import androidx.annotation.Nullable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.themescoder.androidstore.R;

import am.appwise.components.ni.NoInternetDialog;


public class Intro_Slide_4 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.intro_slide_4, container, false);

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        //noInternetDialog.show();

        return rootView;
    }
    
}

