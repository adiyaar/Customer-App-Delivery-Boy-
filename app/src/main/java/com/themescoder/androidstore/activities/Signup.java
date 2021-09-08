package com.themescoder.androidstore.activities;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/*
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
*/
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import com.themescoder.androidstore.customs.CircularImageView;

import com.themescoder.androidstore.R;

import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.customs.DialogLoader;
import com.themescoder.androidstore.utils.CheckPermissions;
import com.themescoder.androidstore.utils.LocaleHelper;
import com.themescoder.androidstore.models.user_model.UserData;
import com.themescoder.androidstore.network.APIClient;

import com.themescoder.androidstore.utils.ImagePicker;
import com.themescoder.androidstore.utils.ValidateInputs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import am.appwise.components.ni.NoInternetDialog;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * SignUp activity handles User's Registration
 **/

public class Signup extends AppCompatActivity {

    View parentView;
    File profileImage;
    private static final int PICK_IMAGE_ID = 360;           // the number doesn't matter

    Toolbar toolbar;
    ActionBar actionBar;

    DialogLoader dialogLoader;

    //AdView mAdView;
    Button signupBtn;
    FrameLayout banner_adView;
    TextView signup_loginText;
    TextView service_terms, privacy_policy, refund_policy, and_text;
    CircularImageView user_photo;
    //FloatingActionButton user_photo_edit_fab;
    EditText user_firstname, user_lastname, user_email, user_password, user_mobile, input_dob;
    Spinner genderSpinner;

    //Custom Dialog Vies
    Dialog dialogOTP;
    EditText ed_otp;
    //It is the verification id that will be sent to the user
    private String mVerificationId;
    //firebase auth object
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ConstantValues.THEME_ID);
        setContentView(R.layout.signup);

        //MobileAds.initialize(this, ConstantValues.ADMOBE_ID);

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(Signup.this).build();
        //noInternetDialog.show();

        //initializing objects
        mAuth = FirebaseAuth.getInstance();
        // setting Toolbar
        toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.signup));
        actionBar.setDisplayHomeAsUpEnabled(true);


        // Binding Layout Views
        user_photo = (CircularImageView) findViewById(R.id.user_photo);
        user_firstname = (EditText) findViewById(R.id.user_firstname);
        user_lastname = (EditText) findViewById(R.id.user_lastname);
        user_email = (EditText) findViewById(R.id.user_email);
        user_password = (EditText) findViewById(R.id.user_password);
        user_mobile = (EditText) findViewById(R.id.user_mobile);
        signupBtn = (Button) findViewById(R.id.signupBtn);
        and_text = (TextView) findViewById(R.id.and);
        service_terms = (TextView) findViewById(R.id.service_terms);
        privacy_policy = (TextView) findViewById(R.id.privacy_policy);
        refund_policy = (TextView) findViewById(R.id.refund_policy);
        signup_loginText = (TextView) findViewById(R.id.signup_loginText);
        banner_adView = (FrameLayout) findViewById(R.id.banner_adView);
        input_dob = (EditText) findViewById(R.id.et_dob);
        //user_photo_edit_fab = (FloatingActionButton) findViewById(R.id.user_photo_edit_fab);
        genderSpinner = findViewById(R.id.genderSpinner);

