package com.example.emergencyapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class AdminProfiel extends AppCompatActivity {
    Button logout;
    TextView mName, mDis, mSubDis, mPhn, mMail,mStatus,mHospital;
    Button update;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String adminID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profiel);


        logout=findViewById(R.id.logout);

        mName=findViewById(R.id.profilename);
        mDis=findViewById(R.id.profiledis);
        mSubDis=findViewById(R.id.profilesub);
        mPhn=findViewById(R.id.profilephn);
        mMail=findViewById(R.id.profilemail);
        mStatus=findViewById(R.id.profilestatus);
        mHospital=findViewById(R.id.profilehos);
        update=findViewById(R.id.update);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        adminID= fAuth.getCurrentUser().getUid();

        DocumentReference documentReference= fStore.collection("admin").document(adminID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                //if document exists
                mName.setText(documentSnapshot.getString("fName"));
                mDis.setText(documentSnapshot.getString("fdistrict"));
                mSubDis.setText(documentSnapshot.getString("fSubDistrict"));
                mPhn.setText(documentSnapshot.getString("Mobile"));
                mMail.setText(documentSnapshot.getString("email"));
                mStatus.setText(documentSnapshot.getString("status"));
                mHospital.setText(documentSnapshot.getString("hospital"));

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminProfiel.this, UpdateProfile.class);
                intent.putExtra("editname",mName.getText().toString());
                intent.putExtra("editdis",mDis.getText().toString());
                intent.putExtra("editsub",mSubDis.getText().toString());
                intent.putExtra("editphn",mPhn.getText().toString());
                intent.putExtra("editmail",mMail.getText().toString());
                intent.putExtra("editstatus",mStatus.getText().toString());
                intent.putExtra("edithospital",mHospital.getText().toString());

                startActivity(intent);
            }
        });




        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(AdminProfiel.this,Admin_Login.class);
                startActivity(intent);
                finish();
            }
        });
    }


}