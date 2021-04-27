package com.example.eventify;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventRegVH extends RecyclerView.ViewHolder{
    TextView name,rollno,year,branch;

    public EventRegVH(@NonNull View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.emp_nameEnabled);
        rollno=itemView.findViewById(R.id.emp_postEnabled);
        year=itemView.findViewById(R.id.emp_tempEnabled);
        branch=itemView.findViewById(R.id.emp_status);
    }
}
