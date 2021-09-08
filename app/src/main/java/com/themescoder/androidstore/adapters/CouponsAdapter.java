package com.themescoder.androidstore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.themescoder.androidstore.R;

import java.text.DecimalFormat;
import java.util.List;

import com.themescoder.androidstore.constant.ConstantValues;
import com.themescoder.androidstore.fragment.CheckoutFinal;
import com.themescoder.androidstore.models.coupons_model.CouponsInfo;


/**
 * CouponsAdapter is the adapter class of RecyclerView holding List of Coupons in CheckoutFinal and Order_Details
 **/

public class CouponsAdapter extends RecyclerView.Adapter<CouponsAdapter.MyViewHolder> {

    Context context;
    Boolean isRemovable;

    CheckoutFinal checkoutFinal;
    List<CouponsInfo> couponsList;

    private String currency = "";

    public CouponsAdapter(Context context, List<CouponsInfo> couponsList, Boolean isRemovable, CheckoutFinal checkoutFinal) {
        this.context = context;
        this.checkoutFinal = checkoutFinal;
        this.isRemovable = isRemovable;
        this.couponsList = couponsList;
    }

    public CouponsAdapter(Context context, List<CouponsInfo> couponsList, Boolean isRemovable, CheckoutFinal checkoutFinal, String currency) {
        this.context = context;
        this.checkoutFinal = checkoutFinal;
        this.isRemovable = isRemovable;
        this.couponsList = couponsList;
        this.currency = currency;
    }


    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_coupons, parent, false);

        return new MyViewHolder(itemView);
    }


    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // Get the data model based on Position
        final CouponsInfo coupon = couponsList.get(position);

        holder.coupon_code.setText(coupon.getCode());
        if (currency.isEmpty())
            holder.coupon_discount.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(Double.parseDouble(coupon.getAmount())));
        else
            holder.coupon_discount.setText(new DecimalFormat("#0.00").format(Double.parseDouble(coupon.getAmount())) + " " + currency);

        if (coupon.getDiscountType().equalsIgnoreCase("fixed_cart") || coupon.getDiscountType().equalsIgnoreCase("percent")) {
            holder.coupon_type.setText(context.getString(R.string.cart));
        } else if (coupon.getDiscountType().equalsIgnoreCase("fixed_product") || coupon.getDiscountType().equalsIgnoreCase("percent_product")) {
            holder.coupon_type.setText(context.getString(R.string.product));
        }


        if (coupon.getDiscountType().equalsIgnoreCase("fixed_cart") || coupon.getDiscountType().equalsIgnoreCase("fixed_product")) {
            if (currency.isEmpty())
                holder.coupon_amount.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(Double.parseDouble(coupon.getAmount())));
            else
                holder.coupon_amount.setText(new DecimalFormat("#0.00").format(Double.parseDouble(coupon.getAmount())) + " " + currency);
        } else if (coupon.getDiscountType().equalsIgnoreCase("percent") || coupon.getDiscountType().equalsIgnoreCase("percent_product")) {
            holder.coupon_amount.setText(new DecimalFormat("#0.00").format(Double.parseDouble(coupon.getAmount())) + "%");
        }


        if (isRemovable) {
            holder.coupon_delete.setVisibility(View.VISIBLE);

            holder.coupon_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    checkoutFinal.removeCoupon(coupon);

                    notifyItemRemoved(holder.getAdapterPosition());
                    notifyItemRangeRemoved(holder.getAdapterPosition(), getItemCount());
                    notifyDataSetChanged();
                }
            });

        } else {
            holder.coupon_delete.setVisibility(View.GONE);
        }

    }


    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return couponsList.size();
    }


    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageButton coupon_delete;
        private TextView coupon_code, coupon_type, coupon_amount, coupon_discount;


        public MyViewHolder(final View itemView) {
            super(itemView);

            coupon_code = (TextView) itemView.findViewById(R.id.coupon_code);
            coupon_type = (TextView) itemView.findViewById(R.id.coupon_type);
            coupon_amount = (TextView) itemView.findViewById(R.id.coupon_amount);
            coupon_discount = (TextView) itemView.findViewById(R.id.coupon_discount);
            coupon_delete = (ImageButton) itemView.findViewById(R.id.coupon_delete);

        }
    }
}

