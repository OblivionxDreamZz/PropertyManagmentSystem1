package com.example.propertymanagmentsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TenantLogin extends AppCompatActivity {

    //Variables
    EditText mEmail, mPassword;
    Button mLoginBtn;
    FirebaseAuth fAuth;
    TextView registerButton, passwordReset;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_login);

        mEmail = findViewById(R.id.tenantEmail1);
        mPassword = findViewById(R.id.password2);
        mLoginBtn = findViewById(R.id.loginbtn);
        passwordReset = findViewById(R.id.forgetPassword);

        fAuth = FirebaseAuth.getInstance();
        passwordReset = findViewById(R.id.forgetPassword);

        //Login Button Validation
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    mEmail.setError("Please enter your registered email address");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    mPassword.setError("Please enter your password");
                    return;
                }

                FirebaseUser user = fAuth.getCurrentUser();

                {
                    //Login user using Firebase AUTHENTICATION architecture
                    fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(TenantLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(TenantLogin.this, TenantDashboard.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(TenantLogin.this, "Please enter correct email and password" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }

            }
        });


        //Register Button on Click
        registerButton = findViewById(R.id.createAccountBtn);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TenantLogin.this, TenantRegistration.class);
                startActivity(intent);
            }
        });

        //Reset Password
        passwordReset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Forgot your password");
                passwordResetDialog.setMessage("No worries. Enter your registered email and we will send you a reset link.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("SEND REQUEST", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String mail = resetMail.getText().toString();

                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                Toast.makeText(TenantLogin.this,"Reset Link Has Been Sent To Your Email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                Toast.makeText(TenantLogin.this,"Reset Link Has Not Been Sent", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                passwordResetDialog.create().show();
            }
        });


    }
}