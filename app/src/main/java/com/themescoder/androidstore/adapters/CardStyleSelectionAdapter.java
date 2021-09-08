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

public class CardStyleSelectionAdapter extends RecyclerView.Adapter<CardStyleSelectionAdapter.SingleViewHolder> {

    private Context context;
    private ArrayList<String> employees;
    private CardStyleClickListener cardStyleClickListener;

    // if checkedPosition = -1, there is no default selection
    // if checkedPosition = 0, 1st item is selected by default
    private int checkedPosition = ConstantValues.DEFAULT_PRODUCT_CARD_STYLE;

    public CardStyleSelectionAdapter(Context context, ArrayList<String> employees, CardStyleClickListener cardStyleClickListener) {
        this.context = context;
        this.employees = employees;
        this.cardStyleClickListener = cardStyleClickListener;
    }

    public void setEmployees(ArrayList<String> employees) {
        this.employees = new ArrayList<>();
        this.employees = employees;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SingleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card_style_selection, viewGroup, false);
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
            bannerImage = itemView.findViewById(R.id.imageView);
            checkMark = itemView.findViewById(R.id.checkMark);

        }

        void bind(final String employee, final int position) {
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
                        cardStyleClickListener.onCardStyleClicked(employee, position);
                    }
                }
            });

            switch (position){
                case 0:
                    bannerImage.setImageResource(R.drawable.rcard1);
                    break;
                case 1:
                    bannerImage.setImageResource(R.drawable.rcard2);
                    break;
                case 2:
                    bannerImage.setImageResource(R.drawable.rcard3);
                    break;
                case 3:
                    bannerImage.setImageResource(R.drawable.rcard4);
                    break;
                case 4:
                    bannerImage.setImageResource(R.drawable.rcard5);
                    break;
                case 5:
                    bannerImage.setImageResource(R.drawable.rcard6);
                    break;
                case 6:
                    bannerImage.setImageResource(R.drawable.rcard7);
                    break;
                case 7:
                    bannerImage.setImageResource(R.drawable.rcard8);
                    break;
                case 8:
                    bannerImage.setImageResource(R.drawable.rcard9);
                    break;
                case 9:
                    bannerImage.setImageResource(R.drawable.rcard10);
                    break;
                case 10:
                    bannerImage.setImageResource(R.drawable.rcard11);
                    break;
                case 11:
                    bannerImage.setImageResource(R.drawable.rcard12);
                    break;
                case 12:
                    bannerImage.setImageResource(R.drawable.rcard13);
                    break;
                case 13:
                    bannerImage.setImageResource(R.drawable.rcard14);
                    break;
                case 14:
                    bannerImage.setImageResource(R.drawable.rcard15);
                    break;
                case 15:
                    bannerImage.setImageResource(R.drawable.rcard16);
                    break;
                case 16:
                    bannerImage.setImageResource(R.drawable.rcard17);
                    break;
                case 17:
                    bannerImage.setImageResource(R.drawable.rcard18);
                    break;
                case 18:
                    bannerImage.setImageResource(R.drawable.rcard19);
                    break;
                case 19:
                    bannerImage.setImageResource(R.drawable.rcard20);
                    break;
                case 20:
                    bannerImage.setImageResource(R.drawable.rcard21);
                    break;
                case 21:
                    bannerImage.setImageResource(R.drawable.rcard22);
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

    public interface CardStyleClickListener {
        void onCardStyleClicked(String cardStyle, int position);
    }
}