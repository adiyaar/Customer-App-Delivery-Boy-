package com.themescoder.androidstore.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.themescoder.androidstore.R;
import com.themescoder.androidstore.activities.MainActivity;
import com.themescoder.androidstore.adapters.BannersSelectionAdapter;
import com.themescoder.androidstore.adapters.CardStyleSelectionAdapter;
import com.themescoder.androidstore.adapters.ColorsAdapter;
import com.themescoder.androidstore.adapters.CurrenciesSelectionAdapter;
import com.themescoder.androidstore.adapters.LanguageSelectionAdapter;
import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.app.MyAppPrefsManager;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.customs.DialogLoader;
import com.themescoder.androidstore.models.ColorsModal;
import com.themescoder.androidstore.models.banner_model.BannerDetails;
import com.themescoder.androidstore.models.category_model.CategoryDetails;
import com.themescoder.androidstore.models.currency_model.CurrencyList;
import com.themescoder.androidstore.models.currency_model.CurrencyModel;
import com.themescoder.androidstore.models.device_model.AppSettingsDetails;
import com.themescoder.androidstore.models.language_model.LanguageData;
import com.themescoder.androidstore.models.language_model.LanguageDetails;
import com.themescoder.androidstore.network.APIClient;
import com.themescoder.androidstore.network.StartAppRequests;
import com.themescoder.androidstore.utils.Utilities;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class SettingsPannel
        extends Fragment
        implements
        LanguageSelectionAdapter.LanguageClickListener,
        CurrenciesSelectionAdapter.CurrencyClickListener,
        ColorsAdapter.ColorClickListener,
        BannersSelectionAdapter.BannerClickListener,
        CardStyleSelectionAdapter.CardStyleClickListener {

    RecyclerView colorsRv;
    RecyclerView languagesRv;
    RecyclerView currenciesRv;
    RecyclerView bannersRv;
    RecyclerView cardStyleRv;
    CardView cardDarkMode;
    CardView cardLightMode;
    CardView cardNavigationBottom;
    CardView cardNavigationleft;
    LinearLayout darkModeLayout;
    LinearLayout navigationStyleLayout;

    ColorsAdapter colorsAdapter;
    LanguageSelectionAdapter languageSelectionAdapter;
    CurrenciesSelectionAdapter currenciesSelectionAdapter;
    BannersSelectionAdapter bannersSelectionAdapter;
    CardStyleSelectionAdapter cardStyleSelectionAdapter;

    private ArrayList<ColorsModal> colors = new ArrayList<>();
    private ArrayList<LanguageDetails> languages = new ArrayList<>();
    private ArrayList<CurrencyList> currencies = new ArrayList<>();
    private ArrayList<String> bannerStyles = new ArrayList<>();
    private ArrayList<String> cardStyles = new ArrayList<>();

    DialogLoader dialogLoader;

    View rootView;

    String selectedLanguageID = "";
    String selectedLanguageCode = "";
    int selectedLanguagePosition = 0;

    String selectedCurrencyID = "";
    int selectedCurrencyPosition = 0;

    MyAppPrefsManager appPrefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_panel, container, false);
        ((MainActivity) getActivity()).toggleNavigaiton(false);
        dialogLoader = new DialogLoader(getContext());
        appPrefs = new MyAppPrefsManager(getContext());

        colorsRv = rootView.findViewById(R.id.colorsRv);
        cardDarkMode = rootView.findViewById(R.id.cardDarkMode);
        cardLightMode = rootView.findViewById(R.id.cardLightMode);
        darkModeLayout = rootView.findViewById(R.id.darkModeLayout);
        navigationStyleLayout = rootView.findViewById(R.id.navigationStyleLayout);
        cardNavigationBottom = rootView.findViewById(R.id.cardNavigationBottom);
        cardNavigationleft = rootView.findViewById(R.id.cardNavigationleft);
        languagesRv = rootView.findViewById(R.id.languagesRv);
        currenciesRv = rootView.findViewById(R.id.currenciesRv);
        bannersRv = rootView.findViewById(R.id.bannersRv);
        cardStyleRv = rootView.findViewById(R.id.cardstyleRv);

        setupColorsRv();
        setupThemeMode();
        setupNavigationMode();
        setupLanguagesRv();
        setupCurrenciesRv();
        setupBannersSelectionRv();
        setupCardStyleSelectionRv();

        return rootView;
    }

    private void setupColorsRv() {
        // colors Rv
        colorsRv.setLayoutManager(new GridLayoutManager(getContext(), 6));
        colorsAdapter = new ColorsAdapter(getContext(), colors, this);
        colorsRv.setAdapter(colorsAdapter);

        colors.add(new ColorsModal(R.style.AppTheme, "Default", R.color.colorPrimary));
        colors.add(new ColorsModal(R.style.TemplateTheme1, "Red", R.color.red_500));
        colors.add(new ColorsModal(R.style.TemplateTheme2, "Deep purple", R.color.deep_purple_500));
        colors.add(new ColorsModal(R.style.TemplateTheme3, "Light Blue", R.color.light_blue_500));
        colors.add(new ColorsModal(R.style.TemplateTheme4, "Green", R.color.green_500));
        colors.add(new ColorsModal(R.style.TemplateTheme6, "Grey", R.color.grey_500));
        colors.add(new ColorsModal(R.style.TemplateTheme7, "Pink", R.color.pink_500));
        colors.add(new ColorsModal(R.style.TemplateTheme8, "Indigo", R.color.indigo_500));
        colors.add(new ColorsModal(R.style.TemplateTheme9, "Cyan", R.color.cyan_500));
        colors.add(new ColorsModal(R.style.TemplateTheme10, "Light Green", R.color.light_green_500));

        colorsAdapter.setEmployees(colors);
    }


    private void setupThemeMode() {
        darkModeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleThemeButton();
            }
        });
        if (ConstantValues.DAYNIGHT_MODE.equals("day")) {
            cardDarkMode.setVisibility(View.INVISIBLE);
            cardLightMode.setVisibility(View.VISIBLE);
        } else if (ConstantValues.DAYNIGHT_MODE.equals("night")) {
            cardDarkMode.setVisibility(View.VISIBLE);
            cardLightMode.setVisibility(View.INVISIBLE);
        }

    }


    private void toggleThemeButton() {
        if (cardDarkMode.getVisibility() == View.VISIBLE) {
            cardDarkMode.setVisibility(View.INVISIBLE);
            cardLightMode.setVisibility(View.VISIBLE);
            ConstantValues.DAYNIGHT_MODE = "day";
        } else {
            cardDarkMode.setVisibility(View.VISIBLE);
            cardLightMode.setVisibility(View.INVISIBLE);
            ConstantValues.DAYNIGHT_MODE = "night";
        }
        recreateActivity();
    }

    private void setupNavigationMode() {
        navigationStyleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleNavigationButton();
            }
        });
        if (ConstantValues.NAVIGATION_STYLE.equals("side")) {
            cardNavigationBottom.setVisibility(View.INVISIBLE);
            cardNavigationleft.setVisibility(View.VISIBLE);
        } else if (ConstantValues.NAVIGATION_STYLE.equals("bottom")) {
            cardNavigationBottom.setVisibility(View.VISIBLE);
            cardNavigationleft.setVisibility(View.INVISIBLE);
        }
    }

    private void toggleNavigationButton() {
        if (cardNavigationBottom.getVisibility() == View.VISIBLE) {
            cardNavigationBottom.setVisibility(View.INVISIBLE);
            cardNavigationleft.setVisibility(View.VISIBLE);
            ConstantValues.NAVIGATION_STYLE = "side";
        } else {
            cardNavigationBottom.setVisibility(View.VISIBLE);
            cardNavigationleft.setVisibility(View.INVISIBLE);
            ConstantValues.NAVIGATION_STYLE = "bottom";
        }
        recreateActivity();
    }

    private void setupLanguagesRv() {
        languagesRv.setLayoutManager(new LinearLayoutManager(getContext()));
        languageSelectionAdapter = new LanguageSelectionAdapter(getContext(), languages, this);
        languagesRv.setAdapter(languageSelectionAdapter);

        requestLanguages();

    }

    public void requestLanguages() {

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

                    } else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_LONG).show();

                    } else {
                        // Unable to get Success status
                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
                dialogLoader.hideProgressDialog();
            }

            @Override
            public void onFailure(Call<LanguageData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(getContext(), "NetworkCallFailure : " + t, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addLanguages(LanguageData languageData) {

        languages.addAll(languageData.getLanguages());


        if (selectedLanguageID.equalsIgnoreCase("") && languages.size() != 0) {

            selectedLanguageID = languages.get(0).getLanguagesId();
            selectedLanguageCode = languages.get(0).getCode();

            for (int i = 0; i < languages.size(); i++) {
                if (languages.get(i).getIsDefault().equalsIgnoreCase("1")) {
                    selectedLanguageCode = languages.get(i).getCode();
                    selectedLanguageID = languages.get(i).getLanguagesId();
                }
                if (languages.get(i).getLanguagesId().equalsIgnoreCase(String.valueOf(ConstantValues.LANGUAGE_ID)))
                    selectedLanguagePosition = i;
            }

        } else {
            for (int i = 0; i < languages.size(); i++) {
                if (languages.get(i).getLanguagesId().equalsIgnoreCase(String.valueOf(appPrefs.getUserLanguageId()))) {
                    selectedLanguageCode = languages.get(i).getCode();
                    selectedLanguageID = languages.get(i).getLanguagesId();
                }
                if (languages.get(i).getLanguagesId().equalsIgnoreCase(String.valueOf(ConstantValues.LANGUAGE_ID)))
                    selectedLanguagePosition = i;
            }
        }

        languageSelectionAdapter.setCheckedPosition(selectedLanguagePosition);
        languageSelectionAdapter.notifyDataSetChanged();


/*
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
*/

    }

    private void setupCurrenciesRv() {
        currenciesRv.setLayoutManager(new LinearLayoutManager(getContext()));
        currenciesSelectionAdapter = new CurrenciesSelectionAdapter(getContext(), currencies, this);
        currenciesRv.setAdapter(currenciesSelectionAdapter);

        requestCurrency();
    }

    public void requestCurrency() {
        dialogLoader.showProgressDialog();
        Call<CurrencyModel> call = APIClient.getInstance()
                .getCurrency();

        call.enqueue(new Callback<CurrencyModel>() {
            @Override
            public void onResponse(Call<CurrencyModel> call, retrofit2.Response<CurrencyModel> response) {

                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        // Languages have been returned. Add Languages to the languageList
                        addCurrencies(response.body());
                        dialogLoader.hideProgressDialog();

                    } else {
                        // Unable to get Success status
                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                        dialogLoader.hideProgressDialog();
                    }
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    dialogLoader.hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<CurrencyModel> call, Throwable t) {
                Toast.makeText(getContext(), "NetworkCallFailure : " + t, Toast.LENGTH_LONG).show();
                dialogLoader.hideProgressDialog();
            }
        });
    }

    private void addCurrencies(CurrencyModel languageData) {

        currencies.addAll(languageData.getData());

        if (selectedCurrencyID.equalsIgnoreCase("") && currencies.size() != 0) {

            selectedCurrencyID = currencies.get(0).getTitle();
            // selectedLanguageCode = languagesList.get(0).getCode();

            for (int i = 0; i < currencies.size(); i++) {
                if (currencies.get(i).getIsDefault() == 1) {
                    selectedCurrencyID = currencies.get(i).getTitle();
                    // selectedLanguageID = languagesList.get(i).getLanguagesId();
                }
                if (currencies.get(i).getCode().equalsIgnoreCase(ConstantValues.CURRENCY_CODE))
                    selectedCurrencyPosition = i;
            }

        } else {
            for (int i = 0; i < currencies.size(); i++) {
                if (currencies.get(i).getTitle().equalsIgnoreCase(String.valueOf(appPrefs.getCurrencyCode()))) {
                    selectedCurrencyID = currencies.get(i).getTitle();
                    // selectedLanguageID = languagesList.get(i).getLanguagesId();
                }
                if (currencies.get(i).getCode().equalsIgnoreCase(ConstantValues.CURRENCY_CODE))
                    selectedCurrencyPosition = i;
            }
        }

        currenciesSelectionAdapter.setCheckedPosition(selectedCurrencyPosition);
        currenciesSelectionAdapter.notifyDataSetChanged();


/*
        currencyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CheckBox currentChecked_CB = (CheckBox) view.findViewById(R.id.cb_currency);
                CurrencyList currencyList = (CurrencyList) parent.getAdapter().getItem(position);


                // UnCheck last Checked CheckBox
                if (lastChecked_CB != null) {
                    lastChecked_CB.setChecked(false);
                }

                currentChecked_CB.setChecked(true);
                lastChecked_CB = currentChecked_CB;


                selectedLanguageID = currencyList.getCode();
                // selectedLanguageCode = language.getCode();
            }
        });
*/

    }

    private void setupBannersSelectionRv() {
        bannersRv.setLayoutManager(new LinearLayoutManager(getContext()));
        bannersSelectionAdapter = new BannersSelectionAdapter(getContext(), bannerStyles, this);
        bannersRv.setAdapter(bannersSelectionAdapter);

        bannerStyles.add("1");
        bannerStyles.add("2");
        bannerStyles.add("3");
        bannerStyles.add("4");
        bannerStyles.add("5");
        bannerStyles.add("6");

        bannersSelectionAdapter.setEmployees(bannerStyles);

    }

    private void setupCardStyleSelectionRv() {
        cardStyleRv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        cardStyleSelectionAdapter = new CardStyleSelectionAdapter(getContext(), cardStyles, this);
        cardStyleRv.setAdapter(cardStyleSelectionAdapter);

        cardStyles.add("1");
        cardStyles.add("2");
        cardStyles.add("3");
        cardStyles.add("4");
        cardStyles.add("5");
        cardStyles.add("7");
        cardStyles.add("8");
        cardStyles.add("9");
        cardStyles.add("10");
        cardStyles.add("11");
        cardStyles.add("12");
        cardStyles.add("13");
        cardStyles.add("14");
        cardStyles.add("15");
        cardStyles.add("16");
        cardStyles.add("17");
        cardStyles.add("18");
        cardStyles.add("19");
        cardStyles.add("20");
        cardStyles.add("21");

        cardStyleSelectionAdapter.setEmployees(cardStyles);

    }

    private void recreateActivity() {
        Intent intent = getActivity().getIntent();
        getActivity().finish();
        startActivity(intent);
    }

    @Override
    public void onLanguageClicked(LanguageDetails languageDetails) {
        appPrefs.setUserLanguageCode(languageDetails.getCode());
        appPrefs.setUserLanguageId(Integer.parseInt(languageDetails.getLanguagesId()));

        ConstantValues.LANGUAGE_ID = Integer.parseInt(languageDetails.getLanguagesId());
        ConstantValues.LANGUAGE_CODE = languageDetails.getCode();

        ChangeLanguageTask changeLanguageTask = new ChangeLanguageTask();
        changeLanguageTask.execute();
    }

    @Override
    public void onCurrencyClickListener(CurrencyList currencyDetails) {
        appPrefs.setCurrencyCode(selectedCurrencyID);
        ConstantValues.CURRENCY_CODE = currencyDetails.getCode();
        ConstantValues.CURRENCY_SYMBOL = Utilities.getCurrencySymbol(currencyDetails.getCode());
        ChangeCurrencyTask changeCurrencyTask = new ChangeCurrencyTask();
        changeCurrencyTask.execute();
    }

    @Override
    public void onColorClicked(ColorsModal colorsModal) {
        Toast.makeText(getContext(), colorsModal.getColorName(), Toast.LENGTH_SHORT).show();
        ConstantValues.THEME_ID = colorsModal.getThemeID();
        recreateActivity();
    }

    @Override
    public void onBannerClicked(String bannerDetail, int position) {
        ConstantValues.DEFAULT_BANNER_STYLE = position;
        AppSettingsDetails appSettingsDetails = ((App) getContext().getApplicationContext()).getAppSettingsDetails();
        appSettingsDetails.setBannerStyle(String.valueOf(position));
        ((App) getContext().getApplicationContext()).setAppSettingsDetails(appSettingsDetails);
        //((App) getContext().getApplicationContext()).setBannersList(new ArrayList<BannerDetails>());
        recreateActivity();
    }

    @Override
    public void onCardStyleClicked(String cardStyle, int position) {
        ConstantValues.DEFAULT_PRODUCT_CARD_STYLE = position;
        AppSettingsDetails appSettingsDetails = ((App) getContext().getApplicationContext()).getAppSettingsDetails();
        appSettingsDetails.setCardStyle(String.valueOf(position));
        ((App) getContext().getApplicationContext()).setAppSettingsDetails(appSettingsDetails);
        recreateActivity();
    }

    private class ChangeLanguageTask extends AsyncTask<Void, Void, Void> {

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
            My_Cart.ClearCart();
            ((App) getContext().getApplicationContext()).setBannersList(new ArrayList<BannerDetails>());
            ((App) getContext().getApplicationContext()).setCategoriesList(new ArrayList<CategoryDetails>());
            recreateActivity();
        }
    }

    private class ChangeCurrencyTask extends AsyncTask<Void, Void, Void> {

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
            My_Cart.ClearCart();
            ((App) getContext().getApplicationContext()).setBannersList(new ArrayList<BannerDetails>());
            ((App) getContext().getApplicationContext()).setCategoriesList(new ArrayList<CategoryDetails>());
            recreateActivity();
        }
    }
}
