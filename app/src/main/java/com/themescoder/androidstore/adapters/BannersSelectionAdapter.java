package com.themescoder.androidstore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.themescoder.androidstore.R;
import com.themescoder.androidstore.constant.ConstantValues;

import java.util.ArrayList;

public class BannersSelectionAdapter extends RecyclerView.Adapter<BannersSelectionAdapter.SingleViewHolder> {

    private Context context;
    private ArrayList<String> employees;
    private BannerClickListener bannerClickListener;

    // if checkedPosition = -1, there is no default selection
    // if checkedPosition = 0, 1st item is selected by default
    private int checkedPosition = ConstantValues.DEFAULT_BANNER_STYLE;

    public BannersSelectionAdapter(Context context, ArrayList<String> employees, BannerClickListener bannerClickListener) {
        this.context = context;
        this.employees = employees;
        this.bannerClickListener = bannerClickListener;
    }

    public void setEmployees(ArrayList<String> employees) {
        this.employees = new ArrayList<>();
        this.employees = employees;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SingleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_banners_selection, viewGroup, false);
        return new SingleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleViewHolder singleViewHolder, int position) {
        singleViewHolder.bind(employees.get(position), position);
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    class SingleViewHolder extends RecyclerView.ViewHolder {

        private ImageView bannerImage;
        private ImageView checkMark;

        SingleViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerImage = itemView.findViewById(R.id.bannerImage);
            checkMark = itemView.findViewById(R.id.checkMark);

        }

        void bind(final String employee, int position) {
            if (checkedPosition == -1) {
                checkMark.setVisibility(View.GONE);
            } else {
                if (checkedPosition == getAdapterPosition()) {
                    checkMark.setVisibility(View.VISIBLE);
                } else {
                    checkMark.setVisibility(View.GONE);
                }
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkMark.setVisibility(View.VISIBLE);
                    if (checkedPosition != getAdapterPosition()) {
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                        bannerClickListener.onBannerClicked(employee, getAdapterPosition());
                    }
                }
            });

            switch (position){
                case 0:
                    bannerImage.setImageResource(R.drawable.banner_selection_1);
                    break;
                case 1:
                    bannerImage.setImageResource(R.drawable.banner_selection_2);
                    break;
                case 2:
                    bannerImage.setImageResource(R.drawable.banner_selection_3);
                    break;
                case 3:
                    bannerImage.setImageResource(R.drawable.banner_selection_4);
                    break;
                case 4:
                    bannerImage.setImageResource(R.drawable.banner_selection_5);
                    break;
                case 5:
                    bannerImage.setImageResource(R.drawable.banner_selection_6);
                    break;
            }

        }
    }

    public String getSelected() {
        if (checkedPosition != -1) {
            return employees.get(checkedPosition);
        }
        return null;
    }

    public interface BannerClickListener{
        void onBannerClicked(String employee, int position);
    }
}