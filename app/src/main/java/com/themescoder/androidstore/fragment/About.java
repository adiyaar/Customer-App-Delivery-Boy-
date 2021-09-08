package com.themescoder.androidstore.fragment;

import android.graphics.Color;
import androidx.annotation.Nullable;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

/*
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
*/
import com.themescoder.androidstore.R;
import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.constant.ConstantValues;

import am.appwise.components.ni.NoInternetDialog;


public class About extends Fragment {
    
    WebView about_us_webView;
    TextView official_web,official_whatsapp,privacy_policy, refund_policy, service_terms,a_z_terms;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.about, container, false);

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
      //  MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        // Intializing No internet Dialogue
        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        //noInternetDialog.show();

        // Set the Title of Toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.actionAbout));


        // Binding Layout Views
        official_web = (TextView) rootView.findViewById(R.id.official_web);
        official_whatsapp = (TextView) rootView.findViewById(R.id.official_whatsapp);
        privacy_policy = (TextView) rootView.findViewById(R.id.privacy_policy);
        refund_policy = (TextView) rootView.findViewById(R.id.refund_policy);
        a_z_terms =(TextView) rootView.findViewById(R.id.a_z_terms);
        service_terms = (TextView) rootView.findViewById(R.id.service_terms);
        about_us_webView = (WebView) rootView.findViewById(R.id.about_us_webView);
    
        
