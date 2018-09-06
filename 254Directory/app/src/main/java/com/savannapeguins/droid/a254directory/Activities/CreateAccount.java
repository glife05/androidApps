package com.savannapeguins.droid.a254directory.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.savannapeguins.droid.a254directory.R;

public class CreateAccount extends AppCompatActivity implements View.OnClickListener{
    //UI widgets variables-----------------------------------------
    private TextInputLayout lEmail,lPassword;
    private TextView tvCreateLink;
    private Button bLogin,bCancel;
    private ProgressBar progressBar;
    //UI widgets variables ends here---------------------------------

    //createAccount CONSTANTS
    private static final String TAG="CreateAccount";
    public static final String EDIT_TEXT_EMAIL="EmailAddress";
    //Reference to Firestore
    private FirebaseAuth mAuth;
    FirebaseFirestore firestoreDB=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        lEmail=findViewById(R.id.ctext_email_address);
        lPassword=findViewById(R.id.ctext_password);
        tvCreateLink=findViewById(R.id.tvLoginLink);
        bLogin=(Button)findViewById(R.id.buttonRegister);
        bCancel=(Button)findViewById(R.id.buttonCancel);
        progressBar=(ProgressBar)findViewById(R.id.progressbar1);

        mAuth=FirebaseAuth.getInstance();

        bLogin.setOnClickListener(this);
        bCancel.setOnClickListener(this);


    }

    //======method to create account================================================================
    private void createAccount()
    {
        final String varEmail=lEmail.getEditText().getText().toString();
        String varPassword=lPassword.getEditText().getText().toString();

        if (varEmail.isEmpty())
        {
            lEmail.setError("Email is required");
            lEmail.requestFocus();
            return;

        }else if(!Patterns.EMAIL_ADDRESS.matcher(varEmail).matches()){
            lEmail.setError("Please enter a valid email");
            lEmail.requestFocus();
            return;

        }else if(varPassword.isEmpty()){
            lPassword.setError("Password is required");
            lPassword.requestFocus();
            return;
        }else
            {

            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(varEmail,varPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                      if(task.isSuccessful())
                      {
                          Log.d(TAG, "onComplete: User created successfully");
                          Toast.makeText(CreateAccount.this, "User created successfully", Toast.LENGTH_SHORT).show();
                         //clears editText fields===================================================
                          lEmail.getEditText().setText("");
                          lPassword.getEditText().setText("");
                          lEmail.requestFocus();
                         //=========================================================================
                          startActivity(new Intent(CreateAccount.this,LoginActivity.class));
                      }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: Error creating user");
                    Toast.makeText(CreateAccount.this, "Error creating user,try again later!!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    //=====create account ends here=================================================================
    @Override
    public void onClick(View v) {
        switch (v.getId())
            {
                case R.id.buttonRegister:
                    //ToDo creates new firestore accounts
                    createAccount();
                    break;
                case R.id.buttonCancel:
                    //ToDo: clears text fields and terminates registration

                    break;
            }
    }
}
