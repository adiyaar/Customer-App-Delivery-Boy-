package com.themescoder.androidstore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.themescoder.androidstore.R;
import com.themescoder.androidstore.models.ColorsModal;

import java.util.ArrayList;

public class ColorsAdapter extends RecyclerView.Adapter<ColorsAdapter.SingleViewHolder> {

    private Context context;
    private ArrayList<ColorsModal> employees;
    private ColorClickListener colorClickListener;
    // if checkedPosition = -1, there is no default selection
    // if checkedPosition = 0, 1st item is selected by default
    private int checkedPosition = 0;

    public ColorsAdapter(Context context, ArrayList<ColorsModal> employees, ColorClickListener colorClickListener) {
        this.context = context;
        this.employees = employees;
        this.colorClickListener = colorClickListener;
    }

    public void setEmployees(ArrayList<ColorsModal> employees) {
        this.employees = new ArrayList<>();
        this.employees = employees;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SingleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_color_selection, viewGroup, false);
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

        private CardView cardView;
        private ImageView selectionIv;

        SingleViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            selectionIv = itemView.findViewById(R.id.selectionIV);
        }

        void bind(final ColorsModal employee) {
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
            cardView.setCardBackgroundColor(context.getResources().getColor(employee.getColorPrimary()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectionIv.setVisibility(View.VISIBLE);
                    if (checkedPosition != getAdapterPosition()) {
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                        colorClickListener.onColorClicked(employee);
                    }
                }
            });
        }
    }

    public ColorsModal getSelected() {
        if (checkedPosition != -1) {
            return employees.get(checkedPosition);
        }
        return null;
    }

    public interface ColorClickListener{
        void onColorClicked(ColorsModal colorsModal);
    }
}