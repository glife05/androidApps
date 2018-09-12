package com.savannapeguins.droid.a254directory.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.savannapeguins.droid.a254directory.GlobalVariable.GloabalVariables;
import com.savannapeguins.droid.a254directory.R;

import org.w3c.dom.Text;

import static com.savannapeguins.droid.a254directory.Adapter.RecyclerViewProfilePage.TEXT_BUSINESS;
import static com.savannapeguins.droid.a254directory.Adapter.RecyclerViewProfilePage.TEXT_EMAIL;


public class DetailsActivity extends AppCompatActivity {
    private static final String TAG ="DetailsActivity" ;
    //Details activity widgets
    private TextView varBizDetails,varEmailDetails;
    private ImageView deleteImg;
    //Dialog widgets
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialogBox;
    private LayoutInflater inflateDialogBox;

    //database variable
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        varBizDetails=(TextView)findViewById(R.id.tv_BizDetails);
        varEmailDetails=(TextView)findViewById(R.id.tv_EmailDetails);
        deleteImg=(ImageView)findViewById(R.id.imViewDeleteDetails);


        //Used to fetch textview items from RecyclerViewProfilePage varBizName**********************
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            varBizDetails.setText(bundle.getString(TEXT_BUSINESS));
            varEmailDetails.setText(bundle.getString(TEXT_EMAIL));
        }
        //ends here*********************************************************************************

        deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToDo:calls method createDialog
                createDialogBox();
            }
        });

    }

    //Creates dialogbox method**********************************************************************
    public void createDialogBox(){
       alertDialogBuilder=new AlertDialog.Builder(DetailsActivity.this);
       inflateDialogBox=LayoutInflater.from(DetailsActivity.this);
       View view=inflateDialogBox.inflate(R.layout.confirmation_dialog,null);

       //Confirmation box widgets
        Button noButton=(Button)view.findViewById(R.id.btnNo);
        Button yesButton=(Button)view.findViewById(R.id.btnYes);

        alertDialogBuilder.setView(view);
        dialogBox=alertDialogBuilder.create();
        dialogBox.show();

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToDo; cancels deleting
                dialogBox.dismiss();
            }
        });


        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToDo: deletes business from database
                db.collection("Listings").document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                  .delete()
                  .addOnSuccessListener(new OnSuccessListener<Void>() {
                      @Override
                      public void onSuccess(Void aVoid) {
                          Log.d(TAG, "onSuccess: DocumentSnapshot successfully deleted");
                          Toast.makeText(DetailsActivity.this, "Businsess deleted successfully", Toast.LENGTH_SHORT).show();
                      }
                  }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Error deleting record");
                        Toast.makeText(DetailsActivity.this, "Problem deleting,try again later", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    //*****ENDS HERE********************************************************************************
}
