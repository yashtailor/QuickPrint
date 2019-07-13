package com.android.quickjob;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class VendorLogin extends AppCompatActivity {

    private EditText vendorLoginName, vendorLoginNumber, vendorLoginEmail;
    private Button vendorloginBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_login);

        vendorloginBtn = (Button) findViewById(R.id.vendorloginBtn);
        vendorLoginName = (EditText) findViewById(R.id.vendorLoginName);
        vendorLoginNumber = (EditText) findViewById(R.id.vendorLoginNumber);
        vendorLoginEmail = (EditText) findViewById(R.id.vendorLoginEmail);
        mAuth=FirebaseAuth.getInstance();

        vendorloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vendorLogin();
            }
        });
    }

    public void vendorLogin() {
        String name = vendorLoginName.getText().toString().trim();
        String number = vendorLoginNumber.getText().toString().trim();
        final String email = vendorLoginEmail.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(email, number).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(VendorLogin.this, "Login successful", Toast.LENGTH_LONG).show();
                    SharedPreferences sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("username",email);
                    editor.putBoolean("flag1",true);
                    editor.putBoolean("flag2",false);
                    editor.apply();
                    finish();
                    startActivity(new Intent(getApplicationContext(), VendorProfile.class));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(VendorLogin.this, "Login failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}