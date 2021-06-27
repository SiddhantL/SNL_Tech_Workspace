package com.example.snltech.ui.blog;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditBlog extends AppCompatActivity {
    private Button btnUpload;
    private RelativeLayout btnSelect;
    // view for image view
    private ImageView imageView;

    // Uri indicates, where the image will be picked from
    private Uri filePath;
    EditText name,about;
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
        setContentView(R.layout.activity_edit_blog);
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
        about=findViewById(R.id.content);
        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        df= FirebaseDatabase.getInstance().getReference();
        categories=getIntent().getStringExtra("category");
        id=getIntent().getStringExtra("id");
        DatabaseReference dref=FirebaseDatabase.getInstance().getReference().child("Blog").child(id);
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(snapshot.child("Topic").getValue(String.class));
                about.setText(snapshot.child("Content").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // on pressing btnSelect SelectImage() is called
        // on pressing btnUpload uploadImage() is called
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(about.getText())){
                    Toast.makeText(EditBlog.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                }else{

                    DatabaseReference dref=FirebaseDatabase.getInstance().getReference().child("Blog").child(id);
                    dref.child("Topic").setValue(name.getText().toString());
                    dref.child("Content").setValue(about.getText().toString());
                    Toast.makeText(EditBlog.this, "Blog Edited", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Select Image method
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
}
