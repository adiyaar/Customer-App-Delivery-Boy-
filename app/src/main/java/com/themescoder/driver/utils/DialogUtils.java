package com.themescoder.driver.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.Toast;

import com.themescoder.driver.MainActivity;
import com.themescoder.driver.dialogs.GoOnlineDialog;
import com.themescoder.driver.dialogs.LegalStatementsDialog;
import com.themescoder.driver.dialogs.MyProgressDialog;
import com.themescoder.driver.dialogs.OTPDialog;

public class DialogUtils {


    public static void showStatusDialog(final Activity context) {
        // Fields
        final AppCompatCheckBox checkBox;
        //////////////////////////
        final GoOnlineDialog dialog = new GoOnlineDialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (PreferencesUtils.getString(context, PreferencesUtils.DONT_SHOW_AGAIN_DIALOG, "NOT checked").equals("NOT checked")) {
            dialog.show();
            dialog.submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String checkBoxResult = "NOT checked";
                    if (dialog.checkBox.isChecked()) {
                        checkBoxResult = "checked";
                    }
                    PreferencesUtils.putString(context, PreferencesUtils.DONT_SHOW_AGAIN_DIALOG, checkBoxResult);
                    dialog.dismiss();
                }
            });
        }
    }

    public static void showOTPDialog(final Activity context) {
        OTPDialog cdd = new OTPDialog(context);
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.show();
        cdd.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesUtils.putBoolean(context, PreferencesUtils.LOGED_IN_USER, true);
                Toast.makeText(context, "User is Logged in", Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, MainActivity.class));
                context.finish();
            }
        });

    }

    public static void showLegalStatementsDialog(final Activity context) {
        LegalStatementsDialog dialog = new LegalStatementsDialog(context);
        dialog.show();
    }

    private static MyProgressDialog progressDialog;

    public static void showProgressDialog(final Activity context) {
        progressDialog = new MyProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public static void hideProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

}
