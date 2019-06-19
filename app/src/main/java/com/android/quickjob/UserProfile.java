package com.android.quickjob;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class UserProfile extends AppCompatActivity implements View.OnClickListener{
    Button btnForLogOut;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        btnForLogOut = (Button)findViewById(R.id.logoutBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        btnForLogOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==btnForLogOut){
            firebaseAuth.signOut();
            Intent logOut = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(logOut);
        }
    }
}
