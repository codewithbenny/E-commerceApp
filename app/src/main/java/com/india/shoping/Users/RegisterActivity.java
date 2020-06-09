package com.india.shoping.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.india.shoping.R;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
EditText name, password,phno;
Button createaccount;
private ProgressDialog loadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=findViewById(R.id.input_name);
        password=findViewById(R.id.input_password_register);
        phno=findViewById(R.id.input_phn_register);
        createaccount=findViewById(R.id.CreateAccountbtn);
        loadingbar=new ProgressDialog(this);
        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Createaccount();
            }
        });

    }

    private void Createaccount() {
       final String phone=phno.getText().toString();
        final  String Password=password.getText().toString();
        final  String Name=name.getText().toString();
        if(TextUtils.isEmpty(Name)){
            Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(Password)){
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Please Enter Your Phone No.", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingbar.setTitle("Creating Account");
            loadingbar.setMessage("Please Wait while we are checking the Credentials");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

            Valiadatephoneno(Name,phone,Password);
        }
    }

    private void Valiadatephoneno(final String name, final String phone, final String password) {
        final DatabaseReference Rootref;
        Rootref= FirebaseDatabase.getInstance().getReference();
        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(phone).exists())){
                    HashMap<String,Object>UserdataMap=new HashMap<>();
                    UserdataMap.put("phone",phone);
                    UserdataMap.put("password",password);
                    UserdataMap.put("name",name);
                    Rootref.child("Users").child(phone).updateChildren(UserdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Successfully Account Created", Toast.LENGTH_SHORT)
                                        .show();
                                loadingbar.dismiss();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(RegisterActivity.this, "Network Error Please Try Again", Toast.LENGTH_SHORT)
                                        .show();
                                loadingbar.dismiss();
                            }
                        }
                    });

                }else{
                    Toast.makeText(RegisterActivity.this, "This"+phone+"already have an Account", Toast.LENGTH_SHORT)
                            .show();
//                    loadingbar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please Try again with another phone Number", Toast.LENGTH_SHORT)
                            .show();
                    Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RegisterActivity.this, " Database Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
