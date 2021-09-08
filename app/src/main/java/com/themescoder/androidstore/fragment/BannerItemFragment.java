package com.themescoder.androidstore.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.themescoder.androidstore.R;
import com.themescoder.androidstore.constant.ConstantValues;

public class BannerItemFragment extends Fragment {

    ImageView imageView;
    ShimmerFrameLayout bannerItemShimmer;

    String pathToImage;

    public BannerItemFragment(String image) {
        this.pathToImage = image;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_banner, container, false);

        imageView = rootView.findViewById(R.id.imageView);
        bannerItemShimmer = rootView.findViewById(R.id.bannerItemShimmer);

        bannerItemShimmer.setVisibility(View.VISIBLE);
        bannerItemShimmer.startShimmer();

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        Glide.with(getActivity())
                .setDefaultRequestOptions(options)
                .load(ConstantValues.ECOMMERCE_URL + pathToImage)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        bannerItemShimmer.stopShimmer();
                        bannerItemShimmer.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        bannerItemShimmer.stopShimmer();
                        bannerItemShimmer.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);

        return rootView;
    }
}
