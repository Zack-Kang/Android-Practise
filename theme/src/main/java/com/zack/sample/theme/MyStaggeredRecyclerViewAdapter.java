package com.zack.sample.theme;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: zhoukang
 * Time: 2017/9/25.
 * Description:
 */

public class MyStaggeredRecyclerViewAdapter extends RecyclerView.Adapter<MyStaggeredRecyclerViewAdapter.MyViewHolder> {
    List<String> list;
    List<Integer> heights;
    public MyStaggeredRecyclerViewAdapter(List<String> list){
        this.list = list;
        heights = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            heights.add((int) (200+Math.random()*50));
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       // LayoutInflater inflater = LayoutInflater.from(parent.getContext());
       // MyViewHolder viewHolder = new MyViewHolder(inflater.inflate(android.R.layout.simple_list_item_1,parent,false));
        MyViewHolder viewHolder = new MyViewHolder(View.inflate(parent.getContext(),R.layout.list_item,null));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ViewGroup.LayoutParams params = holder.tvContent.getLayoutParams();
        params.height = heights.get(position);
        holder.tvContent.setLayoutParams(params);
        holder.tvContent.setBackgroundColor(Color.rgb(100, heights.get(position), heights.get(position)-4));
        holder.tvContent.setText(list.get(position));
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
        }
    }
}
