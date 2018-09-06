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
    //Declare variables for the confirmation dialog to delete items from that database
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private LayoutInflater conLayoutInflater;
    //ends here alertDialog
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
        //****starts*******************************************************************************
        public void confirmationDialog(Context c){
            //creates and AlertDialog
            alertDialogBuilder=new AlertDialog.Builder(c);
            conLayoutInflater=LayoutInflater.from(c);
            View view=conLayoutInflater.inflate(R.layout.confirmation_dialog,null);

            Button noButton=(Button)view.findViewById(R.id.btnNo);
            Button yesButton=(Button)view.findViewById(R.id.btnYes);

            alertDialogBuilder.setView(view);
            dialog=alertDialogBuilder.create();
            dialog.show();


        }



        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.imViewDelete:
                    Intent intentDetailsActivity=new Intent(context,DetailsActivity.class);
                    //intentAddBusiness.putExtra(EDITEXT_EMAIL_PROFILE_PAGE,varEM);
                    context.startActivity(intentDetailsActivity);
                    break;

                case R.id.imViewEdit:

                    break;
            }
        }
        //**ends************************************************************************************


    }


}
