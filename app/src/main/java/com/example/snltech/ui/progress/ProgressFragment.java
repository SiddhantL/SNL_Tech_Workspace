package com.example.snltech.ui.progress;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snltech.EventData;
import com.example.snltech.ModelClass;
import com.example.snltech.R;
import com.example.snltech.ui.home.AddHome;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProgressFragment extends Fragment {

    private com.example.snltech.ui.progress.ProgressViewModel slideshowViewModel;
    ArrayList<ModelClass> items,items2,items3;
    int count=0;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(com.example.snltech.ui.progress.ProgressViewModel.class);
        View root = inflater.inflate(R.layout.fragment_progress, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
/*        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        final RecyclerView recyclerView = root.findViewById(R.id.recycleapps);
        items = new ArrayList<>();
        /*root.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddHome.class));
            }
        });*/
        final CustomProgressAdapter adapter = new CustomProgressAdapter(getContext(), items);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        final DatabaseReference dapps= FirebaseDatabase.getInstance().getReference("App");
        dapps.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    //Toast.makeText(getContext(), ds.getKey(), Toast.LENGTH_SHORT).show();
                    final EventData data=new EventData();
                    data.setID(ds.getKey());
                    DatabaseReference dproject = FirebaseDatabase.getInstance().getReference("todo").child(ds.getKey());
                    dproject.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds:snapshot.getChildren()){
                            if (ds.child("Complete").getValue(String.class).equals("Yes")){
                                count++;
                            }
                        }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    dapps.child(ds.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            data.setAdult("Yes");
                            data.setCost("0");
                            data.setDate("00/00/0000");
                            data.setDrinks("Yes");
                            data.setFood("Yes");
                            data.setIntro("This is a Loading Message...");
                            data.setMusic(false);
                            data.setName("Loadingsss...");
                            data.setTime(Integer.toString(count));
                            data.setCategory("App");
                            data.setName(snapshot.child("Name").getValue(String.class));
                            data.setIntro(snapshot.child("About").getValue(String.class));
                            items.add(new ModelClass(data));
                            adapter.notifyDataSetChanged();
                            count=0;
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
        RecyclerView recyclerView2 = root.findViewById(R.id.recycleappsproject);
        items2 = new ArrayList<>();
        final CustomProgressAdapter adapter2 = new CustomProgressAdapter(getContext(), items2);
        recyclerView2.setAdapter(adapter2);
        recyclerView2.setNestedScrollingEnabled(false);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        final DatabaseReference dproject= FirebaseDatabase.getInstance().getReference("Project");
       dproject.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               items2.clear();
               for (DataSnapshot ds:snapshot.getChildren()){
                   //Toast.makeText(getContext(), ds.getKey(), Toast.LENGTH_SHORT).show();
                   final EventData data2=new EventData();
                   data2.setID(ds.getKey());
                   DatabaseReference dprojects = FirebaseDatabase.getInstance().getReference("todo").child(ds.getKey());
                   dprojects.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           for (DataSnapshot ds:snapshot.getChildren()){
                               if (ds.child("Complete").getValue(String.class).equals("Yes")){
                                   count++;
                               }
                           }
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });
                   dproject.child(ds.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           data2.setAdult("Yes");
                           data2.setCost("0");
                           data2.setDate("00/00/0000");
                           data2.setDrinks("Yes");
                           data2.setFood("Yes");
                           data2.setIntro("This is a Loading Message...");
                           data2.setMusic(false);
                           data2.setName("Loadingsss...");
                           data2.setTime(Integer.toString(count));
                           data2.setCategory("App");
                           data2.setName(snapshot.child("Name").getValue(String.class));
                           data2.setIntro(snapshot.child("About").getValue(String.class));
                           items2.add(new ModelClass(data2));
                           adapter2.notifyDataSetChanged();
                           count=0;
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
        RecyclerView recyclerView3 = root.findViewById(R.id.recycleappsidea);
        items3 = new ArrayList<>();
        final CustomProgressAdapter adapter3 = new CustomProgressAdapter(getContext(), items3);
        recyclerView3.setAdapter(adapter3);
        recyclerView3.setNestedScrollingEnabled(false);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        final DatabaseReference didea= FirebaseDatabase.getInstance().getReference("Idea");
        didea.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items3.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    //Toast.makeText(getContext(), ds.getKey(), Toast.LENGTH_SHORT).show();
                    final EventData data3=new EventData();
                    data3.setID(ds.getKey());
                    DatabaseReference dprojects = FirebaseDatabase.getInstance().getReference("todo").child(ds.getKey());
                    dprojects.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds:snapshot.getChildren()){
                                if (ds.child("Complete").getValue(String.class).equals("Yes")){
                                    count++;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    didea.child(ds.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            data3.setAdult("Yes");
                            data3.setCost("0");
                            data3.setDate("00/00/0000");
                            data3.setDrinks("Yes");
                            data3.setFood("Yes");
                            data3.setIntro("This is a Loading Message...");
                            data3.setMusic(false);
                            data3.setName("Loadingsss...");
                            data3.setTime(Integer.toString(count));
                            data3.setCategory("App");
                            data3.setName(snapshot.child("Name").getValue(String.class));
                            data3.setIntro(snapshot.child("About").getValue(String.class));
                            items3.add(new ModelClass(data3));
                            adapter3.notifyDataSetChanged();
                            count=0;
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
      /*  for (int i = 0; i < 5; i++) {
            EventData data=new EventData();
            data.setAdult("Yes");
            data.setCost("0");
            data.setDate("00/00/0000");
            data.setDrinks("Yes");
            data.setFood("Yes");
            data.setID("");
            data.setIntro("This is a Loading Message...");
            data.setMusic(false);
            data.setName("Loadingsss...");
            data.setTime("00:00");
            data.setCategory("App");
            items.add(new ModelClass(data));
            adapter.notifyDataSetChanged();
        }
        for (int i = 0; i < 5; i++) {
            EventData data2=new EventData();
            data2.setAdult("Yes");
            data2.setCost("0");
            data2.setDate("00/00/0000");
            data2.setDrinks("Yes");
            data2.setFood("Yes");
            data2.setID("");
            data2.setIntro("This is a Loading Message...");
            data2.setMusic(false);
            data2.setName("Loadingsss...");
            data2.setTime("00:00");
            data2.setCategory("Project");
            items2.add(new ModelClass(data2));
            adapter2.notifyDataSetChanged();
        }
        for (int i = 0; i < 5; i++) {
            EventData data3=new EventData();
            data3.setAdult("Yes");
            data3.setCost("0");
            data3.setDate("00/00/0000");
            data3.setDrinks("Yes");
            data3.setFood("Yes");
            data3.setID("");
            data3.setIntro("This is a Loading Message...");
            data3.setMusic(false);
            data3.setName("Loadingsss...");
            data3.setTime("00:00");
            data3.setCategory("Idea");
            items3.add(new ModelClass(data3));
            adapter3.notifyDataSetChanged();
        }*/
        return root;
    }
}
