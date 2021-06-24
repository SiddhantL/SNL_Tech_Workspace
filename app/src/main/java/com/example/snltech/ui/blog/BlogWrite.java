package com.example.snltech.ui.blog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BlogWrite extends AppCompatActivity {
    private Button btnUpload;
    private RelativeLayout btnSelect;
    // view for image view

    // Uri indicates, where the image will be picked from
    private Uri filePath;
    EditText topic,content;
    // request code
    String fileExtension;
    private final int PICK_IMAGE_REQUEST = 22;
    DatabaseReference df;
    ArrayList<ModelClass> item;
    BlogImageAdapter adapter;
    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;
    RecyclerView recyclerView;
    String folderID;
    ArrayList<Uri> imageList;
    ArrayList<String> extension;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_write);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView appbarTV = (TextView)toolbar.findViewById(R.id.appbartextview);
        appbarTV.setText("SNL Tech");
        item=new ArrayList<ModelClass>();
        imageList=new ArrayList<Uri>();
        extension=new ArrayList<>();
        adapter=new BlogImageAdapter(this,item);
        recyclerView=findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(adapter);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // initialise views
        //folderID=getIntent().getStringExtra("folderID");
        btnSelect = findViewById(R.id.button2);
        btnUpload = findViewById(R.id.button3);
        topic=findViewById(R.id.editText);
        content=findViewById(R.id.content);
        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
       df= FirebaseDatabase.getInstance().getReference().child("Blog");//.child(folderID);
        folderID=df.push().getKey().toString();
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
                if (TextUtils.isEmpty(topic.getText())&&TextUtils.isEmpty(content.getText())){
                    Toast.makeText(BlogWrite.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                }else{
                    String pushed=df.push().getKey();
                    uploadImage(pushed);}
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
                imageList.add(filePath);
                item.add(new ModelClass(bitmap, filePath));
                adapter.notifyDataSetChanged();

            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }


            Uri uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String displayName = null;
            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = this.getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
            }
            fileExtension = "." + displayName.substring(displayName.lastIndexOf(".") + 1);
        }
    }

    // UploadImage method
    private void uploadImage(final String pushed) {
        for (int i = 0; i < imageList.size(); i++) {
            if (imageList.get(i) != null) {

                // Code for showing progressDialog while uploading
                final ProgressDialog progressDialog
                        = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                // Defining the child of storageReference
                StorageReference ref
                        = storageReference
                        .child("blog/" + folderID + "/" + folderID+Integer.toString(i+1) /*+ extension.get(i)*/);
                // adding listeners on upload
                // or failure of image
                final int finalI = i;
                ref.putFile(imageList.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onSuccess(
                            UploadTask.TaskSnapshot taskSnapshot) {
                        // Image uploaded successfully
                        // Dismiss dialog
                        progressDialog.dismiss();
                        Toast.makeText(BlogWrite.this, "Blog Added", Toast.LENGTH_SHORT).show();
                        df.child(folderID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String last = "First";
                                int i = 0;
                                while (last != null) {
                                    i++;
                                    last = snapshot.child("Image" + Integer.toString(i)).getValue(String.class);
                                    if (last != null) {
                                    } else {
                                    }
                                }
                                try {
                                    df.child(folderID).child("Image" + i).setValue(folderID+Integer.toString(i) /*+extension.get(i-1)+" "+Integer.toString(extension.size())*/);

                                }catch (Exception e){
                                    Toast.makeText(BlogWrite.this, "Image"+Integer.toString(i), Toast.LENGTH_SHORT).show();
                                }
                                df.child(folderID).child("Topic").setValue(topic.getText().toString());
                                df.child(folderID).child("Content").setValue(content.getText().toString());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                // Error, Image not uploaded
                                progressDialog.dismiss();
                                Toast
                                        .makeText(BlogWrite.this,
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
                                            UploadTask.TaskSnapshot taskSnapshot) {
                                        double progress
                                                = (100.0
                                                * taskSnapshot.getBytesTransferred()
                                                / taskSnapshot.getTotalByteCount());
                                        progressDialog.setMessage(
                                                "Uploaded Blog "+ (int) progress + "%");
                                    }
                                });
            }
        }
    }
}
