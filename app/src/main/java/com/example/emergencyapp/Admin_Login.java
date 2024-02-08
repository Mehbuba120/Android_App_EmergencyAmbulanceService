package com.example.emergencyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Admin_Login extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button login;
    TextView signup;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__login);

        mEmail=findViewById(R.id.AdminLoginMail);
        mPassword=findViewById(R.id.adminLoginPass);
        login=findViewById(R.id.adminLoginButton);
        signup=findViewById(R.id.adminSignup);

        fAuth=FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= mEmail.getText().toString().trim();
                String password= mPassword.getText().toString().trim();
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
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Admin_Login.this,"Logged In Successfully.",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Admin_Login.this,AdminProfiel.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(Admin_Login.this, "Error !" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Admin_Login.this,Admin_Reg.class);
                startActivity(intent);
            }
        });
    }

}