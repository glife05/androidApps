package com.savannapeguins.droid.a254directory.Activities;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.savannapeguins.droid.a254directory.GlobalVariable.GloabalVariables;
import com.savannapeguins.droid.a254directory.Model.Listings;
import com.savannapeguins.droid.a254directory.R;

public class AddBusinessActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tvVarEmail;
    private TextInputLayout bName,bPhone,bContactPerson,bLocation;
    private Button btnPublish,btnUpdate;
    private ProgressBar progressBarAddBiz;

    //Firestore variables
    public FirebaseFirestore firestoreDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_business);
        tvVarEmail=(TextView)findViewById(R.id.tvHiddenVar);//holds userEmailAddress
        tvVarEmail.setText(GloabalVariables.emailInfo);//sets tv value to emailInfo
        bName=findViewById(R.id.edBusinessName);
        bPhone=findViewById(R.id.edPhoneNumber);
        bContactPerson=findViewById(R.id.edContactPerson);
        bLocation=findViewById(R.id.edLocation);
        btnPublish=findViewById(R.id.bPublishBusiness);
        btnUpdate=findViewById(R.id.btnUpdateBusiness);
        progressBarAddBiz=findViewById(R.id.progressBarBiz);

        firestoreDB=FirebaseFirestore.getInstance();
        btnPublish.setOnClickListener(this);//click event
        btnUpdate.setOnClickListener(this);//click event
    }
    //action when AddBusinessActivity button is clicked*********************************************
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bPublishBusiness:
                //ToDo button event
                publishBusiness();
                break;
            case R.id.btnUpdateBusiness:
                //ToDo update database

                break;

        }
    }
    //****action on button ends here****************************************************************

    /*method to create firestore collection and documents for the listings--------------------------
    * */
    public void publishBusiness()
    {
        //get widgets items
        String varPK=tvVarEmail.getText().toString();
        String varName=bName.getEditText().getText().toString();
        String varPhone=bPhone.getEditText().getText().toString();
        String varContact=bContactPerson.getEditText().getText().toString();
        String varLocation=bLocation.getEditText().getText().toString();

        //validate user entries---------------------------------------------------------------------
        if (varName.isEmpty()){
            bName.setError("Name is required");
            bName.requestFocus();
            return;
        }else if (varPhone.isEmpty()){
            bPhone.setError("Phone is required");
            bPhone.requestFocus();
            return;
        }else if(varContact.isEmpty()){
            bContactPerson.setError("Contact person is required");
            bContactPerson.requestFocus();
            return;
        }else if (varLocation.isEmpty()){
            bLocation.setError("Location is required");
            bLocation.requestFocus();
            return;
        }else{
            progressBarAddBiz.setVisibility(View.VISIBLE);
            //Call Listing class to assign the variables
            Listings userListings=new Listings(varPK,varName,varPhone,varContact,varLocation,GloabalVariables.varUID);
            //set firestore collection/document path in firestoreDB
            firestoreDB.collection("Listings").document(varPK)
                    .set(userListings)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressBarAddBiz.setVisibility(View.GONE);
                            Toast.makeText(AddBusinessActivity.this, "Business added successfully", Toast.LENGTH_SHORT).show();
                            //clear input fields and disables UI button to prevent data redundancy
                            bName.getEditText().setText("");
                            bPhone.getEditText().setText("");
                            bContactPerson.getEditText().setText("");
                            bLocation.getEditText().setText("");
                            btnPublish.setEnabled(false);
                            //--cl ends here--------------------------------------------------------
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBarAddBiz.setVisibility(View.GONE);
                    Toast.makeText(AddBusinessActivity.this, "Sorry,error adding business!!", Toast.LENGTH_SHORT).show();
                    bName.getEditText().setText("");
                    bPhone.getEditText().setText("");
                    bContactPerson.getEditText().setText("");
                    bLocation.getEditText().setText("");
                    bName.requestFocus();//cl input fields and set focus in bName field-------------
                }
            });

        }

    }
    //----ends here---------------------------------------------------------------------------------


}
