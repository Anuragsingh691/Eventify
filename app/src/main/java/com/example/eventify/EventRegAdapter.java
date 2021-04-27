package com.example.eventify;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class EventRegAdapter extends FirestoreRecyclerAdapter<RegistrationsModel,EventRegVH> {
    public EventRegAdapter(@NonNull FirestoreRecyclerOptions<RegistrationsModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull EventRegVH holder, int position, @NonNull RegistrationsModel model) {
        holder.name.setText(model.getName());
        holder.rollno.setText(model.getRoll());
        holder.year.setText(model.getYear());
        holder.branch.setText(model.getBranch());
    }

    @NonNull
    @Override
    public EventRegVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.enabled_layout, parent, false);
        EventRegVH eventsVH=new EventRegVH(view);
        return eventsVH;
    }
    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }
}
