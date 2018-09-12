package com.savannapeguins.droid.a254directory.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.savannapeguins.droid.a254directory.Activities.UpdateActivity;
import com.savannapeguins.droid.a254directory.Activities.DetailsActivity;
import com.savannapeguins.droid.a254directory.Model.Listings;
import com.savannapeguins.droid.a254directory.Model.Users;
import com.savannapeguins.droid.a254directory.R;

import java.util.List;

public class RecyclerViewProfilePage extends RecyclerView.Adapter<RecyclerViewProfilePage.ViewHolder> {
    private static final String TAG_REC="RecycleViewTAG";
    private List<Listings>listingsList;
    private Context context;
    private FirebaseFirestore firestoreDB;
     //variables for putExtra
    public static final String TEXT_EMAIL="EmailAddress";
    public static final String TEXT_BUSINESS="BusinessName";
    public static final String TEXT_LOCATION="BusinessLocation";
    public static final String TEXT_PHONE="BusinessPhone";
    public static final String TEXT_CONTACT="BusinessContact";
    public RecyclerViewProfilePage(List<Listings> listingsList, Context context, FirebaseFirestore firestoreDB) {
        this.listingsList = listingsList;
        this.context = context;
        this.firestoreDB = firestoreDB;
    }

    //inflates the Row_profile_page xml------------------------------------------------------------------------------------
    @NonNull
    @Override
    public RecyclerViewProfilePage.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.row_profile_page,parent,false);
        return new ViewHolder(view,context);
    }
    //---------ends inflating row_profile_page xml--------------------------------------------------------------------------

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewProfilePage.ViewHolder holder, int position) {
        Listings listings=listingsList.get(position);

        holder.varTvEmail.setText(listings.getVarEmail());
        holder.varTvBizName.setText(listings.getVarName());
        holder.varTvPhone.setText(listings.getVarPhone());
        holder.varTvContact.setText(listings.getVarContactPerson());
        holder.varTvLocation.setText(listings.getVarLocation());
    }

    @Override
    public int getItemCount() {
        return listingsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        /*UI widgets from row_profile_page xml<<it will be populated in the ProfilePage
        * */
        private TextView varTvEmail,varTvBizName,varTvContact,varTvPhone,varTvLocation;
        private ImageView imDelete,imEdit;
        public ViewHolder(View itemView,Context ctx) {
            super(itemView);
            context=ctx;
            //Reference the UI widgets
            varTvEmail=(TextView)itemView.findViewById(R.id.tvEmailProfilePage);
            varTvBizName=(TextView)itemView.findViewById(R.id.tvBusinessNameProfilePage);
            varTvContact=(TextView)itemView.findViewById(R.id.tvContactPersonProfilePage);
            varTvPhone=(TextView)itemView.findViewById(R.id.tvPhoneNumberProfilepage);
            varTvLocation=(TextView)itemView.findViewById(R.id.tvLocationProfilePage);
            imDelete=(ImageView)itemView.findViewById(R.id.imViewDelete);
            imEdit=(ImageView)itemView.findViewById(R.id.imViewEdit);

            imEdit.setOnClickListener(this);
            imDelete.setOnClickListener(this);

        }


     //ImageView click events starts here**********************************************************
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.imViewDelete:

                    String varBizNameDetails=varTvBizName.getText().toString();
                    String varEmailDetails=varTvEmail.getText().toString();
                    Intent intentDetailsActivity=new Intent(context,DetailsActivity.class);
                    intentDetailsActivity.putExtra(TEXT_BUSINESS,varBizNameDetails);
                    intentDetailsActivity.putExtra(TEXT_EMAIL,varEmailDetails);
                    context.startActivity(intentDetailsActivity);
                    break;

                case R.id.imViewEdit:
                    String bizNameDetails=varTvBizName.getText().toString();
                    String bizEmailDetails=varTvEmail.getText().toString();
                    String bizContactDetails=varTvContact.getText().toString();
                    String bizPhoneDetails=varTvPhone.getText().toString();
                    String bizLocationDetails=varTvLocation.getText().toString();
                    //starts AddBusinessActivity for editing purposes*******************************
                    Intent intentEditDetails=new Intent(context,UpdateActivity.class);
                    intentEditDetails.putExtra(TEXT_BUSINESS,bizNameDetails);
                    intentEditDetails.putExtra(TEXT_EMAIL,bizEmailDetails);
                    intentEditDetails.putExtra(TEXT_CONTACT,bizContactDetails);
                    intentEditDetails.putExtra(TEXT_PHONE,bizPhoneDetails);
                    intentEditDetails.putExtra(TEXT_LOCATION,bizLocationDetails);
                    context.startActivity(intentEditDetails);
                    //end here***********************************************************************

                 break;
            }
        }
        //**ends************************************************************************************


    }


}
