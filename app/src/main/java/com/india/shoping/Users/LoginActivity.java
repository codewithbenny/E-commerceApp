package com.india.shoping.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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
import com.india.shoping.ResetPasswordActivity;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    private EditText phoneno, password1;
    private CheckBox rememberme;
    private Button login;
    private TextView amaadmin, notadmin,forgetpasswordlink;
    private ProgressDialog loadingbar;
    private String dbname="Users";
    public Users currentuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phoneno = findViewById(R.id.input_phn);
        password1 = findViewById(R.id.input_password);
        forgetpasswordlink=findViewById(R.id.forgetpassword);
        rememberme = findViewById(R.id.Remember_me);
        login = findViewById(R.id.login_btn);
        amaadmin = findViewById(R.id.admin_link);
        notadmin = findViewById(R.id.admin_not_link);
        Paper.init(this);
        loadingbar = new ProgressDialog(this);
        notadmin.setVisibility(View.INVISIBLE);

        Paper.init(this);
        Paper.book().write("phone",phoneno.getText().toString());

        forgetpasswordlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, ResetPasswordActivity.class);
                intent.putExtra("check","Login");
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logginguser();


            }
        });


        amaadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.setText("Login Admin");
               amaadmin.setVisibility(View.INVISIBLE);
               notadmin.setVisibility(View.VISIBLE);
               login.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                      // loggingAdmin();
                       final String phone = phoneno.getText().toString();
                       final String password = password1.getText().toString();
                       if(phone.equals("8979296885")&&password.equals("123456789")){
                           Toast.makeText(LoginActivity.this, " Welcome Admin, You Logged in Succesfully", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                           loadingbar.dismiss();
                           startActivity(intent);
                       }else {
                           Toast.makeText(LoginActivity.this, "Either Phone or Password is Incorrect", Toast.LENGTH_SHORT).show();
                       }
                   }
               });



            }
        });

        notadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.setText("Login");
                amaadmin.setVisibility(View.VISIBLE);
                notadmin.setVisibility(View.INVISIBLE);


            }
        });
    }

    private void logginguser() {
        final String phone = phoneno.getText().toString();
        final String password = password1.getText().toString();

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please Enter Your Phone No.", Toast.LENGTH_SHORT).show();
        } else {
            loadingbar.setTitle("Logging in");
            loadingbar.setMessage("Please Wait while we are checking the Credentials");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();
             Allowacess(phone,password);
        }
    }

    private void Allowacess(final String phone, final String password) {
        if(rememberme.isChecked()){
            Paper.book().write(Prevalent.UserPhoneKey,phone);
            Paper.book().write(Prevalent.UserPasswordKey,password);
        }
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
                            if(dbname.equals("Users")){

                               Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                loadingbar.dismiss();
                                 Paper.book().write("loveyou",usersdata.getPhone());
//                                Prevalent.currentonlineuser=usersdata;
//                                currentuser= Prevalent.currentonlineuser;
                                 startActivity(intent);
                            }
                        }else{
                            Toast.makeText(LoginActivity.this, "Password is Incorrect", Toast.LENGTH_SHORT)
                                    .show();
//                            loadingbar.dismiss();
                        }
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "You don't have an account", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void loggingAdmin(){
        final String phone = phoneno.getText().toString();
        final String password = password1.getText().toString();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please Enter Your Phone No.", Toast.LENGTH_SHORT).show();
        } else {
            loadingbar.setTitle("Logging in");
            loadingbar.setMessage("Please Wait while we are checking the Credentials");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

        }
        final DatabaseReference Rootref;
        Rootref= FirebaseDatabase.getInstance().getReference().child("Admins");
        Rootref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(phone).exists()){
                    String ppassword=dataSnapshot.child("password").getValue().toString();
                    if(password.equals("123456789")){
                        Toast.makeText(LoginActivity.this, " Welcome Admin, You Logged in Succesfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                        loadingbar.dismiss();
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "This Mobile do not have any Admin account", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}