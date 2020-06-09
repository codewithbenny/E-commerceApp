package com.india.shoping.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.india.shoping.Interface.ItemClicklistner;
import com.india.shoping.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtproductname,txtproductDescription,txtproductprice;
    public ImageView imageView;
    public ItemClicklistner itemClicklistner;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.product_image_item);
        txtproductDescription=itemView.findViewById(R.id.product_description_item);
        txtproductname=itemView.findViewById(R.id.product_name_item);
        txtproductprice=itemView.findViewById(R.id.product_price_item);
    }
public void setItemClickListner(ItemClicklistner clickListner){
        this.itemClicklistner=itemClicklistner;
}
    @Override
    public void onClick(View view) {
        itemClicklistner.onclick(view,getAdapterPosition(),false);
    }
}
