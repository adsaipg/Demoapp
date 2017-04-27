package com.example.adarsh.demoapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by adaRSH on 27-Apr-17.
 */

public class viewHolder extends RecyclerView.ViewHolder {

    TextView nameView;
    ImageView imageDetail;
    public viewHolder(View itemView) {
        super(itemView);
        imageDetail= (ImageView) itemView.findViewById(R.id.imageDetail);
        nameView= (TextView) itemView.findViewById(R.id.nameDetail);
    }
}
