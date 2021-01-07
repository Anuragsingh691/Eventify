package com.example.eventify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

public class SocietyDetails extends AppCompatActivity {
    TextView societyName;
    private FirebaseAuth mFirebaseAuth;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_details);
        Intent intent=getIntent();
        String name=intent.getStringExtra("title");
        societyName=findViewById(R.id.society_name);
        societyName.setText(name);
        logout=findViewById(R.id.logout);
        mFirebaseAuth = FirebaseAuth.getInstance();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.signOut();
                Paper.book().destroy();
                startActivity(new Intent(SocietyDetails.this, AdminLogin.class));
                finish();
            }
        });
    }
}