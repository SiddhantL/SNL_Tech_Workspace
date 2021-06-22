package com.example.snltech.ui.slideshow;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snltech.EventData;
import com.example.snltech.ModelClass;
import com.example.snltech.R;
import com.example.snltech.ui.progress.DisplayTask;
import com.example.snltech.ui.progress.TodoAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment {
    ArrayList<ModelClass> items;
    RulesAdapter adapter;
    RecyclerView recyclerView;
    SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;
    private SlideshowViewModel slideshowViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = ViewModelProviders.of(this).get(SlideshowViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        root.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewDialog alerts = new ViewDialog();
                alerts.showDialog(getActivity(),"Add a Rule");
            }
        });

        recyclerView=root.findViewById(R.id.taskrcycle);
        items = new ArrayList<>();
        adapter = new RulesAdapter(getContext(), items);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        final DatabaseReference dproject= FirebaseDatabase.getInstance().getReference("rules");
        setHasOptionsMenu(true);
        dproject.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    //Toast.makeText(getContext(), ds.getKey(), Toast.LENGTH_SHORT).show();
                    final EventData data=new EventData();
                    data.setID(ds.getKey());
                    dproject.child(ds.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
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
                            data.setTime("0");
                            data.setCategory("Project");
                            data.setName(snapshot.child("Rule").getValue(String.class));
                            data.setIntro(snapshot.child("Keys").getValue(String.class));
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
        return root;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
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

        // running a for loop to compare elements.
        for (ModelClass item : items) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getData().getName().toLowerCase().contains(text.toLowerCase())||item.getData().getIntro().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
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
        }
    }
}
