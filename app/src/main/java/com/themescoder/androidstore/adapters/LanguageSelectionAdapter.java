package com.themescoder.androidstore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.themescoder.androidstore.R;
import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.models.language_model.LanguageDetails;

import java.util.ArrayList;

public class LanguageSelectionAdapter extends RecyclerView.Adapter<LanguageSelectionAdapter.SingleViewHolder> {

    private Context context;
    private ArrayList<LanguageDetails> employees;
    private LanguageClickListener languageClickListener;

    // if checkedPosition = -1, there is no default selection
    // if checkedPosition = 0, 1st item is selected by default
    private int checkedPosition = 0;

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
    }

    public LanguageSelectionAdapter(Context context, ArrayList<LanguageDetails> employees, LanguageClickListener languageClickListener) {
        this.context = context;
        this.employees = employees;
        this.languageClickListener = languageClickListener;
    }

    public void setEmployees(ArrayList<LanguageDetails> employees) {
        this.employees = new ArrayList<>();
        this.employees = employees;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SingleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_language_selection, viewGroup, false);
        return new SingleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleViewHolder singleViewHolder, int position) {
        singleViewHolder.bind(employees.get(position));
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    class SingleViewHolder extends RecyclerView.ViewHolder {


        private ImageView icon;
        private TextView titleText;
        private ImageView selectionIv;

        SingleViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.language_title);
            selectionIv = itemView.findViewById(R.id.checkMark);
            icon = itemView.findViewById(R.id.language_icon);
        }

        void bind(final LanguageDetails employee) {
            if (checkedPosition == -1) {
                selectionIv.setVisibility(View.GONE);
            } else {
                if (checkedPosition == getAdapterPosition()) {
                    selectionIv.setVisibility(View.VISIBLE);
                } else {
                    selectionIv.setVisibility(View.GONE);
                }
            }
            //textView.setText(employee.getName());
            titleText.setText(employee.getName());
            Glide.with(context).load(ConstantValues.ECOMMERCE_URL+ employee.getImage()).into(icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectionIv.setVisibility(View.VISIBLE);
                    if (checkedPosition != getAdapterPosition()) {
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                        languageClickListener.onLanguageClicked(employee);
                    }
                }
            });
        }
    }

    public LanguageDetails getSelected() {
        if (checkedPosition != -1) {
            return employees.get(checkedPosition);
        }
        return null;
    }

    public interface LanguageClickListener{
        void onLanguageClicked(LanguageDetails languageDetails);
    }
}