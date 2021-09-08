package com.themescoder.driver.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import android.view.View;
import android.view.Window;

import com.themescoder.driver.R;


public class ConnectionFailedDialog extends Dialog implements View.OnClickListener{
    public Activity context;
    public Dialog dialog;
    public AppCompatButton close, tryagain;

    public ConnectionFailedDialog(@NonNull Activity context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_connection_failed);
        close = findViewById(R.id.btn_close);
        tryagain = findViewById(R.id.btn_tryagin);
        close.setOnClickListener(this);
        tryagain.setOnClickListener(this);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    // Click Listener
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tryagin:
                // try again.
                break;
            case R.id.btn_close:
                context.finish();
                break;
            default:
                break;
        }
        dismiss();
    }
}
