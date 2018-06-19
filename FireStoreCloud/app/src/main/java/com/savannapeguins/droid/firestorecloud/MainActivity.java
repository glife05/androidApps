package com.savannapeguins.droid.firestorecloud;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final String KEY_TITLE="title";
    private static final String KEY_DESCRIPTION="description";

    private EditText edTitle,edDesc;

    private FirebaseFirestore db=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edTitle=(EditText)findViewById(R.id.ed_title);
        edDesc=(EditText)findViewById(R.id.ed_description);

    }

    public void saveNote(View v)
    {
        String title=edTitle.getText().toString();
        String description=edDesc.getText().toString();

        Map<String,Object>note=new HashMap<>();
        note.put(KEY_TITLE,title);
        note.put(KEY_DESCRIPTION,description);

        //Add a new document with a generated ID
        db.collection("users")
                .add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this, "Note saved", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onSuccess: Document add with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error!!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: Error addind document",e);
                    }
                });
    }
}
