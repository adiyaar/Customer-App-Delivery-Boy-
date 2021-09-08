package com.themescoder.androidstore.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

import com.github.paolorotolo.appintro.AppIntro;

import com.themescoder.androidstore.R;
import com.themescoder.androidstore.utils.LocaleHelper;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.app.MyAppPrefsManager;
import com.themescoder.androidstore.fragment.Intro_Slide_1;
import com.themescoder.androidstore.fragment.Intro_Slide_2;
import com.themescoder.androidstore.fragment.Intro_Slide_3;
import com.themescoder.androidstore.fragment.Intro_Slide_4;
import com.themescoder.androidstore.fragment.Intro_Slide_5;

import am.appwise.components.ni.NoInternetDialog;


/**
 * IntroScreen activity, appears only when the App starts for the very first time
 **/


public class IntroScreen extends AppIntro {
    
    MyAppPrefsManager myAppPrefsManager;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ConstantValues.THEME_ID);
        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(IntroScreen.this).build();
        //noInternetDialog.show();
    
        myAppPrefsManager = new MyAppPrefsManager(IntroScreen.this);
        
    
        addSlide(new Intro_Slide_1());
        addSlide(new Intro_Slide_2());
        addSlide(new Intro_Slide_3());
        addSlide(new Intro_Slide_4());
        addSlide(new Intro_Slide_5());
    
    
        // Hide StatusBar
        showStatusBar(false);
        showSkipButton(true);
        setProgressButtonEnabled(true);
    
        setBarColor(ContextCompat.getColor(IntroScreen.this, R.color.white));
        setSeparatorColor(ContextCompat.getColor(IntroScreen.this, R.color.colorPrimaryLight));
        
        setColorDoneText(ContextCompat.getColor(IntroScreen.this, R.color.colorPrimary));
        setColorSkipButton(ContextCompat.getColor(IntroScreen.this, R.color.colorPrimary));
        setNextArrowColor(ContextCompat.getColor(IntroScreen.this, R.color.colorPrimary));
        
        setIndicatorColor(ContextCompat.getColor(IntroScreen.this, R.color.colorPrimary),
                            ContextCompat.getColor(IntroScreen.this, R.color.iconsLight));
        
        
        // Set Slide Change Animations
//        setFadeAnimation();
//        setZoomAnimation();
//        setFlowAnimation();
//        setDepthAnimation();
//        setSlideOverAnimation();
        
    }
    
    
    
    //*********** Called when the Skip Button pressed on IntroScreen ********//
    
    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
    
        if (myAppPrefsManager.isFirstTimeLaunch()) {
            // Navigate to MainActivity
            startActivity(new Intent(IntroScreen.this, MainActivity.class));
            finish();
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
        }
        else {
            // Finish this Activity
            finish();
        }
        
    }
    
    
    
    //*********** Called when the Done Button pressed on IntroScreen ********//
    
    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        
        if (myAppPrefsManager.isFirstTimeLaunch()) {
            // Navigate to MainActivity
            startActivity(new Intent(IntroScreen.this, MainActivity.class));
            finish();
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
        }
        else {
            // Finish this Activity
            finish();
        }
    }
    
    
    
    //*********** Called when the active Slide Changes ********//
    
    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
    
    
    
    //*********** Set the Base Context for the ContextWrapper ********//
    
    @Override
    protected void attachBaseContext(Context newBase) {
    
        String languageCode = ConstantValues.LANGUAGE_CODE;
        if ("".equalsIgnoreCase(languageCode))
            languageCode = ConstantValues.LANGUAGE_CODE = "en";
    
        super.attachBaseContext(LocaleHelper.wrapLocale(newBase, languageCode));
    }
    
    
    
    //*********** Called when the Activity has detected the User pressed the Back key ********//
    
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    
        if (myAppPrefsManager.isFirstTimeLaunch()) {
            // Navigate to MainActivity
            startActivity(new Intent(IntroScreen.this, MainActivity.class));
            finish();
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
        }
        else {
            // Finish this Activity
            finish();
        }
        
    }
    
}

