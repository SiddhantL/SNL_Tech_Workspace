package com.example.snltech;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ContactHolder> {

    // List to store all the contact details
    private ArrayList<Contact> contactList;
    private Context mContext;

    // Counstructor for the Class
    public MyAdapter(ArrayList<Contact> contactsList, Context context) {
        this.contactList = contactsList;
        this.mContext = context;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.links_display, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public int getItemCount() {
        return contactList == null? 0: contactList.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull final ContactHolder holder, final int position) {
        final Contact contact = contactList.get(position);
        holder.categorytv.setText(contactList.get(position).getCategory());
        holder.idtv.setText(contactList.get(position).getId());
        // Set the data to the views here
        holder.setContactName(contact.getName());
        holder.setContactNumber(contact.getNumber());
        holder.deletelink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String category=holder.categorytv.getText().toString();
                final String id=holder.idtv.getText().toString();
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                final DatabaseReference drefcheck=FirebaseDatabase.getInstance().getReference().child(category).child(id);
                                drefcheck.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String last="First";
                                        String alttext="First";
                                        int i=0;
                                        while (last!=null){
                                            i++;
                                            last=snapshot.child("Link"+Integer.toString(i)).getValue(String.class);
                                            alttext=snapshot.child("Alttext"+Integer.toString(i)).getValue(String.class);
                                            if (last!=null) {
                                                if (alttext.equals(holder.txtName.getText().toString()) && last.equals(holder.txtNumber.getText().toString())) {
                                                    drefcheck.child("Link" + Integer.toString(i)).setValue("");
                                                    drefcheck.child("Alttext" + Integer.toString(i)).setValue("Deleted");
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
holder.layout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String url=contact.getNumber();
        if (url.startsWith("http://") || url.startsWith("https://")){}else{url="http://"+url;}
        intent.setData(Uri.parse(url));
        mContext.startActivity(intent);
    }
});
        // You can set click listners to indvidual items in the viewholder here
        // make sure you pass down the listner or make the Data members of the viewHolder public

    }

    // This is your ViewHolder class that helps to populate data to the view
    public class ContactHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtNumber,categorytv,idtv;
        private RelativeLayout layout;
        private ImageView deletelink;
        public ContactHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.link);
            txtNumber = itemView.findViewById(R.id.alttext);
            layout=itemView.findViewById(R.id.layout);
            deletelink=itemView.findViewById(R.id.deletelink);
            categorytv=itemView.findViewById(R.id.category);
            idtv=itemView.findViewById(R.id.id);
        }

        public void setContactName(String name) {
            txtName.setText(name);
        }

        public void setContactNumber(String number) {
            txtNumber.setText(number);
        }
    }
}