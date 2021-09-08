package com.themescoder.androidstore.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

/*
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
*/
import com.themescoder.androidstore.activities.MainActivity;
import com.themescoder.androidstore.R;

import java.util.ArrayList;
import java.util.List;

import com.themescoder.androidstore.adapters.LanguagesAdapter;
import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.customs.DialogLoader;
import com.themescoder.androidstore.models.banner_model.BannerDetails;
import com.themescoder.androidstore.models.category_model.CategoryDetails;
import com.themescoder.androidstore.models.language_model.LanguageData;
import com.themescoder.androidstore.models.language_model.LanguageDetails;
import com.themescoder.androidstore.app.MyAppPrefsManager;
import com.themescoder.androidstore.network.APIClient;
import com.themescoder.androidstore.network.StartAppRequests;

import am.appwise.components.ni.NoInternetDialog;
import retrofit2.Call;
import retrofit2.Callback;



public class Languages extends Fragment {

    View rootView;
    
    MyAppPrefsManager appPrefs;
    
    String selectedLanguageID;
    String selectedLanguageCode;
    
    //AdView mAdView;
    Button saveLanguageBtn;
    ListView languageListView;
    FrameLayout banner_adView;
    
    LanguagesAdapter languagesAdapter;
    List<LanguageDetails> languagesList;
    
    private CheckBox lastChecked_CB = null;
    DialogLoader dialogLoader;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.languages, container, false);
    
       // MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        ((MainActivity)getActivity()).toggleNavigaiton(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.actionLanguage));

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        //noInternetDialog.show();

        appPrefs = new MyAppPrefsManager(getContext());

        dialogLoader = new DialogLoader(getContext());
        selectedLanguageCode = appPrefs.getUserLanguageCode();
        selectedLanguageID = String.valueOf(appPrefs.getUserLanguageId());
        
    
        // Binding Layout Views
        banner_adView = (FrameLayout) rootView.findViewById(R.id.banner_adView);
        saveLanguageBtn = (Button) rootView.findViewById(R.id.btn_save_language);
        languageListView = (ListView) rootView.findViewById(R.id.languages_list);
    
        
/*
        if (ConstantValues.IS_ADMOBE_ENABLED) {
            // Initialize Admobe
            mAdView = new AdView(getContext());
            mAdView.setAdSize(AdSize.BANNER);
            mAdView.setAdUnitId(ConstantValues.AD_UNIT_ID_BANNER);
            AdRequest adRequest = new AdRequest.Builder().build();
            banner_adView.addView(mAdView);
            mAdView.loadAd(adRequest);
            mAdView.setAdListener(new AdListener(){
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    banner_adView.setVisibility(View.VISIBLE);
                }
                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    banner_adView.setVisibility(View.GONE);
                }
            });
        }
*/

    
        languagesList = new ArrayList<>();
    
        // Request Languages
        RequestLanguages();
    
    
        // Initialize the LanguagesAdapter for RecyclerView
        languagesAdapter = new LanguagesAdapter(getContext(), languagesList, this);
    
        languageListView.setAdapter(languagesAdapter);
    
    
        saveLanguageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            
                if (!selectedLanguageID.equalsIgnoreCase(String.valueOf(appPrefs.getUserLanguageId()))) {
                    // Change Language
                    
                    appPrefs.setUserLanguageCode(selectedLanguageCode);
                    appPrefs.setUserLanguageId(Integer.parseInt(selectedLanguageID));
    
                    ConstantValues.LANGUAGE_ID = appPrefs.getUserLanguageId();
                    ConstantValues.LANGUAGE_CODE = appPrefs.getUserLanguageCode();

                    ChangeLocaleTask changeLocaleTask = new ChangeLocaleTask();
                    changeLocaleTask.execute();
                    
                }
            }
        });


        return rootView;
    }

    
    //*********** Recreates Activity ********//
    
    private void recreateActivity() {
        My_Cart.ClearCart();
        ((App) getContext().getApplicationContext()).setBannersList(new ArrayList<BannerDetails>());
        ((App) getContext().getApplicationContext()).setCategoriesList(new ArrayList<CategoryDetails>());
        Intent intent = getActivity().getIntent();
        getActivity().finish();
        startActivity(intent);
    }
    
    
    public void setLastCheckedCB(CheckBox lastChecked_CB) {
        this.lastChecked_CB = lastChecked_CB;
    }
    
    
    public String getSelectedLanguageID() {
        return selectedLanguageID;
    }
    
    
    
    //*********** Adds Orders returned from the Server to the OrdersList ********//
    
    private void addLanguages(LanguageData languageData) {
        
        languagesList.addAll(languageData.getLanguages());
        
        
        if (selectedLanguageID.equalsIgnoreCase("") && languagesList.size() != 0) {
    
            selectedLanguageID = languagesList.get(0).getLanguagesId();
            selectedLanguageCode = languagesList.get(0).getCode();
            
            for (int i=0;  i<languagesList.size();  i++) {
                if (languagesList.get(i).getIsDefault().equalsIgnoreCase("1")) {
                    selectedLanguageCode = languagesList.get(i).getCode();
                    selectedLanguageID = languagesList.get(i).getLanguagesId();
                }
            }
            
        }
        else {
            for (int i=0;  i<languagesList.size();  i++) {
                if (languagesList.get(i).getLanguagesId().equalsIgnoreCase(String.valueOf(appPrefs.getUserLanguageId()))) {
                    selectedLanguageCode = languagesList.get(i).getCode();
                    selectedLanguageID = languagesList.get(i).getLanguagesId();
                }
            }
        }
        
        
        languagesAdapter.notifyDataSetChanged();
        
        
        languageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
                CheckBox currentChecked_CB = (CheckBox) view.findViewById(R.id.cb_language);
                LanguageDetails language = (LanguageDetails) parent.getAdapter().getItem(position);
                
                
                // UnCheck last Checked CheckBox
                if (lastChecked_CB != null) {
                    lastChecked_CB.setChecked(false);
                }
                
                currentChecked_CB.setChecked(true);
                lastChecked_CB = currentChecked_CB;
    
    
                selectedLanguageID = language.getLanguagesId();
                selectedLanguageCode = language.getCode();
            }
        });
        
    }
    

    //*********** Request App Languages from the Server ********//
    
    public void RequestLanguages() {
        
        Call<LanguageData> call = APIClient.getInstance()
                .getLanguages();
        dialogLoader.showProgressDialog();
        call.enqueue(new Callback<LanguageData>() {
            @Override
            public void onResponse(Call<LanguageData> call, retrofit2.Response<LanguageData> response) {
                
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
                        
                        // Languages have been returned. Add Languages to the languageList
                        addLanguages(response.body());
                        
                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
    
                    }
                    else {
                        // Unable to get Success status
                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
                dialogLoader.hideProgressDialog();
            }
            
            @Override
            public void onFailure(Call<LanguageData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
    

    private class ChangeLocaleTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogLoader.showProgressDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
    
            // Recall some Network Requests
            StartAppRequests startAppRequests = new StartAppRequests(getContext());
            startAppRequests.StartRequests();
            
            return null;
        }
    
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialogLoader.hideProgressDialog();
            recreateActivity();
        }
    }
}

