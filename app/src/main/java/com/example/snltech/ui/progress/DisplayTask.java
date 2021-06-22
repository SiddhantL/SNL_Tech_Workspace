package com.example.snltech.ui.progress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.snltech.EventData;
import com.example.snltech.ModelClass;
import com.example.snltech.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayTask extends AppCompatActivity {
    Boolean tick;
    float total=0,comp=0;
    ArrayList<ModelClass> items;
    ProgressBar pb;
    TodoAdapter adapter;
    RecyclerView recyclerView;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_task);
        recyclerView = findViewById(R.id.taskrcycle);
        pb=findViewById(R.id.progress);
        items = new ArrayList<>();
        adapter = new TodoAdapter(DisplayTask.this, items);
        recyclerView.setAdapter(adapter);
        id=getIntent().getStringExtra("id");
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(DisplayTask.this, LinearLayoutManager.VERTICAL, false));
        final DatabaseReference dproject= FirebaseDatabase.getInstance().getReference("todo").child(id);
        dproject.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                total=0;
                for (DataSnapshot ds:snapshot.getChildren()){
                    total++;
                    //Toast.makeText(getContext(), ds.getKey(), Toast.LENGTH_SHORT).show();
                    final EventData data=new EventData();
                    data.setID(ds.getKey());
                    dproject.child(ds.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            tick=false;
                            if (snapshot.child("Complete").getValue(String.class).equals("Yes")){
                                tick=true;
                                comp++;
                            }
                            data.setAdult("Yes");
                            data.setCost("0");
                            data.setDate("00/00/0000");
                            data.setDrinks("Yes");
                            data.setFood("Yes");
                            data.setIntro("This is a Loading Message...");
                            data.setMusic(tick);
                            data.setName("Loadingsss...");
                            data.setTime(id);
                            data.setCategory("Project");
                            data.setName(snapshot.child("Task").getValue(String.class));
                            data.setIntro(snapshot.child("About").getValue(String.class));
                            items.add(new ModelClass(data));
                            adapter.notifyDataSetChanged();
                            if (total!=0) {
                                float perc = comp / total;
                                perc=perc*100;
                                pb.setMax(100);
                                pb.setProgress((int)perc);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.example.snltech.ui.progress.ViewDialog alerts = new ViewDialog();
                alerts.showDialog(DisplayTask.this,id);
            }
        });
        return ;
    }
    public void activity(){
        items = new ArrayList<>();
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(DisplayTask.this, LinearLayoutManager.VERTICAL, false));
        final DatabaseReference dproject= FirebaseDatabase.getInstance().getReference("todo").child(id);
        dproject.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                total=0;
                comp=0;
                for (DataSnapshot ds:snapshot.getChildren()){
                    total++;
                    //Toast.makeText(getContext(), ds.getKey(), Toast.LENGTH_SHORT).show();
                    final EventData data=new EventData();
                    data.setID(ds.getKey());
                    dproject.child(ds.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            tick=false;
                            if (snapshot.child("Complete").getValue(String.class).equals("Yes")){
                                tick=true;
                                comp++;
                            }
                            data.setAdult("Yes");
                            data.setCost("0");
                            data.setDate("00/00/0000");
                            data.setDrinks("Yes");
                            data.setFood("Yes");
                            data.setIntro("This is a Loading Message...");
                            data.setMusic(tick);
                            data.setName("Loadingsss...");
                            data.setTime(id);
                            data.setCategory("Project");
                            data.setName(snapshot.child("Task").getValue(String.class));
                            data.setIntro(snapshot.child("About").getValue(String.class));
                            items.add(new ModelClass(data));
                            adapter.notifyDataSetChanged();
                            if (total!=0) {
                                float perc = comp / total;
                                perc=perc*100;
                                pb.setMax(100);
                                pb.setProgress((int)perc);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    }
