package com.themescoder.driver.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.themescoder.driver.LegalContectsActivity;
import com.themescoder.driver.R;


public class LegalStatementsDialog extends Dialog implements View.OnClickListener{
    public Activity context;
    public Dialog dialog;
    public TextView general_terms;
    public TextView code_of_conduct;

    public LegalStatementsDialog(@NonNull Activity context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_legal);

        general_terms = findViewById(R.id.dialog_item_general);
        code_of_conduct = findViewById(R.id.dialog_item_code);

        general_terms.setOnClickListener(this);
        code_of_conduct.setOnClickListener(this);
    }

    // Click Listener
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, LegalContectsActivity.class);
        switch (v.getId()){
            case R.id.dialog_item_general:
                intent.putExtra("ITEM_CLICKED", "general_terms");
                break;
            case R.id.dialog_item_code:
                intent.putExtra("ITEM_CLICKED", "code_of_conduct");
                break;
        }
        context.startActivity(intent);
        dismiss();
    }
}
