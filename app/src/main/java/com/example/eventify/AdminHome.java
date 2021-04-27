package com.example.eventify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventify.Prevalent.Prevalent;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.paperdb.Paper;

public class AdminHome extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference;
    RecyclerView all_ser_rv;
    Home_all_serv_adapter serv_adapter;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        firebaseFirestore=FirebaseFirestore.getInstance();
        collectionReference=firebaseFirestore.collection("Societies");
        floatingActionButton=findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),NotesEdit.class);
                startActivity(intent);
            }
        });
        all_ser_rv=(RecyclerView)findViewById(R.id.rv_all_serv);
        all_ser_rv.setLayoutManager(new GridLayoutManager(this,3));
//        all_ser_rv.setAdapter(serv_adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Query query=collectionReference.orderBy("Title",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Home_all_services> options=new FirestoreRecyclerOptions.Builder<Home_all_services>()
                .setQuery(query,Home_all_services.class).build();
        FirestoreRecyclerAdapter<Home_all_services, Home_all_serv_adapter.MyViewHolder> adapter=new FirestoreRecyclerAdapter<Home_all_services, Home_all_serv_adapter.MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Home_all_serv_adapter.MyViewHolder holder, int position, @NonNull Home_all_services model) {
                holder.service_title.setText(model.getTitle());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(AdminHome.this,SocietyDetails.class);
                        intent.putExtra("title",model.getTitle());
                        startActivity(intent);
                    }
                });
            }
            public void deleteItem(int position){
                getSnapshots().getSnapshot(position).getReference().delete();
            }

            @NonNull
            @Override
            public Home_all_serv_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_services_cv_item, parent, false);
                Home_all_serv_adapter.MyViewHolder holder = new Home_all_serv_adapter.MyViewHolder(view);
                return holder;
            }

        };
        all_ser_rv.setAdapter(adapter);
        adapter.startListening();
        adapter.notifyDataSetChanged();
    }
}