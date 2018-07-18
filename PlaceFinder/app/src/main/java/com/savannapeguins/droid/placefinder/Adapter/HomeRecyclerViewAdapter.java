package com.savannapeguins.droid.placefinder.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.savannapeguins.droid.placefinder.Model.Listings;
import com.savannapeguins.droid.placefinder.R;

import java.util.List;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {
   private List<Listings>homeList;
   private Context context;
   private FirebaseFirestore firestoreDB;

    public HomeRecyclerViewAdapter(List<Listings> homeList, Context context,FirebaseFirestore firestoreDB) {
        this.homeList = homeList;
        this.context = context;
        this.firestoreDB=firestoreDB;
    }

    @NonNull
    @Override
    public HomeRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecyclerViewAdapter.ViewHolder holder, int position) {
        Listings listings=homeList.get(position);

        holder.tvBusinessrw.setText(listings.getBiz_name());
        holder.tvCatrw.setText(listings.getNew_spinner());
        holder.tvConrw.setText(listings.getBiz_phone());
    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       private TextView tvBusinessrw,tvCatrw,tvConrw;
        public ViewHolder(View itemView) {
            super(itemView);
            tvBusinessrw=(TextView)itemView.findViewById(R.id.rwBusinessName);
            tvCatrw=(TextView)itemView.findViewById(R.id.rwCategory);
            tvConrw=(TextView)itemView.findViewById(R.id.rwContact);
        }
    }
}
