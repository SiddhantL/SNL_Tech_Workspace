package com.example.snltech.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.snltech.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class EditInfo extends AppCompatActivity {
    private Button btnUpload;
    private RelativeLayout btnSelect;
    // view for image view
    private ImageView imageView;

    // Uri indicates, where the image will be picked from
    private Uri filePath;
    EditText name,about;
    Spinner category;
    // request code
    private final int PICK_IMAGE_REQUEST = 22;
    DatabaseReference df;
    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    String id,categories;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView appbarTV = (TextView)toolbar.findViewById(R.id.appbartextview);
        appbarTV.setText("SNL Tech");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // initialise views
        btnSelect = findViewById(R.id.button2);
        btnUpload = findViewById(R.id.button3);
        imageView = findViewById(R.id.imageView);
        name=findViewById(R.id.editText);
        about=findViewById(R.id.editText2);
        category=findViewById(R.id.spinner);
        String[] arraycategory=getResources().getStringArray(R.array.array_name);
        category.setEnabled(false);
        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        df= FirebaseDatabase.getInstance().getReference();
        categories=getIntent().getStringExtra("category");
        id=getIntent().getStringExtra("id");
        for (int i=0;i<arraycategory.length;i++){
            if (categories.equals(arraycategory[i])){
                category.setSelection(i);
            }
        }
        DatabaseReference dref=FirebaseDatabase.getInstance().getReference().child(categories).child(id);
        FirebaseStorage stor = FirebaseStorage.getInstance();
        stor.getReference().child("images/"+id).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                //Toast.makeText(context, url, Toast.LENGTH_SHORT).show();
                //Toast.makeText(context, idEvent, Toast.LENGTH_SHORT).show();
                Glide.with(getApplicationContext()).load(url).into(imageView);
            }
        });
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(snapshot.child("Name").getValue(String.class));
                about.setText(snapshot.child("About").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // on pressing btnSelect SelectImage() is called
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SelectImage();
            }
        });

        // on pressing btnUpload uploadImage() is called
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(about.getText())){
                    Toast.makeText(EditInfo.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                }else{
                    uploadImage(id);}
            }
        });
    }

    // Select Image method
    private void SelectImage()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imageView.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    // UploadImage method
    private void uploadImage(final String pushed)
    {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            final ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child("images/"+pushed);
            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(
                        UploadTask.TaskSnapshot taskSnapshot)
                {
                    // Image uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss();
                    Toast.makeText(EditInfo.this, categories+" Updated", Toast.LENGTH_SHORT).show();
                    df.child(categories).child(pushed).child("Name").setValue(name.getText().toString());
                    df.child(categories).child(pushed).child("About").setValue(about.getText().toString());
                }
            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(EditInfo.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }else{
            df.child(categories).child(pushed).child("Name").setValue(name.getText().toString());
            df.child(categories).child(pushed).child("About").setValue(about.getText().toString());
            Toast.makeText(this, categories+" Updated", Toast.LENGTH_SHORT).show();
        }
    }
}
