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
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VendorEditDetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private EditText newNumber,newName,newEmail;
    private Button editVendorDetailsBtn;
    private FirebaseUser user;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_edit_details);
        drawer = findViewById(R.id.draw_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view_1);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.bringToFront();

        editDetails();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.nav_vendorprofile) {
            startActivity(new Intent(getApplicationContext(), VendorProfile.class));
            finish();
        } else if (menuItem.getItemId() == R.id.nav_editdetails_vendor) {
            onBackPressed();
        } else if (menuItem.getItemId() == R.id.nav_previousorders_vendor) {
            startActivity(new Intent(getApplicationContext(), VendorPreviousOrders.class));
            finish();
        } else if (menuItem.getItemId() == R.id.nav_settings_vendor) {
            startActivity(new Intent(getApplicationContext(), VendorSettings.class));
            finish();
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
        newName=(EditText)findViewById(R.id.vendorEditName);
        newNumber=(EditText)findViewById(R.id.vendorEditNumber);
        newEmail=(EditText)findViewById(R.id.vendorEditEmail);
        editVendorDetailsBtn=(Button)findViewById(R.id.vendorEditDetailsBtn);

        user= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("Vendors");

        editVendorDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(newEmail.getText().toString())) {
                    final String email = newEmail.getText().toString().trim();
                    Log.e("Valid Email: ",""+isEmailValid(email));
                    if (!isEmailValid(email)) {
                        newEmail.getText().clear();
                        Toast.makeText(VendorEditDetails.this, "Invalid Email", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        Log.e("else ","here");
                        user.updateEmail(email)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        newEmail.getText().clear();
                                    }
                                });
                        databaseReference.child(user.getUid()).child("email").setValue(email);
                    }
                }
                if(!TextUtils.isEmpty(newNumber.getText().toString())) {
                    final String number=newNumber.getText().toString().trim();
                    user.updatePassword(number).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            newNumber.getText().clear();
                        }
                    });
                    databaseReference.child(user.getUid()).child("vendorNumber").setValue(number);
                }
                if(!TextUtils.isEmpty(newName.getText().toString())) {
                    final String name=newName.getText().toString().trim();
                    databaseReference.child(user.getUid()).child("vendorName").setValue(name);
                    newName.getText().clear();
                }
                Toast.makeText(VendorEditDetails.this,"Details edited",Toast.LENGTH_LONG).show();
            }
        });
    }
    boolean isEmailValid(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
