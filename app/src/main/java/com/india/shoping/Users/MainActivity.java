package com.india.shoping.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.india.shoping.Admin.AdminCategoryActivity;
import com.india.shoping.Model.Users;
import com.india.shoping.Model.Prevalent;
import com.india.shoping.R;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
 private Button loginbtn,joinnowbtn;
    private ProgressDialog loadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginbtn=findViewById(R.id.login_btn);
        joinnowbtn=findViewById(R.id.join_now_btn);
        Paper.init(this);
        loadingbar=new ProgressDialog(this);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        joinnowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
         String UserPhoneKey=Paper.book().read(Prevalent.UserPasswordKey);
        String UserPasswordKey=Paper.book().read(Prevalent.UserPasswordKey);
        if(UserPhoneKey!="" && UserPasswordKey!=""){
            if (!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey)) {

                AllowAccess(UserPhoneKey,UserPasswordKey);
                loadingbar.setTitle("Already Logged in");
                loadingbar.setMessage("Please Wait.......");
                loadingbar.setCanceledOnTouchOutside(false);
                loadingbar.show();
            }

        }
    }

    private void AllowAccess(final String phone, final String password) {

        final DatabaseReference Rootref;
        Rootref= FirebaseDatabase.getInstance().getReference();
        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(phone).exists()){
                    Users usersdata=dataSnapshot.child("Users").child(phone)
                            .getValue(Users.class);
                    if(usersdata.getPhone().equals(phone)){

                        if(usersdata.getPassword().equals(password)){
                            if("Users".equals("Admins")) {
                                Toast.makeText(MainActivity.this, " Welcome Admin, You Logged in Succesfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, AdminCategoryActivity.class);
                                loadingbar.dismiss();
                                startActivity(intent);
                            }else if("Users".equals("Users")){
                                Toast.makeText(MainActivity.this, "Logged in Succesfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                loadingbar.dismiss();
                                startActivity(intent);
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "Password is Incorrect", Toast.LENGTH_SHORT)
                                    .show();
                            loadingbar.dismiss();
                        }
                    }
                }else{
                    Toast.makeText(MainActivity.this, "You don't have an account", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    }

