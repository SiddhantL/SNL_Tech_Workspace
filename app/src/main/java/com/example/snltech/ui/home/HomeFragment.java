package com.example.snltech.ui.home;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snltech.EventData;
import com.example.snltech.ModelClass;
import com.example.snltech.R;
import com.example.snltech.ui.slideshow.SlideshowViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ArrayList<ModelClass> items,items2,items3;
    SearchView searchView;
    LinearLayoutManager lmanager;
    CustomAdapter adapter,adapter2,adapter3;
    private SearchView.OnQueryTextListener queryTextListener;
    private SlideshowViewModel slideshowViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
setHasOptionsMenu(true);
        final TextView textView = root.findViewById(R.id.text_home);
        final RecyclerView recyclerView = root.findViewById(R.id.recycleapps);
        items = new ArrayList<>();
        root.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),AddHome.class));
            }
        });
        adapter = new CustomAdapter(getContext(), items);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        lmanager=new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(lmanager);
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
                            recyclerView.suppressLayout(false);
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
        final RecyclerView recyclerView2 = root.findViewById(R.id.recycleappsproject);
        items2 = new ArrayList<>();
        adapter2 = new CustomAdapter(getContext(), items2);
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
                            recyclerView2.suppressLayout(false);
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
        final RecyclerView recyclerView3 = root.findViewById(R.id.recycleappsidea);
        items3 = new ArrayList<>();
        adapter3 = new CustomAdapter(getContext(), items3);
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
                            recyclerView3.suppressLayout(false);
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
            recyclerView.suppressLayout(true);
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
            recyclerView2.suppressLayout(true);
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
            recyclerView3.suppressLayout(true);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);
                    filter(newText);
                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }
    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<ModelClass> filteredlist = new ArrayList<>();
        ArrayList<ModelClass> filteredlist2 = new ArrayList<>();
        ArrayList<ModelClass> filteredlist3= new ArrayList<>();
        // running a for loop to compare elements.
        for (ModelClass item : items) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getData().getName().toLowerCase().contains(text.toLowerCase())||item.getData().getIntro().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        for (ModelClass item2 : items2) {
            // checking if the entered string matched with any item of our recycler view.
            if (item2.getData().getName().toLowerCase().contains(text.toLowerCase())||item2.getData().getIntro().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist2.add(item2);
            }
        }
        for (ModelClass item3 : items3) {
            // checking if the entered string matched with any item of our recycler view.
            if (item3.getData().getName().toLowerCase().contains(text.toLowerCase())||item3.getData().getIntro().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist3.add(item3);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist);
            adapter2.filterList(filteredlist2);
            adapter3.filterList(filteredlist3);
        }
    }
}
