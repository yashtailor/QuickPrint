package com.android.quickjob;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditDetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private EditText newemail, newpassword, oldpassword, newpassword2;
    private TextView forgot_password;
    private Button edit_btn;
    private FirebaseUser user, mUser;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);
        drawer = findViewById(R.id.draw_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.bringToFront();

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        editDetails();
        passwordRetrieving();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.nav_userprfile) {
            startActivity(new Intent(getApplicationContext(), UserProfile.class));
            finish();
        } else if (menuItem.getItemId() == R.id.nav_editdetails) {
            onBackPressed();
        } else if (menuItem.getItemId() == R.id.nav_previousorders) {
            startActivity(new Intent(getApplicationContext(), PreviousOrders.class));
            finish();
        } else if (menuItem.getItemId() == R.id.nav_settings) {
            startActivity(new Intent(getApplicationContext(), Settings.class));
            finish();
        } else if (menuItem.getItemId() == R.id.nav_about) {
            if (mUser.getEmail().equals("aaathorve@gmail.com") || mUser.getEmail().equals("yashtailor2000@gmail.com") || mUser.getEmail().equals("02aditya96@gmail.com")) {
                startActivity(new Intent(getApplicationContext(), DeveloperOptions.class));
                finish();
            } else {
                //Toast.makeText(getApplicationContext(),"Not a developer",Toast.LENGTH_SHORT);
                onBackPressed();
            }
        }
        return true;

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void editDetails() {
        newemail = (EditText) findViewById(R.id.emailText);
        oldpassword = (EditText) findViewById(R.id.passwordText);
        newpassword = (EditText) findViewById(R.id.passwordText2);
        newpassword2 = (EditText) findViewById(R.id.passwordText3);
        edit_btn = (Button) findViewById(R.id.button);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final StringBuilder sb = new StringBuilder();
                //Log.e("email empty",""+TextUtils.isEmpty(newemail.getText().toString()));
                //System.out.println(sb);
                if (!TextUtils.isEmpty(newemail.getText().toString())) {
                    final String email = newemail.getText().toString().trim();
                    //Log.e("Valid Email: ",""+isEmailValid(email));
                    if (!isEmailValid(email)) {
                        newemail.getText().clear();
                        Toast.makeText(EditDetails.this, "Invalid Email", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        //Log.e("else ","here");
                        user.updateEmail(email)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        newemail.getText().clear();
                                    }
                                });
                        Toast.makeText(EditDetails.this, "Details edited", Toast.LENGTH_LONG).show();
                        databaseReference.child(user.getUid()).child("userEmail").setValue(email);
                    }
                }
                //System.out.println(TextUtils.isEmpty(oldpassword.getText().toString()));
                if (!TextUtils.isEmpty(oldpassword.getText().toString().trim())) {
                    String password1 = oldpassword.getText().toString().trim();
                    AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), password1);
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        final String password2, password3;
                                        password2 = newpassword.getText().toString().trim();
                                        password3 = newpassword2.getText().toString().trim();
                                        //System.out.println(TextUtils.isEmpty(newpassword.getText().toString()));
                                        //System.out.println(TextUtils.isEmpty(newpassword2.getText().toString()));
                                        if (!(password2.contains("_") || password2.trim().length() < 6 || password2.trim().length() > 24)) {
                                            if (!TextUtils.isEmpty(password3) && password2.equals(password3)) {
                                                user.updatePassword(password2)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                oldpassword.getText().clear();
                                                                newpassword.getText().clear();
                                                                newpassword2.getText().clear();
                                                                Toast.makeText(EditDetails.this, "Details edited", Toast.LENGTH_LONG).show();
                                                                //sb.append("Password updated\n");
                                                            }
                                                        });
                                                databaseReference.child(user.getUid()).child("userPassword").setValue(password2);
                                            } else {
                                                Toast.makeText(EditDetails.this, "Both passwords input do not match", Toast.LENGTH_LONG).show();
                                                oldpassword.getText().clear();
                                                newpassword.getText().clear();
                                                newpassword2.getText().clear();
                                                return;
                                            }
                                        } else {
                                            Toast.makeText(EditDetails.this, "Invalid password", Toast.LENGTH_LONG).show();
                                            oldpassword.getText().clear();
                                            newpassword.getText().clear();
                                            return;
                                        }
                                    } else {
                                        Toast.makeText(EditDetails.this, "Incorrect Password", Toast.LENGTH_LONG).show();
                                        if(!TextUtils.isEmpty(newpassword.getText())) {
                                            newpassword.getText().clear();
                                        } if(!TextUtils.isEmpty(newpassword2.getText())) {
                                            newpassword2.getText().clear();
                                        }
                                        oldpassword.getText().clear();
                                        return;
                                    }
                                }
                            });
                } else {
                    if(TextUtils.isEmpty(newemail.getText())) {
                        Toast.makeText(getApplicationContext(), "Enter previous password", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });
    }

    public void passwordRetrieving() {
        forgot_password = (TextView) findViewById(R.id.textView);
        user = FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.sendPasswordResetEmail(user.getEmail())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isComplete()) {
                                    Toast.makeText(EditDetails.this, "Password reset email sent.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }

    boolean isEmailValid(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}

