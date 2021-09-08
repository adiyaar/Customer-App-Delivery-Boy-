package com.themescoder.androidstore.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.themescoder.androidstore.R;
import com.themescoder.androidstore.activities.IntroScreen;
import com.themescoder.androidstore.activities.Login;
import com.themescoder.androidstore.activities.MainActivity;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.utils.Utilities;

public class MeFragment extends Fragment implements View.OnClickListener {


    TextView loginRegister;
    TextView aboutUs;
    TextView intro;
    TextView shareApp;
    TextView rateReview;
    TextView settings;
    TextView contactUS;
    ImageView languages;
    ImageView currencies;

    MainActivity activity;

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_me, container, false);

        activity = (MainActivity) getActivity();

        loginRegister = rootView.findViewById(R.id.loginRegister);
        aboutUs = rootView.findViewById(R.id.aboutUs);
        intro = rootView.findViewById(R.id.intro);
        shareApp = rootView.findViewById(R.id.shareApp);
        rateReview = rootView.findViewById(R.id.rateReview);
        settings = rootView.findViewById(R.id.settings);
        contactUS = rootView.findViewById(R.id.contactUS);
        languages = rootView.findViewById(R.id.languages);
        currencies = rootView.findViewById(R.id.currencies);

        loginRegister.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
        intro.setOnClickListener(this);
        shareApp.setOnClickListener(this);
        rateReview.setOnClickListener(this);
        settings.setOnClickListener(this);
        contactUS.setOnClickListener(this);
        languages.setOnClickListener(this);
        currencies.setOnClickListener(this);

        if (ConstantValues.IS_USER_LOGGED_IN)
            loginRegister.setText(getString(R.string.actionAccount));

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginRegister:
                if (ConstantValues.IS_USER_LOGGED_IN){
                    activity.enableBottomNavigation(false);
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.main_fragment, new Update_Account(false))
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                } else {
                    startActivity(new Intent(getActivity(), Login.class));
                }
                break;
            case R.id.aboutUs:
                activity.enableBottomNavigation(false);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.main_fragment, new About())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
                break;
            case R.id.intro:
                startActivity(new Intent(getActivity(), IntroScreen.class));
                break;
            case R.id.shareApp:
                Utilities.shareMyApp(getActivity());
                break;
            case R.id.rateReview:
                Utilities.rateMyApp(getActivity());
                break;
            case R.id.settings:
                activity.enableBottomNavigation(false);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.main_fragment, new SettingsFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
                break;
            case R.id.contactUS:
                activity.enableBottomNavigation(false);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.main_fragment, new ContactUs())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
                break;
            case R.id.languages:
                activity.enableBottomNavigation(false);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.main_fragment, new Languages())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
                break;
            case R.id.currencies:
                activity.enableBottomNavigation(false);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.main_fragment, new CurrencyFrag())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();

                break;
        }
    }
}
