package com.example.propertymanagmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class DetailActivity2 extends AppCompatActivity {

    TextView propertyDescription, name, listingPrice, address, additionalInfo;
    ImageView dataImage;

    Button intrestedButton, deleteButton;
    Button UpdateButton2;

    String Key = "";

    String imageUrl = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);

        propertyDescription = findViewById(R.id.detailDesc);
        dataImage = findViewById(R.id.detailImage);
        name = findViewById(R.id.detailTitle);
        listingPrice = findViewById(R.id.detailPrice);
        additionalInfo = findViewById(R.id.detailInfo);
        address = findViewById(R.id.detailAdd);
        intrestedButton = findViewById(R.id.UpdatePropertyBTN2);


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

        //Parse data to interested users
        intrestedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity2.this, PropertyScreening.class)
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