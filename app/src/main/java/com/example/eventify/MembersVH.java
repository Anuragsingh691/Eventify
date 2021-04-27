package com.example.eventify;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MembersVH extends RecyclerView.ViewHolder{

    public TextView Mname,Mpost,Mphone;

    public MembersVH(@NonNull View itemView) {
        super(itemView);
        Mname=itemView.findViewById(R.id.emp_name);
        Mpost=itemView.findViewById(R.id.emp_post);
        Mphone=itemView.findViewById(R.id.emp_temp);
    }
}
