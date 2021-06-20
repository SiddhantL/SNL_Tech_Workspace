package com.example.snltech.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ArrayList<ModelClass> items,items2,items3;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        /*
        DARK MODE COMPATIBILITY
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
        editor.putBoolean("isDarkModeOn", true);
        editor.apply();
        // When user reopens the app
        // after applying dark/light mode
        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }*/

        final TextView textView = root.findViewById(R.id.text_home);
        final RecyclerView recyclerView = root.findViewById(R.id.recycleapps);
        items = new ArrayList<>();
        root.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),AddHome.class));
            }
        });
        final CustomAdapter adapter = new CustomAdapter(getContext(), items);
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
                                data.setTime("00:00");
                                data.setCategory("App");
                                data.setName(snapshot.child("Name").getValue(String.class));
                                data.setIntro(snapshot.child("About").getValue(String.class));
                                items.add(new ModelClass(data));
                                adapter.notifyDataSetChanged();
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
        final CustomAdapter adapter2 = new CustomAdapter(getContext(), items2);
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
                            data2.setTime("00:00");
                            data2.setCategory("Project");
                            data2.setName(snapshot.child("Name").getValue(String.class));
                            data2.setIntro(snapshot.child("About").getValue(String.class));
                            items2.add(new ModelClass(data2));
                            adapter2.notifyDataSetChanged();
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
        final CustomAdapter adapter3 = new CustomAdapter(getContext(), items3);
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
                            data3.setTime("00:00");
                            data3.setCategory("Idea");
                            data3.setName(snapshot.child("Name").getValue(String.class));
                            data3.setIntro(snapshot.child("About").getValue(String.class));
                            items3.add(new ModelClass(data3));
                            adapter3.notifyDataSetChanged();
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
        for (int i = 0; i < 5; i++) {
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
        }
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem mSearchMenuItem = menu.findItem(R.id.menu_action_search);
        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();
    }
}
