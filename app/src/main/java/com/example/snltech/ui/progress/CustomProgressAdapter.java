package com.example.snltech.ui.progress;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.snltech.EventData;
import com.example.snltech.ModelClass;
import com.example.snltech.R;
import com.example.snltech.ui.home.DisplayInfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomProgressAdapter extends RecyclerView.Adapter<CustomProgressAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<ModelClass> items;
    String idEvent;
    float total=0,comp=0;
    public CustomProgressAdapter(Context context, ArrayList<ModelClass> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.items_progress, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {
        // holder.itemTitle.setText(items.get(position).getData().getName());
        if (items.get(position).getData().getID()!="") {
            //  Toast.makeText(context, items.get(position).getData().getName(), Toast.LENGTH_SHORT).show();
            FirebaseStorage stor = FirebaseStorage.getInstance();
            idEvent = items.get(position).getData().getID();
              // holder.itemImage.setImageResource(R.drawable.profile);
               holder.name.setText(items.get(position).getData().getName());
               holder.name.setText("");
            //Toast.makeText(context, "images/" + idEvent + ".png", Toast.LENGTH_SHORT).show();
            stor.getReference().child("images/"+idEvent).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    String url = uri.toString();
                    //Toast.makeText(context, url, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(context, idEvent, Toast.LENGTH_SHORT).show();
                    Glide.with(context.getApplicationContext()).load(url).into(holder.itemImage);
                }
            });
        }
        holder.pb.setProgress(0);
        holder.pb.setMax(100);
        comp=0;
        //if (!items.get(position).getData().getID().equals("")) {
            final DatabaseReference dproject = FirebaseDatabase.getInstance().getReference("todo").child(items.get(position).getData().getID());
            //Toast.makeText(context, items.get(position).getData().getID(), Toast.LENGTH_SHORT).show();
holder.name.setText(items.get(position).getData().getName());
       dproject.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    total=Integer.parseInt(Long.toString(snapshot.getChildrenCount()));
                    comp=Integer.parseInt(items.get(position).getData().getTime());
                    float perc = comp / total;
                    perc = perc * 100;
                    holder.pb.setProgress((int) perc);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        //}
        holder.fullitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, DisplayTask.class);
                intent.putExtra("id",items.get(position).getData().getID());
                intent.putExtra("category",items.get(position).getData().getCategory());
context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private ImageView itemImage;
        private TextView name;
        RelativeLayout fullitem;
        private ProgressBar pb;
        public CustomViewHolder(View view) {
            super(view);
            itemImage = view.findViewById(R.id.item_images);
            name=view.findViewById(R.id.item_score2);
            fullitem=view.findViewById(R.id.fullitem);
            pb=view.findViewById(R.id.progressBar);
        }
    }
}
