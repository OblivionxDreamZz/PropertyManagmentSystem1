package com.example.propertymanagmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.text.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.propertymanagmentsystem.databinding.ActivityReadData2Binding;
import com.example.propertymanagmentsystem.databinding.ActivityViewApprovedMaintenanceBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;


public class ViewApprovedMaintenance extends AppCompatActivity {

    ActivityViewApprovedMaintenanceBinding binding;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewApprovedMaintenanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.Backbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ViewApprovedMaintenance.this, MaintenanceReporting.class);
                startActivity(intent);
            }
        });

        binding.printBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                String name = binding.name.getText().toString();
                if(!name.isEmpty())
                {
                    viewApprovedMaintenance(name);

                }else
                {
                    Toast.makeText(ViewApprovedMaintenance.this,"Please Enter Your Username", Toast.LENGTH_LONG).show();
                }
            }
        });

        binding.ViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdf();
                Intent intent = new Intent(ViewApprovedMaintenance.this, ViewPDF1.class);
                startActivity(intent);
            }
        });
    }

    //Read Data Method
    public void viewApprovedMaintenance(String name)
    {
        reference = FirebaseDatabase.getInstance().getReference("Approved Maintenance");
        reference.child(name).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task)
            {

                if(task.isSuccessful())
                {
                    if(task.getResult().exists())
                    {
                        Toast.makeText(ViewApprovedMaintenance.this, "Successfully Read Data", Toast.LENGTH_LONG).show();
                        DataSnapshot dataSnapshot = task.getResult();
                        String name = String.valueOf(dataSnapshot.child("name").getValue());
                        String reference = String.valueOf(dataSnapshot.child("reference").getValue());
                        String reporting = String.valueOf(dataSnapshot.child("reporting").getValue());
                        String address = String.valueOf(dataSnapshot.child("address").getValue());
                        String progress = String.valueOf(dataSnapshot.child("progress").getValue());

                        binding.name.setText(name);
                        binding.reference.setText(reference);
                        binding.reporting.setText(reporting);
                        binding.address.setText(address);
                        binding.progress.setText(progress);

                    }else
                    {
                        Toast.makeText(ViewApprovedMaintenance.this, "User does not exist", Toast.LENGTH_SHORT).show();

                    }

                }else
                {
                    Toast.makeText(ViewApprovedMaintenance.this, "Could not read the data", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    //Generate Report PDF format
    public void pdf()
    {
        String username = binding.name.getText().toString();
        String reference = binding.reference.getText().toString();
        String address = binding.address.getText().toString();
        String reporting = binding.reporting.getText().toString();
        String progress = binding.progress.getText().toString();

        String path = getExternalFilesDir(null). toString()+ "/user.pdf";
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
        paragraph.add(new Paragraph("Approved Maintenance", myFont));
        paragraph.add(new Paragraph("LLZ PROPERTIES MAINTENANCE DIVISION", myFont));
        paragraph.add(new Paragraph("\n"));
        paragraph.add(new Paragraph("username: " + username, myFont));
        paragraph.add(new Paragraph("\n"));
        paragraph.add(new Paragraph("reference: " + reference, myFont));
        paragraph.add(new Paragraph("\n"));
        paragraph.add(new Paragraph("address: " + address, myFont));
        paragraph.add(new Paragraph("\n"));
        paragraph.add(new Paragraph("reporting: " + reporting, myFont));
        paragraph.add(new Paragraph("\n"));
        paragraph.add(new Paragraph("progress: " + progress, myFont));
        paragraph.add(new Paragraph("\n"));
        paragraph.add(new Paragraph("\n"));
        paragraph.add(new Paragraph("LLZ PROPERTIES PTY LTD: "));

        try {
            document.add(paragraph);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        document.close();

        Toast.makeText(ViewApprovedMaintenance.this, "PDF report has been created", Toast.LENGTH_LONG).show();

    }


}