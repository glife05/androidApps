package com.savannapeguins.droid.placefinder.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.savannapeguins.droid.placefinder.Model.Listings;
import com.savannapeguins.droid.placefinder.ProfilePageActivity;
import com.savannapeguins.droid.placefinder.R;

import java.util.List;

public class AccountRecyclerViewAdapter extends RecyclerView.Adapter<AccountRecyclerViewAdapter.ViewHolder> {
    private List<Listings>accountLists;
    private Context context;
    private FirebaseFirestore firestoreDB;

    public AccountRecyclerViewAdapter(List<Listings> accountLists, Context context, FirebaseFirestore firestoreDB) {
        this.accountLists = accountLists;
        this.context = context;
        this.firestoreDB = firestoreDB;
    }

    @NonNull
    @Override
    public AccountRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_account,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountRecyclerViewAdapter.ViewHolder holder, int position) {
        Listings listings=accountLists.get(position);

        holder.tvBusinessNameRW.setText(listings.getBiz_name());
        holder.tvContactPhoneRW.setText(listings.getBiz_contact());
        holder.spBizCatRW.getSelectedItem();
    }

    @Override
    public int getItemCount() {
        return accountLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvBusinessNameRW,tvContactPhoneRW;
        private Spinner spBizCatRW;
        public Button editButton,deleteButton;
        public ViewHolder(View itemView) {
            super(itemView);
            tvBusinessNameRW=(itemView).findViewById(R.id.tv_business_name_ac);
            tvContactPhoneRW=(itemView).findViewById(R.id.tv_contact_phone_ac);
            spBizCatRW=(itemView).findViewById(R.id.spinner);

            editButton=(itemView).findViewById(R.id.editButton);
            deleteButton=(itemView).findViewById(R.id.deleteButton);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.editButton:
                    //ToDo:edit firestore listing records
                    Toast.makeText(context, "Edit button clicked", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.deleteButton:
                    //ToDo: delete records from firestore
                    Toast.makeText(context, "Delete button clicked", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
