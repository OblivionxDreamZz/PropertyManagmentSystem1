package com.example.propertymanagmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class MaintenanceProcessing extends AppCompatActivity {

    EditText mName, mAddress, mReference, mReporting, mProgress;

    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_processing);

        mName = findViewById(R.id.name);
        mAddress = findViewById(R.id.address);
        mReference = findViewById(R.id.reference);
        mReporting = findViewById(R.id.reporting);
        mProgress = findViewById(R.id.progress);

        saveButton = findViewById(R.id.sendApproval);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String progress = mProgress.getText().toString().trim();

                if (TextUtils.isEmpty(progress))
                {
                    mProgress.setError("Please enter your name");
                }
                else
                {
                    uploadData();
                }

            }
        });

        showUserDetails();

    }

    //Show Data
    public void showUserDetails()
    {
        mName = findViewById(R.id.name);
        mAddress = findViewById(R.id.address);
        mReference = findViewById(R.id.reference);
        mReporting = findViewById(R.id.reporting);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {

            mName.setText(bundle.getString("name"));
            mAddress.setText(bundle.getString("address"));
            mReference.setText(bundle.getString("reference"));
            mReporting.setText(bundle.getString("reporting"));

        }
    }

    //Database Method Upload Data
    public void uploadData()
    {
        String name = mName.getText().toString().trim();
        String address = mAddress.getText().toString().trim();
        String reference = mReference.getText().toString().trim();
        String reporting = mReporting.getText().toString().trim();
        String progress = mProgress.getText().toString().trim();


        DataClass6 dataClass6 = new DataClass6(name,address, reference, reporting, progress);

        FirebaseDatabase.getInstance().getReference("Approved Maintenance").child(name)
                .setValue(dataClass6).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(MaintenanceProcessing.this, "Saved", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MaintenanceProcessing.this, ManagementDashboard.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(MaintenanceProcessing.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }

}