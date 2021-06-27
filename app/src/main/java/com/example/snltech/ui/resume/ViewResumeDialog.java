package com.example.snltech.ui.resume;

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

public class ViewResumeDialog {
    EditText editText;
    public void showDialog(final Activity activity,final String link){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.folder_dialog);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText("Enter Resume Link");

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        Button dialogClose = (Button) dialog.findViewById(R.id.btn_close);
        editText = dialog.findViewById(R.id.edittext);
        editText.setHint("Resume Link");
        editText.setText(link);
        dialogButton.setText("Update");
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editText.getText())) {
                    DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("Resume");//.child(id);
                    String pushed = dref.push().getKey();
                    dref.child("ResumeLink").setValue(editText.getText().toString());
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