package com.example.propertymanagmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.propertymanagmentsystem.databinding.ActivityMainBinding;
import com.example.propertymanagmentsystem.databinding.ActivityReadDataBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class ReadData extends AppCompatActivity {

    ActivityReadDataBinding binding;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityReadDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Back Button
        binding.Backbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ReadData.this, TenantDashboard.class);
                startActivity(intent);
            }
        });

        //Logout
        binding.Logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ReadData.this, usercontrol.class);
                startActivity(intent);
            }
        });

        //Read Button
        binding.readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String name = binding.name.getText().toString();
                if(!name.isEmpty())
                {
                    readData(name);
                }else
                {
                    Toast.makeText(ReadData.this,"Please Enter Your Username", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Update Button
        binding.UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.name.getText().toString();
                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();

                updateData(name, email, password);

            }
        });

        //View report
        binding.ViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdf();
                Intent intent = new Intent(ReadData.this, ViewPDF2.class);
                startActivity(intent);
            }
        });
    }

    private void updateData(String name, String email, String password)
    {
        HashMap User = new HashMap();
        User.put("name", name);
        User.put("email", email);
        User.put("password", password);

        reference = FirebaseDatabase.getInstance().getReference("Registered Client");
        reference.child(name).updateChildren(User).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful())
                {
                    binding.name.setText("");
                    binding.email.setText("");
                    binding.password.setText("");
                    Toast.makeText(ReadData.this, "Successfull Update", Toast.LENGTH_SHORT).show();


                }else
                {
                    Toast.makeText(ReadData.this, "Faild to update", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    //Read Data Method
    public void readData(String name)
    {
        reference = FirebaseDatabase.getInstance().getReference("Registered Client");
        reference.child(name).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task)
            {

                if(task.isSuccessful())
                {
                    if(task.getResult().exists())
                    {
                        Toast.makeText(ReadData.this, "Successfully Read Data", Toast.LENGTH_LONG).show();
                        DataSnapshot dataSnapshot = task.getResult();
                        String name = String.valueOf(dataSnapshot.child("name").getValue());
                        String email = String.valueOf(dataSnapshot.child("email").getValue());
                        String password = String.valueOf(dataSnapshot.child("password").getValue());
                        binding.name.setText(name);
                        binding.email.setText(email);
                        binding.password.setText(password);
                    }else
                    {
                        Toast.makeText(ReadData.this, "User does not exist", Toast.LENGTH_SHORT).show();

                    }

                }else
                {
                    Toast.makeText(ReadData.this, "Could not read the data", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    //Print PDF Report
    public void pdf()
    {
        String username = binding.name.getText().toString();
        String email = binding.email.getText().toString();
        String password = binding.password.getText().toString();

        String path = getExternalFilesDir(null). toString()+ "/ProfileReport.pdf";
        File file = new File(path);

        if(!file.exists())
        {

            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file.getAbsoluteFile()));
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        document.open();

        Font myFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);

        Paragraph paragraph = new Paragraph();
        paragraph.add(new Paragraph("USER PROFILE:", myFont));
        paragraph.add(new Paragraph("PRINTED REPORT:", myFont));
        paragraph.add(new Paragraph("\n"));
        paragraph.add(new Paragraph("username: " + username, myFont));
        paragraph.add(new Paragraph("\n"));
        paragraph.add(new Paragraph("reference: " + email, myFont));
        paragraph.add(new Paragraph("\n"));
        paragraph.add(new Paragraph("password: " + password, myFont));
        paragraph.add(new Paragraph("\n"));
        paragraph.add(new Paragraph("\n"));
        paragraph.add(new Paragraph("LLZ PROPERTIES PTY LTD: "));

        try {
            document.add(paragraph);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        document.close();

        Toast.makeText(ReadData.this, "PDF report has been created", Toast.LENGTH_LONG).show();

    }

}