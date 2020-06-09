package com.india.shoping.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.india.shoping.Model.Products;
import com.india.shoping.R;
import com.india.shoping.ViewHolder.ProductViewHolder;
import com.squareup.picasso.Picasso;

public class CategoryActivity extends AppCompatActivity {


    private RecyclerView searchlist;

    private String Category;
    public DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        searchlist=findViewById(R.id.product_list);

         Category=getIntent().getStringExtra("category");

        searchlist.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));
        searchlist.setHasFixedSize(true);

    }

    @Override
    protected void onStart() {
        super.onStart();
        reference= FirebaseDatabase.getInstance().getReference().child("Products");
        FirebaseRecyclerOptions<Products> options=
                new FirebaseRecyclerOptions.Builder<Products>().setQuery(reference.orderByChild("Category").startAt(Category).endAt(Category),Products.class).build();
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

                                Intent intent=new Intent(CategoryActivity.this,ProductsDetailsActivity.class);
                                intent.putExtra("pid",products.getPid());
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
        searchlist.setAdapter(adapter);
        adapter.startListening();
    }



}
