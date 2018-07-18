package com.savannapeguins.droid.placefinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.savannapeguins.droid.placefinder.Adapter.AccountRecyclerViewAdapter;
import com.savannapeguins.droid.placefinder.Model.Listings;

import java.util.ArrayList;
import java.util.List;

public class ProfilePageActivity extends AppCompatActivity {
private static final String TAG="ProfilePageActivity";
private RecyclerView recyclerViewAcc;
private AccountRecyclerViewAdapter accAdapter;
private FirebaseFirestore firestoreDB;
//private CollectionReference listsRef=firestoreDB.collection("lists");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                startActivity(new Intent(ProfilePageActivity.this,AddBusinessActivity.class));
            }
        });

        recyclerViewAcc=findViewById(R.id.recyclerViewAccounts);
        firestoreDB=FirebaseFirestore.getInstance();
        //loadUsers();
        fillterUser();
    }

    private void fillterUser() {
   firestoreDB.collection("lists")
           .whereEqualTo("biz_contact","0789555555")
            .get()
             .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                 @Override
                 public void onComplete(@NonNull Task<QuerySnapshot> task) {
                     if (task.isSuccessful())
                     {
                         List<Listings>accountLists=new ArrayList<>();
                         for (DocumentSnapshot doc:task.getResult())
                         {
                             Listings list=doc.toObject(Listings.class);
                             accountLists.add(list);
                         }
                         accAdapter=new AccountRecyclerViewAdapter(accountLists,getApplicationContext(),firestoreDB);
                         RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getApplicationContext());
                         recyclerViewAcc.setLayoutManager(mLayoutManager);
                         recyclerViewAcc.setAdapter(accAdapter);
                     }else{
                         Log.d(TAG,"Error getting documents:",task.getException());
                     }
                 }

             });
    }

    private void loadUsers() {

        firestoreDB.collection("lists")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                            {
                                List<Listings>accountLists=new ArrayList<>();
                                for (DocumentSnapshot doc:task.getResult())
                                    {
                                        Listings list=doc.toObject(Listings.class);
                                        accountLists.add(list);
                                    }
                                    accAdapter=new AccountRecyclerViewAdapter(accountLists,getApplicationContext(),firestoreDB);
                                    RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getApplicationContext());
                                    recyclerViewAcc.setLayoutManager(mLayoutManager);
                                    recyclerViewAcc.setAdapter(accAdapter);
                            }else{
                            Log.d(TAG,"Error gettig documents:",task.getException());
                                 }
                    }
                });
    }

}
