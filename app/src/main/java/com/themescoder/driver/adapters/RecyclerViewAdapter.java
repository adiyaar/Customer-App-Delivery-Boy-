package com.themescoder.driver.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.themescoder.driver.Listeners.QrCodeListener;
import com.themescoder.driver.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<ItemObject> itemList;
    private Context context;
    private QrCodeListener listener;

    public RecyclerViewAdapter(Context context, List<ItemObject> itemList, QrCodeListener listener) {
        this.itemList = itemList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_list, null);
        final RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        layoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, rcv.getPosition());
            }
        });
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.countryName.setText(itemList.get(position).getName());
        holder.countryPhoto.setImageResource(R.drawable.qrcode);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
