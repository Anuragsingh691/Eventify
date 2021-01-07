package com.example.eventify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AdminHome extends AppCompatActivity {
    static List<Home_all_services> all_serv_Slide;
    RecyclerView all_ser_rv;
    Home_all_serv_adapter serv_adapter;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        floatingActionButton=findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),NotesEdit.class);
                startActivity(intent);
            }
        });
        all_ser_rv=(RecyclerView)findViewById(R.id.rv_all_serv);
        all_serv_Slide= new ArrayList<>();
        all_serv_Slide.add(new Home_all_services("Fashion",R.drawable.logo));
        all_serv_Slide.add(new Home_all_services("BlockChain",R.drawable.logo));
        all_serv_Slide.add(new Home_all_services("BigData",R.drawable.logo));
        all_serv_Slide.add(new Home_all_services("software incubator",R.drawable.logo));
        all_serv_Slide.add(new Home_all_services("goonj",R.drawable.logo));
        all_serv_Slide.add(new Home_all_services("conatus",R.drawable.logo));
        all_serv_Slide.add(new Home_all_services("cloud computing cell",R.drawable.logo));
        serv_adapter=new Home_all_serv_adapter(this,all_serv_Slide);
        all_ser_rv.setLayoutManager(new GridLayoutManager(this,3));
        all_ser_rv.setAdapter(serv_adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}