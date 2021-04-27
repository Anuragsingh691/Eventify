package com.example.eventify;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventsVH extends RecyclerView.ViewHolder{
    public TextView Ename,Edate,Evenue;
    public ImageView Eimg;

    public EventsVH(@NonNull View itemView) {
        super(itemView);
        Ename=itemView.findViewById(R.id.card_title);
        Edate=itemView.findViewById(R.id.card_dist);
        Evenue=itemView.findViewById(R.id.card_people);
        Eimg=itemView.findViewById(R.id.cardimg);
    }
}
