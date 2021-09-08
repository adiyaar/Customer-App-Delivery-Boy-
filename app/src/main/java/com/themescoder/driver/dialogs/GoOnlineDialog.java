package com.themescoder.driver.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import android.view.View;
import android.view.Window;

import com.themescoder.driver.R;


public class GoOnlineDialog extends Dialog implements View.OnClickListener{
    public Activity context;
    public Dialog dialog;
    public AppCompatButton submit;
    public AppCompatCheckBox checkBox;

    public GoOnlineDialog(@NonNull Activity context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_go_online);
        submit = findViewById(R.id.btn_yes);
        checkBox = findViewById(R.id.skip);
        submit.setOnClickListener(this);
    }

    // Click Listener
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                context.finish();
                break;
            default:
                break;
        }
        dismiss();
    }
}
