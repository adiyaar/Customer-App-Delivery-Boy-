package com.themescoder.androidstore.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
/*
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
*/
import com.google.firebase.iid.FirebaseInstanceId;
import com.themescoder.androidstore.models.user_model.UserData;
import com.themescoder.androidstore.utils.ValidateInputs;
import com.onesignal.OneSignal;
import com.themescoder.androidstore.R;

import com.themescoder.androidstore.activities.Login;
import com.themescoder.androidstore.activities.MainActivity;
import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.customs.CircularImageView;
import com.themescoder.androidstore.customs.DialogLoader;
import com.themescoder.androidstore.databases.User_Info_DB;
import com.themescoder.androidstore.models.contact_model.ContactUsData;
import com.themescoder.androidstore.models.user_model.UserDetails;
import com.themescoder.androidstore.network.APIClient;
import com.themescoder.androidstore.app.MyAppPrefsManager;
import com.themescoder.androidstore.receivers.AlarmReceiver;
import com.themescoder.androidstore.utils.NotificationScheduler;
import com.themescoder.androidstore.utils.Utilities;

import am.appwise.components.ni.NoInternetDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class SettingsFragment extends Fragment {

    View rootView;

    DialogLoader dialogLoader;
    MyAppPrefsManager appPrefs;
    SharedPreferences sharedPreferences;

    CircularImageView profile_image;
    Button btn_edit_profile, btn_logout;
    TextView profile_name, profile_email;
    Switch local_notification, push_notification;
    TextView edit_account, change_password, select_language, official_web, share_app, rate_app, privacy_policy, refund_policy, service_terms, test_ad_interstitial,
            a_z_terms;

    EditText oldPassword;
    EditText newPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.settings, container, false);

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        // noInternetDialog.show();

        dialogLoader = new DialogLoader(getContext());
        appPrefs = new MyAppPrefsManager(getContext());
        sharedPreferences = getContext().getSharedPreferences("UserInfo", MODE_PRIVATE);

        //MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.actionSettings));


        // Binding Layout Views
        rate_app = (TextView) rootView.findViewById(R.id.rate_app);
        share_app = (TextView) rootView.findViewById(R.id.share_app);
        official_web = (TextView) rootView.findViewById(R.id.official_web);
        refund_policy = (TextView) rootView.findViewById(R.id.refund_policy);
        service_terms = (TextView) rootView.findViewById(R.id.service_terms);
        a_z_terms = (TextView) rootView.findViewById(R.id.a_z_terms);
        privacy_policy = (TextView) rootView.findViewById(R.id.privacy_policy);
        change_password = (TextView) rootView.findViewById(R.id.change_password);
        edit_account = (TextView) rootView.findViewById(R.id.edit_account);
        select_language = (TextView) rootView.findViewById(R.id.select_language);
        test_ad_interstitial = (TextView) rootView.findViewById(R.id.test_ad_interstitial);
        push_notification = (Switch) rootView.findViewById(R.id.switch_push_notification);
        local_notification = (Switch) rootView.findViewById(R.id.switch_local_notification);

        btn_logout = (Button) rootView.findViewById(R.id.btn_logout);
        btn_edit_profile = (Button) rootView.findViewById(R.id.btn_edit_account);
        profile_name = (TextView) rootView.findViewById(R.id.profile_name);
        profile_email = (TextView) rootView.findViewById(R.id.profile_email);
        profile_image = (CircularImageView) rootView.findViewById(R.id.profile_image);


        setupAppBarHeader();

        if (!ConstantValues.IS_USER_LOGGED_IN) {
            btn_logout.setText(getString(R.string.login));
        }


        local_notification.setChecked(appPrefs.isLocalNotificationsEnabled());
        push_notification.setChecked(appPrefs.isPushNotificationsEnabled());


        local_notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                appPrefs.setLocalNotificationsEnabled(isChecked);
                ConstantValues.IS_LOCAL_NOTIFICATIONS_ENABLED = appPrefs.isLocalNotificationsEnabled();

                if (isChecked) {
                    NotificationScheduler.setReminder(getContext(), AlarmReceiver.class);
                } else {
                    NotificationScheduler.cancelReminder(getContext(), AlarmReceiver.class);
                }

            }
        });


        push_notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                appPrefs.setPushNotificationsEnabled(isChecked);
                ConstantValues.IS_PUSH_NOTIFICATIONS_ENABLED = appPrefs.isPushNotificationsEnabled();

                TogglePushNotification(ConstantValues.IS_PUSH_NOTIFICATIONS_ENABLED);
            }
        });

