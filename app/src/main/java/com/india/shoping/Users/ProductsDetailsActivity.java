package com.india.shoping.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.india.shoping.Model.Prevalent;
import com.india.shoping.Model.Products;
import com.india.shoping.Model.Users;
import com.india.shoping.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;

public class ProductsDetailsActivity extends AppCompatActivity {
    private Button addtocartbtn;
    private ElegantNumberButton elegantNumberButton;
    private TextView productname,productprice,productdescription;
    private ImageView imageView;
    String donateproductid;
    private String productid="",state="Normal";
    Users user;
    private String phonekey,name,id,descripton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_details);
        elegantNumberButton=findViewById(R.id.elegant_btn);
        productname=findViewById(R.id.product_name);
        productprice=findViewById(R.id.product_price);
        productdescription=findViewById(R.id.product_description);
        addtocartbtn=findViewById(R.id.add_product_cart);
        imageView=findViewById(R.id.product_image);

        Paper.init(this);
        phonekey=Paper.book().read("loveyou");
        getIntent();
        productid=getIntent().getStringExtra("pid");
        id=getIntent().getStringExtra("id");
        if(productid==null){
            getProductdetails1(id);
            productid=id;
        }else {


//        Toast.makeText(this, String.valueOf(Prevalent.currentonlineuser.getPhone()), Toast.LENGTH_SHORT).show();
            getProductdetails(productid);
        }
        addtocartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(state.equals("order placed")||state.equals("order Shipped")){
                    Toast.makeText(ProductsDetailsActivity.this, "You can order more products once your first order is shipped ", Toast.LENGTH_LONG).show();
                }else {
                    addingtocartlist();
                }
                addingtocartlist();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        orderstate();
    }

    private void addingtocartlist() {
        String savecurrenttime,savecurrentdate;
        Calendar calfordate=Calendar.getInstance();
        SimpleDateFormat currentdate=new SimpleDateFormat("MMM dd,yyyy");
        savecurrentdate=currentdate.format(calfordate.getTime());
        SimpleDateFormat currenttime=new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime=currentdate.format(calfordate.getTime());
      final  DatabaseReference cartlistref=FirebaseDatabase.getInstance().getReference().child("Cartlist");
        final HashMap<String,Object> cartmap=new HashMap<>();
        cartmap.put("pid",productid);
        cartmap.put("pname",productname.getText().toString());
        cartmap.put("price",productprice.getText().toString());
        cartmap.put("date",savecurrentdate);
        cartmap.put("time",savecurrenttime);
        cartmap.put("quantity",elegantNumberButton.getNumber());
        cartmap.put("discount","");
        cartlistref.child("User View").child(phonekey).child("Products").child(productid)
                .updateChildren(cartmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    cartlistref.child("Admin View").child(phonekey).child("Products").child(productid)
                            .updateChildren(cartmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ProductsDetailsActivity.this, "Products are added to cart", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(ProductsDetailsActivity.this,HomeActivity.class);
                                startActivity(intent);
                            }


                        }
                    });
                }
            }
        });
    }


    private void getProductdetails(String productid) {
        DatabaseReference productref= FirebaseDatabase.getInstance().getReference().child("Products");
        productref.child(productid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Products products=dataSnapshot.getValue(Products.class);
                    Picasso.get().load(products.getImg()).into(imageView);
                    productname.setText(products.getName());
                    productprice.setText(products.getPrice());
                    productdescription.setText(products.getDescription());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void orderstate(){
        final DatabaseReference orderref= FirebaseDatabase.getInstance().getReference().child("orders").child(phonekey);
        orderref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String ordestate=dataSnapshot.child("state").getValue().toString();
                    String Username=dataSnapshot.child("name").getValue().toString();

                    if(ordestate.equals("shipped")){
                        state="order Shipped";

                    }else if(ordestate.equals("not shipped")) {
                        state="order placed";
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void getProductdetails1(String id) {
        DatabaseReference productref= FirebaseDatabase.getInstance().getReference().child("Donate Products");
        productref.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Products products=dataSnapshot.getValue(Products.class);
                    Picasso.get().load(products.getImg()).into(imageView);
                    productname.setText(products.getName());
                    productprice.setText(products.getPrice());
                    productdescription.setText(products.getDescription());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
