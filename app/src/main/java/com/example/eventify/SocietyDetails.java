package com.example.eventify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventify.Prevalent.Prevalent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

public class SocietyDetails extends AppCompatActivity {
    TextView societyName;
    private FirebaseAuth mFirebaseAuth;
    Button logout;
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    String name;
    Fragment fragment1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_details);
        Intent intent=getIntent();
        name=intent.getStringExtra("title");
        societyName=findViewById(R.id.society_name);
        societyName.setText(name);
        Paper.init(SocietyDetails.this);
//        logout=findViewById(R.id.logout);
        mFirebaseAuth = FirebaseAuth.getInstance();
        bottomNavigationView = findViewById(R.id.bottom_nav);
        frameLayout = findViewById(R.id.frameLayout);
        fragment1=new EventsFragment();
        Bundle data = new Bundle();//Use bundle to pass data
        data.putString("name", name);//put string, int, etc in bundle with a key value
        fragment1.setArguments(data);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment1).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomMethod);
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mFirebaseAuth.signOut();
//                Paper.book().destroy();
//                startActivity(new Intent(SocietyDetails.this, AdminLogin.class));
//                finish();
//            }
//        });
    }
    private BottomNavigationView.OnNavigationItemSelectedListener bottomMethod =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment = null;
                    switch (item.getItemId())
                    {
                        case R.id.bottom_nav_home:
                            fragment=new HomeFragment();
                            Bundle data = new Bundle();//Use bundle to pass data
                            data.putString("Sname", name);//put string, int, etc in bundle with a key value
                            fragment.setArguments(data);
                            break;
                        case R.id.bottom_nav_event:
                            fragment=new EventsFragment();
                            Bundle dataE = new Bundle();
                            dataE.putString("name", name);
                            fragment.setArguments(dataE);
                            //put string, int, etc in bundle with a key value
                            break;
                        case R.id.bottom_nav_issue:
                            fragment=new IssuesFragment();
                            Bundle dataI = new Bundle();
                            dataI.putString("Sname", name);//put string, int, etc in bundle with a key value
                            fragment.setArguments(dataI);
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();

                    return true;
                }
            };
}