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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.savannapeguins.droid.a254directory.GlobalVariable.GloabalVariables;
import com.savannapeguins.droid.a254directory.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvSignUpLink;
    private Button lButton;
    private TextInputLayout mEmail, mPassword;//**ends GUI variables

    //**LOGIN ACTIVITY CONSTANTS
    private static final String TAG = "LoginActivity";
    public static final String LOGIN_EMAIL_EDITTEXT = "EmailAddress";

    //***DECLARES firestore variables
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvSignUpLink = (TextView) findViewById(R.id.tv_sign_up);
        lButton = (Button) findViewById(R.id.button_login);
        mEmail = findViewById(R.id.text_email_address);
        mPassword = findViewById(R.id.text_password);
        mAuth=FirebaseAuth.getInstance();

        tvSignUpLink.setOnClickListener(this);
        lButton.setOnClickListener(this);
    }
    //method holds user email Address>>sends to AddBusinessActivity--------------------------------
    public void sendEmailAddress(){
        GloabalVariables.emailInfo=mEmail.getEditText().getText().toString();
    }
    //ends here------------------------------------------------------------------------------------

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sign_up:
                //ToDo starts create account activity
                startActivity(new Intent(LoginActivity.this, CreateAccount.class));
                break;
            case R.id.button_login:
                //ToDo login user to mainpage
                loginUser();
                sendEmailAddress();//<<refer to this method
                break;
        }
    }

    //method to login user to main page=============================================================
    private void loginUser() {
        String varEmail = mEmail.getEditText().getText().toString();
        String varPass = mPassword.getEditText().getText().toString();
        //validate entries
        if (!Patterns.EMAIL_ADDRESS.matcher(varEmail).matches()) {
            mEmail.setError("Enter a valid email to continue");
            mEmail.requestFocus();
            return;
        } else if (varEmail.isEmpty()) {
            mEmail.setError("Email is required");
            mEmail.requestFocus();
            return;

        } else if (varPass.isEmpty()) {
            mPassword.setError("Password is required");
            mPassword.requestFocus();
            return;

        } else {
            //login user
            mAuth.signInWithEmailAndPassword(varEmail,varPass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "onComplete: signInWithEmail:success ");
                            mEmail=findViewById(R.id.text_email_address);
                            String emailText=mEmail.getEditText().getText().toString();
                            Intent intent=new Intent(LoginActivity.this,MainPage.class);
                            intent.putExtra(LOGIN_EMAIL_EDITTEXT,emailText);
                            startActivity(intent);//starts MainPage activity
                            finish();//kills the activity after login
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: signInWithEmail,failure");
                    Toast.makeText(LoginActivity.this, "Error signing...", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    //method ends here==============================================================================
}
