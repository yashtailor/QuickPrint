package com.android.quickjob;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Declaring variables
    private EditText userEmail, userPassword;
    private Button registerUser;
    private TextView userSignIn,vendorSignIn;
    private FirebaseAuth firebaseAuth;

    //Since it has internet access, showing progress bar
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Type casting and telling which identifier belongs to which which field
        userEmail = (EditText) findViewById(R.id.emailText);
        // userEmail.setGravity(1);
        userPassword = (EditText) findViewById(R.id.passwordText);
        // userPassword.setGravity(1);
        registerUser = (Button) findViewById(R.id.userLogin);
        userSignIn = (TextView) findViewById(R.id.signupActivityLink);
        vendorSignIn= (TextView) findViewById(R.id.vendor_signup);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), UserProfile.class));
        }
        //Attaching on click Listener for registering and logging in
        registerUser.setOnClickListener(this);
        userSignIn.setOnClickListener(this);
        vendorSignIn.setOnClickListener(this);
    }

    @Override //An over-ridden method from interface View.OnClickListener
    public void onClick(View v) {
        if (v == registerUser) {
            // Calling the function to register
            registerUser();
        }
        if (v == userSignIn) {
            //Start the activity for login
            Intent intent = new Intent("com.android.login");
            startActivity(intent);
        }
        if (v == vendorSignIn) {
            //VEndot activity login
            Intent intent = new Intent(getApplicationContext(),VendorSignIn.class);
            startActivity(intent);
        }
    }

    public void registerUser() {
        String email, password;
        email = userEmail.getText().toString().trim();
        password = userPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {

            Toast.makeText(MainActivity.this, "Please Enter Email and Password", Toast.LENGTH_LONG).show();
            //stopping the further execution of the function
            return;
        }
        if (TextUtils.isEmpty(email)) {

            Toast.makeText(MainActivity.this, "Please Enter Email", Toast.LENGTH_LONG).show();
            //stopping the further execution of the function
            return;
        }
        if (TextUtils.isEmpty(password)) {

            Toast.makeText(MainActivity.this, "Please Enter Password", Toast.LENGTH_LONG).show();
            //stopping the further execution of the function
            return;
        }

        if (!isEmailValid(email)) {
            Toast.makeText(MainActivity.this, "Invalid Email", Toast.LENGTH_LONG).show();
            return;
        }

        if (password.contains("_") || password.trim().length() < 6 || password.trim().length() > 24) {
            Toast.makeText(MainActivity.this, "Invalid Password! \n Min length 6 \n Max length 24 \n Underscores not allowed", Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Registering User....");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //Open the activity of content
                    Toast.makeText(MainActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    finish();
                    startActivity(new Intent(getApplicationContext(), UserProfile.class));
                } else {
                    Toast.makeText(MainActivity.this, "Registration unsuccessful, Try Again :(", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }
            }
        });
    }

    boolean isEmailValid(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