/*
        if (ConstantValues.IS_ADMOBE_ENABLED) {
            // Initialize InterstitialAd
            final InterstitialAd mInterstitialAd = new InterstitialAd(getActivity());
            mInterstitialAd.setAdUnitId(ConstantValues.AD_UNIT_ID_INTERSTITIAL);
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            mInterstitialAd.setAdListener(new AdListener(){
                @Override
                public void onAdLoaded() {
                    mInterstitialAd.show();
                }
            });
        }
*/

    
        String description = ConstantValues.ABOUT_US;
        String styleSheet = "<style> " +
                                "body{background:#eeeeee; margin:10; padding:10} " +
                                "p{color:#757575;} " +
                                "img{display:inline; height:auto; max-width:100%;}" +
                            "</style>";
    
        about_us_webView.setVerticalScrollBarEnabled(true);
        about_us_webView.setHorizontalScrollBarEnabled(false);
        about_us_webView.setBackgroundColor(Color.TRANSPARENT);
        about_us_webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        about_us_webView.loadDataWithBaseURL(null, styleSheet+description, "text/html", "utf-8", null);
        
        

        official_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String web_url = ((App) getActivity().getApplicationContext()).getAppSettingsDetails().getSiteUrl();
                if (!web_url.startsWith("https://")  &&  !web_url.startsWith("http://"))
                    web_url = "http://" + web_url;
                
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(web_url)));
            }
        });

        official_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String web_url = ((App) getActivity().getApplicationContext()).getAppSettingsDetails().getSiteUrl();
                if (!web_url.startsWith("https://")  &&  !web_url.startsWith("http://"))
                    web_url = "https://" + web_url;

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=919500109405&text=Welcome%20to%20*CdmKart%20Chat%2FCall%20Support*")));
            }
        });

        privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), android.R.style.Theme_NoTitleBar);
                View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_webview_fullscreen, null);
                dialog.setView(dialogView);
                dialog.setCancelable(true);
    
                final ImageButton dialog_button = (ImageButton) dialogView.findViewById(R.id.dialog_button);
                final TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
                final WebView dialog_webView = (WebView) dialogView.findViewById(R.id.dialog_webView);
    
                dialog_title.setText(getString(R.string.privacy_policy));
    
    
                String description = ConstantValues.PRIVACY_POLICY;
                String styleSheet = "<style> " +
                                        "body{background:#eeeeee; margin:10; padding:10} " +
                                        "p{color:#757575;} " +
                                        "img{display:inline; height:auto; max-width:100%;}" +
                                    "</style>";
    
                dialog_webView.setVerticalScrollBarEnabled(true);
                dialog_webView.setHorizontalScrollBarEnabled(false);
                dialog_webView.setBackgroundColor(Color.TRANSPARENT);
                dialog_webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
                dialog_webView.loadDataWithBaseURL(null, styleSheet+description, "text/html", "utf-8", null);
    
    
                final AlertDialog alertDialog = dialog.create();
    
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    alertDialog.getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
                }
    
                dialog_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
    
                alertDialog.show();
                
            }
        });

        refund_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), android.R.style.Theme_NoTitleBar);
                View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_webview_fullscreen, null);
                dialog.setView(dialogView);
                dialog.setCancelable(true);
    
                final ImageButton dialog_button = (ImageButton) dialogView.findViewById(R.id.dialog_button);
                final TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
                final WebView dialog_webView = (WebView) dialogView.findViewById(R.id.dialog_webView);
    
                dialog_title.setText(getString(R.string.refund_policy));
    
    
                String description = ConstantValues.REFUND_POLICY;
                String styleSheet = "<style> " +
                                        "body{background:#eeeeee; margin:10; padding:10} " +
                                        "p{color:#757575;} " +
                                        "img{display:inline; height:auto; max-width:100%;}" +
                                    "</style>";
    
                dialog_webView.setVerticalScrollBarEnabled(true);
                dialog_webView.setHorizontalScrollBarEnabled(false);
                dialog_webView.setBackgroundColor(Color.TRANSPARENT);
                dialog_webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
                dialog_webView.loadDataWithBaseURL(null, styleSheet+description, "text/html", "utf-8", null);
    
    
                final AlertDialog alertDialog = dialog.create();
    
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    alertDialog.getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
                }
    
                dialog_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
    
                alertDialog.show();
            }
        });


        a_z_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), android.R.style.Theme_NoTitleBar);
                View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_webview_fullscreen, null);
                dialog.setView(dialogView);
                dialog.setCancelable(true);

                final ImageButton dialog_button = (ImageButton) dialogView.findViewById(R.id.dialog_button);
                final TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
                final WebView dialog_webView = (WebView) dialogView.findViewById(R.id.dialog_webView);

                dialog_title.setText(getString(R.string.a_z));


                String description = ConstantValues.A_Z;
                String styleSheet = "<style> " +
                        "body{background:#eeeeee; margin:10; padding:10} " +
                        "p{color:#757575;} " +
                        "img{display:inline; height:auto; max-width:100%;}" +
                        "</style>";

                dialog_webView.setVerticalScrollBarEnabled(true);
                dialog_webView.setHorizontalScrollBarEnabled(false);
                dialog_webView.setBackgroundColor(Color.TRANSPARENT);
                dialog_webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
                dialog_webView.loadDataWithBaseURL(null, styleSheet+description, "text/html", "utf-8", null);


                final AlertDialog alertDialog = dialog.create();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    alertDialog.getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
                }

                dialog_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });

        service_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), android.R.style.Theme_NoTitleBar);
                View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_webview_fullscreen, null);
                dialog.setView(dialogView);
                dialog.setCancelable(true);
    
                final ImageButton dialog_button = (ImageButton) dialogView.findViewById(R.id.dialog_button);
                final TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
                final WebView dialog_webView = (WebView) dialogView.findViewById(R.id.dialog_webView);
    
                dialog_title.setText(getString(R.string.service_terms));
    
    
                String description = ConstantValues.TERMS_SERVICES;
                String styleSheet = "<style> " +
                                        "body{background:#eeeeee; margin:10; padding:10} " +
                                        "p{color:#757575;} " +
                                        "img{display:inline; height:auto; max-width:100%;}" +
                                    "</style>";
    
                dialog_webView.setVerticalScrollBarEnabled(true);
                dialog_webView.setHorizontalScrollBarEnabled(false);
                dialog_webView.setBackgroundColor(Color.TRANSPARENT);
                dialog_webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
                dialog_webView.loadDataWithBaseURL(null, styleSheet+description, "text/html", "utf-8", null);
    
    
                final AlertDialog alertDialog = dialog.create();
    
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    alertDialog.getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
                }
    
                dialog_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
    
                alertDialog.show();
            }
        });



        return rootView;
    }

}



