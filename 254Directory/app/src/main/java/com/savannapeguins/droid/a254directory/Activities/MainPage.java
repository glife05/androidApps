package com.savannapeguins.droid.a254directory.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.savannapeguins.droid.a254directory.Adapter.RecyclerViewMainPage;
import com.savannapeguins.droid.a254directory.Model.Listings;
import com.savannapeguins.droid.a254directory.R;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends AppCompatActivity {
    private static final String TAG ="MainPageActivity" ;
    //UI widgets
    private RecyclerView mainPageRecyclerView;
    private RecyclerViewMainPage mainPageAdapter;
    private FirebaseFirestore db;
    //layout inflater for singout
    private LayoutInflater inflateLayout;
    private AlertDialog dialogBox;
    private AlertDialog.Builder buildAlertDialog;

    //layout inflater for about
    private LayoutInflater inflateLayout2;
    private AlertDialog dialogBox2;
    private AlertDialog.Builder buildAlertDialog2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        mainPageRecyclerView=(RecyclerView)findViewById(R.id.recyclerViewMainPage);
        db=FirebaseFirestore.getInstance();
        bizListings();


    }

    //pull listings from firestore******************************************************************
    public void bizListings(){
    db.collection("Listings").get()
      .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
          @Override
          public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    List<Listings>mainPageList=new ArrayList<>();
                    for (DocumentSnapshot doc:task.getResult())
                    {
                        Listings mainList=doc.toObject(Listings.class);
                        mainPageList.add(mainList);
                    }

                    mainPageAdapter=new RecyclerViewMainPage(mainPageList,getApplicationContext(),db);
                    RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getApplicationContext());
                    mainPageRecyclerView.setLayoutManager(mLayoutManager);
                    mainPageRecyclerView.setAdapter(mainPageAdapter);
                }else{
                    Log.d(TAG, "Error getting documents:",task.getException());
                    Toast.makeText(MainPage.this, "Error getting records", Toast.LENGTH_SHORT).show();
                }
          }
      });
    }
    //********ends**********************************************************************************

    //implements menu layout========================================================================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_row, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mAdd:
                //start add business activity
                Intent intent=new Intent(this,AddBusinessActivity.class);
                this.startActivity(intent);
                return true;
            case R.id.mProfilePage:
                Intent intent2=new Intent(this,ProfilePageActivity.class);
                this.startActivity(intent2);
                return true;
            case R.id.mSignOut:
                signOut();
             case R.id.mAbout:
                 about();
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    //ends menu layout implementation actions=================================================================

    //******close application*********************************************************************
    public void signOut(){
        buildAlertDialog =new AlertDialog.Builder(MainPage.this);
        inflateLayout=LayoutInflater.from(MainPage.this);
        View view=inflateLayout.inflate(R.layout.sign_out_dialog,null);

        //reference sign_out_dialog widgets
        Button yesBtn=(Button)view.findViewById(R.id.btnSignOutYES);
        Button noBtn=(Button)view.findViewById(R.id.btnSignOutNO);

        buildAlertDialog.setView(view);
        dialogBox=buildAlertDialog.create();
        dialogBox.show();

        //button click events
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo:close application
                finish();
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBox.dismiss();
            }
        });
    }

    //***close  ENDS HERE**************************************************************

    //****opens about page*************************************************************
    public void about(){
        buildAlertDialog2=new AlertDialog.Builder(MainPage.this);
        inflateLayout2=LayoutInflater.from(MainPage.this);
        View view=inflateLayout2.inflate(R.layout.about_page,null);

        TextView tvOk=(TextView)view.findViewById(R.id.tvOkButton);

        buildAlertDialog2.setView(view);
        dialogBox2=buildAlertDialog2.create();
        dialogBox2.show();
        //ok click
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //close dialog box
                dialogBox2.dismiss();
            }
        });

    }

    //***close ENDS HERE**************************************************************
}
