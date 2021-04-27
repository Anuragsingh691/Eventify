package com.example.eventify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class NotesEdit extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference;
    int noteId;
    Button add;
    Home_all_serv_adapter serv_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_edit);
        firebaseFirestore=FirebaseFirestore.getInstance();
        collectionReference=firebaseFirestore.collection("Societies");
        EditText editText=findViewById(R.id.edtText);
        Intent intent=getIntent();
        noteId=intent.getIntExtra("noteId",-1);
        add=findViewById(R.id.add_society);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ttile=editText.getText().toString();
                HashMap<String, Object> productMap = new HashMap<>();
                productMap.put("Title", ttile);
                collectionReference.document(ttile).set(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(NotesEdit.this, "Society Added Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent1=new Intent(NotesEdit.this,AdminHome.class);
                            startActivity(intent1);
                        }
                    }
                });

            }
        });
    }
}