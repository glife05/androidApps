package com.savannapeguins.droid.placefinder.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.savannapeguins.droid.placefinder.R;


import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateAccountFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateAccountFragment extends Fragment {
    /*Firestore code starts here*/
  private static final String TAG = "CreateAccountFragment";

//    //Firestore vars
    private static final String KEY_FNAME="first_name";
    private static final String KEY_LNAME="last_name";
    private static final String KEY_PASSWORD="password";
    private static final String KEY_EMIAL="email_address";
    private static final String KEY_TEST="test";

   private TextInputLayout textInputFirstName,textInputLastName,textInputPassword,textInputEmailAddress;
   private TextView tvLogin;
   private Button mCreateButton;
//    //Reference to Firestore
   FirebaseFirestore db=FirebaseFirestore.getInstance();
   private FirebaseAuth mAuth;

    /*ENDS HERE*/
    
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CreateAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateAccountFragment newInstance(String param1, String param2) {
        CreateAccountFragment fragment = new CreateAccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_create_account, container, false);
        textInputFirstName = rootView.findViewById(R.id.text_first_name);
        textInputLastName = rootView.findViewById(R.id.text_last_name);
        textInputEmailAddress = rootView.findViewById(R.id.text_email);
        textInputPassword = rootView.findViewById(R.id.text_password_ac);
        mCreateButton = rootView.findViewById(R.id.createAccount);
        tvLogin=(TextView) rootView.findViewById(R.id.tv_login);
        mAuth= FirebaseAuth.getInstance();
      //firestore data capture----------------------------------------------------------------------

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createFirebaseUser();

                String first_name = textInputFirstName.getEditText().getText().toString();
                String last_name = textInputLastName.getEditText().getText().toString();
                String email = textInputEmailAddress.getEditText().getText().toString();
                String password = textInputPassword.getEditText().getText().toString();

                if (first_name.isEmpty()) {
                    textInputFirstName.setError("First name required");
                    textInputFirstName.requestFocus();
                } else if (last_name.isEmpty()) {
                    textInputLastName.setError("Last name is required");
                    textInputLastName.requestFocus();
                } else if (email.isEmpty()) {
                    textInputEmailAddress.setError("Email address required");
                    textInputEmailAddress.requestFocus();
                } else if (password.isEmpty()) {
                    textInputPassword.setError("Password required");
                    textInputPassword.requestFocus();
                } else {

                    Map<String, Object> user = new HashMap<>();
                    user.put(KEY_FNAME, first_name);
                    user.put(KEY_LNAME, last_name);
                    user.put(KEY_EMIAL, email);
                    user.put(KEY_PASSWORD, password);
                    db.collection("Users")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    //Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                                    createFirebaseUser();
                                    Log.d(TAG, "onSuccess: DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onFailure: Error adding document ", e);
                                }
                            });
                    //Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();

                }//close if statement----------------------------------------------------------------------
            }
        });



        //firestore data capture ends here-----------------------------------------------------------------


        //tvLoginScreen starts here*************************************************************************

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToDo:open login screen
              //  startActivity(new Intent(getActivity(),LoginFragment.class));

            }
        });
        //ends here tvLogin*********************************************************************************
      return rootView;
    }
    //create new Firebase user using email verification************************************
    public void createFirebaseUser()
    {
        final String email = textInputEmailAddress.getEditText().getText().toString();
        String password = textInputPassword.getEditText().getText().toString();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(getContext(), "User created successfully,go to login", Toast.LENGTH_LONG).show();

                }
           }
       });
    }

    //create new firebase user using email verification ends here*********************************************

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

    }



}
