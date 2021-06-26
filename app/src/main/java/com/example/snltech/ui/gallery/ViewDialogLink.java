package com.example.snltech.ui.gallery;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snltech.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class ViewDialogLink {
    Spinner category;
    public void showDialog(final Activity activity, final String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.gallery_dialog);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText("Link Folder");

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        Button dialogClose = (Button) dialog.findViewById(R.id.btn_close);
        final ArrayList<String> arrayList = new ArrayList<>();
        final ArrayList<String> arrayListId = new ArrayList<>();
        final DatabaseReference app=FirebaseDatabase.getInstance().getReference().child("App");
        final DatabaseReference project=FirebaseDatabase.getInstance().getReference().child("Project");
        final DatabaseReference ideas=FirebaseDatabase.getInstance().getReference().child("Idea");
        category = dialog.findViewById(R.id.spinner);
        arrayList.add("-");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(dialog.getContext(),R.layout.custom_spinner, arrayList);
        //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(arrayAdapter);
        app.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (final DataSnapshot ds:snapshot.getChildren()){
                    app.child(ds.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            arrayList.add(snapshot.child("Name").getValue(String.class)+"-App");
                            arrayListId.add(ds.getKey());
                            arrayAdapter.notifyDataSetChanged();
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
        project.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (final DataSnapshot ds:snapshot.getChildren()){
                    project.child(ds.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            arrayList.add(snapshot.child("Name").getValue(String.class)+"-Project");
                            arrayListId.add(ds.getKey());
                            arrayAdapter.notifyDataSetChanged();
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
        ideas.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (final DataSnapshot ds:snapshot.getChildren()){
                    ideas.child(ds.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            arrayList.add(snapshot.child("Name").getValue(String.class)+"-Idea");
                            arrayListId.add(ds.getKey());
                            arrayAdapter.notifyDataSetChanged();
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

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!category.getSelectedItem().toString().equals("-")){
                    Toast.makeText(activity, arrayListId.get(category.getSelectedItemPosition()-1), Toast.LENGTH_SHORT).show();
                    int indexs=category.getSelectedItem().toString().lastIndexOf("-")+1;
                    String categorys= category.getSelectedItem().toString().substring(indexs);
                    DatabaseReference df=FirebaseDatabase.getInstance().getReference().child(categorys).child(arrayListId.get(category.getSelectedItemPosition()-1));
                    df.child("file").setValue(msg);
                    Toast.makeText(activity, category.getSelectedItem().toString().replace("-"+categorys,"")+" Linked", Toast.LENGTH_SHORT).show();
                }
            }
            });
        dialog.show();
    dialogClose.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog.dismiss();
        }
    });
    }
}