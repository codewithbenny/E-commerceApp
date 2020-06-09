package com.india.shoping.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.india.shoping.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminUpdateActivity extends AppCompatActivity {
private EditText name,price,Description;
private Button update,delete;
private ImageView imageView;
    private String productID="";
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update);
        name=findViewById(R.id.product_name_maintain);
        price=findViewById(R.id.product_price_maintain);
        Description=findViewById(R.id.product_description_maintain);
        update=findViewById(R.id.appluchanges);
        imageView=findViewById(R.id.product_image_maintain);
        delete=findViewById(R.id.deleteproduct);
        productID=getIntent().getStringExtra("pid");
        reference=FirebaseDatabase.getInstance().getReference().child("Products").child(productID);
        displaySpecificProductinfo();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeproduct();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applychanges();
            }
        });

    }

    private void applychanges() {
        String pName=name.getText().toString();
        String pPrice=price.getText().toString();
        String pDescription=Description.getText().toString();
        if(pName.equals("")){
            Toast.makeText(this, "Enter Name of Product", Toast.LENGTH_SHORT).show();
        }else if(pPrice.equals("")){
            Toast.makeText(this, "Enter your Price", Toast.LENGTH_SHORT).show();
        }else if(pDescription.equals("")){
            Toast.makeText(this, "Provide Description of the Product", Toast.LENGTH_SHORT).show();
        }else{
            HashMap<String, Object> ProductMap = new HashMap<>();
            ProductMap.put("description", pDescription);
            ProductMap.put("Price", pPrice);
            ProductMap.put("Name", pName);
            reference.updateChildren(ProductMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(AdminUpdateActivity.this, "Info is Successfully Updated", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(AdminUpdateActivity.this, AdminCategoryActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
    }

    private void displaySpecificProductinfo() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String pname=dataSnapshot.child("Name").getValue().toString();
                    String pprice=dataSnapshot.child("Price").getValue().toString();
                    String pdescription=dataSnapshot.child("description").getValue().toString();
                    String pimage=dataSnapshot.child("Img").getValue().toString();

                    name.setText(pname);
                    price.setText(pprice);
                    Description.setText(pdescription);
                    Picasso.get().load(pimage).into(imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void removeproduct(){
        reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AdminUpdateActivity.this, "Product deleted Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(AdminUpdateActivity.this,AdminCategoryActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
