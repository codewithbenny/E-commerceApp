package com.india.shoping.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.india.shoping.Interface.ItemClicklistner;
import com.india.shoping.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtproductname,txtproductquantity,txtproductprice;
    private ItemClicklistner itemClicklistner;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txtproductname=itemView.findViewById(R.id.product_name_layout);
        txtproductprice=itemView.findViewById(R.id.product_price_layout);
        txtproductquantity=itemView.findViewById(R.id.produt_quantity);
    }

    @Override
    public void onClick(View view) {
        itemClicklistner.onclick(view,getAdapterPosition(),false);
    }

    public void setItemClicklistner(ItemClicklistner itemClicklistner) {
        this.itemClicklistner = itemClicklistner;
    }
}
