package com.india.shoping.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.india.shoping.Model.Cart;
import com.india.shoping.Model.Prevalent;
import com.india.shoping.Model.Products;
import com.india.shoping.R;
import com.india.shoping.ViewHolder.CartViewHolder;

import io.paperdb.Paper;

public class CartActivity extends AppCompatActivity {
private TextView totalprice,quantity,Message;
private RecyclerView recyclerView;
private Button next;
private Products products;
private Cart cart;
private String phonekey;
private int overallprice;
    private DatabaseReference cartlistref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        quantity=findViewById(R.id.produt_quantity);
        totalprice=findViewById(R.id.totalPrice_cart);
        recyclerView=findViewById(R.id.recycler);
        next=findViewById(R.id.Nextbtn);
        Message=findViewById(R.id.msg1);

        Paper.init(this);
        phonekey=Paper.book().read("loveyou");


        Message.setVisibility(View.GONE);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(CartActivity.this,ConfirmActivity.class);
                intent.putExtra("totalprice",String.valueOf(overallprice));
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        cartlistref= FirebaseDatabase.getInstance().getReference().child("Cartlist");
        orderstate();
        FirebaseRecyclerOptions<Cart> options=
                new FirebaseRecyclerOptions.Builder<Cart>().setQuery(cartlistref.child("User View")
                        .child(phonekey).child("Products"),Cart.class).build();

            FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter=new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull final Cart cart) {
                    cartViewHolder.txtproductquantity.setText(cart.getQuantity());
                    cartViewHolder.txtproductname.setText(cart.getPname());
                    cartViewHolder.txtproductprice.setText("$"+cart.getPrice());

                    String check=cart.getPname();
                    if(check!=null){
                        next.setVisibility(View.VISIBLE);
                    }
                    int oneTypeProductPrice=(Integer.valueOf(cart.getPrice())) * Integer.valueOf(cart.getQuantity());
                    overallprice=overallprice+oneTypeProductPrice;
                    totalprice.setText("Total Price =$"+String.valueOf(overallprice));
                    cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            CharSequence options[]=new CharSequence[]{
                                   "Edit",
                                   "Remove"
                            };
                            AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this);
                            builder.setTitle("Cart Options");
                            builder.setItems(options, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(i==0){
                                        Intent intent=new Intent(CartActivity.this,ProductsDetailsActivity.class);
                                        intent.putExtra("pid",cart.getPid());
                                        startActivity(intent);
                                    }
                                    if(i==1){
                                        final DatabaseReference cartlistref= FirebaseDatabase.getInstance().getReference().child("Cartlist");
                                    cartlistref.child("User View").child(phonekey).child("Products")
                                            .child(cart.getPid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(CartActivity.this, "Removed Successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    }
                                }
                            });
                            builder.show();
                        }
                    });
                }

                @NonNull
                @Override
                public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitem_layout,parent,false);
                    CartViewHolder holder=new CartViewHolder(view);
                    return holder;
                }
            };
            recyclerView.setAdapter(adapter);
            adapter.startListening();
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
                       totalprice.setText("State = Shipped");
                       recyclerView.setVisibility(View.GONE);
                       Message.setVisibility(View.VISIBLE);
                       next.setVisibility(View.GONE);
                       Toast.makeText(CartActivity.this, "You can buy more Products once your first order is placed.", Toast.LENGTH_SHORT).show();

                   }else if(ordestate.equals("not shipped")){
                       totalprice.setText("State = Not Shipped");
                       recyclerView.setVisibility(View.GONE);
                       Message.setText("When Your Order is Shpped you got a Confirmation Message from Company");
                       Message.setVisibility(View.VISIBLE);
                       next.setVisibility(View.GONE);
                       Toast.makeText(CartActivity.this, "You can buy more Products once your first order is placed.", Toast.LENGTH_SHORT).show();
                   }
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

    }
}
