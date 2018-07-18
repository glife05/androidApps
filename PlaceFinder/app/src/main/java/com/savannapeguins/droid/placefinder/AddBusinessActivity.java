package com.savannapeguins.droid.placefinder;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.savannapeguins.droid.placefinder.Model.Listings;

public class AddBusinessActivity extends AppCompatActivity implements View.OnClickListener{
private static final String TAG="AddBusinessActivity";

private TextInputLayout bName,bIndu,bStreet,bPhone,bContact,bBuild;
private Spinner spinner;
private Button addBiz;
    //Reference to Firestore
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference bizListRef=db.collection("lists");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_business);
        bName=findViewById(R.id.ed_business_name);
        spinner=findViewById(R.id.spinner);
        bStreet=findViewById(R.id.ed_street);
        bPhone=findViewById(R.id.ed_contact_phone);
        bContact=findViewById(R.id.ed_contact_person);
        bBuild=findViewById(R.id.ed_building_name);
        addBiz=findViewById(R.id.buttonAddBusiness);

        //click event
        addBiz.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //declares new variables for the widgets
        String biz_name=bName.getEditText().getText().toString();
        String new_spinner=spinner.getSelectedItem().toString();
        String biz_street=bStreet.getEditText().getText().toString();
        String biz_phone=bPhone.getEditText().getText().toString();
        String biz_contact=bContact.getEditText().getText().toString();
        String biz_build=bBuild.getEditText().getText().toString();
        //ToDo:validated entries>>check if empty
        Listings listings=new Listings(biz_name,new_spinner,biz_street,biz_phone,biz_contact,biz_build);
        bizListRef.add(listings)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(AddBusinessActivity.this, "Business added", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onSuccess: Document add with ID:" +documentReference.getId());
                        }
                    })
                 .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddBusinessActivity.this, "Error saving business", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: Error adding document",e);
            }
        });


    }
}
