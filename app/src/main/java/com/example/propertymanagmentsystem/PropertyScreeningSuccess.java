package com.example.propertymanagmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PropertyScreeningSuccess extends AppCompatActivity {

    Button HomeBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_screening_success);

        HomeBTN = findViewById(R.id.ClientHome);

        HomeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PropertyScreeningSuccess.this, TenantDashboard.class);
                startActivity(intent);
            }
        });



    }
}