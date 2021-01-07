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

import java.util.List;

public class Home_all_serv_adapter extends RecyclerView.Adapter<com.example.eventify.Home_all_serv_adapter.MyViewHolder> {

   Context mContext;
   public static List<Home_all_services> mData;

    public Home_all_serv_adapter(Context mContext, List<Home_all_services> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mlayoutinflater= LayoutInflater.from(mContext);
        view=mlayoutinflater.inflate(R.layout.all_services_cv_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.service_title.setText(mData.get(position).getTitle());
        holder.service_thumbnail.setImageResource(mData.get(position).getThumbnail());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(mContext,SocietyDetails.class);
                intent.putExtra("title",mData.get(position).getTitle());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
            return mData.size();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView service_title;
        ImageView service_thumbnail;
        CardView cardView ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            service_title = (TextView) itemView.findViewById(R.id.all_services_item_title);
            service_thumbnail=(ImageView) itemView.findViewById(R.id.all_services_item_img);
            cardView=(CardView)itemView.findViewById(R.id.all_services_card);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });

        }

    }

}
