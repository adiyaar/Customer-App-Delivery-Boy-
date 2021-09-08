package com.themescoder.androidstore.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.themescoder.androidstore.customs.CircularImageView;

import com.themescoder.androidstore.activities.MainActivity;
import com.themescoder.androidstore.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.themescoder.androidstore.customs.DialogLoader;
import com.themescoder.androidstore.databases.User_Info_DB;
import com.themescoder.androidstore.models.uploadimage.UploadImageModel;
import com.themescoder.androidstore.models.user_model.UserData;
import com.themescoder.androidstore.models.user_model.UserDetails;
import com.themescoder.androidstore.network.APIClient;

import com.themescoder.androidstore.utils.CheckPermissions;
import com.themescoder.androidstore.utils.ImagePicker;
import com.themescoder.androidstore.utils.ValidateInputs;

import am.appwise.components.ni.NoInternetDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Update_Account extends Fragment {

    View rootView;
    String customers_id;
    String profileImageCurrent = "";
    File profileImageChanged;
    private static final int PICK_IMAGE_ID = 360;           // the number doesn't matter

    String imageID;
    Boolean isImgUploaded = false;

    Button updateInfoBtn;
    CircularImageView user_photo;
    FloatingActionButton user_photo_edit_fab;
    EditText input_first_name, input_last_name, input_current_password, input_new_password, input_contact_no, input_dob;
    DialogLoader dialogLoader;

    UserDetails userInfo;
    User_Info_DB userInfoDB = new User_Info_DB();

    boolean isRedirectFromSettings;

    public Update_Account(boolean isRedirectFromSettings) {
        this.isRedirectFromSettings = isRedirectFromSettings;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.update_account, container, false);

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        //MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        if (isRedirectFromSettings)
            ((MainActivity) getActivity()).toggleNavigaiton(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.actionAccount));

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        //noInternetDialog.show();


        // Get the CustomerID from SharedPreferences
        customers_id = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");


        // Binding Layout Views
        user_photo = (CircularImageView) rootView.findViewById(R.id.user_photo);
        input_first_name = (EditText) rootView.findViewById(R.id.firstname);
        input_last_name = (EditText) rootView.findViewById(R.id.lastname);
        input_dob = rootView.findViewById(R.id.dob);
        input_contact_no = rootView.findViewById(R.id.contact);
        //input_current_password = (EditText) rootView.findViewById(R.id.current_password);
        //input_new_password = (EditText) rootView.findViewById(R.id.new_password);
        updateInfoBtn = (Button) rootView.findViewById(R.id.updateInfoBtn);
        user_photo_edit_fab = (FloatingActionButton) rootView.findViewById(R.id.user_photo_edit_fab);


        // Set KeyListener of some View to null
        input_dob.setKeyListener(null);


        dialogLoader = new DialogLoader(getContext());

        // Get the User's Info from the Local Databases User_Info_DB
        userInfo = userInfoDB.getUserData(customers_id);


        // Set User's Info to Form Inputs
        input_first_name.setText(userInfo.getFirstName());
        input_last_name.setText(userInfo.getLastName());
        input_contact_no.setText(userInfo.getPhone());


        // Set User's Date of Birth

        if (userInfo.getDob() == null || userInfo.getDob().equalsIgnoreCase("0000-00-00 00:00:00")) {
            input_dob.setText("");
        } else {
            // Get the String of Date from userInfo
            String dateString = userInfo.getDob();
            // Set Date Format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

            // Convert String of Date to Date Format
            Date convertedDate = new Date();
            try {
                convertedDate = dateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            input_dob.setText(dateFormat.format(convertedDate));
        }


        // Set User's Photo
/*
        if (!TextUtils.isEmpty(userInfo.getAvatar()) && userInfo.getAvatar() != null) {
            profileImageCurrent = userInfo.getAvatar();
            Glide.with(getContext())
                    .asBitmap()
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.profile)
                            .error(R.drawable.profile)
                            .fitCenter())
                    .load(ConstantValues.ECOMMERCE_URL + profileImageCurrent)
                    .into(user_photo);

        } else {
            profileImageCurrent = "";
            Glide.with(getContext())
                    .asBitmap()
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.profile)
                            .error(R.drawable.profile)
                            .fitCenter())
                    .load(ConstantValues.ECOMMERCE_URL + profileImageCurrent)
                    .into(user_photo);
        }
*/


        // Handle Touch event of input_dob EditText
/*        input_dob.setOnTouchListener(new View.OnTouchListener() {
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
                                    getContext(),
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
        });*/


        // Handle Click event of user_photo_edit_fab FAB
/*
        user_photo_edit_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CheckPermissions.is_CAMERA_PermissionGranted() && CheckPermissions.is_STORAGE_PermissionGranted()) {
                    pickImage();
                } else {
                    requestPermissions
                            (
                                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    CheckPermissions.PERMISSIONS_REQUEST_CAMERA
                            );
                }

            }
        });
*/


        // Handle Click event of updateInfoBtn Button
        updateInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate User's Info Form Inputs
                boolean isValidData = validateInfoForm();

                if (isValidData) {

                    updateCustomerInfo();
                        /*if ("".equalsIgnoreCase(input_current_password.getText().toString()) && "".equalsIgnoreCase(input_new_password.getText().toString())) {
                            // Proceed User Registration
                            updateCustomerInfo();
                        } else {
                            if (validatePasswordForm())
                                updateCustomerInfo();
                        }*/
                }
            }
        });


        return rootView;

    }

    //*********** Picks User Profile Image from Gallery or Camera ********//
    private void pickImage() {
        // Intent with Image Picker Apps from the static method of ImagePicker class
        Intent chooseImageIntent = ImagePicker.getImagePickerIntent(getContext());
        chooseImageIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        chooseImageIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        // Start Activity with Image Picker Intent
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
    }


    //*********** Receives the result from a previous call of startActivityForResult(Intent, int) ********//
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE_ID) {

                // Get the User Selected Image as Bitmap from the static method of ImagePicker class
                Bitmap bitmap = ImagePicker.getImageFromResult(this.getActivity(), resultCode, data);

                // Upload the Bitmap to ImageView
                user_photo.setImageBitmap(bitmap);

                // Get the converted Bitmap as Base64ImageString from the static method of Helper class
                //profileImageChanged = Utilities.getBase64ImageStringFromBitmap(bitmap);

                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri tempUri = getImageUri(getContext(), bitmap);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                profileImageChanged = new File(getRealPathFromURI(tempUri));

                RequestImageUpload();

            }
        }
    }

    private void RequestImageUpload() {
        dialogLoader.showProgressDialog();
        MultipartBody.Part filePart = null;
        if (profileImageChanged != null) {
            filePart = MultipartBody.Part.createFormData("file", profileImageChanged.getName(), RequestBody.create(MediaType.parse("image/*"), profileImageChanged));
        }
        Call<UploadImageModel> call = APIClient.getInstance().uploadImage(filePart);
        call.enqueue(new Callback<UploadImageModel>() {
            @Override
            public void onResponse(Call<UploadImageModel> call, Response<UploadImageModel> response) {

                if (response.isSuccessful()) {

                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        imageID = "" + response.body().getData().get(0).getImageId();
                        Toast.makeText(getContext(), "Image added successfully!", Toast.LENGTH_SHORT).show();

                        isImgUploaded = true;
                    } else {

                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
                dialogLoader.hideProgressDialog();
            }

            @Override
            public void onFailure(Call<UploadImageModel> call, Throwable t) {
                Toast.makeText(getContext(), "NetworkCallFailure : " + t, Toast.LENGTH_SHORT).show();
                dialogLoader.hideProgressDialog();
            }
        });
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
        Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    //*********** This method is invoked for every call on requestPermissions(Activity, String[], int) ********//
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CheckPermissions.PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // The Camera and Storage Permission is granted
                pickImage();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                    // Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(getString(R.string.permission_camera_storage));
                    builder.setMessage(getString(R.string.permission_camera_storage_needed));
                    builder.setPositiveButton(getString(R.string.grant), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            requestPermissions
                                    (
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
                    Toast.makeText(getContext(), getString(R.string.permission_rejected), Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    //*********** Updates User's Personal Information ********//
    private void updateCustomerInfo() {

        dialogLoader.showProgressDialog();

        Call<UserData> call = APIClient.getInstance()
                .updateCustomerInfo
                        (customers_id,
                                input_first_name.getText().toString().trim(),
                                input_last_name.getText().toString().trim(),
                                "1",
                                input_contact_no.getText().toString().trim(),
                                /*input_dob.getText().toString().trim(),*/
                                imageID);

        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, retrofit2.Response<UserData> response) {

                dialogLoader.hideProgressDialog();

                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1") && response.body().getData() != null) {
                        // User's Info has been Updated.

                        UserDetails userDetails = response.body().getData().get(0);

                        // Update in Local Databases as well
                        userInfoDB.updateUserData(userDetails);
                        userInfoDB.updateUserPassword(userDetails);

                        // Get the User's Info from the Local Databases User_Info_DB
                        userInfo = userInfoDB.getUserData(customers_id);

                        // Set the userName in SharedPreferences
                        SharedPreferences.Editor editor = getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).edit();
                        editor.putString("userName", userDetails.getFirstName() + " " + userDetails.getLastName());
                        editor.apply();

                        // Set the User Info in the NavigationDrawer Header with the public method of MainActivity
                        ((MainActivity) getActivity()).setupExpandableDrawerHeader();

                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();

                    } else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        // Unable to Update User's Info.
                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();

                    } else if (response.body().getSuccess().equalsIgnoreCase("2")) {
                        // Unable to Update User's Info.
                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
                    } else {
                        // Unable to get Success status
                        Toast.makeText(getContext(), getString(R.string.unexpected_response), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(getContext(), "NetworkCallFailure : " + t, Toast.LENGTH_LONG).show();
            }
        });
    }


    //*********** Validate User Info Form Inputs ********//
    private boolean validateInfoForm() {
        if (!ValidateInputs.isValidName(input_first_name.getText().toString().trim())) {
            input_first_name.setError(getString(R.string.invalid_first_name));
            return false;
        } else if (!ValidateInputs.isValidName(input_last_name.getText().toString().trim())) {
            input_last_name.setError(getString(R.string.invalid_last_name));
            return false;
        } else {
            return true;
        }
    }


    //*********** Validate Password Info Form Inputs ********//
/*    private boolean validatePasswordForm() {
        if (!input_current_password.getText().toString().trim().equals(userInfo.getPassword())) {
            input_current_password.setError(getString(R.string.invalid_password));
            return false;
        } else if (!ValidateInputs.isValidPassword(input_new_password.getText().toString().trim())) {
            input_new_password.setError(getString(R.string.invalid_password));
            return false;
        } else {
            return true;
        }
    }*/
}
