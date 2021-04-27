package com.example.eventify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.eventify.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;

import static com.example.eventify.Prevalent.Prevalent.AdminPhoneKey;
import static com.example.eventify.Prevalent.Prevalent.UserPhoneKey;

public class AdminOtpActivity extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference;
    String id,phone;
    PinView pinView;
    String codeBySystem;
    String phone_number;
    PhoneAuthProvider.ForceResendingToken token ;
    Button verify;
    TextView waittext,WelcomeTxt,retry;
    String newAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_otp);
        Intent intent=getIntent();
        newAdmin=intent.getStringExtra("NewAdmin");
        firebaseFirestore=FirebaseFirestore.getInstance();
        collectionReference=firebaseFirestore.collection("Admin");
        phone=intent.getStringExtra("phone");
        phone_number=phone;
        WelcomeTxt=findViewById(R.id.enter_phone_number_title);
        verify=findViewById(R.id.submit);
        Paper.init(this);
        pinView=findViewById(R.id.otp_pin);
        if (newAdmin==null){
            DocumentReference documentReference=collectionReference.document(phone);
            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value.exists()){
                        sendVerificationCode(phone);
                        Paper.book().write(AdminPhoneKey,phone);
                    }else
                    {
                        Toast.makeText(AdminOtpActivity.this, "Staff doesn't exist", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
        {
            sendVerificationCode(phone);
        }
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Otp = pinView.getText().toString();

                if (!Otp.isEmpty())
                {
                    verifyCode(Otp);
                }
            }
        });
    }
    private void sendVerificationCode(String phone_number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone_number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                    token = forceResendingToken;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code!=null)
                    {
                        pinView.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    WelcomeTxt.setText("Otp didn't match");
                    Toast.makeText(AdminOtpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem,code);
        signInWithPhoneAuthCredential(credential);
        Toast.makeText(this, "Verify Code Entered........", Toast.LENGTH_SHORT).show();
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            if (newAdmin==null){
                                DocumentReference documentReference=collectionReference.document(phone);
                                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                        if (value.exists()){

                                            Intent intent=new Intent(AdminOtpActivity.this, AdminHome.class);
                                            startActivity(intent);
                                            finish();
                                            Toast.makeText(AdminOtpActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                                        }else
                                        {
                                            Toast.makeText(AdminOtpActivity.this, "Admin doesn't exist", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else{
                                HashMap<String,Object> productMap=new HashMap<>();
                                productMap.put("Admin exists",phone);
                                collectionReference.document(phone).set(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(AdminOtpActivity.this, "Sign in with firebase Auth entered", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(AdminOtpActivity.this, AdminHome.class);
                                        String newAdmin=intent.getStringExtra("NewAdmin");
                                        Paper.book().write(Prevalent.AdminPhoneKey,phone);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(AdminOtpActivity.this, "Admin Registered succesfully...", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }
                });
    }
}