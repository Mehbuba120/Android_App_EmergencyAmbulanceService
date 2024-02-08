package com.example.emergencyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CALL extends AppCompatActivity {
    private TextView name, dis, phn;
    private ImageView callimage;
    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_a_l_l);

        myDialog = new Dialog(this);

        name = findViewById(R.id.textView8);
        dis = findViewById(R.id.textView9);
        phn = findViewById(R.id.textView10);
        callimage = findViewById(R.id.callimage);
        name.setText(getIntent().getStringExtra("fName"));
        dis.setText(getIntent().getStringExtra("fdistrict"));
        phn.setText(getIntent().getStringExtra("Mobile"));

        callimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallButton();

            }
        });


    }

    private void CallButton() {
        String number = phn.getText().toString();
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(CALL.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CALL.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }


        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CallButton();
            } else {
                Toast.makeText(this, "Permission denyed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //PopUp
    public void showPopup(View v) {
        TextView closs;
        myDialog.setContentView(R.layout.terms_policies);
        closs = myDialog.findViewById(R.id.cross);
        closs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();


    }
}