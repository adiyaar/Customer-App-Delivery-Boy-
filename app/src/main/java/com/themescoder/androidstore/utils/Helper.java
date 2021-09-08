package com.themescoder.androidstore.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.themescoder.androidstore.BuildConfig;
import com.themescoder.androidstore.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class Helper {

    private static boolean DISPLAY_DEBUG = BuildConfig.DEBUG;

    /**
     * Shows a 'no connection' dialog if the user is not online, or a general error if the user is online.
     *
     * @param context context
     * @param message additional message to show
     */
    public static void noConnection(final Activity context, String message) {

        AlertDialog.Builder ab = new AlertDialog.Builder(context);

        if (isOnline(context)) {
            String messageText = "";
            if (message != null && DISPLAY_DEBUG) {
                messageText = "\n\n" + message;
            }

            ab.setMessage(context.getResources().getString(R.string.dialog_connection_description) + messageText);
            ab.setPositiveButton(context.getResources().getString(R.string.ok), null);
            ab.setTitle(context.getResources().getString(R.string.dialog_connection_title));
        } else {
            ab.setMessage(context.getResources().getString(R.string.dialog_internet_description));
            ab.setPositiveButton(context.getResources().getString(R.string.ok), null);
            ab.setTitle(context.getResources().getString(R.string.dialog_internet_title));
        }

        if (!context.isFinishing()) {
            ab.show();
        }
    }


    /**
     * Shows a 'no connection' dialog if the user is not online, or a general error if the user is online.
     *
     * @param context context
     */
    public static void noConnection(final Activity context) {
        noConnection(context, null);
    }

    /**
     * Returns if the user has an internet connection
     *
     * @param context the context
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        return ni != null && ni.isConnected();
    }

    /**
     * Returns true if the user is online, and returns false and shows a dialog otherwise
     *
     * @param c Activity to show dialog in.
     */
    public static boolean isOnlineShowDialog(Activity c) {
        if (isOnline(c))
            return true;
        else
            noConnection(c);
        return false;
    }

 /*   @Deprecated
    public static void admobLoader(Context c, Resources resources, View admobView) {
        admobLoader(c, admobView);
    }

    *//**
     * If Admob is enabled (by entering id in strings), this will load an adview.
     *//*
    public static void admobLoader(Context c, View AdmobView) {
        String adId = c.getResources().getString(R.string.admob_banner_id);
        if (!adId.equals("") && !SettingsFragment.getIsPurchased(c)) {
            AdView adView = (AdView) AdmobView;
            adView.setVisibility(View.VISIBLE);

            // Look up the AdView as a resource and load a request.
            Builder adRequestBuilder = new AdRequest.Builder();
            adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
            adView.loadAd(adRequestBuilder.build());
        }
    }*/

    //Sets the status bar color
    @SuppressLint("NewApi")
    public static void setStatusBarColor(Activity mActivity, int color) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mActivity.getWindow().setStatusBarColor(color);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    /**
     * Loads content of file from assets as string.
     */
    public static String loadJSONFromAsset(Context context, String name) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(name);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

  /*  public static void showEmptyView(Fragment fragment, int title, int subtitle) {
        if (fragment == null || fragment.getActivity() == null) return;

        ViewGroup rootView = (ViewGroup) fragment.getView();
        rootView.findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.list).setVisibility(View.GONE);

        ((TextView) rootView.findViewById(R.id.title)).setText(fragment.getString(title));
        ((TextView) rootView.findViewById(R.id.subtitle)).setText(fragment.getString(subtitle));
    }
*/
   /* public static void hideEmptyView(Fragment fragment) {
        ViewGroup rootView = (ViewGroup) fragment.getView();
        if (rootView != null && rootView.findViewById(R.id.empty_view) != null) {
            rootView.findViewById(R.id.empty_view).setVisibility(View.GONE);
            rootView.findViewById(R.id.list).setVisibility(View.VISIBLE);
        }
    }*/

    //Makes high numbers readable (e.g. 5000 -> 5K)
    public static String formatValue(double value) {
        if (value > 0) {
            int power;
            String suffix = " kmbt";
            String formattedNumber = "";

            NumberFormat formatter = new DecimalFormat("#,###.#");
            power = (int) StrictMath.log10(value);
            value = value / (Math.pow(10, (power / 3) * 3));
            formattedNumber = formatter.format(value);
            formattedNumber = formattedNumber + suffix.charAt(power / 3);
            return formattedNumber.length() > 4 ? formattedNumber.replaceAll("\\.[0-9]+", "") : formattedNumber;
        } else {
            return "0";
        }
    }

    //Get response from an URL request (GET)
    public static String getDataFromUrl(String url) {
        // Making HTTP request
        Log.v("INFO", "Requesting: " + url);

        StringBuffer chaine = new StringBuffer("");
        try {
            URL urlCon = new URL(url);

            //Open a connection
            HttpURLConnection connection = (HttpURLConnection) urlCon
                    .openConnection();
            connection.setRequestProperty("User-Agent", "Universal/2.0 (Android)");
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            //Handle redirecti
            int status = connection.getResponseCode();
            if ((status != HttpURLConnection.HTTP_OK) && (status == HttpURLConnection.HTTP_MOVED_TEMP
                    || status == HttpURLConnection.HTTP_MOVED_PERM
                    || status == HttpURLConnection.HTTP_SEE_OTHER)) {

                // get redirect url from "location" header field
                String newUrl = connection.getHeaderField("Location");
                // get the cookie if need, for login
                String cookies = connection.getHeaderField("Set-Cookie");

                // open the new connnection again
                connection = (HttpURLConnection) new URL(newUrl).openConnection();
                connection.setRequestProperty("Cookie", cookies);
                connection.setRequestProperty("User-Agent", "Universal/2.0 (Android)");
                connection.setRequestMethod("GET");
                connection.setDoInput(true);

                Log.v("INFO", "Redirect to URL : " + newUrl);
            }

            //Get the stream from the connection and read it
            InputStream inputStream = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    inputStream));
            String line = "";
            while ((line = rd.readLine()) != null) {
                chaine.append(line);
            }

        } catch (IOException e) {
            // writing exception to log
            e.getStackTrace();
        }

        return chaine.toString();
    }

    //Get JSON from an url and parse it to a JSON Object.
    public static JSONObject getJSONObjectFromUrl(String url) {
        String data = getDataFromUrl(url);

        try {
            return new JSONObject(data);
        } catch (Exception e) {
            Log.e("INFO", "Error parsing JSON. Printing stacktrace now");
            e.getStackTrace();
        }

        return null;
    }

    //Get JSON from an url and parse it to a JSON Array.
    public static JSONArray getJSONArrayFromUrl(String url) {
        String data = getDataFromUrl(url);

        try {
            return new JSONArray(data);
        } catch (Exception e) {
            Log.e("INFO", "Error parsing JSON. Printing stacktrace now");
            e.getStackTrace();
        }

        return null;
    }

    //Install certificates to reach HTTPS sites with specific certificates on older devices
    public static void updateAndroidSecurityProvider(Activity callingActivity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            try {
                ProviderInstaller.installIfNeeded(callingActivity);
            } catch (GooglePlayServicesRepairableException e) {
                // Thrown when Google Play Services is not installed, up-to-date, or enabled
                // Show dialog to allow users to install, update, or otherwise enable Google Play services.
                GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
                apiAvailability.getErrorDialog(callingActivity, e.getConnectionStatusCode(), 0);
            } catch (GooglePlayServicesNotAvailableException e) {
                Log.e("SecurityException", "Google Play Services not available.");
            }
        }
    }

    /**
     * Get a gradient drawable resource
     * @param number number between and including 1 and 5
     * @return gradient drawable resource
     */
    public static int getGradient(int number) {
        int backgroundResource;
        switch (number) {
            case 1:
                backgroundResource = R.drawable.gradient_one;
                break;
            case 2:
                backgroundResource = R.drawable.gradient_two;
                break;
            case 3:
                backgroundResource = R.drawable.gradient_five;
                break;
            case 4:
                backgroundResource = R.drawable.gradient_four;
                break;
            default:
                backgroundResource = R.drawable.gradient_three;
                break;
        }
        return backgroundResource;
    }

    /**
     * Download a file from a certain url
     * @param context context
     * @param url file to download
     */
    public static void download(Activity context, String url){
        if (!hasPermissionToDownload(context))
            return;

        String source = url;

        if (source == null){
            Toast.makeText(context, context.getResources().getString(R.string.download_unavailable), Toast.LENGTH_LONG).show();
            return;
        }

        DownloadManager downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri Download_Uri = Uri.parse(source);
        String name = url.substring(url.lastIndexOf("/"), url.length());

        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name);
        final Long downloadReference = downloadManager.enqueue(request);

        //A broadcast receiver to open the file after it is downloaded
        String downloadCompleteIntentName = DownloadManager.ACTION_DOWNLOAD_COMPLETE;
        IntentFilter downloadCompleteIntentFilter = new IntentFilter(downloadCompleteIntentName);
        BroadcastReceiver downloadCompleteReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0L);
                if (id != downloadReference) {
                    return;
                }

                //Unregister the broadcastreceiver
                context.unregisterReceiver(this);

                //A list of actions from here on to open the file
                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(id);
                Cursor cursor = downloadManager.query(query);

                // it shouldn't be empty, but just in case
                if (!cursor.moveToFirst()) {
                    return;
                }

                int statusIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                if (DownloadManager.STATUS_SUCCESSFUL != cursor.getInt(statusIndex)) {
                    Toast.makeText(context, context.getResources().getString(R.string.download_failed), Toast.LENGTH_LONG).show();
                    return;
                }

                int uriIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
                String downloadedPackageUriString = cursor.getString(uriIndex);
                Uri fileUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", new File(Uri.parse(downloadedPackageUriString).getPath()));

                Intent open = new Intent(Intent.ACTION_VIEW);
                open.setData(fileUri);
                open.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                open.setDataAndType(fileUri, downloadManager.getMimeTypeForDownloadedFile(id));

                PackageManager manager = context.getPackageManager();
                List<ResolveInfo> infos = manager.queryIntentActivities(open, 0);
                if (infos.size() > 0) {
                    //Then there is an Application(s) can handle the intent
                    context.startActivity(open);
                } else {
                    //No Application can handle your intent
                    Toast.makeText(context, context.getResources().getString(R.string.download_open_failed), Toast.LENGTH_LONG).show();
                }

            }
        };


        //Register the broadcastreceiver
        context.registerReceiver(downloadCompleteReceiver, downloadCompleteIntentFilter);

    }

    /**
     * Check if there are sufficient permissions to download a file and otherwise prompt
     * @param context Context
     * @return True if permission is given
     */
    private static boolean hasPermissionToDownload(final Activity context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED )
            return true;

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setMessage(R.string.download_permission_explaination);
        builder.setPositiveButton(R.string.download_permission_grant, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Fire off an async request to actually get the permission
                // This will show the standard permission request dialog UI
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    context.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        });
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();

        return false;
    }

}
