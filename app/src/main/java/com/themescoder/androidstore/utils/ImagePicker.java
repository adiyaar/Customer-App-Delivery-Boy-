package com.themescoder.androidstore.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.provider.MediaStore;
import androidx.core.content.FileProvider;

import com.themescoder.androidstore.BuildConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


/**
 * ImagePicker class is used for picking images from Gallery or Camera with Android Intents
 **/

public class ImagePicker {

    private static final String TAG = "ImagePicker";
    private static final String TEMP_IMG_NAME = "tempImage";
    private static final int DEFAULT_MIN_WIDTH_QUALITY = 400;

    private static int minWidthQuality = DEFAULT_MIN_WIDTH_QUALITY;


    //*********** Returns Intent with Options of Image Picker Apps like Gallery, Camera etc ********//

    public static Intent getImagePickerIntent(Context context) {
        // Chooser Intent of Filesystem Options
        Intent chooserIntent = null;
        // List of Intents
        List<Intent> pickerIntentsList = new ArrayList<>();


        // Filesystem Intent
        Intent galleryIntent;
        if (Build.VERSION.SDK_INT <= 19) {
            galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        else if (Build.VERSION.SDK_INT > 19) {
            galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        else {
            galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }

        
        Uri uriForFile = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", getTempFile(context));
        
        // Camera Intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra("return_data", true);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);


        // Adding Filesystem and Camera Intents to IntentList
        pickerIntentsList = addIntentToList(context, pickerIntentsList, galleryIntent);
        pickerIntentsList = addIntentToList(context, pickerIntentsList, cameraIntent);


        // Initializing Chooser Intent
        chooserIntent = new Intent(Intent.createChooser(pickerIntentsList.remove(pickerIntentsList.size() - 1), "Select Source"));

        // Adding IntentList of Camera and Filesystem Intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, pickerIntentsList.toArray(new Parcelable[]{}));


        return chooserIntent;
    }


    
    //*********** Adds Intents to the IntentList ********//

    private static List<Intent> addIntentToList(Context context, List<Intent> intentList, Intent intent) {
        List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, 0);

        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            Intent targetedIntent = new Intent(intent);
            targetedIntent.setPackage(packageName);

            intentList.add(targetedIntent);
        }

        return  intentList;
    }


    
    //*********** Returns Temp File ********//

    private static File getTempFile(Context context) {
        // Create New Temp File
        File imageFile = new File(context.getExternalCacheDir(), TEMP_IMG_NAME);

        // Create Dir for Temp File
        imageFile.getParentFile().mkdir();

        return imageFile;
    }


    
    //*********** Returns the User Selected Image as Bitmap ********//

    public static Bitmap getImageFromResult(Context context, int resultCode, Intent imageReturnedIntent) {
        Bitmap bitmap = null;
        File imageFile = getTempFile(context);

        if (resultCode == Activity.RESULT_OK) {
            Uri selectedImage;
            boolean isCamera = (imageReturnedIntent == null || imageReturnedIntent.getData() == null || imageReturnedIntent.getData().toString().contains(imageFile.toString()));

            if (isCamera) {
                // From Camera
                selectedImage = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", imageFile);
            } else {
                // From Storage
                selectedImage = imageReturnedIntent.getData();
            }

            bitmap = getResizedImage(context, selectedImage);
        }
        return bitmap;
    }


    
    //*********** Resize to avoid using too much Memory loading Big Images (2560*1920) ********//

    private static Bitmap getResizedImage(Context context, Uri selectedImage) {
        Bitmap resizedBitmap = null;
        int[] sampleSizes = new int[]{5, 4, 3, 2, 1};

        int i = 0;
        do {
            resizedBitmap = decodeBitmap(context, selectedImage, sampleSizes[i]);
            i++;
            
        } while (resizedBitmap.getWidth() < minWidthQuality && i < sampleSizes.length);
        
        return resizedBitmap;
    }


    
    //*********** Returns Bitmap Decoded from Uri ********//

    private static Bitmap decodeBitmap(Context context, Uri uri, int sampleSize) {
        Bitmap decodedBitmap = null;
        AssetFileDescriptor fileDescriptor = null;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

        try {
            fileDescriptor = context.getContentResolver().openAssetFileDescriptor(uri, "r");
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        decodedBitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, bitmapOptions);

        return decodedBitmap;
    }


    
    //*********** Used for the Rotation of Image ********//

    private static int getRotation(Context context, Uri imageUri, boolean isCamera) {
        int rotation;

        if (isCamera) {
            rotation = getRotationFromCamera(context, imageUri);
        } else {
            rotation = getRotationFromGallery(context, imageUri);
        }

        return rotation;
    }
    
    
    
    //*********** Used for the Rotation of Image ********//
    
    private static int getRotationFromCamera(Context context, Uri imageFile) {

        int rotate = 0;

        try {
            context.getContentResolver().notifyChange(imageFile, null);
            ExifInterface exif = new ExifInterface(imageFile.getPath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rotate;
    }
    
    
    
    //*********** Used for the Rotation of Image ********//
    
    public static int getRotationFromGallery(Context context, Uri imageUri) {

        int result = 0;
        Cursor cursor = null;
        String[] columns = {MediaStore.Images.Media.ORIENTATION};

        try {
            cursor = context.getContentResolver().query(imageUri, columns, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int orientationColumnIndex = cursor.getColumnIndex(columns[0]);
                result = cursor.getInt(orientationColumnIndex);
            }
            
        } catch (Exception e) {
            // Handle Exception
            
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return result;
    }
    
    
    
    //*********** Rotates the Bitmap ********//
    
    private static Bitmap rotate(Bitmap bm, int rotation) {

        if (rotation != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(rotation);
            Bitmap bmOut = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);

            return bmOut;
        }
        return bm;
    }

}


