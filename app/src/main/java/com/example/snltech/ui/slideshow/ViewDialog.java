package com.example.snltech.ui.slideshow;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snltech.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewDialog {

    public void showDialog(final Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.rules_dialog);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        Button dialogClose = (Button) dialog.findViewById(R.id.btn_close);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = dialog.findViewById(R.id.edittext);
                EditText editText2 = dialog.findViewById(R.id.edittext2);
                if (!TextUtils.isEmpty(editText.getText()) || !TextUtils.isEmpty(editText2.getText())) {
                    DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("rules");//.child(id);
                    String pushed = dref.push().getKey();
                    dref.child(pushed).child("Rule").setValue(editText.getText().toString());
                    dref.child(pushed).child("Keys").setValue(editText2.getText().toString());
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