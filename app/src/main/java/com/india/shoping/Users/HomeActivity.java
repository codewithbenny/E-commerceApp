package com.india.shoping.Users;

import android.content.Intent;
import android.content.res.Configuration;
import android.icu.util.ULocale;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.india.shoping.Admin.AdminCategoryActivity;
import com.india.shoping.Admin.AdminUpdateActivity;
import com.india.shoping.Admin.AdminaddnewProductActivity;
import com.india.shoping.GodGift;
import com.india.shoping.Model.Products;
import com.india.shoping.Model.Prevalent;
import com.india.shoping.R;
import com.india.shoping.ViewHolder.ProductViewHolder;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private DatabaseReference rootref;
    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private RecyclerView.LayoutManager layoutManager;
    private String productID="";
    private String type="";
    String Currentuser;
    private Toolbar toolbar;
    private ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Paper.init(this);
        imageView=findViewById(R.id.search);
        Currentuser=Paper.book().read(Prevalent.UserPhoneKey);
        String imguri=Paper.book().read("image");
        String uusername=Paper.book().read("username");

       // floating Button

        FloatingActionButton fab = findViewById(R.id.fab);
        recyclerView=findViewById(R.id.RecyclerView);
        toolbar=findViewById(R.id.toolbar);


        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        toolbar.setTitle("Shop Online");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            type=getIntent().getExtras().get("Admin").toString();
        }

        rootref= FirebaseDatabase.getInstance().getReference().child("Products");
        toolbar.setTitle("Home");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type!="Admin") {
                    Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                    startActivity(intent);
                }
            }
        });


        NavigationView navigationView = findViewById(R.id.nav_view);
        Paper.init(this);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView=navigationView.getHeaderView(0);
        TextView usernametext=headerView.findViewById(R.id.nav_Username);
        CircleImageView profile=headerView.findViewById(R.id.profile);

        if(type!="Admin") {
            usernametext.setText(uusername);
            Picasso.get().load(imguri).placeholder(R.drawable.download).into(profile);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Products>options=
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
                                if (type.equals("Admin")) {
                                    Intent intent=new Intent(HomeActivity.this, AdminUpdateActivity.class);
                                    intent.putExtra("pid", products.getPid());

                                    startActivity(intent);

                                } else {
                                    Intent intent = new Intent(HomeActivity.this, ProductsDetailsActivity.class);
                                    intent.putExtra("pid", products.getPid());
                                    startActivity(intent);
                                }
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


            @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id=menuItem.getItemId();
        if(id==R.id.nav_cart){
            if(type.equals("Admin")) {
                Toast.makeText(this, "You Can't Use these options", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        } else if(id==R.id.nav_categories){
            if(type.equals("Admin")) {
                Toast.makeText(this, "You Can't Use these options", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(HomeActivity.this, UserActivity.class);
                startActivity(intent);
            }
        }
        else if(id==R.id.nav_orders){
            if(type.equals("Admin")) {
                Toast.makeText(this, "You Can't Use these options", Toast.LENGTH_SHORT).show();
            }else {

                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        }
        else if(id==R.id.nav_Settings){
            if(type!="Admin") {
            Intent intent=new Intent(HomeActivity.this,SettingsActivity.class);
            startActivity(intent);
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "You Can't Use these options", Toast.LENGTH_SHORT).show();

            }
        }
        else if(id==R.id.nav_logout){
            if(type!="Admin") {
                Paper.book().destroy();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(this, "You Can't Use these options", Toast.LENGTH_SHORT).show();

            }
        }else if(id==R.id.donate1){
            if(type!="Admin") {
                Intent intent = new Intent(HomeActivity.this, CategoryDonate.class);

                startActivity(intent);
            }
        }else if(id==R.id.getdonate){
            Intent intent = new Intent(HomeActivity.this, GodGift.class);

            startActivity(intent);        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}
