package com.example.propertymanagmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UpdateProperties2 extends AppCompatActivity {

    EditText name1, address1, listingPrice1,propertyDescription1, additionalInfo1;
    Button update;

    DatabaseReference reference;

    ImageView updateImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_properties2);

        name1 = findViewById(R.id.name);
        address1 = findViewById(R.id.address);
        listingPrice1 = findViewById(R.id.listingPrice);
        propertyDescription1 = findViewById(R.id.propertyDescription);
        additionalInfo1 = findViewById(R.id.additionalInfo);
        update = findViewById(R.id.UpdatePropertyBTN2);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = name1.getText().toString();
                String address = address1.getText().toString();
                String listingPrice = listingPrice1.getText().toString();
                String propertyDescription = propertyDescription1.getText().toString();
                String additionalInfo = additionalInfo1.getText().toString();

                updateData(name,address, listingPrice, propertyDescription, additionalInfo);
            }
        });

        showUserDetails();

    }

    public void showUserDetails(){
        name1 = findViewById(R.id.name);
        address1 = findViewById(R.id.address);
        listingPrice1 = findViewById(R.id.listingPrice);
        propertyDescription1 = findViewById(R.id.propertyDescription);
        additionalInfo1 = findViewById(R.id.additionalInfo);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {

            name1.setText(bundle.getString("name"));
            address1.setText(bundle.getString("Address"));
            listingPrice1.setText(bundle.getString("Price"));
            propertyDescription1.setText(bundle.getString("Description"));
            additionalInfo1.setText(bundle.getString("AdditionalInfo"));
        }
    }

    private void updateData(String name, String address, String listingPrice,String propertyDescription, String additionalInfo)
    {
        HashMap User = new HashMap();
        User.put("name", name);
        User.put("address", address);
        User.put("listingPrice", listingPrice);
        User.put("propertyDescription", propertyDescription);
        User.put("additionalInfo", additionalInfo);

        reference = FirebaseDatabase.getInstance().getReference("Properties");
        reference.child(name).updateChildren(User).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful())
                {
                    Intent intent = new Intent(UpdateProperties2.this, ManagementDashboard.class);
                    startActivity(intent);
                    Toast.makeText(UpdateProperties2.this, "Successfull Update", Toast.LENGTH_SHORT).show();


                }else
                {
                    Toast.makeText(UpdateProperties2.this, "Faild to update", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

}