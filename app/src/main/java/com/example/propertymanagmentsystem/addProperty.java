package com.example.propertymanagmentsystem;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.propertymanagmentsystem.Model.propertyModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Set;


public class addProperty extends Fragment {

    private Button uploadButton;
    private EditText mName, mAddress, mListingPrice, mDescription, mAdditionalInfo;
    private MaterialCardView SelectPhoto;
    private ImageView propertyImage;
    private Uri ImageUri;
    private Bitmap bitmap;

    private FirebaseStorage storage;
    private FirebaseFirestore firestore;
    private StorageReference mStorageref;
    private FirebaseAuth firebaseAuth;
    private String CurrentUserId;

    private String PhotoUrl;
    private String DocID;

    public addProperty() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_property, container, false);

        mName = view.findViewById(R.id.propertyName);
        mAddress = view.findViewById(R.id.address);
        mListingPrice = view.findViewById(R.id.listingPrice);
        mDescription = view.findViewById(R.id.propertyDescription);
        mAdditionalInfo = view.findViewById(R.id.additionalInfo);
        uploadButton = view.findViewById(R.id.uploadPropertyBTN);

        SelectPhoto = view.findViewById(R.id.selectProperty);
        propertyImage = view.findViewById(R.id.uploadImage);

        //Creating instances
        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        mStorageref = storage.getReference();


        //Get Current User ID
        firebaseAuth = FirebaseAuth.getInstance();
        CurrentUserId = firebaseAuth.getCurrentUser().getUid();

        SelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckStoragePermissions();


            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();
            }
        });

        return view;


    }

    private void CheckStoragePermissions()
    {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
        {
            if(ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }else
                {
                    PickImageFromGallery();
                }

        }else
            {
                PickImageFromGallery();
            }

    }

    //Create method to pick image
    private void PickImageFromGallery()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(intent);

    }
    ActivityResultLauncher<Intent> launcher
            =registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
            result -> {
                        if(result.getResultCode() == Activity.RESULT_OK)
                        {
                            Intent data = result.getData();
                            if(data!= null && data.getData()!= null)
                            {

                                ImageUri = data.getData();

                                //Convert Image to bitmap
                                try {
                                    bitmap = MediaStore.Images.Media.getBitmap(
                                            getActivity().getContentResolver(),
                                            ImageUri
                                    );
                                } catch (IOException e) {
                                    e.printStackTrace();
                            }
                        }
                            //set Image into Image View
                            if(ImageUri!= null)
                            {
                                propertyImage.setImageBitmap(bitmap);
                            }
                        }
            }
    );

    //upload image into Firebase Storage and store image Url into Firebase Firestore
    //method for Firebase Storage
    private void UploadImage()
    {
        //check Imageuri
        if(ImageUri != null)
        {
            final StorageReference myRef = mStorageref.child("photo/"  + ImageUri.getLastPathSegment());
            myRef.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                     myRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                         @Override
                         public void onSuccess(Uri uri) {
                             if(uri!=null)
                             {
                                PhotoUrl = uri.toString();
                                UploadImageInfo();
                             }

                         }
                     }).addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(Exception e) {

                         }
                     });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    //method for Property Information
    private void UploadImageInfo()
    {
        String name = mName.getText().toString().trim();
        String address = mAddress.getText().toString().trim();
        String listing = mListingPrice.getText().toString().trim();
        String description = mDescription.getText().toString().trim();
        String info = mAdditionalInfo.getText().toString().trim();

        //Validation
        if(TextUtils.isEmpty(name) && TextUtils.isEmpty(address) && TextUtils.isEmpty(listing) && TextUtils.isEmpty(description) && TextUtils.isEmpty(info))
        {
            Toast.makeText(getContext(), "Please enter all the fields ro add a property", Toast.LENGTH_SHORT).show();
        }else
            {
                DocumentReference documentReference = firestore.collection("My Properties").document();
                propertyModel propertyModel = new propertyModel(name, listing, address,description, info, "", "", PhotoUrl, CurrentUserId);
                documentReference.set(propertyModel, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {

                        if(task.isSuccessful()){
                            if(task.isSuccessful()){
                                DocID = documentReference.getId();
                                propertyModel.setPropertyDocID(DocID);
                                documentReference.set(propertyModel, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(getContext(), "Uplaod Successfully", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                             Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }



                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {

                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }

    }
}