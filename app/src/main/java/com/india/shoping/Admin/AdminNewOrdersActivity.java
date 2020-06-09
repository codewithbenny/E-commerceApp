package com.india.shoping.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.india.shoping.Model.Orders;
import com.india.shoping.R;

public class AdminNewOrdersActivity extends AppCompatActivity {
    private RecyclerView orderlist;
    DatabaseReference orderref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_orders);
        orderlist = findViewById(R.id.recycler);

        orderlist.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();


         orderref = FirebaseDatabase.getInstance().getReference().child("orders");

        FirebaseRecyclerOptions<Orders> options =
                new FirebaseRecyclerOptions.Builder<Orders>().setQuery(orderref, Orders.class).build();
        FirebaseRecyclerAdapter<Orders, OrderViewHolder> adapter = new FirebaseRecyclerAdapter<Orders, OrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, final int i, @NonNull final Orders orders) {
                holder.username.setText("Name:" + orders.getName());
                holder.Ophone.setText("Phone:" + orders.getPhone());
                holder.Oprice.setText("Price $:" + orders.getTotalAmount());
                holder.date.setText("at:" + orders.getDate() + "\nTime:" + orders.getTime());
                holder.city.setText("Shipping Address:" + orders.getAddress());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] =new CharSequence[]{
                                "Yes",
                                "No"
                        };
                        AlertDialog.Builder builder=new AlertDialog.Builder(AdminNewOrdersActivity.this);
                        builder.setTitle("Have you Shipped this product?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i==0){
                                  String Uid=getRef(i).getKey();
                                  Removeorder(Uid);
                                }else {
                                    finish();
                                }
                            }
                        });
                        builder.show();
                    }
                });
                holder.showdetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String uid=getRef(i).getKey();
                        Intent intent=new Intent(AdminNewOrdersActivity.this,AdminUserProductsActivity.class);
                        intent.putExtra("uid",uid);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_layout, parent, false);
                OrderViewHolder orderViewHolder = new OrderViewHolder(view);
                return orderViewHolder;
            }
        };
        orderlist.setAdapter(adapter);
        adapter.startListening();
    }

    private void Removeorder(String uid) {

        orderref.child(uid).removeValue();
    }


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


}