package com.savannapeguins.droid.a254directory.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.savannapeguins.droid.a254directory.Model.Listings;
import com.savannapeguins.droid.a254directory.R;

import java.util.List;

public class RecyclerViewMainPage extends RecyclerView.Adapter<RecyclerViewMainPage.ViewHolder>{
    private List<Listings>listingsMainPage;
    private Context contextMainPage;
    private FirebaseFirestore firestoreMainPageDb;

    public RecyclerViewMainPage(List<Listings> listingsMainPage, Context contextMainPage, FirebaseFirestore firestoreMainPageDb) {
        this.listingsMainPage = listingsMainPage;
        this.contextMainPage = contextMainPage;
        this.firestoreMainPageDb = firestoreMainPageDb;
    }

    //------inflates row_main_page_xml-------------------------------------------------------------------------------------
    @NonNull
    @Override
    public RecyclerViewMainPage.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_main_page,parent,false);
        return new ViewHolder(view);
    }
    //-------inflater row_main_page_xml ends here--------------------------------------------------------------------------

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewMainPage.ViewHolder holder, int position) {
        Listings listings=listingsMainPage.get(position);

        holder.rwBusinessName.setText(listings.getVarName());
        holder.rwPhone.setText(listings.getVarPhone());
        holder.rwContactPerson.setText(listings.getVarContactPerson());
    }

    @Override
    public int getItemCount() {
        return listingsMainPage.size();
    }

    //declares row_main_page xml widgets
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView rwBusinessName,rwPhone,rwContactPerson;
        public ViewHolder(View itemView) {
            super(itemView);
            rwBusinessName=(TextView)itemView.findViewById(R.id.bizNameMainPageTag);
            rwPhone=(TextView)itemView.findViewById(R.id.bizContactMainPageTag);
            rwContactPerson=(TextView)itemView.findViewById(R.id.biz_contact_person_tag);
        }
    }
}
