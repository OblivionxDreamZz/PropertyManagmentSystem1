package com.example.propertymanagmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MarketPlace extends AppCompatActivity {


    ImageView backButton, product1, product2,product3, product4, product5, product6, product7, product8, product9;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_place);

        backButton = findViewById(R.id.backBtn);
        product1 = findViewById(R.id.button1);
        product2 = findViewById(R.id.button2);
        product3 = findViewById(R.id.button3);
        product4 = findViewById(R.id.button4);
        product5 = findViewById(R.id.button5);
        product6 = findViewById(R.id.button6);
        product7 = findViewById(R.id.button7);
        product8 = findViewById(R.id.button8);
        product9 = findViewById(R.id.button9);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarketPlace.this, TenantDashboard.class);
                startActivity(intent);
            }
        });

        product1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarketPlace.this, propertyItem1.class);
                startActivity(intent);
            }
        });

        product2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarketPlace.this, propertyItem2.class);
                startActivity(intent);
            }
        });

        product3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarketPlace.this, propertyItem3.class);
                startActivity(intent);
            }
        });

        product4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarketPlace.this, propertyItem4.class);
                startActivity(intent);
            }
        });

        product5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarketPlace.this, propertyItem5.class);
                startActivity(intent);
            }
        });

        product6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarketPlace.this, propertyItem6.class);
                startActivity(intent);
            }
        });

        product7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarketPlace.this, propertyItem7.class);
                startActivity(intent);
            }
        });

        product8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarketPlace.this, propertyItem8.class);
                startActivity(intent);
            }
        });

        product9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarketPlace.this, propertyItem9.class);
                startActivity(intent);
            }
        });

    }
}