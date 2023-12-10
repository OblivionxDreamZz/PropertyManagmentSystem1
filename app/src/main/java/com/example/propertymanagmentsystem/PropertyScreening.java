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

public class PropertyScreening extends AppCompatActivity {

    EditText mName, mAddress, mPrice, mDescription, mAddInfo, mClient, mContactInfo;

    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_screening);

        mName = findViewById(R.id.UpdatepropertyName);
        mAddress = findViewById(R.id.Updateaddress);
        mPrice = findViewById(R.id.UpdatelistingPrice);
        mDescription = findViewById(R.id.Description);
        mAddInfo = findViewById(R.id.additionalInfo);
        mClient = findViewById(R.id.name);
        mContactInfo = findViewById(R.id.contactDetails);

        saveButton = findViewById(R.id.sendIntrest);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String client = mClient.getText().toString().trim();
                String contact = mContactInfo.getText().toString().trim();

                if (TextUtils.isEmpty(client))
                {
                    mClient.setError("Please enter your name");
                }
                else if (TextUtils.isEmpty(contact))
                {
                    mContactInfo.setError("Please enter your contact information");
                }

                else
                {
                    uploadData();
                }


            }
        });

        showUserDetails();

    }

    //Show parsed information
    public void showUserDetails()
    {
        mName = findViewById(R.id.UpdatepropertyName);
        mAddress = findViewById(R.id.Updateaddress);
        mPrice = findViewById(R.id.UpdatelistingPrice);
        mDescription = findViewById(R.id.Description);
        mAddInfo = findViewById(R.id.additionalInfo);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {

            mName.setText(bundle.getString("name"));
            mAddress.setText(bundle.getString("Address"));
            mPrice.setText(bundle.getString("Price"));
            mDescription.setText(bundle.getString("Description"));
            mAddInfo.setText(bundle.getString("AdditionalInfo"));

        }
    }

    //Database Method
    public void uploadData()
    {
        String name = mName.getText().toString().trim();
        String address = mAddress.getText().toString().trim();
        String listingPrice = mPrice.getText().toString().trim();
        String propertyDescription = mDescription.getText().toString().trim();
        String additionalInfo = mAddInfo.getText().toString().trim();
        String client = mClient.getText().toString().trim();
        String contact = mContactInfo.getText().toString().trim();

        DataClass3 dataClass3 = new DataClass3(name,address, listingPrice, propertyDescription, additionalInfo,client, contact );

        FirebaseDatabase.getInstance().getReference("Prospect-full Clients").child(client)
                .setValue(dataClass3).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(PropertyScreening.this, "Saved", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(PropertyScreening.this, PropertyScreeningSuccess.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(PropertyScreening.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}