package com.example.propertymanagmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class MaintenanceReporting extends AppCompatActivity {

    Button saveButton;

    TextView backButton, viewButton;

    EditText mName, mReference, mAddress, mReporting;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_reporting);

        mName = findViewById(R.id.Name);
        mReference = findViewById(R.id.Reference);
        mAddress = findViewById(R.id.Address);
        mReporting = findViewById(R.id.Report);
        saveButton = findViewById(R.id.uploadMaintenanceBTN);
        backButton = findViewById(R.id.Backbtn1);
        viewButton = findViewById(R.id.viewBtn1);

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MaintenanceReporting.this, ViewApprovedMaintenance.class);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MaintenanceReporting.this, TenantDashboard.class);
                startActivity(intent);
            }
        });

      saveButton.setOnClickListener(new View.OnClickListener()
      {
          @Override
          public void onClick(View v)
          {

              String name = mName.getText().toString().trim();
              String reference = mReference.getText().toString().trim();
              String address = mAddress.getText().toString().trim();
              String reporting = mReporting.getText().toString().trim();

              if (TextUtils.isEmpty(name))
              {
                  mName.setError("Please enter your name");
              }
              else if (TextUtils.isEmpty(reference))
              {
                  mReference.setError("Please enter your reference");
              }
              else if (TextUtils.isEmpty(address))
              {
                  mAddress.setError("Please enter your address");

              }else if(TextUtils.isEmpty(reporting))
              {
                  mReporting.setError("Please enter the issue");
              }
              else
              {
                  uploadData();
              }
          }
      });
    }


    //Upload Data Method
    public void uploadData()
    {
        String name = mName.getText().toString();
        String reference = mReference.getText().toString();
        String address = mAddress.getText().toString();
        String reporting = mReporting.getText().toString();

        DataClass2 dataClass2 = new DataClass2(name,reference, address, reporting);

        FirebaseDatabase.getInstance().getReference("Maintenance Reporting").child(name)
                .setValue(dataClass2).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(MaintenanceReporting.this, "Saved", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MaintenanceReporting.this, MaintenanceSuccess.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(MaintenanceReporting.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }

}