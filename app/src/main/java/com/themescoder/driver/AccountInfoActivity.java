package com.themescoder.driver;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.themescoder.driver.R;
import com.themescoder.driver.api.RetrofitClient;
import com.themescoder.driver.api.ServerData;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AccountInfoActivity extends AppCompatActivity {

    @BindView(R.id.profile_image)
    CircleImageView profilePicture;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.name2)
    TextView name2;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.email2)
    TextView email2;
    @BindView(R.id.dob)
    TextView dob;
    @BindView(R.id.blodGroup)
    TextView blodGroup;
    @BindView(R.id.bikeName)
    TextView bikeName;
    @BindView(R.id.bikeDetails)
    TextView bikeDetails;
    @BindView(R.id.bikeColor)
    TextView bikeColor;
    @BindView(R.id.ownerName)
    TextView ownerName;
    @BindView(R.id.registrationNo)
    TextView registrationNo;

    @BindView(R.id.voterID)
    TextView voterId;

    @BindView(R.id.refferrerName)
    TextView refferrerName;



    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        ButterKnife.bind(this);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Account");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Glide.with(AccountInfoActivity.this)
                .load(RetrofitClient.BASE_URL_RES +""+ ServerData.currentDriver.getData().get(0).getAvatar())
                .into(profilePicture);

        name.setText(ServerData.currentDriver.getData().get(0).getFirstName() + " " + ServerData.currentDriver.getData().get(0).getLastName());
        email.setText(ServerData.currentDriver.getData().get(0).getEmail());

        //Personal
        name2.setText(ServerData.currentDriver.getData().get(0).getFirstName() + " " + ServerData.currentDriver.getData().get(0).getLastName());
        email2.setText(ServerData.currentDriver.getData().get(0).getEmail());
        phone.setText(ServerData.currentDriver.getData().get(0).getPhone());
        dob.setText(ServerData.currentDriver.getData().get(0).getDob().split(" ")[0]);
        blodGroup.setText(ServerData.currentDriver.getData().get(0).getBloodGroup());

        //Bike
        bikeName.setText(ServerData.currentDriver.getData().get(0).getBikeName());
        bikeDetails.setText(ServerData.currentDriver.getData().get(0).getBikeDetails());
        bikeColor.setText(ServerData.currentDriver.getData().get(0).getBikeColor());
        ownerName.setText(ServerData.currentDriver.getData().get(0).getOwnerName());
        registrationNo.setText(ServerData.currentDriver.getData().get(0).getVehicleRegistrationNumber());

        voterId.setText(ServerData.currentDriver.getData().get(0).getVoterId()+"");
        //Refferrer
        refferrerName.setText(ServerData.currentDriver.getData().get(0).getReferrerName());


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

    public void imageClicked(View view) {

        Intent intent = new Intent(AccountInfoActivity.this, ImagePreviewActivity.class);
        switch (view.getId()){
            case R.id.license:
                intent.putExtra("IMAGE_URL", RetrofitClient.BASE_URL_RES +""+ ServerData.currentDriver.getData().get(0).getDrivingLicenseImage());
                break;
            case R.id.rcBook:
                intent.putExtra("IMAGE_URL", RetrofitClient.BASE_URL_RES +""+ ServerData.currentDriver.getData().get(0).getVehicleRcBookImage());
                break;
            case R.id.aadharCard:
                intent.putExtra("IMAGE_URL", RetrofitClient.BASE_URL_RES +""+ ServerData.currentDriver.getData().get(0).getAadhaarCardImage());
                break;
            case R.id.bankPassbook:
                intent.putExtra("IMAGE_URL", RetrofitClient.BASE_URL_RES +""+ ServerData.currentDriver.getData().get(0).getBankPassbookImage());
                break;
            case R.id.panCard:
                intent.putExtra("IMAGE_URL", RetrofitClient.BASE_URL_RES +""+ ServerData.currentDriver.getData().get(0).getPanCardImage());
                break;
            case R.id.refferrerAadhaarCard:
                intent.putExtra("IMAGE_URL", RetrofitClient.BASE_URL_RES +""+ ServerData.currentDriver.getData().get(0).getReferrerAadhaarImage());
                break;
        }
        startActivity(intent);
    }
}
