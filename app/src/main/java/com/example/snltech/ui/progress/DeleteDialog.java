package com.example.snltech.ui.progress;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.snltech.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteDialog {

    public void showDialog(final Context activity, final String child1, final String child2){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.delete_dialog);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText("Delete Task");

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        Button dialogClose = (Button) dialog.findViewById(R.id.btn_close);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("todo").child(child1).child(child2);
                df.removeValue();
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