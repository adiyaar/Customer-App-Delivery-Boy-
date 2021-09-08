package com.themescoder.androidstore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.themescoder.androidstore.R;
import com.themescoder.androidstore.models.currency_model.CurrencyList;

import java.util.ArrayList;

public class CurrenciesSelectionAdapter extends RecyclerView.Adapter<CurrenciesSelectionAdapter.SingleViewHolder> {

    private Context context;
    private ArrayList<CurrencyList> employees;
    private CurrencyClickListener currencyClickListener;

    // if checkedPosition = -1, there is no default selection
    // if checkedPosition = 0, 1st item is selected by default
    private int checkedPosition = 0;

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
    }

    public CurrenciesSelectionAdapter(Context context, ArrayList<CurrencyList> employees, CurrencyClickListener currencyClickListener) {
        this.context = context;
        this.employees = employees;
        this.currencyClickListener = currencyClickListener;
    }

    public void setEmployees(ArrayList<CurrencyList> employees) {
        this.employees = new ArrayList<>();
        this.employees = employees;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SingleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_currency_selection, viewGroup, false);
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


        private TextView titleText;
        private ImageView selectionIv;

        SingleViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.textTitle);
            selectionIv = itemView.findViewById(R.id.checkMark);

        }

        void bind(final CurrencyList employee) {

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
            titleText.setText(employee.getTitle() + " (" + employee.getCode() + ")");


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectionIv.setVisibility(View.VISIBLE);
                    if (checkedPosition != getAdapterPosition()) {
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                        currencyClickListener.onCurrencyClickListener(employee);
                    }
                }
            });
        }
    }

    public CurrencyList getSelected() {
        if (checkedPosition != -1) {
            return employees.get(checkedPosition);
        }
        return null;
    }

    public interface CurrencyClickListener {
        void onCurrencyClickListener(CurrencyList currencyDetails);
    }
}