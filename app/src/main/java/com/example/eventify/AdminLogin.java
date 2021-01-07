package com.example.eventify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminLogin extends AppCompatActivity {
    Button phoneLogin,fbLogin,newAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        phoneLogin=findViewById(R.id.admin_phone_login);
        fbLogin=findViewById(R.id.admin_fb_login);
        newAdmin=findViewById(R.id.new_admin);
        phoneLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminLogin.this,PhoneLogin.class));
            }
        });
        newAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminLogin.this,NewAdminLogin.class);
                String newAdmin="true";
                intent.putExtra("NewAdmin",newAdmin);
                startActivity(intent);
            }
        });
    }
}