package com.example.emergencyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfile extends AppCompatActivity {


    public static final String TAG = "TAG";
    EditText pName, pDis,pSub,pPhn,pMail,pStatus,pHospital;
    Button save;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        Intent data=getIntent();
        String editname=data.getStringExtra("editname");
        String editdis=data.getStringExtra("editdis");
        String editsub=data.getStringExtra("editsub");
        String editphn=data.getStringExtra("editphn");
        String editmail=data.getStringExtra("editmail");
        String editstatus=data.getStringExtra("editstatus");
        String edithospital=data.getStringExtra("edithospital");

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        user=fAuth.getCurrentUser();

        pName=findViewById(R.id.updatename);
        pDis=findViewById(R.id.updateDis);
        pSub=findViewById(R.id.updatSub);
        pPhn=findViewById(R.id.updatphn);
        pMail=findViewById(R.id.updatmail);
        pStatus=findViewById(R.id.updateStat);
        pHospital=findViewById(R.id.updatHos);
        save=findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pName.getText().toString().isEmpty() || pDis.getText().toString().isEmpty() || pSub.getText().toString().isEmpty() || pPhn.getText().toString().isEmpty() || pMail.getText().toString().isEmpty()||pStatus.getText().toString().isEmpty()||pHospital.getText().toString().isEmpty()){
                    Toast.makeText(UpdateProfile.this,"One or many fields are empty",Toast.LENGTH_SHORT).show();
                    return;
                }

                String email=pMail.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docRef = fStore.collection("admin").document(user.getUid());
                        Map<String,Object> edited= new HashMap<>();
                        edited.put("email",email);
                        edited.put("fName",pName.getText().toString());
                        edited.put("fdistrict",pDis.getText().toString());
                        edited.put("fSubDistrict",pSub.getText().toString());
                        edited.put("Mobile",pPhn.getText().toString());
                        edited.put("status",pStatus.getText().toString());
                        edited.put("hospital",pHospital.getText().toString());
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(UpdateProfile.this,"Profile Updated",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(UpdateProfile.this, AdminProfiel.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                        //Toast.makeText(UpdateProfile.this,"Email is changed",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateProfile.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        pName.setText(editname);
        pDis.setText(editdis);
        pSub.setText(editsub);
        pPhn.setText(editphn);
        pMail.setText(editmail);
        pStatus.setText(editstatus);
        pHospital.setText(edithospital);


        Log.d(TAG,"onCreate: " + editname + " " + editdis + " " + editsub + " " + editphn + " " + editmail +" " + editstatus);


    }
}