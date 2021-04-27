package com.example.eventify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class EventDetailsActivity extends AppCompatActivity {
    TextView name,desc,date,venue;
    ImageView img;
    Button delete,registrations;
    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference;
    String id,society;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        society=intent.getStringExtra("society");
        firebaseFirestore=FirebaseFirestore.getInstance();
        collectionReference=firebaseFirestore.collection("Societies");
        name=findViewById(R.id.event_name);
        desc=findViewById(R.id.event_desc);
        date=findViewById(R.id.event_date);
        venue=findViewById(R.id.event_venue);
        img=findViewById(R.id.event_img);
        registrations=findViewById(R.id.event_registrations);
        registrations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(EventDetailsActivity.this,Registrations.class);
                intent1.putExtra("id",id);
                intent1.putExtra("society",society);
                startActivity(intent1);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        collectionReference.document(society).collection("Events"
        ).document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot!=null && documentSnapshot.exists()){
                    Events products=documentSnapshot.toObject(Events.class);
                    Picasso.get().load(products.getImage()).into(img);
                    name.setText(products.getName());
                    desc.setText(products.getDesc());
                    date.setText(products.getDate()+" "+products.getTime());
                    venue.setText(products.getVenue());
                }
            }
        });
    }
}