package com.example.snltech.ui.blog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.snltech.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class BlogDisplay extends AppCompatActivity {
String id;
int limit=0;
TextView topic, content;
ImageView displayImage;
TextView positionImage;
    int position=1;
    String imageURL;
    String filename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_display);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView appbarTV = (TextView)toolbar.findViewById(R.id.appbartextview);
        appbarTV.setText("SNL Tech");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        positionImage=findViewById(R.id.textView3);
        id=getIntent().getStringExtra("id");
        topic=findViewById(R.id.topic);
        content=findViewById(R.id.content);
        displayImage=findViewById(R.id.imageView3);
        final DatabaseReference df= FirebaseDatabase.getInstance().getReference().child("Blog").child(id);
        df.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String last="First";
                int i=0;
                while (last!=null){
                    i++;
                    last=snapshot.child("Image"+Integer.toString(i)).getValue(String.class);
                    if (last!=null) {
                    }else{
                        limit=i;
                        if (position<=limit) {
                            positionImage.setText(Integer.toString(position) + " / " + Integer.toString(limit));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        df.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                topic.setText(snapshot.child("Topic").getValue(String.class));
                content.setText(snapshot.child("Content").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseStorage stor = FirebaseStorage.getInstance();
        stor.getReference().child("blog/"+id+"/"+id+"1").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                //Toast.makeText(context, url, Toast.LENGTH_SHORT).show();
                //Toast.makeText(context, idEvent, Toast.LENGTH_SHORT).show();
                Glide.with(getApplicationContext()).load(url).into(displayImage);
            }
        });
        Button left=findViewById(R.id.button4);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position==1){
                    position=limit;
                    if (position<=limit) {
                        positionImage.setText(Integer.toString(position) + " / " + Integer.toString(limit));
                    }
                    //Toast.makeText(BlogDisplay.this, "His", Toast.LENGTH_SHORT).show();
                }else{
                    position--;
                    if (position<=limit) {
                        positionImage.setText(Integer.toString(position) + " / " + Integer.toString(limit));
                    }
                }
                //Toast.makeText(BlogDisplay.this, "Hi", Toast.LENGTH_SHORT).show();
                FirebaseStorage stor = FirebaseStorage.getInstance();
                stor.getReference().child("blog/"+id+"/"+id+Integer.toString(position)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String url = uri.toString();
                        //Toast.makeText(context, url, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(context, idEvent, Toast.LENGTH_SHORT).show();
                        Glide.with(getApplicationContext()).load(url).into(displayImage);
                    }

                });
            }
        });
        Button right=findViewById(R.id.button5);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position==limit){
                    position=1;
                    if (position<=limit) {
                        positionImage.setText(Integer.toString(position) + " / " + Integer.toString(limit));
                    }
                    //Toast.makeText(BlogDisplay.this, "His", Toast.LENGTH_SHORT).show();
                }else{
                    position++;
                    if (position<=limit) {
                        positionImage.setText(Integer.toString(position) + " / " + Integer.toString(limit));
                    }
                }
                //Toast.makeText(BlogDisplay.this, "Hi", Toast.LENGTH_SHORT).show();
                FirebaseStorage stor = FirebaseStorage.getInstance();
                stor.getReference().child("blog/"+id+"/"+id+Integer.toString(position)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String url = uri.toString();
                        //Toast.makeText(context, url, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(context, idEvent, Toast.LENGTH_SHORT).show();
                        Glide.with(getApplicationContext()).load(url).into(displayImage);
                    }

                });
            }
        });

    }
}

