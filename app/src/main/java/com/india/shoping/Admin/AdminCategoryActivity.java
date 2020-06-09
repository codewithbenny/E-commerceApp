package com.india.shoping.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.india.shoping.Users.Donate;
import com.india.shoping.Users.HomeActivity;
import com.india.shoping.Users.MainActivity;
import com.india.shoping.R;

public class AdminCategoryActivity extends AppCompatActivity {
private ImageView tshirts,sportstshirt,femaledresses,sweaters;
private ImageView glasses,hats,watches,purses,shoes,hadphones,laptops,phones;
private Button neworders,logout,update;
private String qwe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        tshirts=findViewById(R.id.t_shirts);
        sportstshirt=findViewById(R.id.sports_tshirts);
        femaledresses=findViewById(R.id.female_dresses);
        sweaters=findViewById(R.id.sweater);
        glasses=findViewById(R.id.glasses);
        hats=findViewById(R.id.hats);
        purses=findViewById(R.id.purses);
        shoes=findViewById(R.id.shoes);
        hadphones=findViewById(R.id.headphones);
        laptops=findViewById(R.id.laptop);
        phones=findViewById(R.id.mobiles);
        watches=findViewById(R.id.watches);
        neworders=findViewById(R.id.checknewordersbtn);
        update=findViewById(R.id.update);
        logout=findViewById(R.id.logoutadmin);
        qwe=getIntent().getStringExtra("donate");

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(AdminCategoryActivity.this, HomeActivity.class);
                intent.putExtra("Admin","Admin");
                startActivity(intent);
            }
        });

        neworders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(AdminCategoryActivity.this, AdminNewOrdersActivity.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(AdminCategoryActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qwe=="donate"){
                    Intent intent =new Intent(AdminCategoryActivity.this, Donate.class);
                    intent.putExtra("category","tshirts");
                    startActivity(intent) ;
                }else {
                    Intent intent = new Intent(AdminCategoryActivity.this, AdminaddnewProductActivity.class);
                    intent.putExtra("category", "tshirts");
                    startActivity(intent);
                }
            }
        });

        sportstshirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qwe=="donate"){
                    Intent intent =new Intent(AdminCategoryActivity.this, Donate.class);
                    intent.putExtra("category","sporttshirt");
                    startActivity(intent) ;
                }else {
                    Intent intent = new Intent(AdminCategoryActivity.this, AdminaddnewProductActivity.class);
                    intent.putExtra("category", "sporttshirt");
                    startActivity(intent);
                }
            }
        });

        femaledresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qwe == "donate") {
                    Intent intent = new Intent(AdminCategoryActivity.this, Donate.class);
                    intent.putExtra("category", "femaledresses");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(AdminCategoryActivity.this, AdminaddnewProductActivity.class);
                    intent.putExtra("category", "femaledresses");
                    startActivity(intent);
                }
            }
        });

        sweaters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qwe == "donate") {
                    Intent intent = new Intent(AdminCategoryActivity.this, Donate.class);
                    intent.putExtra("category", "sweaters");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(AdminCategoryActivity.this, AdminaddnewProductActivity.class);
                    intent.putExtra("category", "sweaters");
                    startActivity(intent);
                }
            }
        });

        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qwe == "donate") {
                    Intent intent = new Intent(AdminCategoryActivity.this, Donate.class);
                    intent.putExtra("category", "glasses");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(AdminCategoryActivity.this, AdminaddnewProductActivity.class);
                    intent.putExtra("category", "glasses");
                    startActivity(intent);
                }
            }
        });

        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qwe == "donate") {
                    Intent intent = new Intent(AdminCategoryActivity.this, Donate.class);
                    intent.putExtra("category", "hats");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(AdminCategoryActivity.this, AdminaddnewProductActivity.class);
                    intent.putExtra("category", "hats");
                    startActivity(intent);
                }
            }
        });

        hadphones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qwe == "donate") {
                    Intent intent = new Intent(AdminCategoryActivity.this, Donate.class);
                    intent.putExtra("category", "headphones");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(AdminCategoryActivity.this, AdminaddnewProductActivity.class);
                    intent.putExtra("category", "hadphones");
                    startActivity(intent);
                }
            }
        });

        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qwe == "donate") {
                    Intent intent = new Intent(AdminCategoryActivity.this, Donate.class);
                    intent.putExtra("category", "watches");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(AdminCategoryActivity.this, AdminaddnewProductActivity.class);
                    intent.putExtra("category", "watches");
                    startActivity(intent);
                }
            }
        });

        phones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qwe == "donate") {
                    Intent intent = new Intent(AdminCategoryActivity.this, Donate.class);
                    intent.putExtra("category", "phones");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(AdminCategoryActivity.this, AdminaddnewProductActivity.class);
                    intent.putExtra("category", "phones");
                    startActivity(intent);
                }
            }
        });

        purses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qwe == "donate") {
                    Intent intent = new Intent(AdminCategoryActivity.this, Donate.class);
                    intent.putExtra("category", "purses");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(AdminCategoryActivity.this, AdminaddnewProductActivity.class);
                    intent.putExtra("category", "purses");
                    startActivity(intent);
                }
            }
        });

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qwe == "donate") {
                    Intent intent = new Intent(AdminCategoryActivity.this,Donate.class);
                    intent.putExtra("category", "shoes");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(AdminCategoryActivity.this, AdminaddnewProductActivity.class);
                    intent.putExtra("category", "shoes");
                    startActivity(intent);
                }
            }
        });

        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qwe == "donate") {
                    Intent intent = new Intent(AdminCategoryActivity.this, Donate.class);
                    intent.putExtra("category", "laptops");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(AdminCategoryActivity.this, AdminaddnewProductActivity.class);
                    intent.putExtra("category", "laptops");
                    startActivity(intent);
                }
            }
        });


    }
}