/*
        if (!ConstantValues.IS_CLIENT_ACTIVE)

            if (ConstantValues.IS_ADMOBE_ENABLED) {
                // Initialize Admobe
                final AdView mAdView = rootView.findViewById(R.id.adView);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
                mAdView.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        mAdView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        super.onAdFailedToLoad(i);
                        mAdView.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Failed to Load BannerAd", Toast.LENGTH_SHORT).show();
                    }
                });


                test_ad_interstitial.setVisibility(View.VISIBLE);
                test_ad_interstitial.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Initialize InterstitialAd
                        final InterstitialAd mInterstitialAd = new InterstitialAd(getActivity());
                        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                        mInterstitialAd.setAdListener(new AdListener() {
                            @Override
                            public void onAdLoaded() {
                                mInterstitialAd.show();
                            }

                            @Override
                            public void onAdFailedToLoad(int i) {
                                super.onAdFailedToLoad(i);
                                Toast.makeText(getContext(), "Failed to Load InterstitialAd", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
*/

        edit_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update_Account update_account = new Update_Account(true);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().add(R.id.main_fragment, update_account)
                        .addToBackStack(null).commit();
            }
        });
        if (ConstantValues.IS_USER_LOGGED_IN) {
            edit_account.setVisibility(View.VISIBLE);
        } else {
            edit_account.setVisibility(View.GONE);
        }
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog fullscreenDialog = new Dialog(getContext());
                fullscreenDialog.setContentView(R.layout.dialog_change_password);

                oldPassword = fullscreenDialog.findViewById(R.id.current_password);
                newPassword = fullscreenDialog.findViewById(R.id.new_password);
                Button saveButton = fullscreenDialog.findViewById(R.id.dialog_button);

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (validatePasswordForm()) {
                            dialogLoader.showProgressDialog();
                            Call<UserData> call = APIClient.getInstance().updatePassword(oldPassword.getText().toString().trim(),
                                    newPassword.getText().toString().trim(),
                                    getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "")
                            );

                            call.enqueue(new Callback<UserData>() {
                                @Override
                                public void onResponse(Call<UserData> call, Response<UserData> response) {
                                    if (response.isSuccessful()) {
                                        if (response.body().getSuccess().equalsIgnoreCase("1") && response.body().getData() != null) {
                                            // User's Info has been Updated.
                                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        } else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                                            // Unable to Update User's Info.
                                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        } else if (response.body().getSuccess().equalsIgnoreCase("2")) {
                                            // Unable to Update User's Info.
                                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        } else {
                                            // Unable to get Success status
                                            Toast.makeText(getContext(), getString(R.string.unexpected_response), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                                    }
                                    dialogLoader.hideProgressDialog();
                                }

                                @Override
                                public void onFailure(Call<UserData> call, Throwable t) {
                                    Toast.makeText(getContext(), "NetworkCallFailure : " + t, Toast.LENGTH_LONG).show();
                                    dialogLoader.hideProgressDialog();
                                }
                            });

                        } else {

                            Toast.makeText(getContext(), "Invalid Password", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

                fullscreenDialog.show();

            }
        });

        select_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Languages Fragment
                Fragment fragment = new Languages();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().add(R.id.main_fragment, fragment)
                        .addToBackStack(null).commit();
            }
        });


        official_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String web_url = ((App) getActivity().getApplicationContext()).getAppSettingsDetails().getSiteUrl();
                if (!web_url.startsWith("https://") && !web_url.startsWith("http://"))
                    web_url = "http://" + web_url;

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(web_url)));
            }
        });

        share_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.shareMyApp(getContext());
            }
        });

        rate_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.rateMyApp(getContext());
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
                        "body{background:#ffffff; margin:10; padding:10} " +
                        "p{color:#757575;} " +
                        "img{display:inline; height:auto; max-width:100%;}" +
                        "</style>";

                dialog_webView.setHorizontalScrollBarEnabled(false);
                dialog_webView.loadDataWithBaseURL(null, styleSheet + description, "text/html", "utf-8", null);


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
                        "body{background:#ffffff; margin:10; padding:10} " +
                        "p{color:#757575;} " +
                        "img{display:inline; height:auto; max-width:100%;}" +
                        "</style>";

                dialog_webView.setHorizontalScrollBarEnabled(false);
                dialog_webView.loadDataWithBaseURL(null, styleSheet + description, "text/html", "utf-8", null);


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
                dialog_webView.loadDataWithBaseURL(null, styleSheet + description, "text/html", "utf-8", null);


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
                        "body{background:#ffffff; margin:10; padding:10} " +
                        "p{color:#757575;} " +
                        "img{display:inline; height:auto; max-width:100%;}" +
                        "</style>";

                dialog_webView.setHorizontalScrollBarEnabled(false);
                dialog_webView.loadDataWithBaseURL(null, styleSheet + description, "text/html", "utf-8", null);


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


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ConstantValues.IS_USER_LOGGED_IN) {
                    // Edit UserID in SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userID", "");
                    editor.apply();

                    // Set UserLoggedIn in MyAppPrefsManager
                    MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(getContext());
                    myAppPrefsManager.setUserLoggedIn(false);

                    // Set isLogged_in of ConstantValues
                    ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();

                    setupAppBarHeader();
                    ((MainActivity) getContext()).setupExpandableDrawerList();
                    ((MainActivity) getContext()).setupExpandableDrawerHeader();


                    btn_logout.setText(getString(R.string.login));

                } else {
                    // Navigate to Login Activity
                    startActivity(new Intent(getContext(), Login.class));
                    ((MainActivity) getContext()).finish();
                    ((MainActivity) getContext()).overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
                }
            }
        });


        return rootView;
    }


    //*********** Setup Header of Navigation Drawer ********//

    public void setupAppBarHeader() {

        // Check if the User is Authenticated
        if (ConstantValues.IS_USER_LOGGED_IN) {
            // Check User's Info from SharedPreferences
            if (!"".equalsIgnoreCase(sharedPreferences.getString("userID", ""))) {

                // Get User's Info from Local Database User_Info_DB
                User_Info_DB userInfoDB = new User_Info_DB();
                UserDetails userInfo = userInfoDB.getUserData(sharedPreferences.getString("userID", null));

                // Set User's Name, Email and Photo
                profile_email.setText(userInfo.getEmail());
                profile_name.setText(userInfo.getFirstName() + " " + userInfo.getLastName());

                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.profile)
                        .error(R.drawable.profile)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH);
                Glide.with(getContext())
                        .setDefaultRequestOptions(options)
                        .asBitmap()
                        .load(ConstantValues.ECOMMERCE_URL + userInfo.getAvatar())
                        .into(profile_image);

                btn_edit_profile.setText(getString(R.string.edit_profile));
                btn_edit_profile.setBackground(getResources().getDrawable(R.drawable.rounded_corners_button_green));

                btn_edit_profile.setVisibility(View.GONE);

            } else {
                // Set Default Name, Email and Photo
                profile_image.setImageResource(R.drawable.profile);
                profile_name.setText(getString(R.string.login_or_signup));
                profile_email.setText(getString(R.string.login_or_create_account));
                btn_edit_profile.setText(getString(R.string.login));
                btn_edit_profile.setBackground(getResources().getDrawable(R.drawable.rounded_corners_button_red));
            }

        } else {
            // Set Default Name, Email and Photo
            profile_image.setImageResource(R.drawable.profile);
            profile_name.setText(getString(R.string.login_or_signup));
            profile_email.setText(getString(R.string.login_or_create_account));
            btn_edit_profile.setText(getString(R.string.login));
            btn_edit_profile.setBackground(getResources().getDrawable(R.drawable.rounded_corners_button_red));
            change_password.setVisibility(View.GONE);
        }


        // Handle DrawerHeader Click Listener
        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check if the User is Authenticated
                if (ConstantValues.IS_USER_LOGGED_IN) {

                    // Navigate to Update_Account Fragment
                    Fragment fragment = new Update_Account(true);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(getString(R.string.actionCart)).commit();

                } else {
                    // Navigate to Login Activity
                    startActivity(new Intent(getContext(), Login.class));
                    ((MainActivity) getContext()).finish();
                    ((MainActivity) getContext()).overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
                }
            }
        });
    }


    /*********** Request Server to Enable or Disable Notifications ********/

    public void TogglePushNotification(Boolean enable) {

        dialogLoader.showProgressDialog();

        String notify = "1";
        if (!enable) {
            notify = "0";
        }

        String deviceID = "";

        if (ConstantValues.DEFAULT_NOTIFICATION.equalsIgnoreCase("onesignal")) {
            deviceID = OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getUserId();
        } else if (ConstantValues.DEFAULT_NOTIFICATION.equalsIgnoreCase("fcm")) {
            deviceID = FirebaseInstanceId.getInstance().getToken();
        }


        Call<ContactUsData> call = APIClient.getInstance()
                .notify_me
                        (
                                notify,
                                deviceID
                        );

        call.enqueue(new Callback<ContactUsData>() {
            @Override
            public void onResponse(Call<ContactUsData> call, retrofit2.Response<ContactUsData> response) {

                dialogLoader.hideProgressDialog();

                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();

                    } else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_LONG).show();

                    } else {
                        // Unable to get Success status
                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ContactUsData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(getContext(), "NetworkCallFailure : " + t, Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validatePasswordForm() {
        if (!ValidateInputs.isValidPassword(newPassword.getText().toString().trim())) {
            newPassword.setError(getString(R.string.invalid_password));
            return false;
        } else {
            return true;
        }
    }
}
