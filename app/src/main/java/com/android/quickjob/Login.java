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

public class Login extends AppCompatActivity implements View.OnClickListener{
    EditText alreadyRegisteredUserEmail,alreadyRegisteredUserPassword;
    Button login;
    TextView toGoToSignUpPage;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        alreadyRegisteredUserEmail = (EditText)findViewById(R.id.emailText);
        alreadyRegisteredUserPassword = (EditText)findViewById(R.id.passwordText);
        login = (Button)findViewById(R.id.userLogin);
        toGoToSignUpPage = (TextView)findViewById(R.id.signupActivityLink);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        toGoToSignUpPage.setOnClickListener(this);
        login.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v==login){
            login();
        }
        if(v==toGoToSignUpPage){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }
    public void login(){
        String email,password;
        email = alreadyRegisteredUserEmail.getText().toString().trim();
        password = alreadyRegisteredUserPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){

            Toast.makeText(Login.this,"Please Enter Email and Password",Toast.LENGTH_LONG).show();
            //stopping the further execution of the function
            return;
        }
        if(TextUtils.isEmpty(email)){

            Toast.makeText(Login.this,"Please Enter Email",Toast.LENGTH_LONG).show();
            //stopping the further execution of the function
            return;
        }
        if(TextUtils.isEmpty(password)){

            Toast.makeText(Login.this,"Please Enter Password",Toast.LENGTH_LONG).show();
            //stopping the further execution of the function
            return;
        }


        progressDialog.setMessage("Opening User Profile....");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Open the activity of content
                    startActivity(new Intent(getApplicationContext(),UserProfile.class));
                    progressDialog.dismiss();
                    return;
                }
                else{
                    Toast.makeText(Login.this,"Login unsuccessful!",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }
            }
        });
    }
}
