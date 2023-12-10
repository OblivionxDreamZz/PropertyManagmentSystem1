package com.example.propertymanagmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.propertymanagmentsystem.databinding.ActivityReadData2Binding;
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

public class ReadData2 extends AppCompatActivity {

    ActivityReadData2Binding binding;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReadData2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Back Button
        binding.Backbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ReadData2.this, ManagementDashboard.class);
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
                    readData2(name);
                }else
                {
                    Toast.makeText(ReadData2.this,"Please Enter Your Username", Toast.LENGTH_LONG).show();
                }
            }
        });


        //Update Button
        binding.UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.name.getText().toString();
                String email = binding.email.getText().toString();
                String company = binding.company.getText().toString();

                String password = binding.password.getText().toString();

                if(!name.isEmpty())
                {
                    updateData(name, email, company, password);
                }
                else
                {
                    Toast.makeText(ReadData2.this, "In order to update please retrieve data", Toast.LENGTH_LONG);
                }
            }
        });

        //View report
        binding.ViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdf();
                Intent intent = new Intent(ReadData2.this, ViewPDF3.class);
                startActivity(intent);
            }
        });

        binding.Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReadData2.this, usercontrol.class);
            }
        });
    }


    private void updateData(String name, String email, String company, String password)
    {
        HashMap User = new HashMap();
        User.put("name", name);
        User.put("email", email);
        User.put("company", company);
        User.put("password", password);

        reference = FirebaseDatabase.getInstance().getReference("Registered Property Manager");
        reference.child(name).updateChildren(User).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful())
                {
                    binding.name.setText("");
                    binding.email.setText("");
                    binding.company.setText("");
                    binding.password.setText("");
                    Toast.makeText(ReadData2.this, "Successfull Update", Toast.LENGTH_SHORT).show();

                }else
                {
                    Toast.makeText(ReadData2.this, "Faild to update", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    //Read Data Method
    public void readData2(String name)
    {
        reference = FirebaseDatabase.getInstance().getReference("Registered Property Manager");
        reference.child(name).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task)
            {

                if(task.isSuccessful())
                {
                    if(task.getResult().exists())
                    {
                        Toast.makeText(ReadData2.this, "Successfully Read Data", Toast.LENGTH_LONG).show();
                        DataSnapshot dataSnapshot = task.getResult();
                        String name = String.valueOf(dataSnapshot.child("name").getValue());
                        String email = String.valueOf(dataSnapshot.child("email").getValue());
                        String password = String.valueOf(dataSnapshot.child("password").getValue());
                        String company = String.valueOf(dataSnapshot.child("company").getValue());

                        binding.name.setText(name);
                        binding.email.setText(email);
                        binding.password.setText(password);
                        binding.company.setText(company);
                    }else
                    {
                        Toast.makeText(ReadData2.this, "User does not exist", Toast.LENGTH_SHORT).show();

                    }

                }else
                {
                    Toast.makeText(ReadData2.this, "Could not read the data", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    //Print PDF Report
    public void pdf()
    {
        String username = binding.name.getText().toString();
        String email = binding.email.getText().toString();
        String company = binding.company.getText().toString();
        String password = binding.password.getText().toString();

        String path = getExternalFilesDir(null). toString()+ "/ManagerProfileReport.pdf";
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
        paragraph.add(new Paragraph("MANAGER USER PROFILE:", myFont));
        paragraph.add(new Paragraph("\n"));
        paragraph.add(new Paragraph("PRINTED REPORT:", myFont));
        paragraph.add(new Paragraph("\n"));
        paragraph.add(new Paragraph("username: " + username, myFont));
        paragraph.add(new Paragraph("\n"));
        paragraph.add(new Paragraph("reference: " + email, myFont));
        paragraph.add(new Paragraph("\n"));
        paragraph.add(new Paragraph("company: " + company, myFont));
        paragraph.add(new Paragraph("\n"));
        paragraph.add(new Paragraph("password: " + password, myFont));
        paragraph.add(new Paragraph("\n"));
        paragraph.add(new Paragraph("\n"));
        paragraph.add(new Paragraph("LLZ PROPERTIES PTY LTD: ALL RIGHTS RESERVED"));

        try {
            document.add(paragraph);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        document.close();

        Toast.makeText(ReadData2.this, "PDF report has been created", Toast.LENGTH_LONG).show();

    }


}