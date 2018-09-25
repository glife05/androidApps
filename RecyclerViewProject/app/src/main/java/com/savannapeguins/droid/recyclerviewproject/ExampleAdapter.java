package com.savannapeguins.droid.recyclerviewproject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
private ArrayList<ExampleItem>mExampleList;

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,null,false);
        ExampleViewHolder evh=new ExampleViewHolder(view);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
    ExampleItem currentItem=mExampleList.get(position);

    holder.mImageView.setImageResource(currentItem.getmImageResource());
    holder.mTextView1.setText(currentItem.getmText1());
    holder.mTextView2.setText(currentItem.getmText2());
    }

  public ExampleAdapter(ArrayList<ExampleItem>exampleList){
        mExampleList=exampleList;

  }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1,mTextView2;
        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView=itemView.findViewById(R.id.imageView);
            mTextView1=itemView.findViewById(R.id.text_view_1);
            mTextView2=itemView.findViewById(R.id.text_view_2);
        }
    }
}
