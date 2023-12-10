package com.example.propertymanagmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class TenantDashboard extends AppCompatActivity {

    ImageView mViewProperties, mMarketPlace, mMaintenance, mProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_dashboard);

        mViewProperties = findViewById(R.id.propertyImg);
        mMarketPlace = findViewById(R.id.MarketBTN);
        mMaintenance = findViewById(R.id.maintenanceImg);
        mProfile = findViewById(R.id.mainProfileImg);

        mViewProperties.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(TenantDashboard.this, ViewProperty2.class);
                startActivity(intent);
            }
        });

        mMaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(TenantDashboard.this, MaintenanceReporting.class);
                startActivity(intent);
            }
        });

        mMarketPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(TenantDashboard.this, MarketPlace.class);
                startActivity(intent);
            }
        });

        mProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(TenantDashboard.this, ReadData.class);
                startActivity(intent);
            }
        });


    }
}