package com.savannapeguins.droid.a254directory.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.savannapeguins.droid.a254directory.R;

import java.util.HashMap;
import java.util.Map;

import static com.savannapeguins.droid.a254directory.Adapter.RecyclerViewProfilePage.TEXT_BUSINESS;
import static com.savannapeguins.droid.a254directory.Adapter.RecyclerViewProfilePage.TEXT_CONTACT;
import static com.savannapeguins.droid.a254directory.Adapter.RecyclerViewProfilePage.TEXT_EMAIL;
import static com.savannapeguins.droid.a254directory.Adapter.RecyclerViewProfilePage.TEXT_LOCATION;
import static com.savannapeguins.droid.a254directory.Adapter.RecyclerViewProfilePage.TEXT_PHONE;

public class UpdateActivity extends AppCompatActivity {
    private static final String TAG = "UpdateActivity";
    //Update activity widgets
    private EditText upBizName, upPhone, upContact, upLocation;
    private TextView tvUpEmail;
    private Button bUpdate;
    //firestore database reference
    private FirebaseFirestore firestoreDB;
    //CONSTANTS for HASHMAPS
    private static final String UP_EMAIL = "varEmail";
    private static final String UP_BNAME = "varName";
    private static final String UP_PHONE = "varPhone";
    private static final String UP_CONTACT = "varContactPerson";
    private static final String UP_LOCATION = "varLocation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.savannapeguins.droid.a254directory.R.layout.activity_update);
        tvUpEmail = (TextView) findViewById(R.id.tvEmailUpdatePge);
        upBizName = findViewById(R.id.edUpdateBizName);
        upPhone = findViewById(R.id.edUpdatePhoneNumber);
        upContact = findViewById(R.id.edUpdateContactPerson);
        upLocation = findViewById(R.id.edUpdateLocation);
        bUpdate = findViewById(R.id.buttonUpdate);
        firestoreDB = FirebaseFirestore.getInstance();//db reference
        //variable to fetch view items from recyclerViewProfile Page--------------------------------
        Bundle bundle = getIntent().getExtras();
        //check if is empty
        if (bundle != null) {
            //set given values
            tvUpEmail.setText(bundle.getString(TEXT_EMAIL));
            upBizName.setText(bundle.getString(TEXT_BUSINESS));
            upPhone.setText(bundle.getString(TEXT_PHONE));
            upContact.setText(bundle.getString(TEXT_CONTACT));
            upLocation.setText(bundle.getString(TEXT_LOCATION));
        }
        //ends here*********************************************************************************

        //******button update click*****************************************************************
        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRecord();
            }
        });

        //****ends here button update***************************************************************


    }

    //******method to update firestore**************************************************************
    public void updateRecord() {
        //variable declarations
        String new_email = tvUpEmail.getText().toString();
        String new_biz = upBizName.getText().toString();
        String new_phone = upPhone.getText().toString();
        String new_contact = upContact.getText().toString();
        String new_location = upLocation.getText().toString();
        //validate entries
        if (new_biz.isEmpty()) {
            upBizName.setError("Business name is required");
            upBizName.requestFocus();
            return;
        } else if (new_phone.isEmpty()) {
            upPhone.setError("Phone number is required");
            upPhone.requestFocus();
            return;
        } else if (new_contact.isEmpty()) {
            upContact.setError("Contact person is required");
            upContact.requestFocus();
            return;
        } else if (new_location.isEmpty()) {
            upLocation.setError("Location is required");
            upLocation.requestFocus();
            return;
        }else if (new_email.isEmpty()){
            tvUpEmail.setError("Email is required");

        } else {
            Map<String, Object> updateTaskMap = new HashMap<>();
            updateTaskMap.put(UP_EMAIL, new_email);
            updateTaskMap.put(UP_BNAME, new_biz);
            updateTaskMap.put(UP_PHONE, new_phone);
            updateTaskMap.put(UP_CONTACT, new_contact);
            updateTaskMap.put(UP_LOCATION, new_location);
            firestoreDB.collection("Listings").document(new_email)
                    .update(updateTaskMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //TODO:update successful
                            Toast.makeText(UpdateActivity.this, "Record updated successfully", Toast.LENGTH_SHORT).show();
                            upBizName.setText("");
                            upContact.setText("");
                            upLocation.setText("");
                            upPhone.setText("");
                            tvUpEmail.setText("");
                            upBizName.requestFocus();
                            startActivity(new Intent(UpdateActivity.this, MainPage.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //ToDO:update failure
                    Log.d(TAG, "onFailure: failure to update database");
                    Toast.makeText(UpdateActivity.this, "Failure to update record", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    //***ends update method*************************************************************************

    //******ends activity on back press*************************************************************
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }
    //****ends here*********************************************************************************
}
