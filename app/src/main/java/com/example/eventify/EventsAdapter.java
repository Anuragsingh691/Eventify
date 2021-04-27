package com.example.eventify;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class EventsAdapter extends FirestoreRecyclerAdapter<Events,EventsVH> {

    public EventsAdapter(@NonNull FirestoreRecyclerOptions<Events> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull EventsVH holder, int position, @NonNull Events model) {
        holder.Ename.setText(model.getName());
        holder.Edate.setText(model.getDate()+" "+model.getTime());
        holder.Evenue.setText(model.getVenue());
        Picasso.get().load(model.getImage()).into(holder.Eimg);
        String id=model.getId();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(),EventDetailsActivity.class);
                intent.putExtra("id",model.getId());
                intent.putExtra("society",model.getSociety());
                holder.itemView.getContext().startActivity(intent);

            }
        });
    }

    @NonNull
    @Override
    public EventsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_item, parent, false);
        EventsVH eventsVH=new EventsVH(view);
        return eventsVH;
    }
    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }
}
