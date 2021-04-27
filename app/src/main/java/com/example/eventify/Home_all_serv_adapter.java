package com.example.eventify;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

public class Home_all_serv_adapter extends FirestoreRecyclerAdapter<Home_all_services, Home_all_serv_adapter.MyViewHolder> {

   Context mContext;
   public static List<Home_all_services> mData;

    public Home_all_serv_adapter(@NonNull FirestoreRecyclerOptions<Home_all_services> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Home_all_services model) {
        holder.service_title.setText(model.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext.getApplicationContext(),SocietyDetails.class);
                intent.putExtra("title",model.getTitle());
                mContext.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_services_cv_item, parent, false);
        Home_all_serv_adapter.MyViewHolder holder = new Home_all_serv_adapter.MyViewHolder(view);
        return holder;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView service_title;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            service_title = (TextView) itemView.findViewById(R.id.all_services_item_title);
        }

    }

}
