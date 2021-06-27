package com.example.snltech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Password extends AppCompatActivity {
Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0;
RelativeLayout bdel;
String pass,datapass;
RadioButton r1,r2,r3,r4;
TextView state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pass="";
        setContentView(R.layout.activity_password);
        b1=findViewById(R.id.but1);
        b2=findViewById(R.id.but2);
        b3=findViewById(R.id.but3);
        b4=findViewById(R.id.but4);
        b5=findViewById(R.id.but5);
        b6=findViewById(R.id.but6);
        b7=findViewById(R.id.but7);
        b8=findViewById(R.id.but8);
        b9=findViewById(R.id.but9);
        b0=findViewById(R.id.but0);
        bdel=findViewById(R.id.butdelete);
        DatabaseReference dref= FirebaseDatabase.getInstance().getReference().child("Password");
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datapass=snapshot.child("Pass").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pass.length()<5){
                    pass=pass+Integer.toString(1);
                    enter_num();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pass.length()<5){
                    pass=pass+Integer.toString(2);
                    enter_num();
                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pass.length()<5){
                    pass=pass+Integer.toString(3);
                    enter_num();
                }
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pass.length()<5){
                    pass=pass+Integer.toString(4);
                    enter_num();
                }
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pass.length()<5){
                    pass=pass+Integer.toString(5);
                    enter_num();
                }
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pass.length()<5){
                    pass=pass+Integer.toString(6);
                    enter_num();
                }
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pass.length()<5){
                    pass=pass+Integer.toString(7);
                    enter_num();
                }
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pass.length()<5){
                    pass=pass+Integer.toString(8);
                    enter_num();
                }
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pass.length()<5){
                    pass=pass+Integer.toString(9);
                    enter_num();
                }
            }
        });
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pass.length()<5){
                    pass=pass+Integer.toString(0);
                    enter_num();
                }
            }
        });
        bdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pass.length()>0){
                    pass=pass.substring(0,pass.length()-1);
                    enter_num();
                }
            }
        });
        r1=findViewById(R.id.radioButton);
        r2=findViewById(R.id.radioButton2);
        r3=findViewById(R.id.radioButton3);
        r4=findViewById(R.id.radioButton4);

        state=findViewById(R.id.textView4);
    }
    public void enter_num(){
        if (pass.length()<1){
            r1.setChecked(false);
            r2.setChecked(false);
            r3.setChecked(false);
            r4.setChecked(false);
        }else if (pass.length()<2){
            r1.setChecked(true);
            r2.setChecked(false);
            r3.setChecked(false);
            r4.setChecked(false);
        }else if (pass.length()<3){
            r1.setChecked(true);
            r2.setChecked(true);
            r3.setChecked(false);
            r4.setChecked(false);
        }else if (pass.length()<4){
            r1.setChecked(true);
            r2.setChecked(true);
            r3.setChecked(true);
            r4.setChecked(false);
        }else if (pass.length()<5){
            r1.setChecked(true);
            r2.setChecked(true);
            r3.setChecked(true);
            r4.setChecked(true);
            if (datapass.equals(pass)){
                state.setText("Welcome");
                state.setTextColor(ContextCompat.getColor(Password.this, R.color.green));
                startActivity(new Intent(Password.this,MainActivity.class));
                finish();
            }else{
                state.setVisibility(View.VISIBLE);
                pass="";
                enter_num();
            }
        }
}
}
