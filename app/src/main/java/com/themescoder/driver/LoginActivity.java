package com.themescoder.driver;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.themescoder.driver.R;
import com.themescoder.driver.api.RetrofitClient;
import com.themescoder.driver.api.ServerData;
import com.themescoder.driver.dialogs.OTPDialog;
import com.themescoder.driver.models.DriverDetails;
import com.themescoder.driver.utils.DialogUtils;
import com.themescoder.driver.utils.PreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.onesignal.OneSignal;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.loginButton)
    AppCompatButton loginButton;

    @BindView(R.id.pincode)
    EditText pincode;

    private Context context;

    private String driverPin;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            // User is NOT logged in..
        } else {
            // User is logged in.
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        context = LoginActivity.this;

        //Toolbar
        toolbar.setTitle(getString(R.string.title_login));
        setSupportActionBar(toolbar);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callLoginApi();

            }
        });
        pincode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    callLoginApi();
                    return true;
                }
                return false;
            }
        });
    }

    public void callLoginApi() {

//      Validation..
        if (pincode.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, getString(R.string.invalid_pincode), Toast.LENGTH_SHORT).show();
            return;
        }
        pincode.setEnabled(false);
        DialogUtils.showProgressDialog(this);
        driverPin = pincode.getText().toString().trim();

        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {

                Call<DriverDetails> call = RetrofitClient.getInstance().getUser(driverPin, userId);
                call.enqueue(new Callback<DriverDetails>() {
                    @Override
                    public void onResponse(Call<DriverDetails> call, Response<DriverDetails> response) {
                        DialogUtils.hideProgressDialog();
                        if (response.isSuccessful()) {
                            if (response.body().getSuccess().equals("1")) {

                                ServerData.currentDriver = response.body();
                                showOTPDialog();
                                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                        ServerData.currentDriver.getData().get(0).getPhone(),
                                        60,
                                        TimeUnit.SECONDS,
                                        TaskExecutors.MAIN_THREAD,
                                        mCallbacks);

                            } else {
                                Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                        }
                        pincode.setEnabled(true);
                    }

                    @Override
                    public void onFailure(Call<DriverDetails> call, Throwable t) {
                        DialogUtils.hideProgressDialog();
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        pincode.setEnabled(true);
                    }
                });
            }
        });
    }

    private EditText editTextOTP;
    private void showOTPDialog() {
        final OTPDialog cdd = new OTPDialog(LoginActivity.this);
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.show();
        editTextOTP = cdd.otpEditText;
        cdd.otpEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    if (editTextOTP.getText().toString().trim().isEmpty()){
                        Toast.makeText(context, "Please Enter OTP Code", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    verifyVerificationCode(cdd.otpEditText.getText().toString());
                    return true;
                }
                return false;
            }
        });
        cdd.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextOTP.getText().toString().trim().isEmpty()){
                    Toast.makeText(context, "Please Enter OTP Code", Toast.LENGTH_SHORT).show();
                    return;
                }
                verifyVerificationCode(cdd.otpEditText.getText().toString());
            }
        });
    }

    private String mVerificationId;
    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                editTextOTP.setText(code);
                verifyVerificationCode(code);
            } else {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };

    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            doLogin();
                        } else {
                            String message = "Somthing is wrong, we will fix it soon...";
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void doLogin() {
        PreferencesUtils.putBoolean(context, PreferencesUtils.LOGED_IN_USER, true);
        PreferencesUtils.putString(context, PreferencesUtils.CURRUNT_DRIVER_PIN_CODE, driverPin);
        LoginActivity.this.startActivity(new Intent(context, MainActivity.class));
        LoginActivity.this.finish();
    }
}
