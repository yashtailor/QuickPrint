package com.android.quickjob;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VendorSettings extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Button signOutBtn;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_settings);
        drawer = findViewById(R.id.draw_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view_1);
        navigationView.setNavigationItemSelectedListener(this);
        user= FirebaseAuth.getInstance().getCurrentUser();
        View headerView = navigationView.getHeaderView(0);
        TextView textView = (TextView) headerView.findViewById(R.id.emailDisplayId);
        textView.setText(user.getEmail());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.bringToFront();
        signOutBtn = (Button) findViewById(R.id.vendorSignOutBtn);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vendorSignOut();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.nav_vendorprofile) {
            startActivity(new Intent(getApplicationContext(), VendorPendingOrders.class));
            finish();
        } else if (menuItem.getItemId() == R.id.nav_editdetails_vendor) {
            startActivity(new Intent(getApplicationContext(), VendorEditDetails.class));
            finish();
        } else if (menuItem.getItemId() == R.id.nav_previousorders_vendor) {
            startActivity(new Intent(getApplicationContext(), VendorPreviousOrders.class));
            finish();
        } else if (menuItem.getItemId() == R.id.nav_settings_vendor) {
            onBackPressed();
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

    public void vendorSignOut() {
        mAuth = FirebaseAuth.getInstance();
        AlertDialog.Builder a_builder = new AlertDialog.Builder(VendorSettings.this);
        a_builder.setMessage("Do you want to signout!!!")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("flag1", false);
                        editor.putBoolean("flag2", false);
                        editor.apply();
                        mAuth.signOut();
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = a_builder.create();
        alert.setTitle("SignOut");
        alert.show();
    }

}