/*
        if (ConstantValues.IS_ADMOBE_ENABLED) {
            // Initialize Admobe
            mAdView = new AdView(Signup.this);
            mAdView.setAdSize(AdSize.BANNER);
            mAdView.setAdUnitId(ConstantValues.AD_UNIT_ID_BANNER);
            AdRequest adRequest = new AdRequest.Builder().build();
            banner_adView.addView(mAdView);
            mAdView.loadAd(adRequest);
            mAdView.setAdListener(new AdListener() {
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

        input_dob.setKeyListener(null);
        // Handle Touch event of input_dob EditText
        input_dob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Get Calendar instance
                    final Calendar calendar = Calendar.getInstance();

                    // Initialize DateSetListener of DatePickerDialog
                    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            // Set the selected Date Info to Calendar instance
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, monthOfYear);
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                            // Set Date Format
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

                            // Set Date in input_dob EditText
                            input_dob.setText(dateFormat.format(calendar.getTime()));
                        }
                    };


                    // Initialize DatePickerDialog
                    DatePickerDialog datePicker = new DatePickerDialog
                            (
                                    Signup.this,
                                    date,
                                    calendar.get(Calendar.YEAR),
                                    calendar.get(Calendar.MONTH),
                                    calendar.get(Calendar.DAY_OF_MONTH)
                            );
                    datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                    // Show datePicker Dialog
                    datePicker.show();
                }

                return false;
            }
        });


        dialogLoader = new DialogLoader(Signup.this);

        and_text.setText(" " + getString(R.string.and) + " ");


        privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Signup.this, android.R.style.Theme_NoTitleBar);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_webview_fullscreen, null);
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
                dialog_webView.loadDataWithBaseURL(null, styleSheet + description, "text/html", "utf-8", null);


                final AlertDialog alertDialog = dialog.create();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    alertDialog.getWindow().setStatusBarColor(ContextCompat.getColor(Signup.this, R.color.colorPrimaryDark));
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(Signup.this, android.R.style.Theme_NoTitleBar);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_webview_fullscreen, null);
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
                dialog_webView.loadDataWithBaseURL(null, styleSheet + description, "text/html", "utf-8", null);


                final AlertDialog alertDialog = dialog.create();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    alertDialog.getWindow().setStatusBarColor(ContextCompat.getColor(Signup.this, R.color.colorPrimaryDark));
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(Signup.this, android.R.style.Theme_NoTitleBar);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_webview_fullscreen, null);
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
                dialog_webView.loadDataWithBaseURL(null, styleSheet + description, "text/html", "utf-8", null);


                final AlertDialog alertDialog = dialog.create();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    alertDialog.getWindow().setStatusBarColor(ContextCompat.getColor(Signup.this, R.color.colorPrimaryDark));
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


        // Handle Click event of user_photo_edit_fab FAB
        /*
        user_photo_edit_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckPermissions.is_CAMERA_PermissionGranted()  &&  CheckPermissions.is_STORAGE_PermissionGranted()) {
                    pickImage();
                }
                else {
                    ActivityCompat.requestPermissions
                            (
                                    Signup.this,
                                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    CheckPermissions.PERMISSIONS_REQUEST_CAMERA
                            );
                }
            }
        });
        */


        // Handle Click event of signup_loginText TextView
        signup_loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish SignUpActivity to goto the LoginActivity
                finish();
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
            }
        });

        // Handle Click event of signupBtn Button
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate Login Form Inputs
                boolean isValidData = validateForm();

                if (isValidData) {
                    parentView = v;

                    // Proceed User Registration

                    hideKeyboardFrom(Signup.this);

                    processRegistration();
                    //sendVerificationCode(getResources().getString(R.string.indian_code)+user_mobile.getText().toString().trim());
                }
            }
        });
    }

    //*********** Picks User Profile Image from Gallery or Camera ********//

    private void pickImage() {
        // Intent with Image Picker Apps from the static method of ImagePicker class
        Intent chooseImageIntent = ImagePicker.getImagePickerIntent(Signup.this);
        chooseImageIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        chooseImageIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        // Start Activity with Image Picker Intent
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);

    }


    //*********** Receives the result from a previous call of startActivityForResult(Intent, int) ********//

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Handle Activity Result
            if (requestCode == PICK_IMAGE_ID) {

                // Get the User Selected Image as Bitmap from the static method of ImagePicker class
                Bitmap bitmap = ImagePicker.getImageFromResult(Signup.this, resultCode, data);

                // Upload the Bitmap to ImageView
                user_photo.setImageBitmap(bitmap);

                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri tempUri = getImageUri(getApplicationContext(), bitmap);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                profileImage = new File(getRealPathFromURI(tempUri));
    
                /*File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
                
                // Get the converted Bitmap as Base64ImageString from the static method of Helper class
                profileImage = Utilities.getBase64ImageStringFromBitmap(bitmap);*/

            }
        }
    }

    // Getting image URI
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    // Get absolute image path
    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    /// Custom dialog for OTP
    public void showOTPDialog(Activity activity, String msg) {
        dialogOTP = new Dialog(activity);
        dialogOTP.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogOTP.setCancelable(false);
        dialogOTP.setContentView(R.layout.dialog_otp);

        ed_otp = dialogOTP.findViewById(R.id.ed_otp);
        AppCompatButton btn_resend, btn_submit;
        btn_resend = dialogOTP.findViewById(R.id.btn_resend);
        btn_submit = dialogOTP.findViewById(R.id.btn_submit);

        btn_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationCode(user_mobile.getText().toString().trim());
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ed_otp.getText().toString().trim().isEmpty()) {
                    verifyVerificationCode(ed_otp.getText().toString().trim());
                }
            }
        });

        dialogOTP.show();
    }

    //the method is sending verification code
    //the country id is concatenated
    //you can take the country id as user input as well
    private void sendVerificationCode(String mobile) {

        showOTPDialog(Signup.this, "");

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }


    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {

                ed_otp.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(Signup.this, e.getMessage(), Toast.LENGTH_LONG).show();
            dialogOTP.dismiss();
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

    //*********** This method is invoked for every call on requestPermissions(Activity, String[], int) ********//

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity
                            dialogOTP.dismiss();

                            //Final Registration Call to API
                            processRegistration();
                        } else {
                            //verification unsuccessful.. display an error message
                            String message = "Somthing is wrong, we will fix it soon...";
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }
                            Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
                            snackbar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            snackbar.show();
                        }
                    }
                });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CheckPermissions.PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // The Camera and Storage Permission is granted
                pickImage();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(Signup.this, Manifest.permission.CAMERA)) {
                    // Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this);
                    builder.setTitle(getString(R.string.permission_camera_storage));
                    builder.setMessage(getString(R.string.permission_camera_storage_needed));
                    builder.setPositiveButton(getString(R.string.grant), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions
                                    (
                                            Signup.this,
                                            new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                            CheckPermissions.PERMISSIONS_REQUEST_CAMERA
                                    );
                        }
                    });
                    builder.setNegativeButton(getString(R.string.not_now), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(Signup.this, getString(R.string.permission_rejected), Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    //*********** Proceed User Registration Request ********//

    private void processRegistration() {

        dialogLoader.showProgressDialog();

        RequestBody fname = RequestBody.create(MediaType.parse("text/plain"), user_firstname.getText().toString().trim());
        RequestBody lname = RequestBody.create(MediaType.parse("text/plain"), user_lastname.getText().toString().trim());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), user_email.getText().toString().trim());
        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), user_password.getText().toString().trim());
        RequestBody customers_telephone = RequestBody.create(MediaType.parse("text/plain"), user_mobile.getText().toString().trim());
        RequestBody country_code = RequestBody.create(MediaType.parse("text/plain"), getResources().getString(R.string.indian_code));
        //RequestBody customers_gender = RequestBody.create(MediaType.parse("text/plain"), genderSpinner.getSelectedItemPosition() == 1 ? "0" : "1"); // 0 For Male And 1 for female.
        //RequestBody customers_dob = RequestBody.create(MediaType.parse("text/plain"), input_dob.getText().toString().trim());
        //RequestBody customers_dob = RequestBody.create(MediaType.parse("text/plain"), "");

        Call<UserData> call = APIClient.getInstance()
                .processRegistration
                        (
                                fname, lname, email, password, country_code, customers_telephone
                        );

        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, retrofit2.Response<UserData> response) {

                dialogLoader.hideProgressDialog();

                // Check if the Response is successful
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        // Finish SignUpActivity to goto the LoginActivity
                        finish();
                        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);

                    } else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        // Get the Error Message from Response
                        String message = response.body().getMessage();
                        Snackbar.make(parentView, message, Snackbar.LENGTH_SHORT).show();

                    } else {
                        // Unable to get Success status
                        Toast.makeText(Signup.this, getString(R.string.unexpected_response), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    // Show the Error Message
                    String Str = response.message();
                    Toast.makeText(Signup.this, response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                String Str = "" + t;
                Toast.makeText(Signup.this, "NetworkCallFailure : " + t, Toast.LENGTH_LONG).show();

            }
        });
    }


    //*********** Validate SignUp Form Inputs ********//

    private boolean validateForm() {
        if (!ValidateInputs.isValidName(user_firstname.getText().toString().trim())) {
            user_firstname.setError(getString(R.string.invalid_first_name));
            return false;
        } else if (!ValidateInputs.isValidName(user_lastname.getText().toString().trim())) {
            user_lastname.setError(getString(R.string.invalid_last_name));
            return false;
        } else if (!ValidateInputs.isValidEmail(user_email.getText().toString().trim())) {
            user_email.setError(getString(R.string.invalid_email));
            return false;
        } else if (TextUtils.isEmpty(user_password.getText().toString().trim())) {
            user_password.setError(getString(R.string.invalid_password));
            return false;
        } /*else if (!ValidateInputs.isValidPassword(user_password.getText().toString().trim())) {
            user_password.setError(getString(R.string.invalid_password));
            return false;
        } *//*else if (!ValidateInputs.isValidNumber(user_mobile.getText().toString().trim())) {
            user_mobile.setError(getString(R.string.invalid_contact));
            return false;
        }*/ /*else if (genderSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select Gender First.", Toast.LENGTH_SHORT).show();
            return false;
        }  else if (input_dob.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Select Date of birth", Toast.LENGTH_SHORT).show();
            return false;
        }*/ else {
            return true;
        }
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
        // Finish SignUpActivity to goto the LoginActivity
        finish();
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
        }

        return super.onOptionsItemSelected(item);

    }

    public void hideKeyboardFrom(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(user_email.getWindowToken(), 0);
    }

}

