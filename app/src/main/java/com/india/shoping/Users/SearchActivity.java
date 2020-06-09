package com.india.shoping.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

public class SearchActivity extends AppCompatActivity {
private  com.rey.material.widget.EditText searchtext;
private Button searchbtn;
private RecyclerView searchlist;
private String searchinput;
public DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchtext=findViewById(R.id.search_edit);
        searchbtn=findViewById(R.id.searchbtn);
        searchlist=findViewById(R.id.product_list);

        searchlist.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        searchlist.setHasFixedSize(true);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchinput=searchtext.getText().toString();
                onStart();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        reference= FirebaseDatabase.getInstance().getReference().child("Products");
        FirebaseRecyclerOptions<Products>options=
                new FirebaseRecyclerOptions.Builder<Products>().setQuery(reference.orderByChild("Category").startAt(searchinput).endAt(searchinput),Products.class).build();
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

                                Intent intent=new Intent(SearchActivity.this,ProductsDetailsActivity.class);
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
