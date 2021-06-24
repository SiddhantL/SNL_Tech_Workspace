package com.example.snltech.ui.slideshow;


import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.snltech.ModelClass;
import com.example.snltech.R;
import com.example.snltech.ui.progress.DisplayTask;
import com.example.snltech.ui.progress.ViewDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class RulesAdapter extends RecyclerView.Adapter<RulesAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<ModelClass> items;
    String idEvent;
    public RulesAdapter(Context context, ArrayList<ModelClass> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.items_rules, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {
        // holder.itemTitle.setText(items.get(position).getData().getName());
        if (items.get(position).getData().getID()!="") {
            //  Toast.makeText(context, items.get(position).getData().getName(), Toast.LENGTH_SHORT).show();
            FirebaseStorage stor = FirebaseStorage.getInstance();
            idEvent = items.get(position).getData().getID();
              // holder.itemImage.setImageResource(R.drawable.profile);
               holder.name.setText(Integer.toString(position+1)+". "+items.get(position).getData().getName());
            //Toast.makeText(context, "images/" + idEvent + ".png", Toast.LENGTH_SHORT).show();
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.example.snltech.ui.slideshow.DeleteDialog alerts = new DeleteDialog();
                alerts.showDialog(context,items.get(position).getData().getID());
            }
        });
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private TextView name;
        RelativeLayout fullitem;
        private ImageView delete;
        public CustomViewHolder(View view) {
            super(view);
            name=view.findViewById(R.id.item_score2);
            fullitem=view.findViewById(R.id.fullitem);
            checkBox=view.findViewById(R.id.checkbox);
            delete=view.findViewById(R.id.imageView2);
        }
    }
    public void filterList(ArrayList<ModelClass> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        items = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

}
