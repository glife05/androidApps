package com.savannapeguins.droid.a254directory.Activities;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.savannapeguins.droid.a254directory.Adapter.RecyclerViewProfilePage;
import com.savannapeguins.droid.a254directory.Model.Listings;
import com.savannapeguins.droid.a254directory.R;

import java.util.ArrayList;
import java.util.List;

public class ProfilePageActivity extends AppCompatActivity {
private static final String TAG="ProfilePageActivity";
//reference UI widgets
private RecyclerView recyclerViewAcc;
//reference the recyclerview adapter
private RecyclerViewProfilePage accAdapter;
//reference the FirebaseFirestore database
private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        recyclerViewAcc=findViewById(R.id.recyclerProfilePage);
        db=FirebaseFirestore.getInstance();

        fillterUser();
    }

    private void fillterUser() {
        db.collection("Listings")
          .whereEqualTo("varEmail", FirebaseAuth.getInstance().getCurrentUser().getEmail())
          .get()
          .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
              @Override
              public void onComplete(@NonNull Task<QuerySnapshot> task) {
                  Log.d(TAG, "onComplete: Records successfully pulled from database");
                  if (task.isSuccessful())
                  {
                      List<Listings>listingsList=new ArrayList<>();
                      for (DocumentSnapshot doc:task.getResult()){
                          Listings listings=doc.toObject(Listings.class);
                          listingsList.add(listings);
                      }

                      accAdapter=new RecyclerViewProfilePage(listingsList,getApplicationContext(),db);
                      RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getApplicationContext());
                      recyclerViewAcc.setLayoutManager(mLayoutManager);
                      recyclerViewAcc.setAdapter(accAdapter);
                  }else{
                      Log.d(TAG,"Error getting documents:",task.getException());
                  }
              }

          });
    }
}
