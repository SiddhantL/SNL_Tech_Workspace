package com.example.snltech.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.snltech.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LinkActivity extends AppCompatActivity {
EditText link,alttext;
Button submit;
String category,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);
        final DatabaseReference dref= FirebaseDatabase.getInstance().getReference();
        alttext=findViewById(R.id.editText3);
        link=findViewById(R.id.editText4);
        category=getIntent().getStringExtra("category");
        id=getIntent().getStringExtra("id");
        final DatabaseReference drefs= FirebaseDatabase.getInstance().getReference(category).child(id);
        submit=findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drefs.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String last="First";
                        int i=0;
                        while (last!=null){
                            i++;
                            last=snapshot.child("Link"+Integer.toString(i)).getValue(String.class);
                        }
                        dref.child(category).child(id).child("Link"+i).setValue(link.getText().toString());
                        dref.child(category).child(id).child("Alttext"+i).setValue(alttext.getText().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }
}
