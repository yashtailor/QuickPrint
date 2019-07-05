package com.android.quickjob;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class VendorSignIn extends AppCompatActivity implements View.OnClickListener {
    EditText alreadyRegisteredVendorEmail, alreadyRegisteredVendorPassword;
    Button login;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_sign_in);
        alreadyRegisteredVendorEmail = (EditText) findViewById(R.id.emailText2);
        alreadyRegisteredVendorPassword = (EditText) findViewById(R.id.vendorPassword);
        login = (Button) findViewById(R.id.vendorLogin);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        if (v == login) {
            login();
        }
    }

    public void login() {
        String email, password;
        email = alreadyRegisteredVendorEmail.getText().toString().trim();
        password = alreadyRegisteredVendorPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {

            Toast.makeText(VendorSignIn.this, "Please Enter Email and Password", Toast.LENGTH_LONG).show();
            //stopping the further execution of the function
            return;
        }
        if (TextUtils.isEmpty(email)) {

            Toast.makeText(VendorSignIn.this, "Please Enter Email", Toast.LENGTH_LONG).show();
            //stopping the further execution of the function
            return;
        }
        if (TextUtils.isEmpty(password)) {

            Toast.makeText(VendorSignIn.this, "Please Enter Password", Toast.LENGTH_LONG).show();
            //stopping the further execution of the function
            return;
        }


        progressDialog.setMessage("Opening User Profile....");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //Open the activity of content
                    startActivity(new Intent(getApplicationContext(), UserProfile.class));
                    progressDialog.dismiss();
                    return;
                } else {
                    Toast.makeText(VendorSignIn.this, "Login unsuccessful!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }
            }
        });

    }
}