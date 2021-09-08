package com.themescoder.driver.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.themescoder.driver.AccountInfoActivity;
import com.themescoder.driver.LegalContectsActivity;
import com.themescoder.driver.LoginActivity;
import com.themescoder.driver.MainActivity;
import com.themescoder.driver.R;
import com.themescoder.driver.api.RetrofitClient;
import com.themescoder.driver.api.ServerData;
import com.themescoder.driver.dialogs.LogoutPromptDialog;
import com.themescoder.driver.models.DriverDetails;
import com.themescoder.driver.models.StatusResponse;
import com.themescoder.driver.models.WithDrawAmountResponce;
import com.themescoder.driver.utils.DialogUtils;
import com.themescoder.driver.utils.PreferencesUtils;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Settings extends Fragment {


    private CircleImageView profilePicture;
    private TextView name;
    private TextView email;
    private AppCompatButton withDrawButton;

    private TextView settingsDashboard;
    private TextView settingsHistory;
    private TextView settingsLegal;
    private TextView payoutsButton;
    private TextView accountInfo;
    private TextView userAmountText;
    private TextView floatingCashText;

    public Settings() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity.refreshbutton.setVisibility(View.GONE);
        MainActivity.statusLayout.setVisibility(View.VISIBLE);
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_settings, container, false);
        // Logout Button
        view.findViewById(R.id.logoutbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LogoutPromptDialog dialog = new LogoutPromptDialog(getActivity());
                dialog.show();
                dialog.logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        DialogUtils.showProgressDialog(getActivity());
                        Call<StatusResponse> call = RetrofitClient.getInstance().changeStatus("0", ServerData.currentDriver.getData().get(0).getPassword()+"");
                        call.enqueue(new Callback<StatusResponse>() {
                            @Override
                            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                                DialogUtils.hideProgressDialog();
                                if (response.isSuccessful() && response.body().getSuccess().equals("1")){
                                    FirebaseAuth.getInstance().signOut();
                                    PreferencesUtils.putBoolean(getActivity(), PreferencesUtils.LOGED_IN_USER, false);
                                    PreferencesUtils.putString(getActivity(), PreferencesUtils.ONLNE_STATUS, getString(R.string.offline));
                                    startActivity(new Intent(getActivity(), LoginActivity.class));
                                    getActivity().finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<StatusResponse> call, Throwable t) {
                                DialogUtils.hideProgressDialog();
                            }
                        });
                    }
                });

            }
        });

        updateUserBallance();
        //Dashboard Click Listener
        settingsDashboard = view.findViewById(R.id.settings_dashboard);
        settingsDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Dashboard dashboardFragment = new Dashboard();
                fragmentTransaction.addToBackStack("xyz");
                fragmentTransaction.hide(Settings.this);
                fragmentTransaction.add(R.id.flContent, dashboardFragment);
                fragmentTransaction.commit();
                BottomNavigationView navigationView = getActivity().findViewById(R.id.bottom_navigation);
                navigationView.setSelectedItemId(R.id.navigation_dashboard);
            }
        });
        //History Click Listener
        settingsHistory = view.findViewById(R.id.settings_history);
        settingsHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                History historyFragment = new History();
                fragmentTransaction.addToBackStack("xyz");
                fragmentTransaction.hide(Settings.this);
                fragmentTransaction.add(R.id.flContent, historyFragment);
                fragmentTransaction.commit();
                BottomNavigationView navigationView = getActivity().findViewById(R.id.bottom_navigation);
                navigationView.setSelectedItemId(R.id.navigation_history);
            }
        });
        settingsLegal = view.findViewById(R.id.legalButton);
        settingsLegal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showLegalStatementsDialog(getActivity());
            }
        });
        payoutsButton = view.findViewById(R.id.payoutsButton);
        payoutsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LegalContectsActivity.class);
                intent.putExtra("ITEM_CLICKED", "payouts");
                getContext().startActivity(intent);
            }
        });
        accountInfo = view.findViewById(R.id.accountinfo);
        accountInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), AccountInfoActivity.class));
            }
        });

        profilePicture = view.findViewById(R.id.profile_image);

        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        withDrawButton = view.findViewById(R.id.withDrawButton);
        userAmountText =  view.findViewById(R.id.userAmountText);
        floatingCashText = view.findViewById(R.id.floatingCashText);

        withDrawButton.setVisibility(View.GONE);
        if (ServerData.currentDriver == null){
            Toast.makeText(getActivity(), "Null", Toast.LENGTH_SHORT).show();
            return view;
        }
        Glide.with(getActivity())
                .load(RetrofitClient.BASE_URL_RES +""+ ServerData.currentDriver.getData().get(0).getAvatar())
                .into(profilePicture);
        name.setText(ServerData.currentDriver.getData().get(0).getFirstName() + " " + ServerData.currentDriver.getData().get(0).getLastName());
        email.setText(ServerData.currentDriver.getData().get(0).getEmail());
        userAmountText.setText(ServerData.settingsData.getCurrencySymbol() + String.format("%.2f", Double.valueOf(ServerData.currentDriver.getData().get(0).getBalance())));
        floatingCashText.setText(ServerData.settingsData.getCurrencySymbol() + String.format("%.2f", Double.valueOf(ServerData.currentDriver.getData().get(0).getFlostingCash())));
        withDrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ServerData.currentDriver.getData().get(0).getBalance() > 0) {
                    Call<WithDrawAmountResponce> call = RetrofitClient.getInstance().withdrawAmount(
                            ServerData.currentDriver.getData().get(0).getPassword() + "",
                            ServerData.currentDriver.getData().get(0).getBalance()+"",
                            ""
                    );
                    DialogUtils.showProgressDialog(getActivity());
                    call.enqueue(new Callback<WithDrawAmountResponce>() {
                        @Override
                        public void onResponse(Call<WithDrawAmountResponce> call, Response<WithDrawAmountResponce> response) {
                            DialogUtils.hideProgressDialog();
                            if (response.isSuccessful()) {
                                if (response.body().getSuccess().equalsIgnoreCase("1")) {

                                    Toast.makeText(getContext(), "Request has been submitted", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<WithDrawAmountResponce> call, Throwable t) {
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            DialogUtils.hideProgressDialog();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "You don't have enough amount to withdraw", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    private void updateUserBallance() {
        Call<DriverDetails> call = RetrofitClient.getInstance().updateUserInfo(
                ServerData.currentDriver.getData().get(0).getPassword()+""
        );
        call.enqueue(new Callback<DriverDetails>() {
            @Override
            public void onResponse(Call<DriverDetails> call, Response<DriverDetails> response) {
                if (response.isSuccessful()){
                    if (response.body().getSuccess().equalsIgnoreCase("1")){
                        ServerData.currentDriver = response.body();
                        userAmountText.setText(ServerData.settingsData.getCurrencySymbol() + String.format("%.2f", Double.valueOf(ServerData.currentDriver.getData().get(0).getBalance())));
                        floatingCashText.setText(ServerData.settingsData.getCurrencySymbol() + String.format("%.2f", Double.valueOf(ServerData.currentDriver.getData().get(0).getFlostingCash())));
                    }
                }
            }

            @Override
            public void onFailure(Call<DriverDetails> call, Throwable t) {

            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }



}
