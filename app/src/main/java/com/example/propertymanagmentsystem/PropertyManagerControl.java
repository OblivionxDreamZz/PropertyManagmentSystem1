package com.example.propertymanagmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PropertyManagerControl extends AppCompatActivity {

    ImageView mLogin;
    ImageView mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_manager_control);

        mLogin = findViewById(R.id.loginImg);
        mRegister = findViewById(R.id.registerImg);

        mLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PropertyManagerControl.this, ManagementLoginForm.class);
                startActivity(intent);
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(PropertyManagerControl.this, managementRegistration.class);
                startActivity(intent);
            }
        });
    }
}