package com.example.snltech.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.snltech.Contact;
import com.example.snltech.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class DisplayInfo extends AppCompatActivity {
ImageView logo;
    private MyAdapter listAdapter;
    private ArrayList<Contact> contactsList = new ArrayList<>();
    RecyclerView links;
    FloatingActionButton fab;
    String category,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        logo=findViewById(R.id.item_images);
        TextView appbarTV = (TextView)toolbar.findViewById(R.id.appbartextview);
        links=findViewById(R.id.links);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        links.setLayoutManager(layoutManager);
        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DisplayInfo.this, LinkActivity.class);
                intent.putExtra("id",getIntent().getStringExtra("id"));
                intent.putExtra("category",getIntent().getStringExtra("category"));
                startActivity(intent);
            }
        });
        listAdapter = new MyAdapter(contactsList, this);
        links.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        appbarTV.setText("SNL Tech");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final TextView title=findViewById(R.id.title);
        final TextView about=findViewById(R.id.about);
        id= getIntent().getStringExtra("id");
        FirebaseStorage stor = FirebaseStorage.getInstance();
        stor.getReference().child("images/"+id).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                //Toast.makeText(context, url, Toast.LENGTH_SHORT).show();
                //Toast.makeText(context, idEvent, Toast.LENGTH_SHORT).show();
                Glide.with(DisplayInfo.this).load(url).into(logo);
            }
        });
        category=getIntent().getStringExtra("category");
        DatabaseReference dref= FirebaseDatabase.getInstance().getReference(category).child(id);
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                title.setText(snapshot.child("Name").getValue(String.class));
                about.setText(snapshot.child("About").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String last="First";
                String alttext="First";
                int i=0;
                while (last!=null){
                    i++;
                    last=snapshot.child("Link"+Integer.toString(i)).getValue(String.class);
                    alttext=snapshot.child("Alttext"+Integer.toString(i)).getValue(String.class);
                    if (last!=null){
                        //Toast.makeText(DisplayInfo.this, last, Toast.LENGTH_SHORT).show();
                        if (!alttext.equals("Deleted") && !last.equals("")) {
                            Contact c = new Contact("", "","","");
                            c.setName(alttext);
                            c.setNumber(last);
                            c.setCategory(category);
                            c.setId(id);
                            contactsList.add(c);
                            listAdapter.notifyDataSetChanged();
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
        getMenuInflater().inflate(R.menu.main_edit, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_search : {
                Intent intent=new Intent(DisplayInfo.this, EditInfo.class);
                intent.putExtra("id",id);
                intent.putExtra("category",category);
                startActivity(intent);
                return true;
            }
            case R.id.menu_action_delete : {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                DatabaseReference dref=FirebaseDatabase.getInstance().getReference().child(category);
                                dref.child(id).removeValue();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(DisplayInfo.this);
                builder.setMessage("Are you sure you want to delete this "+category+"?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}