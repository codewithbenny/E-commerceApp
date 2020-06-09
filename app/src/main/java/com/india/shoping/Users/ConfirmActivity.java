package com.india.shoping.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.india.shoping.Model.Prevalent;
import com.india.shoping.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;

public class ConfirmActivity extends AppCompatActivity {
private EditText name,phone,address,city;
private Button confirmbtn;
private String totalprice;
private String phonekey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        confirmbtn=findViewById(R.id.confirmbtn);
        name=findViewById(R.id.ship_name);
        phone=findViewById(R.id.ship_phone);
        address=findViewById(R.id.ship_address);
        city=findViewById(R.id.ship_city);

        Paper.init(this);
        phonekey=Paper.book().read(Prevalent.UserPasswordKey);

          totalprice=getIntent().getStringExtra("totalprice");
        Toast.makeText(this, "Price"+totalprice, Toast.LENGTH_SHORT).show();

        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });
    }

    private void check() {
        if(TextUtils.isEmpty(name.getText().toString())){
            Toast.makeText(this, "Enter Your Name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone.getText().toString())){
            Toast.makeText(this, "Enter Your Current Mobile", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(address.getText().toString())){
            Toast.makeText(this, "Enter Your Adress", Toast.LENGTH_SHORT).show();
        }
       else if(TextUtils.isEmpty(city.getText().toString())){
            Toast.makeText(this, "Enter Your city", Toast.LENGTH_SHORT).show();
        }
       else {
           confirmorder();
        }
    }
    public void confirmorder(){
         final String savecurrenttime,savecurrentdate;
        Calendar calfordate=Calendar.getInstance();
        SimpleDateFormat currentdate=new SimpleDateFormat("MMM dd,yyyy");
        savecurrentdate=currentdate.format(calfordate.getTime());
        SimpleDateFormat currenttime=new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime=currenttime.format(calfordate.getTime());
        final DatabaseReference ordersref= FirebaseDatabase.getInstance().getReference()
                .child("orders").child(phonekey);

        final HashMap<String,Object> ordermap=new HashMap<>();
        ordermap.put("totalAmount",totalprice);
        ordermap.put("name",name.getText().toString());
        ordermap.put("phone",phone.getText().toString());
        ordermap.put("Address",address.getText().toString());
        ordermap.put("time",savecurrenttime);
        ordermap.put("date",savecurrentdate);
        ordermap.put("state","not shipped");

        ordersref.updateChildren(ordermap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference().child("Cartlist").child("User View")
                            .child(phonekey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ConfirmActivity.this, "Your Cart is Placed Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(ConfirmActivity.this,HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                            }
                        }
                    });
                }
            }
        });
    }
}
