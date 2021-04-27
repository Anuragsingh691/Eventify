package com.example.eventify;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class MembersAdapter extends FirestoreRecyclerAdapter<Members,MembersVH> {


    public MembersAdapter(@NonNull FirestoreRecyclerOptions<Members> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MembersVH holder, int position, @NonNull Members model) {
        holder.Mname.setText(model.getMname());
        holder.Mphone.setText(model.getMphone());
        holder.Mpost.setText(model.getMpost());
    }

    @NonNull
    @Override
    public MembersVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diabled_layout, parent, false);
        MembersVH holder = new MembersVH(view);
        return holder;
    }
    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }
}
