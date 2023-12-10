package com.example.propertymanagmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class usercontrol extends AppCompatActivity
{

    ImageView ManagerImage;
    ImageView TenantImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercontrol);

        ManagerImage = findViewById(R.id.ManagerImg);
        TenantImage = findViewById(R.id.TenantImg);

        ManagerImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(usercontrol.this, PropertyManagerControl.class);
                startActivity(intent);
            }
        });

        TenantImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(usercontrol.this, TenantControl.class);
                startActivity(intent);
            }
        });

    }
}