package com.example.propertymanagmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class TenantControl extends AppCompatActivity {

    ImageView mLogin;
    ImageView mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_control);

        mLogin = findViewById(R.id.TenantLogin);
        mRegister = findViewById(R.id.TenantRegister);

        mLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TenantControl.this, TenantLogin.class);
                startActivity(intent);
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(TenantControl.this, TenantRegistration.class);
                startActivity(intent);
            }
        });


    }
}