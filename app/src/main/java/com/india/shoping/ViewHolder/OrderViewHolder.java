package com.india.shoping.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.india.shoping.Interface.ItemClicklistner;
import com.india.shoping.R;

public class OrderViewHolder extends RecyclerView.ViewHolder {
    private TextView username, Ophone, Oprice, city, date;
    private Button showdetails;

    public OrderViewHolder(@NonNull View itemView) {

        super(itemView);
        username = itemView.findViewById(R.id.order_username);
        Ophone = itemView.findViewById(R.id.order_userphone);
        Oprice = itemView.findViewById(R.id.order_totalprice);
        city = itemView.findViewById(R.id.order_address_city);
        date = itemView.findViewById(R.id.order_date_time);
        showdetails = itemView.findViewById(R.id.show_details);


    }
}
