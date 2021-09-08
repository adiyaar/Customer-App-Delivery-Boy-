package com.themescoder.androidstore.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.ColorRes;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.graphics.drawable.DrawableCompat;

import com.themescoder.androidstore.BuildConfig;
import com.themescoder.androidstore.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.themescoder.androidstore.app.App;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.models.device_model.DeviceInfo;

import static android.content.Context.LOCATION_SERVICE;
import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Utilities class has different static methods, that can be implemented in any other class
 **/

public class Utilities {


    //*********** Checks if the Device is Connected to any Network ********//

    public static boolean isNetworkAvailable(Context activity) {

        ConnectivityManager connectivity = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Network[] networks = connectivity.getAllNetworks();
            NetworkInfo networkInfo;

            for (Network mNetwork : networks) {

                networkInfo = connectivity.getNetworkInfo(mNetwork);

                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }

        }
        else {
            if (connectivity != null) {

                NetworkInfo[] info = connectivity.getAllNetworkInfo();

                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }


    public static boolean hasActiveInternetConnection(Context context) {

        if (isNetworkAvailable(context)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(10000);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
                Log.e("Net_Available", "Error checking internet connection", e);
            }
        } else {
            Log.d("Net_Available", "No network available!");
        }
        return false;
    }
    
    
    //*********** Used to check if the App is running in Foreground ********//
    
    public static boolean isAppInForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> services = activityManager.getRunningAppProcesses();
        boolean isActivityFound = false;
        
        if (services.get(0).processName.equalsIgnoreCase(getApplicationContext().getPackageName())) {
            Log.i("test", "PackageName="+getApplicationContext().getPackageName());
            isActivityFound = true;
        }
        
