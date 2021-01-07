package com.example.eventify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NotesEdit extends AppCompatActivity {
    int noteId;
    Button add;
    Home_all_serv_adapter serv_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_edit);
        EditText editText=findViewById(R.id.edtText);
        Intent intent=getIntent();
        noteId=intent.getIntExtra("noteId",-1);
        add=findViewById(R.id.add_society);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(NotesEdit.this,AdminHome.class);
//                intent1.putExtra("Sname",editText.getText().toString());
                startActivity(intent1);
                SharedPreferences sharedPreferences=getSharedPreferences("MySociety",MODE_PRIVATE);
                SharedPreferences.Editor myEdit=sharedPreferences.edit();
                myEdit.putString("Sname",editText.getText().toString());
                AdminHome.all_serv_Slide.add(new Home_all_services(editText.getText().toString(),R.drawable.logo));
                myEdit.commit();
            }
        });
    }
}