package com.zack.sample.theme;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Author: zhoukang
 * Time: 2017/9/25.
 * Description:
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    List<String> list;
    public MyRecyclerViewAdapter(List<String> list){
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(View.inflate(parent.getContext(),android.R.layout.simple_list_item_1,null));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvContent.setText(list.get(position));
        holder.tvContent.setBackgroundColor( holder.tvContent.getResources().getColor(android.R.color.darker_gray));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvContent;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(android.R.id.text1);
            itemView.setBackgroundColor(tvContent.getContext().getResources().getColor(R.color.colorPrimary));
        }
    }
}
