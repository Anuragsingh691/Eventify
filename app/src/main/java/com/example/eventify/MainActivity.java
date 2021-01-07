package com.example.eventify;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.eventify.Prevalent.Prevalent;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private static  int SPLASH_SCREEN=4000;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseFirestore=FirebaseFirestore.getInstance();
        Paper.init(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String AdminPhone= Paper.book().read(Prevalent.AdminPhoneKey);
                String Adminfb= Paper.book().read(Prevalent.AdminFbKey);
                if (AdminPhone!=null){
                    Allowacess(AdminPhone);
                }
                else if (Adminfb!=null){
                    checkEmail(Adminfb);
                }
                else{
                    Intent splash = new Intent(MainActivity.this, AdminLogin.class);
                    startActivity(splash);
                    finish();
                }
            }
        },SPLASH_SCREEN);
    }
    public void  checkEmail(String email){
        CollectionReference collectionReference=firebaseFirestore.collection("Admin");
        DocumentReference documentReference=collectionReference.document(email);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value!=null){
                    Intent intent=new Intent(MainActivity.this,AdminHome.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(MainActivity.this, "Logged in successfully..", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(MainActivity.this, "Admin doesn't exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Allowacess(final String phone){
        CollectionReference collectionReference=firebaseFirestore.collection("Admin");
        DocumentReference documentReference=collectionReference.document(phone);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()){
                    Intent intent=new Intent(MainActivity.this,AdminHome.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(MainActivity.this, "Logged in successfully..", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(MainActivity.this, "Admin doesn't exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}