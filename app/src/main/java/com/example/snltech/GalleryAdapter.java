package com.example.snltech;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<ModelClass> items;
    String timeformat,day;
    String idEvent;
    public GalleryAdapter(Context context, ArrayList<ModelClass> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.items_gallery, parent, false));
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
            //Toast.makeText(context, "images/" + idEvent + ".png", Toast.LENGTH_SHORT).show();
            stor.getReference().child("images/"+idEvent).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    String url = uri.toString();
                    //Toast.makeText(context, url, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(context, idEvent, Toast.LENGTH_SHORT).show();
               //     Glide.with(context.getApplicationContext()).load(url).into(holder.itemImage);
                }
            });
        }
        holder.fullitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,GalleryFiles.class);
                intent.putExtra("id",items.get(position).getData().getID());
                intent.putExtra("foldername",items.get(position).getData().getName());
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

        public CustomViewHolder(View view) {
            super(view);
            itemImage = view.findViewById(R.id.item_images);
            name=view.findViewById(R.id.item_score2);
            fullitem=view.findViewById(R.id.fullitem);
        }
    }
}
