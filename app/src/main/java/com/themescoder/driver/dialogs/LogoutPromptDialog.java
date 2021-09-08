package com.themescoder.driver.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import android.view.View;
import android.view.Window;

import com.themescoder.driver.R;


public class LogoutPromptDialog extends Dialog implements View.OnClickListener{
    public Activity context;
    public Dialog dialog;
    public AppCompatButton logout, cancle;

    public LogoutPromptDialog(@NonNull Activity context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_logout_prompt);
        logout = findViewById(R.id.btn_yes);
        cancle = findViewById(R.id.btn_no);
        logout.setOnClickListener(this);
        cancle.setOnClickListener(this);

    }

    // Click Listener
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_no:
                break;
            default:
                break;
        }
        dismiss();
    }
}
