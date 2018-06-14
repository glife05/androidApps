package com.savannapeguins.droid.placefinder.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

   private TextInputLayout textInputFirstName,textInputLastName,textInputPassword,textInputEmailAddress;
   private Button mCreateButton;
//    //Reference to Firestore
   FirebaseFirestore db=FirebaseFirestore.getInstance();

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
                             Bundle savedInstanceState)
    {

      View rootView= inflater.inflate(R.layout.fragment_create_account, container, false);
      textInputFirstName =rootView.findViewById(R.id.text_first_name);
      textInputLastName=rootView.findViewById(R.id.text_last_name);
      textInputEmailAddress=rootView.findViewById(R.id.text_email);
      textInputPassword=rootView.findViewById(R.id.text_password_ac);
      mCreateButton=rootView.findViewById(R.id.createAccount);

      mCreateButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              createAccountButton(v);
          }
      });

      return rootView;
    }

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


    //FragmentActions
    public void createAccountButton(View v)
    {
        String firstName,lastName,email,password;
        firstName=textInputFirstName.getEditText().getText().toString();
        lastName=textInputLastName.getEditText().getText().toString();
        email=textInputEmailAddress.getEditText().getText().toString();
        password=textInputPassword.getEditText().getText().toString();

        Map<String,Object>users=new HashMap<>();
        users.put(KEY_FNAME,firstName);
        users.put(KEY_LNAME,lastName);
        users.put(KEY_EMIAL,email);
        users.put(KEY_PASSWORD,password);

        //pass to firebase
        //create collections followed by document
       // FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("Users").document("Users Account").set(users)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "User created", Toast.LENGTH_SHORT).show();
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });


   }
}
