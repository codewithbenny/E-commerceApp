package com.india.shoping.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.india.shoping.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminaddnewProductActivity extends AppCompatActivity {
    private String CategoryName, Description, Price, Pname,TAG, savecurrentdate, savecurrenttime, ProductRandomkey, downloadimageurl;
    private ImageView productimage;
    private EditText productname, productdescription, productprice;
    private Button addproduct;
    private static final int GalleryPick = 1;
    private Uri Imageuri;
    private StorageReference ImageRef;
    private DatabaseReference Productsref;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminaddnew_product);
        CategoryName = getIntent().getExtras().get("category").toString();
        productimage = findViewById(R.id.Select_Product_Image);
        productname = findViewById(R.id.product_name);
        productdescription = findViewById(R.id.product_description);
        productprice = findViewById(R.id.product_price);
        addproduct = findViewById(R.id.addnewproduct);
        loadingbar = new ProgressDialog(this);
        ImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        Productsref = FirebaseDatabase.getInstance().getReference().child("Products");

        productimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductdata();
            }
        });

    }

    private void ValidateProductdata() {
        Description = productdescription.getText().toString();
        Price = productprice.getText().toString();
        Pname = productname.getText().toString();
        if (Imageuri == null) {
            Toast.makeText(this, "Insert Product Image", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Pname)) {
            Toast.makeText(this, "Enter Product Name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Description)) {
            Toast.makeText(this, "Enter Product Description", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Price)) {
            Toast.makeText(this, "Enter Product Price", Toast.LENGTH_SHORT).show();
        } else {
            Storeimageinformation();
        }
    }

    private void Storeimageinformation() {
        loadingbar.setTitle("Adding New Product");
        loadingbar.setMessage("Please Wait while we are adding the new Product");
        loadingbar.setCanceledOnTouchOutside(false);
        loadingbar.show();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd, yyyy");
        savecurrentdate = currentdate.format(calendar.getTime());
        SimpleDateFormat currentdatetime = new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime = currentdatetime.format(calendar.getTime());
        ProductRandomkey = savecurrentdate + savecurrenttime;

        final StorageReference filepath = ImageRef.child((Imageuri.getLastPathSegment()) + ProductRandomkey + ".jpg");
        final UploadTask uploadTask = (UploadTask) filepath.putFile(Imageuri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                loadingbar.dismiss();
                Toast.makeText(AdminaddnewProductActivity.this, "Error" + message, Toast.LENGTH_SHORT).show();
            }

        }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return filepath.getDownloadUrl();
            }
        })
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                       downloadimageurl=String.valueOf(uri);
                        Saveproductinfotodatabase();
                        Toast.makeText(AdminaddnewProductActivity.this, "get Successfully"+downloadimageurl, Toast.LENGTH_SHORT).show();
                    }
                });
    }




    private void Saveproductinfotodatabase() {
        HashMap<String, Object> ProductMap = new HashMap<>();
        ProductMap.put("pid", ProductRandomkey);
        ProductMap.put("date", savecurrentdate);
        ProductMap.put("time", savecurrenttime);
        ProductMap.put("description", Description);
        ProductMap.put("Img",downloadimageurl);
        ProductMap.put("Category", CategoryName);
        ProductMap.put("Price", Price);
        ProductMap.put("Name", Pname);
        Productsref.child(ProductRandomkey).updateChildren(ProductMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    loadingbar.dismiss();
                    Intent intent = new Intent(AdminaddnewProductActivity.this, AdminCategoryActivity.class);

                    Toast.makeText(AdminaddnewProductActivity.this, "Product added Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else {
                    loadingbar.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(AdminaddnewProductActivity.this, "Error" + message, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    // Select Image method
    private void openGallery() {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                GalleryPick);
    }

    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == GalleryPick
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            Imageuri = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                Imageuri);
                productimage
                        .setImageBitmap(bitmap);
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }
}