package com.example.propertymanagmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class DetailActivity3 extends AppCompatActivity {

    TextView name, address, reference, reporting;

    Button approveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail3);

        address = findViewById(R.id.detailaddress);
        reference = findViewById(R.id.detailreference);
        name = findViewById(R.id.detailname);
        reporting = findViewById(R.id.detailreporting);
        approveButton = findViewById(R.id.approveBtn);

        String dataToSend = name.getText().toString();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            address.setText(bundle.getString("address"));
            reference.setText(bundle.getString("reference"));
            reporting.setText(bundle.getString("reporting"));
            name.setText(bundle.getString("name"));
        }

        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(DetailActivity3.this,MaintenanceProcessing.class)
                        .putExtra("name", name.getText().toString())
                        .putExtra("address", address.getText().toString())
                        .putExtra("reference", reference.getText().toString())
                        .putExtra("reporting", reporting.getText().toString());
                startActivity(intent);
                Toast.makeText(DetailActivity3.this,"Please approve maintenance", Toast.LENGTH_LONG).show();
            }
        });


    }
}