package com.example.snltech.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.snltech.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteApp {

    public void showDialog(final Context activity, final String category, final String id){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.delete_dialog);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText("Are you sure you want to delete this "+category+"?");

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        Button dialogClose = (Button) dialog.findViewById(R.id.btn_close);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference dref= FirebaseDatabase.getInstance().getReference().child(category);
                dref.child(id).removeValue();
                try {
                    DatabaseReference dref2= FirebaseDatabase.getInstance().getReference("todo").child(id);
                    dref2.removeValue();
                }catch (Exception E){
                }
                dialog.dismiss();
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