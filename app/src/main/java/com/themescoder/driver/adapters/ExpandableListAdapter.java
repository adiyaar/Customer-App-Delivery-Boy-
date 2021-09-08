package com.themescoder.driver.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.themescoder.driver.ImagePreviewActivity;
import com.themescoder.driver.R;
import com.themescoder.driver.api.RetrofitClient;
import com.themescoder.driver.models.OrderDetails;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<String> mListDataHeader; // header titles

    // child data in format of header title, child title
    private HashMap<String, List<OrderDetails>> mListDataChild;
    ExpandableListView expandList;

    String curruncy = "";

    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<OrderDetails>> listChildData, String curruncy, ExpandableListView mView) {
        this.mContext = context;
        this.mListDataHeader = listDataHeader;
        this.mListDataChild = listChildData;
        this.expandList = mView;
        this.curruncy = curruncy;
    }

    @Override
    public int getGroupCount() {
        int i = mListDataHeader.size();
        Log.d("GROUPCOUNT", String.valueOf(i));
        return this.mListDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int childCount = 0;
            childCount = this.mListDataChild.get(this.mListDataHeader.get(groupPosition))
                    .size();

        return childCount;
    }



    @Override
    public Object getGroup(int groupPosition) {
        return this.mListDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Log.d("CHILD", mListDataChild.get(this.mListDataHeader.get(groupPosition))
                .get(childPosition).toString());
        return this.mListDataChild.get(this.mListDataHeader.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_product_header, null);
        }
/*

        final String shopName = mListDataHeader.get(groupPosition).split(" -- ")[0];
        final String shopPhone = mListDataHeader.get(groupPosition).split(" -- ")[1];
        final String shopCoordinates = mListDataHeader.get(groupPosition).split(" -- ")[2];

        TextView header = convertView.findViewById(R.id.header);

        AppCompatButton phoneButton = convertView.findViewById(R.id.contactButtonVendor);
        AppCompatButton navigateButton = convertView.findViewById(R.id.navigateButtonVendor);

        if (shopPhone.equalsIgnoreCase("EMPTY")) phoneButton.setVisibility(View.GONE);

        navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String geoUri = "http://maps.google.com/maps?q=loc:" + shopCoordinates  + " (" + shopName + ")";
                intent.setData(Uri.parse(geoUri));
                if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, "could not find the customer location", Toast.LENGTH_SHORT).show();
                }
            }
        });

        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doMakeCall(shopPhone);
            }
        });

        header.setText(shopName);
*/
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_product_detail, null);
        }
        final ProgressBar progressBar;
        ImageView productImage;
        TextView productName, productPrice, productQuantity, productFinalPrice, productStatusText;
        productName = convertView.findViewById(R.id.productName);
        productPrice = convertView.findViewById(R.id.productPrice);
        productQuantity = convertView.findViewById(R.id.productQuantity);
        productFinalPrice = convertView.findViewById(R.id.productFinalPrice);
        productImage = convertView.findViewById(R.id.productImage);
        progressBar = convertView.findViewById(R.id.progressBar);
        productStatusText = convertView.findViewById(R.id.productStatusText);

        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ImagePreviewActivity.class);
                intent.putExtra("IMAGE_URL", RetrofitClient.BASE_URL_RES +""+ mListDataChild.get(mListDataHeader.get(groupPosition)).get(childPosition).getImage());
                mContext.startActivity(intent);
            }
        });

        Glide.with(mContext)
                .load(RetrofitClient.BASE_URL_RES +""+ mListDataChild.get(mListDataHeader.get(groupPosition)).get(childPosition).getImage())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(productImage);
        productName.setText(mListDataChild.get(mListDataHeader.get(groupPosition)).get(childPosition).getProductsName());
        productPrice.setText(curruncy+" "+mListDataChild.get(mListDataHeader.get(groupPosition)).get(childPosition).getProductsPrice());
        productQuantity.setText(mListDataChild.get(mListDataHeader.get(groupPosition)).get(childPosition).getProductsQuantity().toString());
        productFinalPrice.setText(curruncy+" "+mListDataChild.get(mListDataHeader.get(groupPosition)).get(childPosition).getFinalPrice());
        productStatusText.setText("**"+mListDataChild.get(mListDataHeader.get(groupPosition)).get(childPosition).getVendorOrdersStatusStatus());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void doMakeCall(String phoneNumber) {

        String[] permissions = {Manifest.permission.CALL_PHONE};

        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+phoneNumber));
            mContext.startActivity(callIntent);
            // do your work.
        } else {
            ActivityCompat.requestPermissions((AppCompatActivity)mContext,
                    permissions,
                    123);
        }
    }
}