package com.example.eventify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewAdminLogin extends AppCompatActivity {
    Button phoneLogin,fbLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_admin_login);
        phoneLogin=findViewById(R.id.admin_phone_login);
        phoneLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NewAdminLogin.this,PhoneLogin.class);
                String newAdmin="true";
                intent.putExtra("NewAdmin",newAdmin);
                startActivity(intent);
            }
        });
    }
}