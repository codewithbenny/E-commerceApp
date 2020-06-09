package com.india.shoping.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.india.shoping.Admin.AdminCategoryActivity;
import com.india.shoping.Admin.AdminNewOrdersActivity;
import com.india.shoping.Admin.AdminaddnewProductActivity;
import com.india.shoping.R;

public class CategoryDonate extends AppCompatActivity {
    private ImageView tshirts,sportstshirt,femaledresses,sweaters;
    private ImageView glasses,hats,watches,purses,shoes,hadphones,laptops,phones;
    String qwe="donate";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_donate);
        tshirts=findViewById(R.id.t_shirts1);
        sportstshirt=findViewById(R.id.sports_tshirts1);
        femaledresses=findViewById(R.id.female_dresses1);
        sweaters=findViewById(R.id.sweater1);
        glasses=findViewById(R.id.glasses1);
        hats=findViewById(R.id.hats1);
        purses=findViewById(R.id.purses1);
        shoes=findViewById(R.id.shoes1);
        hadphones=findViewById(R.id.headphones1);
        laptops=findViewById(R.id.laptop1);
        phones=findViewById(R.id.mobiles1);
        watches=findViewById(R.id.watches1);
       


        tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qwe=="donate"){
                    Intent intent =new Intent(CategoryDonate.this, Donate.class);
                    intent.putExtra("category","tshirts");
                    startActivity(intent) ;
                }else {
                    Intent intent = new Intent(CategoryDonate.this, AdminaddnewProductActivity.class);
                    intent.putExtra("category", "tshirts");
                    startActivity(intent);
                }
            }
        });

        sportstshirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qwe=="donate"){
                    Intent intent =new Intent(CategoryDonate.this, Donate.class);
                    intent.putExtra("category","sporttshirt");
                    startActivity(intent) ;
                }else {
                    Intent intent = new Intent(CategoryDonate.this, AdminaddnewProductActivity.class);
                    intent.putExtra("category", "sporttshirt");
                    startActivity(intent);
                }
            }
        });

        femaledresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qwe == "donate") {
                    Intent intent = new Intent(CategoryDonate.this, Donate.class);
                    intent.putExtra("category", "femaledresses");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(CategoryDonate.this, AdminaddnewProductActivity.class);
                    intent.putExtra("category", "femaledresses");
                    startActivity(intent);
                }
            }
        });

        sweaters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qwe == "donate") {
                    Intent intent = new Intent(CategoryDonate.this, Donate.class);
                    intent.putExtra("category", "sweaters");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(CategoryDonate.this, AdminaddnewProductActivity.class);
                    intent.putExtra("category", "sweaters");
                    startActivity(intent);
                }
            }
        });

        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qwe == "donate") {
                    Intent intent = new Intent(CategoryDonate.this, Donate.class);
                    intent.putExtra("category", "glasses");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(CategoryDonate.this, AdminaddnewProductActivity.class);
                    intent.putExtra("category", "glasses");
                    startActivity(intent);
                }
            }
        });

        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qwe == "donate") {
                    Intent intent = new Intent(CategoryDonate.this, Donate.class);
                    intent.putExtra("category", "hats");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(CategoryDonate.this, AdminaddnewProductActivity.class);
                    intent.putExtra("category", "hats");
                    startActivity(intent);
                }
            }
        });

        hadphones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qwe == "donate") {
                    Intent intent = new Intent(CategoryDonate.this, Donate.class);
                    intent.putExtra("category", "headphones");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(CategoryDonate.this, AdminaddnewProductActivity.class);
                    intent.putExtra("category", "hadphones");
                    startActivity(intent);
                }
            }
        });

        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qwe == "donate") {
                    Intent intent = new Intent(CategoryDonate.this, Donate.class);
                    intent.putExtra("category", "watches");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(CategoryDonate.this, AdminaddnewProductActivity.class);
                    intent.putExtra("category", "watches");
                    startActivity(intent);
                }
            }
        });

        phones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qwe == "donate") {
                    Intent intent = new Intent(CategoryDonate.this, Donate.class);
                    intent.putExtra("category", "phones");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(CategoryDonate.this, AdminaddnewProductActivity.class);
                    intent.putExtra("category", "phones");
                    startActivity(intent);
                }
            }
        });

        purses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qwe == "donate") {
                    Intent intent = new Intent(CategoryDonate.this, Donate.class);
                    intent.putExtra("category", "purses");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(CategoryDonate.this, AdminaddnewProductActivity.class);
                    intent.putExtra("category", "purses");
                    startActivity(intent);
                }
            }
        });

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qwe == "donate") {
                    Intent intent = new Intent(CategoryDonate.this,Donate.class);
                    intent.putExtra("category", "shoes");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(CategoryDonate.this, AdminaddnewProductActivity.class);
                    intent.putExtra("category", "shoes");
                    startActivity(intent);
                }
            }
        });

        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qwe == "donate") {
                    Intent intent = new Intent(CategoryDonate.this, Donate.class);
                    intent.putExtra("category", "laptops");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(CategoryDonate.this, AdminaddnewProductActivity.class);
                    intent.putExtra("category", "laptops");
                    startActivity(intent);
                }
            }
        });


    }
}
