package com.themescoder.driver;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.themescoder.driver.Listeners.QrCodeListener;
import com.themescoder.driver.R;
import com.themescoder.driver.adapters.ItemObject;
import com.themescoder.driver.adapters.MyRecyclerViewAdapter;
import com.themescoder.driver.adapters.RecyclerViewAdapter;
import com.themescoder.driver.api.RetrofitClient;
import com.themescoder.driver.models.PagesResponse;
import com.themescoder.driver.models.QrCodesResponse;
import com.themescoder.driver.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LegalContectsActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private FrameLayout frameLayout;
    private TextView legalContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal_contects);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getExtras().getString("ITEM_CLICKED"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        progressBar = findViewById(R.id.progressBar);
        frameLayout = findViewById(R.id.frameLayout);
        legalContent = findViewById(R.id.legalcontent);

        if (getIntent().getExtras().getString("ITEM_CLICKED").equals("payouts")) {
            /*
             * Payouts Triggered
             * * */
            init_for_payouts();
        } else {
            frameLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            Call<PagesResponse> call = RetrofitClient.getInstance().getPages("1");
            call.enqueue(new Callback<PagesResponse>() {
                @Override
                public void onResponse(Call<PagesResponse> call, Response<PagesResponse> response) {
                    if (response.isSuccessful() && response.body().getSuccess().equals("1")) {

                        frameLayout.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        if (getIntent().getExtras().getString("ITEM_CLICKED").equals("general_terms")) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                legalContent.setText(Html.fromHtml(response.body().getPagesData().get(1).getDescription(), Html.FROM_HTML_MODE_COMPACT));
                            } else {
                                legalContent.setText(Html.fromHtml(response.body().getPagesData().get(1).getDescription()));
                            }
                        } else if (getIntent().getExtras().getString("ITEM_CLICKED").equals("code_of_conduct")) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                legalContent.setText(Html.fromHtml(response.body().getPagesData().get(0).getDescription(), Html.FROM_HTML_MODE_COMPACT));
                            } else {
                                legalContent.setText(Html.fromHtml(response.body().getPagesData().get(0).getDescription()));
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<PagesResponse> call, Throwable t) {

                }
            });
        }
    }

    private void init_for_payouts() {
        findViewById(R.id.legalcontent).setVisibility(View.GONE);
        findViewById(R.id.recyclerView).setVisibility(View.VISIBLE);

        frameLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        int spacingInPixels = 10;
        final List<ItemObject> rowListItem = getAllItemList();
        GridLayoutManager lLayout = new GridLayoutManager(LegalContectsActivity.this, 2);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(lLayout);
        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(LegalContectsActivity.this, rowListItem, new QrCodeListener() {
            @Override
            public Void onItemClick(View v, int position) {
                Intent intent = new Intent(LegalContectsActivity.this, ImagePreviewActivity.class);
                intent.putExtra("IMAGE_URL", RetrofitClient.BASE_URL_RES +""+ rowListItem.get(position).getPhoto());
                startActivity(intent);
                return null;
            }
        });
        recyclerView.setAdapter(rcAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "clicked: " + position, Toast.LENGTH_SHORT).show();
    }


    private List<ItemObject> getAllItemList() {

        final List<ItemObject> allItems = new ArrayList<ItemObject>();
        Call<QrCodesResponse> call = RetrofitClient.getInstance().getQrCodes("1");
        call.enqueue(new Callback<QrCodesResponse>() {
            @Override
            public void onResponse(Call<QrCodesResponse> call, Response<QrCodesResponse> response) {

                if (response.isSuccessful() && response.body().getSuccess().equals("1")) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        allItems.add(new ItemObject(response.body().getData().get(i).getQrCodesName(), response.body().getData().get(i).getQrCodesImage()));
                    }
                    progressBar.setVisibility(View.GONE);
                    frameLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<QrCodesResponse> call, Throwable t) {

            }
        });
        return allItems;
    }
}
