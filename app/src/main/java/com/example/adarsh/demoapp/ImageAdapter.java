package com.example.adarsh.demoapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by adaRSH on 27-Apr-17.
 */

class ImageAdapter extends RecyclerView.Adapter<viewHolder> {
    List<ImageList> list;
    Context context;
    public ImageAdapter(Context context, List<ImageList> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_info, parent, false);
        Log.d("image","view created");
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {

        holder.nameView.setText(list.get(position).getName());
        Picasso.with(context).load(list.get(position).getImagePath()).placeholder(R.drawable.loading).into(holder.imageDetail);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,DetailActivity.class);
                Log.d("image","url:"+list.get(position).getImagePath());
                i.putExtra("url",list.get(position).getImagePath());
                i.putExtra("name",list.get(position).getName());
                i.putExtra("id",list.get(position).getPid());
                i.putExtra("desc",list.get(position).getDescription());
                i.putExtra("date",list.get(position).getTimeDate());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
