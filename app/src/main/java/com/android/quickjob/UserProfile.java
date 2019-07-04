package com.android.quickjob;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URI;

import static android.content.Intent.ACTION_GET_CONTENT;

public class UserProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private DrawerLayout drawer;
    private Button btnAdd, btnGallery, btnFileManager, btnCamScanner, btnCamera,btnVendorList;
    private RecyclerView recyclerView;
    StorageReference storageReference;
    private static final int REQUEST_CODE_FILES = 1;
    URI file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setTheFields();

        vendorList();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.nav_userprfile) {
            onBackPressed();
        } else if (menuItem.getItemId() == R.id.nav_editdetails) {
            startActivity(new Intent(getApplicationContext(), EditDetails.class));
            finish();
        } else if (menuItem.getItemId() == R.id.nav_previousorders) {
            startActivity(new Intent(getApplicationContext(), PreviousOrders.class));
            finish();
        } else if (menuItem.getItemId() == R.id.nav_settings) {
            startActivity(new Intent(getApplicationContext(), Settings.class));
            finish();
        } else if (menuItem.getItemId() == R.id.nav_about) {
            startActivity(new Intent(getApplicationContext(), About.class));
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


    @Override
    public void onClick(View v) {
        if (v == btnFileManager) {
            Intent intent = new Intent();
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select File(s)"), REQUEST_CODE_FILES);
        }

    }

    public void setTheFields() {
        drawer = findViewById(R.id.draw_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        btnAdd = (Button) findViewById(R.id.buttonAdd);
        btnCamera = (Button) findViewById(R.id.btnCamera);
        btnCamScanner = (Button) findViewById(R.id.btnCamScanner);
        btnFileManager = (Button) findViewById(R.id.btnFileManger);
        btnGallery = (Button) findViewById(R.id.btnGallery);
        //recyclerView = (RecyclerView) findViewById(R.id.recyclerViewItems);
        btnAdd.setOnClickListener(this);
        btnGallery.setOnClickListener(this);
        btnCamScanner.setOnClickListener(this);
        btnCamera.setOnClickListener(this);
        btnFileManager.setOnClickListener(this);
        storageReference = FirebaseStorage.getInstance().getReference();
        navigationView.bringToFront();
    }
    public void vendorList() {
        btnVendorList=(Button) findViewById(R.id.vendorBtn);
        btnVendorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),vendorList.class));
                finish();
            }
        });
    }

}

