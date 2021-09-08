package com.themescoder.androidstore.customs;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;

import com.themescoder.androidstore.R;


/**
 * DialogLoader will be used to show and hide Dialog with ProgressBar
 **/

public class DialogLoader {

    private Context context;
    private AlertDialog alertDialog;
    private AlertDialog.Builder dialog;
    private LayoutInflater layoutInflater;


    public DialogLoader(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);

        initDialog();
    }

    
    private void initDialog() {
        dialog = new AlertDialog.Builder(context);
        View dialogView = layoutInflater.inflate(R.layout.layout_progress_dialog, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);


        alertDialog = dialog.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }


    public void showProgressDialog() {
        alertDialog.show();
    }

    
    public void hideProgressDialog() {
        alertDialog.dismiss();
    }

    
}

