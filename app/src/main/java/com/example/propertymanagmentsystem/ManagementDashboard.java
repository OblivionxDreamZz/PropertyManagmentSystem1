package com.example.propertymanagmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ManagementDashboard extends AppCompatActivity {

    ImageView mMyProperties, mMyTenants, mMaintenance, mProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_dashboard);

        mMyProperties = findViewById(R.id.propertyImg);
        mMyTenants = findViewById(R.id.TenantImg);
        mMaintenance = findViewById(R.id.maintenanceImg);
        mProfile = findViewById(R.id.mainProfileImg);

        mMyTenants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagementDashboard.this, ViewClients.class);
                startActivity(intent);
            }
        });

        mMyProperties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ManagementDashboard.this, ViewProperty.class);
                startActivity(intent);
            }
        });


        mMaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagementDashboard.this, ViewMaintenance.class);
                startActivity(intent);
            }
        });

        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagementDashboard.this, ReadData2.class);
                startActivity(intent);
            }
        });


    }
}