package com.example.snltech.ui.progress;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.snltech.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewDialog {

    public void showDialog(final Activity activity, final String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.folder_dialog);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText("Add a Task");
        EditText editText = dialog.findViewById(R.id.edittext);
        editText.setHint("Task");
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        Button dialogClose = (Button) dialog.findViewById(R.id.btn_close);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = dialog.findViewById(R.id.edittext);
                if (!TextUtils.isEmpty(editText.getText())) {
                    DatabaseReference dref= FirebaseDatabase.getInstance().getReference().child("todo").child(msg);
                    String pushed=dref.push().getKey();
                    dref.child(pushed).child("Task").setValue(editText.getText().toString());
                    dref.child(pushed).child("Complete").setValue("No");
                    dialog.dismiss();
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