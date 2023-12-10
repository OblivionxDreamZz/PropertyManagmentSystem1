package com.example.propertymanagmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerificationScreen2 extends AppCompatActivity {

    Button mVerifyBtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_screen2);

        mVerifyBtn = findViewById(R.id.verifyBtn);
        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();

        mVerifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Send Verfication Link
                FirebaseUser user = fAuth.getCurrentUser();
                if(!user.isEmailVerified())
                {
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(VerificationScreen2.this, "A Verification Email Has Been Sent To Your Email Address.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(VerificationScreen2.this, TenantLogin.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Log.d("TAG", "onFailure: Email Has Not Ben Sent ");
                        }
                    });
                }

            }
        });

    }
}