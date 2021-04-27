package com.example.eventify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Registrations extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerView;
    private CollectionReference TutorRefs;
    RecyclerView.LayoutManager layoutManager;
    View myFragment;
    private String mParam1;
    String societyName;
    EventRegAdapter adapter;
    String id,society;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrations);
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.regis_rv);
        TutorRefs=firebaseFirestore.collection("Societies");
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        society=intent.getStringExtra("society");
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(Registrations.this);
        recyclerView.setLayoutManager(layoutManager);
        Query query=TutorRefs.document(society).collection("Events").document(id).collection("Registrations")
                .orderBy("name",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<RegistrationsModel> options=new FirestoreRecyclerOptions.Builder<RegistrationsModel>()
                .setQuery(query,RegistrationsModel.class).build();
        adapter=new EventRegAdapter(options);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.startListening();
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
                Toast.makeText(Registrations.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }
}