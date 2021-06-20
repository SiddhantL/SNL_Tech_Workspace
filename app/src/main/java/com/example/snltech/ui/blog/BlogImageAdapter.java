package com.example.snltech.ui.blog;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.snltech.R;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BlogImageAdapter extends RecyclerView.Adapter<BlogImageAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<ModelClass> items;

    public BlogImageAdapter(Context context, ArrayList<ModelClass> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.items_image_blog, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, int position) {
        //holder.itemTitle.setText(items.get(position).getTitle());
        holder.itemImage.setImageBitmap(items.get(position).getBp());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
       /* if (items.get(position).getCount()!=0) {
            String destinationFilename = android.os.Environment.getExternalStorageDirectory().getPath() + File.separatorChar + "evEntry"+ Integer.toString(items.get(position).getCount())+".png";
            Picasso.get().load(new File(destinationFilename)).into(holder.itemImage);
        }else{
            holder.itemImage.setImageResource(R.drawable.add);
        }*/
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private ImageView itemImage;
        private TextView itemTitle;

        public CustomViewHolder(View view) {
            super(view);
            itemImage = view.findViewById(R.id.item_image);
            itemTitle = view.findViewById(R.id.item_title);
        }
    }
}