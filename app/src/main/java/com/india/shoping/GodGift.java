package com.india.shoping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.india.shoping.Admin.AdminUpdateActivity;
import com.india.shoping.Model.Products;
import com.india.shoping.Users.HomeActivity;
import com.india.shoping.Users.ProductsDetailsActivity;
import com.india.shoping.ViewHolder.ProductViewHolder;
import com.squareup.picasso.Picasso;

public class GodGift extends AppCompatActivity {
private RecyclerView recyclerView;
    private DatabaseReference rootref;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_god_gift);
        recyclerView=findViewById(R.id.RecyclerView1);

        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        onStart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        rootref= FirebaseDatabase.getInstance().getReference().child("Donate Products");
        FirebaseRecyclerOptions<Products> options=
                new FirebaseRecyclerOptions.Builder<Products>().setQuery(rootref,Products.class).build();
        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter=
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull final Products products) {
                        productViewHolder.txtproductname.setText(products.getName());
                        productViewHolder.txtproductprice.setText("Price = "+products.getPrice()+"$");
                        productViewHolder.txtproductDescription.setText(products.getDescription());
                        Picasso.get().load(products.getImg()).into(productViewHolder.imageView);

                        productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                  Intent intent = new Intent(GodGift.this, ProductsDetailsActivity.class);
                                    intent.putExtra("id", products.getPid());
                                    intent.putExtra("name",products.getName());
                                    intent.putExtra("description",products.getDescription());
                                    startActivity(intent);

                            }

                        });
                    }


                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout,parent,false);
                        ProductViewHolder productViewHolder=new ProductViewHolder(view);
                        return productViewHolder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
