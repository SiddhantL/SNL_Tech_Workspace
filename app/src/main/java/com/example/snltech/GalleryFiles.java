package com.example.snltech;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GalleryFiles extends AppCompatActivity {
ArrayList<ModelClass> items;
    ArrayList<Contact> contactsList;
    FileAdapter listAdapter;
    RecyclerView files;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_files);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView appbarTV = (TextView)toolbar.findViewById(R.id.appbartextview);
        appbarTV.setText("SNL Tech");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        contactsList = new ArrayList<>();
        String foldername=getIntent().getStringExtra("foldername");
        final String id=getIntent().getStringExtra("id");
        files=findViewById(R.id.recyclerfile);
        DatabaseReference dref= FirebaseDatabase.getInstance().getReference().child("file").child(id);
        items = new ArrayList<>();
        listAdapter= new FileAdapter(this, items);
        final FileAdapter adapter = new FileAdapter(GalleryFiles.this, items);
        files.setLayoutManager(new GridLayoutManager(this,3));
        files.setAdapter(adapter);
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String last="First";
                int i=0;
                while (last!=null){
                    i++;
                    last=snapshot.child("File"+Integer.toString(i)).getValue(String.class);
                    if (last!=null){
                        //Toast.makeText(GalleryFiles.this, last, Toast.LENGTH_SHORT).show();
                        if (!last.equals("")) {
                            EventData data=new EventData();
                            data.setAdult("Yes");
                            data.setCost("0");
                            data.setDate("00/00/0000");
                            data.setDrinks("Yes");
                            data.setFood("Yes");
                            data.setIntro("This is a Loading Message...");
                            data.setMusic("Yes");
                            data.setName("Loadingsss...");
                            data.setTime("00:00");
                            data.setCategory("App");
                            data.setName(last);
                            data.setIntro(last);
                            data.setID(id);
                            items.add(new ModelClass(data));
                            adapter.notifyDataSetChanged();
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_gallery_link, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_link: {

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
