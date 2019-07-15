package com.android.quickjob;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

public class VendorProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private RecyclerView vRecyclerView;
    private RecyclerView.Adapter vAdapter;
    private RecyclerView.LayoutManager vLayoutManager;
    static ArrayList<OrderData> aod=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_profile);
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

        vRecyclerView = (RecyclerView) findViewById(R.id.vendorRecyclerView);
        vRecyclerView.setHasFixedSize(true);
        vLayoutManager = new LinearLayoutManager(this);
        vAdapter = new VendorOrderAdapter(aod);
        vRecyclerView.setLayoutManager(vLayoutManager);
        vRecyclerView.setAdapter(vAdapter);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.nav_vendorprofile) {
            onBackPressed();
        } else if (menuItem.getItemId() == R.id.nav_editdetails_vendor) {
            startActivity(new Intent(getApplicationContext(), VendorEditDetails.class));
            finish();
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
