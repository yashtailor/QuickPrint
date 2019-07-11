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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class VendorEditDetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private EditText newNumber,newName;
    private Button editVendorDetailsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_edit_details);
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

        newName=(EditText)findViewById(R.id.vendorEditName);
        newNumber=(EditText)findViewById(R.id.vendorEditNumber);
        editVendorDetailsBtn=(Button)findViewById(R.id.vendorEditDetailsBtn);

        editVendorDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
}
