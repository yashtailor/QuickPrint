package com.android.quickjob;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;

import java.util.concurrent.TimeUnit;

public class VendorLogin extends AppCompatActivity {

    private EditText vendorLoginName,vendorLoginNumber;
    private Button vendorloginBtn;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_login);

        vendorloginBtn=(Button) findViewById(R.id.vendorloginBtn);
        vendorLoginName=(EditText) findViewById(R.id.vendorLoginName);
        vendorLoginNumber=(EditText) findViewById(R.id.vendorLoginNumber);

        vendorloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vendorLogin();
            }
        });
    }
    public void vendorLogin() {
        final String name=vendorLoginName.getText().toString().trim();
        final String number=vendorLoginNumber.getText().toString().trim();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                45,
                TimeUnit.SECONDS,
                this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }
        });
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(),VendorProfile.class));
                        } else {
                            Toast.makeText(VendorLogin.this,"Login failed",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
