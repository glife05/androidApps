package com.savannapeguins.droid.rwjson;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewPage extends RecyclerView.Adapter<RecyclerViewPage.ViewHolder> {
    private Context context;
    private List<Forcasts>forcastsList;

    public RecyclerViewPage(Context context, List<Forcasts> forcastsList) {
        this.context = context;
        this.forcastsList = forcastsList;
    }

    @NonNull
    @Override
    public RecyclerViewPage.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_page,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewPage.ViewHolder holder, int position) {
    Forcasts forcasts=forcastsList.get(position);

    holder.tvDate.setText(forcasts.varDate);
    holder.tvDay.setText(forcasts.varDay);
//    holder.tvCount.setText(forcasts.count);
    }

    @Override
    public int getItemCount() {
        return forcastsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDay,tvDate,tvCount,tvHeader;
        public ViewHolder(View itemView) {
            super(itemView);
            tvDate=(TextView)itemView.findViewById(R.id.tv_date_view);
            tvDay=(TextView)itemView.findViewById(R.id.tv_day_view);
            tvCount=(TextView)itemView.findViewById(R.id.tv_count);
            
        }
    }
}
