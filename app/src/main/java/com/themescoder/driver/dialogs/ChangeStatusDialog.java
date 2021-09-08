package com.themescoder.driver.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.themescoder.driver.R;


public class ChangeStatusDialog extends Dialog implements View.OnClickListener{
    public Activity context;
    public Dialog dialog;
    public AppCompatButton submit, resend;
    public EditText editText;

    public ChangeStatusDialog(@NonNull Activity context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_change_status);
        submit = findViewById(R.id.btn_yes);
        resend = findViewById(R.id.btn_no);
        editText = findViewById(R.id.pincode);
        resend.setOnClickListener(this);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    // Click Listener
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
