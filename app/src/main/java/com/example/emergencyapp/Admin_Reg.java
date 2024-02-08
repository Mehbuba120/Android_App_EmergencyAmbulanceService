package com.example.emergencyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Admin_Reg extends AppCompatActivity {
    public static final String TAG = "TAG";
    public static final String TAG1 = "TAG";
    EditText mName, mSubDis, mDistrict, mPassword, mPhone, mEmail,mStatus,mHospital;
    Button mRegister;
    TextView login;
    FirebaseAuth fAuth;
    FirebaseFirestore  fStore;
    String adminID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__reg);
        mName=findViewById(R.id.adminname);
        mDistrict=findViewById(R.id.district);
        mSubDis=findViewById(R.id.Sub);
        mPassword=findViewById(R.id.ETpass);
        mPhone=findViewById(R.id.ETphn);
        mEmail=findViewById(R.id.ETmail);
        login=findViewById(R.id.etLOG);
        mRegister=findViewById(R.id.adminReg);
        mStatus=findViewById(R.id.ETstatus);
        mHospital=findViewById(R.id.Hospital);

        fAuth= FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser() !=null){
            Intent intent=new Intent(Admin_Reg.this,AdminProfiel.class);
            startActivity(intent);
            finish();
        }

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= mEmail.getText().toString().trim();
                String password= mPassword.getText().toString().trim();
                String name=mName.getText().toString();
                String district=mDistrict.getText().toString();
                String SubDis=mSubDis.getText().toString();
                String mobile=mPhone.getText().toString();
                String status=mStatus.getText().toString();
                String hospital=mHospital.getText().toString();
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("E-mail is required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required.");
                    return;
                }
                if(password.length() <5){
                    mPassword.setError("Password must be >= 5");
                    return;
                }
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Admin_Reg.this,"Registration Complete.",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Admin_Reg.this,AdminProfiel.class);
                            adminID=fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference=fStore.collection("admin").document(adminID);
                            Map<String,Object> admin=new HashMap<>();
                            admin.put("fName",name);
                            admin.put("fdistrict",district);
                            admin.put("fSubDistrict",SubDis);
                            admin.put("Mobile",mobile);
                            admin.put("email",email);
                            admin.put("status",status);
                            admin.put("hospital",hospital);
                            documentReference.set(admin).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: Admin Profile is created for "+ adminID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG1,"onFailure: " + e.toString());
                                }
                            });

                            startActivity(intent);
                        }else{
                            Toast.makeText(Admin_Reg.this, "Error !" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();


                        }

                    }
                });

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Admin_Reg.this,Admin_Login.class);
                startActivity(intent);
            }
        });



    }
}
