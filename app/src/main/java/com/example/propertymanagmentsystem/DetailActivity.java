package com.example.propertymanagmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetailActivity extends AppCompatActivity {

    TextView propertyDescription, name, listingPrice, address, additionalInfo;
    ImageView dataImage;

    Button updateButton, deleteButton;
    Button UpdateButton2;

    String Key = "";

    String imageUrl = "";



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        propertyDescription = findViewById(R.id.detailDesc);
        dataImage = findViewById(R.id.detailImage);
        name = findViewById(R.id.detailTitle);
        listingPrice = findViewById(R.id.detailPrice);
        additionalInfo = findViewById(R.id.detailInfo);
        address = findViewById(R.id.detailAdd);

        deleteButton = findViewById(R.id.DeletePropertyBTN);

        //updateButton = findViewById(R.id.UpdatePropertyBTN);

        UpdateButton2 = findViewById(R.id.UpdatePropertyBTN2);


        String dataToSend = name.getText().toString();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            Key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            listingPrice.setText(bundle.getString("Price"));
            additionalInfo.setText(bundle.getString("AdditionalInfo"));
            address.setText(bundle.getString("Address"));
            propertyDescription.setText(bundle.getString("Description"));
            name.setText(bundle.getString("name"));
            Glide.with(this).load(bundle.getString("Image")).into(dataImage);

        }

        //Delete Button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Properties");
                FirebaseStorage storage = FirebaseStorage.getInstance();

                StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(Key).removeValue();
                        Toast.makeText(DetailActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), ViewProperty.class));
                        finish();
                    }
                });
            }
        });

        //updateButton.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
                //Intent intent = new Intent(DetailActivity.this, UpdateProperties.class)
                       // .putExtra("name", name.getText().toString())
                        //.putExtra("Description", propertyDescription.getText().toString())
                        //.putExtra("AdditionalInfo", additionalInfo.getText().toString())
                        //.putExtra("Address", address.getText().toString())
                        //.putExtra("Price", listingPrice.getText().toString());
                //startActivity(intent);
            //}
       // });

        UpdateButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, UpdateProperties2.class)
                .putExtra("name", name.getText().toString())
                .putExtra("Description", propertyDescription.getText().toString())
                .putExtra("AdditionalInfo", additionalInfo.getText().toString())
                .putExtra("Address", address.getText().toString())
                .putExtra("Price", listingPrice.getText().toString());

                startActivity(intent);
            }
        });


    }
}