        return isActivityFound;
    }


    @SuppressLint("SimpleDateFormat")
    public static String formatData(String data, Context context) {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        Date date;
        String strData = "";
        try {
            date = parser.parse(data);
            strData = DateUtils.getRelativeDateTimeString(context, date.getTime(), DateUtils.SECOND_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return strData;
    }
    
    
    //*********** Used to Print the HashKey (SHA) ********//

    public static void printHashKey() {
        try {
            PackageInfo info = App.getContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), PackageManager.GET_SIGNATURES);
            
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    // Reconvert Currency Code to Symbol
    public static String getCurrencySymbol(String currencyCode) {
        try {
            Currency currency = Currency.getInstance(currencyCode);
            return currency.getSymbol();
        } catch (Exception e) {
            return currencyCode;
        }
    }
    
    //*********** Convert given String to Md5Hash ********//
    
    public static String getMd5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String md5 = number.toString(16);
            
            while (md5.length() < 32)
                md5 = "0" + md5;
            
            return md5;
        } catch (NoSuchAlgorithmException e) {
            Log.e("MD5", e.getLocalizedMessage());
            return null;
        }
    }
    
    
    
    //*********** Returns information of the Device ********//
    
    public static DeviceInfo getDeviceInfo(Context context) {
    
        double lat = 0;
        double lng = 0;
        String IMEI = "";
        String NETWORK = "";
        String PROCESSORS = "";
        
        
        String UNIQUE_ID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        PROCESSORS = String.valueOf(Runtime.getRuntime().availableProcessors());
    
        ActivityManager actManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        double totalRAM = Math.round( ((memInfo.totalMem /1024.0) /1024.0)  /1024.0 );
        
        
        if (CheckPermissions.is_PHONE_STATE_PermissionGranted()) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            try {
                IMEI = telephonyManager.getDeviceId();
                NETWORK = telephonyManager.getNetworkOperatorName();
                
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    
        
        if (CheckPermissions.is_LOCATION_PermissionGranted()) {
            LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            try {
                boolean gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                boolean network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    
                Location location = null;
                String provider = locationManager.getBestProvider(new Criteria(), true);
                final LocationListener locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location loc) {}
                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {}
                    @Override
                    public void onProviderEnabled(String provider) {}
                    @Override
                    public void onProviderDisabled(String provider) {}
                };
//                locationManager.requestLocationUpdates(provider, 1000, 0, locationListener);
                
                if (gps_enabled) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                } else if (network_enabled) {
                    location = locationManager.getLastKnownLocation(provider);
                }
                
                if (location != null) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                }
                locationManager.removeUpdates(locationListener);
        
            } catch (SecurityException se) {
                se.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        
    
    
        DeviceInfo device = new DeviceInfo();
        
        device.setDeviceID(UNIQUE_ID);
        device.setDeviceType("Android");
        device.setDeviceUser(Build.USER);
        device.setDeviceModel(Build.BRAND +" "+Build.MODEL);
        device.setDeviceBrand(Build.BRAND);
        device.setDeviceSerial(Build.SERIAL);
        device.setDeviceSystemOS(System.getProperty("os.name"));
        device.setDeviceAndroidOS("Android "+ Build.VERSION.RELEASE);
        device.setDeviceManufacturer(Build.MANUFACTURER);
        device.setDeviceIMEI(IMEI);
        device.setDeviceRAM(totalRAM +"GB");
        device.setDeviceCPU(Build.UNKNOWN);
        device.setDeviceStorage(Build.UNKNOWN);
        device.setDeviceProcessors(PROCESSORS);
        device.setDeviceIP(Build.UNKNOWN);
        device.setDeviceMAC(Build.UNKNOWN);
        device.setDeviceNetwork(NETWORK);
        device.setDeviceLocation(lat +", "+ lng);
        device.setDeviceBatteryLevel(Build.UNKNOWN);
        device.setDeviceBatteryStatus(Build.UNKNOWN);
        
        return device;
    }
    

    
    //*********** Returns the current DataTime of Device ********//

    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();

        return dateFormat.format(date);
    }



    //*********** Used to Tint the MenuIcons ********//

    public static void tintMenuIcon(Context context, MenuItem item, @ColorRes int color) {
        Drawable normalDrawable = item.getIcon();
        Drawable wrapDrawable = DrawableCompat.wrap(normalDrawable);
        DrawableCompat.setTint(wrapDrawable, context.getResources().getColor(color));

        item.setIcon(wrapDrawable);
    }



    //*********** Used to Animate the MenuIcons ********//

    public static void animateCartMenuIcon(Context context, Activity activity) {

        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.myToolbar);

        MenuItem cartItem = toolbar.getMenu().findItem(R.id.toolbar_ic_cart);
        if (cartItem == null)
            return;


        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_icon);
        animation.setRepeatMode(Animation.ZORDER_TOP);
        animation.setRepeatCount(2);
        

        Animation anim = cartItem.getActionView().getAnimation();

        cartItem.getActionView().startAnimation(animation);

    }



    //*********** Checks if the Product is newly added ********//

    public static boolean checkNewProduct(String productDate) {

        boolean isNew = false;

        long diff;
        long days;
        Date dateProduct, dateSystem;

        Calendar calender = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = dateFormat.format(calender.getTime());

        try {
            dateSystem = dateFormat.parse(currentDate);
            dateProduct = dateFormat.parse(productDate);

            diff = dateSystem.getTime() - dateProduct.getTime();
            days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            isNew = (days <= ConstantValues.NEW_PRODUCT_DURATION);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return isNew;
    }



    //*********** Checks if the Date is not Passed ********//

    public static boolean checkIsDatePassed(String givenDate) {

        boolean isPassed = false;

        Date dateGiven, dateSystem;

        Calendar calender = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = dateFormat.format(calender.getTime());

        try {
            dateSystem = dateFormat.parse(currentDate);
            dateGiven = dateFormat.parse(givenDate);

            if (dateSystem.getTime() >= dateGiven.getTime()) {
                isPassed = true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return isPassed;
    }



    //*********** Used to Calculate the Discount Percentage between New and Old Prices ********//

    public static String checkDiscount(String actualPrice, String discountedPrice) {

        if (discountedPrice == null) {
            discountedPrice = actualPrice;
        }

        Double oldPrice = Double.parseDouble(actualPrice.replace(",",""));
        Double newPrice = Double.parseDouble(discountedPrice.replace(",",""));

        double discount = (oldPrice - newPrice)/oldPrice * 100;

        return (discount > 0) ? Math.round(discount) +"% " + App.getContext().getString(R.string.OFF) : null;
    }
    
    
    
    //*********** Used to Share the App with Others ********//
    
    public static void rateMyApp(final Context context) {
    
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View dialogView = inflater.inflate(R.layout.dialog_info, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
    
        final TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
        final TextView dialog_message = (TextView) dialogView.findViewById(R.id.dialog_message);
        final Button dialog_button_positive = (Button) dialogView.findViewById(R.id.dialog_button_positive);
        final Button dialog_button_negative = (Button) dialogView.findViewById(R.id.dialog_button_negative);
    
        dialog_title.setText(context.getString(R.string.rate_app));
        dialog_message.setText(context.getString(R.string.rate_app_msg));
        dialog_button_positive.setText(context.getString(R.string.rate_now));
        dialog_button_negative.setText(context.getString(R.string.not_now));
    
    
        final android.app.AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    
        dialog_button_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    
        dialog_button_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            
                Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            
                try {
                    context.startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "Couldn't launch the market", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //*********** Used to Customer Booking Service through Website ********//
    public static void Booking(final Context context) {

        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View dialogView = inflater.inflate(R.layout.dialog_info, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        final TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
        final TextView dialog_message = (TextView) dialogView.findViewById(R.id.dialog_message);
        final Button dialog_button_positive = (Button) dialogView.findViewById(R.id.dialog_button_positive);
        final Button dialog_button_negative = (Button) dialogView.findViewById(R.id.dialog_button_negative);

        dialog_title.setText(context.getString(R.string.actionBooking));
        dialog_message.setText(context.getString(R.string.booking_msg));
        dialog_button_positive.setText(context.getString(R.string.book_now));
        dialog_button_negative.setText(context.getString(R.string.cancel_now));


        final android.app.AlertDialog alertDialog = dialog.create();
        alertDialog.show();

        dialog_button_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        dialog_button_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

                Uri uri = Uri.parse("http://booking.cdmkart.com");
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

                try {
                    context.startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "Couldn't launch the Browser", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static String getCompleteAddressString(Context context,double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("Current loction address", strReturnedAddress.toString());
            } else {
                Log.w("Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Current loction address", "Canont get Address!");
        }
        return strAdd;
    }

    public static String getCityCountry(Context context,double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                strReturnedAddress.append(returnedAddress.getLocality()).append(" ");
                strReturnedAddress.append(returnedAddress.getCountryName()).append("");

                strAdd = strReturnedAddress.toString();
                Log.w("Current loction address", strReturnedAddress.toString());
            } else {
                Log.w("Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Current loction address", "Canont get Address!");
        }
        return strAdd;
    }

    //*********** Used to Customer Service through Whatsapp ********//
    public static void WhatsApp(final Context context) {

        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View dialogView = inflater.inflate(R.layout.dialog_info, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        final TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
        final TextView dialog_message = (TextView) dialogView.findViewById(R.id.dialog_message);
        final Button dialog_button_positive = (Button) dialogView.findViewById(R.id.dialog_button_positive);
        final Button dialog_button_negative = (Button) dialogView.findViewById(R.id.dialog_button_negative);

        dialog_title.setText(context.getString(R.string.whats_app));
        dialog_message.setText(context.getString(R.string.whats_app_msg));
        dialog_button_positive.setText(context.getString(R.string.report_now));
        dialog_button_negative.setText(context.getString(R.string.cancel_now));


        final android.app.AlertDialog alertDialog = dialog.create();
        alertDialog.show();

        dialog_button_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        dialog_button_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

                Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=919500109405&text=Welcome%20to%20*CdmKart%20Chat%2FCall%20Support*");
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

                try {
                    context.startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "Couldn't launch the WhatsApp", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //*********** Used to Customer Service through Whatsapp ********//
    public static void Partner(final Context context, final Activity activity, final String title,final String url) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View dialogView = inflater.inflate(R.layout.dialog_info, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        final TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
        final TextView dialog_message = (TextView) dialogView.findViewById(R.id.dialog_message);
        final Button dialog_button_positive = (Button) dialogView.findViewById(R.id.dialog_button_positive);
        final Button dialog_button_negative = (Button) dialogView.findViewById(R.id.dialog_button_negative);

        dialog_title.setText(context.getString(R.string.actionPartner));
        dialog_message.setText(context.getString(R.string.partner_msg));
        dialog_button_positive.setText(context.getString(R.string.partner_now));
        dialog_button_negative.setText(context.getString(R.string.cancel_now));


        final AlertDialog alertDialog = dialog.create();
        alertDialog.show();

        dialog_button_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        dialog_button_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

               FullScreenDialogue(context,activity,title,url);
            }
        });
    }

    public static void FullScreenDialogue(final Context context, Activity activity,String title,String url){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context, android.R.style.Theme_NoTitleBar);
        View dialogView = activity.getLayoutInflater().inflate(R.layout.dialog_webview_fullscreen, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        final ImageButton dialog_button = (ImageButton) dialogView.findViewById(R.id.dialog_button);
        final TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
        final WebView dialog_webView = (WebView) dialogView.findViewById(R.id.dialog_webView);
        final ProgressBar pbar = (ProgressBar) dialogView.findViewById(R.id.pB1);

        dialog_title.setText(title);

        WebSettings settings = dialog_webView.getSettings();
        settings.setJavaScriptEnabled(true);
        dialog_webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        dialog_webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if(progress < 100 && pbar.getVisibility() == ProgressBar.GONE){
                    pbar.setVisibility(ProgressBar.VISIBLE);

                }

                pbar.setProgress(progress);
                if(progress == 100) {
                    pbar.setVisibility(ProgressBar.GONE);
                }
            }
        });

        dialog_webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("dialogue progress", "Processing webview url click...");
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                Log.i("dialogue progress", "Finished loading URL: " + url);

            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e("dialogue progress", "Error: " + description);
                Toast.makeText(context, "Oh no! " + description, Toast.LENGTH_SHORT).show();

            }
        });
        dialog_webView.loadUrl(url);

        final AlertDialog alertDialog = dialog.create();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            alertDialog.getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        }

        dialog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(dialog_webView.canGoBack()){
                   dialog_webView.goBack();
               }
               else {
                   alertDialog.dismiss();
               }
            }
        });

        alertDialog.show();
    }
    //*********** Used to Share the App with Others ********//

    public static void shareMyApp(Context context) {
    
        String link = "https://play.google.com/store/apps/details?id="+context.getPackageName();
        
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/*");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, link);
        context.startActivity(Intent.createChooser(sharingIntent, "Share Via"));
    }



    //*********** Shares the Product with its Image and Url ********//

    public static void shareProduct(Context context, String subject, ImageView imageView, String url) {

        Uri bmpUri = getLocalBitmapUri(context, imageView);

        if (bmpUri != null) {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.putExtra(Intent.EXTRA_TEXT, url);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);

            context.startActivity(Intent.createChooser(shareIntent, "Share via"));

        } else {
            Toast.makeText(context, "Null bmpUri", Toast.LENGTH_SHORT).show();
        }
    }



    //*********** Convert Bitmap into Uri ********//

    public static Uri getLocalBitmapUri(Context context, ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bitmap = null;
        
        if (drawable instanceof BitmapDrawable){
            bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        }
        else {
            Toast.makeText(context, "drawable isn't instanceof BitmapDrawable", Toast.LENGTH_SHORT).show();
            return null;
        }
        
        // Store image to default external storage directory
        Uri bitmapUri = null;
        
        try {
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            File file =  new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            
//            bitmapUri = Uri.fromFile(file);
            bitmapUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
            
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "IOException"+e, Toast.LENGTH_SHORT).show();
        }
        
        
        return bitmapUri;
    }



    //*********** Converts any Bitmap to Base64String ********//

    public static String getBase64ImageStringFromBitmap(Bitmap bitmap) {
        String imgString;
        
        if(bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            
            byte[] profileImage = byteArrayOutputStream.toByteArray();

            imgString = "data:image/jpeg;base64,"+Base64.encodeToString(profileImage, Base64.NO_WRAP);
            
        }
        else{
            imgString = "";
        }
        
        
        return imgString;
    }



    //*********** Converts a Base64String to the Bitmap ********//

    public static Bitmap getBitmapFromBase64ImageString(String b64) {
        Bitmap bitmap = null;
        
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        
        
        return bitmap;
    }
    

}

