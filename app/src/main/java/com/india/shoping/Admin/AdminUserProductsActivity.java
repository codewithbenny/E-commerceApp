package com.india.shoping.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.india.shoping.Model.Cart;
import com.india.shoping.Model.Products;
import com.india.shoping.R;
import com.india.shoping.ViewHolder.CartViewHolder;

public class AdminUserProductsActivity extends AppCompatActivity {
    private RecyclerView cartlist;
    private DatabaseReference cartlistref;
    RecyclerView.LayoutManager layoutManager;
    private String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_products);
        cartlist=findViewById(R.id.product_list);
        UserId=getIntent().getStringExtra("uid");
        cartlist.setHasFixedSize(true);
        cartlist.setLayoutManager(new LinearLayoutManager(this));

        cartlistref= FirebaseDatabase.getInstance().getReference().child("Cartlist").child("Admin View").child(UserId).child("Products");
        FirebaseRecyclerOptions<Cart> options=new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartlistref,Cart.class).build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder>adapter=new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull Cart cart) {
                cartViewHolder.txtproductquantity.setText(cart.getQuantity());
                cartViewHolder.txtproductname.setText(cart.getPname());
                cartViewHolder.txtproductprice.setText(cart.getPrice());
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitem_layout,parent,false);
                CartViewHolder holder=new CartViewHolder(view);
                return holder;
            }
        };
        cartlist.setAdapter(adapter);
        adapter.startListening();
    }
}
