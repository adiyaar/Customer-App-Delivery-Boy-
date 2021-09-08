package com.themescoder.driver.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.themescoder.driver.R;
import com.themescoder.driver.api.ServerData;


public class OTPDialog extends Dialog implements View.OnClickListener{
    public Activity context;
    public Dialog dialog;
    public AppCompatButton submit, resend;
    public EditText otpEditText;
    public TextView showingText;

    public OTPDialog(@NonNull Activity context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_otp);
        submit = findViewById(R.id.btn_yes);
        resend = findViewById(R.id.btn_no);
        otpEditText = findViewById(R.id.otpedittext);
        showingText = findViewById(R.id.txt_dia);

        showingText.setText(context.getString(R.string.otp_msg_start) + ServerData.currentDriver.getData().get(0).getPhone() + context.getString(R.string.otp_msg_end));

        submit.setOnClickListener(this);
        resend.setOnClickListener(this);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    // Click Listener
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                context.finish();
                break;
            case R.id.btn_no:
                Toast.makeText(context, "Resending OTP Code", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        dismiss();
    }
}
