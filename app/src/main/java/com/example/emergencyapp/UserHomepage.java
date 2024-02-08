package com.example.emergencyapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserHomepage extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<user> userArrayList;
    MyAdapter myAdapter;
    FirebaseFirestore fStore;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepage);
        this.setTitle("Ambulance List");

        recyclerView=findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Fetching Data....");
        progressDialog.show();

        fStore=FirebaseFirestore.getInstance();
        userArrayList= new ArrayList<user>();
        //myAdapter= new MyAdapter(userArrayList);
        //        recyclerView.setAdapter(myAdapter);

        EventChangeListener ();
    }

    private void EventChangeListener() {
       fStore.collection("admin").orderBy("fdistrict", Query.Direction.ASCENDING)
               .addSnapshotListener(new EventListener<QuerySnapshot>() {
           @Override
           public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
               if(error != null){
                   if(progressDialog.isShowing())
                       progressDialog.dismiss();
                   Log.e("Firestore error",error.getMessage());
                   return;
               }
              for (DocumentChange fStore : value.getDocumentChanges()){
                  if(fStore.getType()==DocumentChange.Type.ADDED){
                      userArrayList.add(fStore.getDocument().toObject(user.class));
                  }
                  myAdapter= new MyAdapter(UserHomepage.this,userArrayList);
                  recyclerView.setAdapter(myAdapter);

                  myAdapter.notifyDataSetChanged();
                  if(progressDialog.isShowing())
                      progressDialog.dismiss();

              }
           }

       }) ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list,menu);
        MenuItem menuItem=menu.findItem(R.id.searchbar);
        SearchView searchView=(SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Search Here");
       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               myAdapter.getFilter().filter(newText);
               return false;
           }
       });
       return super.onCreateOptionsMenu(menu);
    }
}