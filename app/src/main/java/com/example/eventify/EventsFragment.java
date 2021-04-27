package com.example.eventify;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eventify.Prevalent.Prevalent;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import io.paperdb.Paper;


public class EventsFragment extends Fragment {
    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerView;
    private CollectionReference TutorRefs;
    RecyclerView.LayoutManager layoutManager;
    View myFragment;
    private String mParam1;
    String societyName;
    EventsAdapter adapter;

    public EventsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            mParam1=getArguments().getString("name");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment= inflater.inflate(R.layout.fragment_events, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = myFragment.findViewById(R.id.offers_rv);
        TutorRefs=firebaseFirestore.collection("Societies");
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        Query query=TutorRefs.document(mParam1).collection("Events").orderBy("name",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Events> options=new FirestoreRecyclerOptions.Builder<Events>()
                .setQuery(query,Events.class).build();
        adapter=new EventsAdapter(options);
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
            }
        }).attachToRecyclerView(recyclerView);
        return myFragment;
    }
}