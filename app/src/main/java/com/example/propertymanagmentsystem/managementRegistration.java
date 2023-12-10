package com.example.propertymanagmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.propertymanagmentsystem.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class managementRegistration extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +
                    "(?=.*[a-z])" +
                    "(?=.*[A-Z])" +
                    "(?=.*[@##$%^&+=])" +
                    "(?=//s+$)" +
                    ".{8,}" +
                    "$");

    public static boolean isValidPassword(String password) {
        Matcher matcher = Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,20})").matcher(password);
        return matcher.matches();
    }

    ActivityMainBinding binding;

    EditText mFullName, mEmail, mPassword, mPassword2, mCompanyName;
    Button mregisterBtn;
    TextView mLoginBtn;
    TextView result;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;

    private FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_registration);

        //Assigning Variables
        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.agentEmail);
        mPassword = findViewById(R.id.password);
        mPassword2 = findViewById(R.id.reEnterPassword);
        mCompanyName = findViewById(R.id.CompanyName);
        mLoginBtn = findViewById(R.id.createAccountBtn);
        mregisterBtn = findViewById(R.id.registerBtn);
        progressBar = findViewById(R.id.progressBar);
        result = findViewById(R.id.textView111);

        //Assigning Firebase Variables For Authentication
        fAuth = FirebaseAuth.getInstance();

        //Assigning Firebase FireStore Database To Store User Information
        fStore = FirebaseFirestore.getInstance();

        mregisterBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                String name = mFullName.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String reEnterPassword = mPassword2.getText().toString().trim();
                String company = mCompanyName.getText().toString().trim();

                //Validation
                if(TextUtils.isEmpty(name))
                {
                    mFullName.setError("Please enter your name");
                    return;
                }else if(TextUtils.isEmpty(email))
                {
                    mEmail.setError("Please enter a valid email address");
                    return;
                }else if(TextUtils.isEmpty(password))
                {
                    mPassword.setError("Strong Password Needed: Password must contain at least a mix of one upper and one lower case letter, one digit and one special case character. Password needs to to be eight or more characters.");
                    return;
                }else if(!isValidPassword(mPassword.getText().toString()))
                {
                    mPassword.setError("Strong Password: Password must contain at least a mix of one upper and one lower case letter, one digit and one special case character. Password needs to to be eight or more characters.");
                    return;
                }else if(TextUtils.isEmpty(company))
                {
                    mCompanyName.setError("Please enter a company name");
                    return;
                }
                else if(TextUtils.isEmpty(reEnterPassword))
                {
                    mPassword2.setError("Please enter a valid password");
                    return;
                }else if(!reEnterPassword.equals(password))
                {
                    mPassword2.setError("Passwords do not match");
                    return;
                }
                else
                    {

                        computeMD5Hash(password);//Function called for Password Encryption
                        String emPass = result.getText().toString().trim();


                        //Add data into real time database
                        DataClass5 dataClass5 = new DataClass5(name, email, company, password);
                        db = FirebaseDatabase.getInstance();
                        reference = db.getReference("Registered Property Manager");
                        reference.child(name).setValue(dataClass5).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(managementRegistration.this, "Successful entry",Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(managementRegistration.this, "Not successfull entry",Toast.LENGTH_LONG).show();

                            }
                        });


                        //Register user using Firebase Authentication architecture
                        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task ->
                        {
                            if(task.isSuccessful())
                            {

                                Toast.makeText(managementRegistration.this, "User Created", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(managementRegistration.this, VerificationScreen.class);
                                startActivity(intent);

                                userID = fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fStore.collection("Property Managers").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("Agent_Name",name);
                                user.put("Email Address", email);
                                user.put("Passwords", password);
                                user.put("Company Name", company);

                                fStore.collection("Property Managers")
                                        .add(user)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                                        {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference)
                                            {
                                                Log.d("tag", "DocumentSnapshot added with ID: " + documentReference.getId());

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(Exception e) {
                                                Log.w("TAG", "Error adding document", e);
                                            }
                                        });

                            }else
                                {
                                    Toast.makeText(managementRegistration.this, "Error Registration unSuccessful." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(view.GONE);
                                }

                        });
                    }

            }
        });


        //Login Button On Click to send to Login Screen
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(managementRegistration.this, ManagementLoginForm.class);
                startActivity(intent);
            }
        });

    }


    //Algorithm: Password Encryption
    public void computeMD5Hash(String password)
    {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte[] messageDigest = digest.digest();

            StringBuffer MD5HASH = new StringBuffer();
            for (byte b : messageDigest) {
                String h = Integer.toHexString(0xFF & b);
                while (h.length() < 2)
                    h = "0" + h;
                MD5HASH.append(h);
            }
            result.setText(MD5HASH);

        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();

        }
    }
}