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


public class CommentsDialog extends Dialog implements View.OnClickListener{
    public Activity context;
    public Dialog dialog;
    public AppCompatButton cancel, submit;
    public EditText otpEditText;

    public CommentsDialog(@NonNull Activity context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_comments);
        submit = findViewById(R.id.btn_yes);
        cancel = findViewById(R.id.btn_no);
        otpEditText = findViewById(R.id.otpedittext);
        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);
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
                break;
            default:
                break;
        }
        dismiss();
    }
}
