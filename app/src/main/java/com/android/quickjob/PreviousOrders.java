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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class PreviousOrders extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawer;
    private ArrayList<PreviousOrdersItems> previousOrdersItems;
    private RecyclerView recyclerView;
    private PreviousOrdersAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_orders);

        setFieldsOfPreviousOrders();
        setRecyclerViewOfPreviousOrders();
    }


    private void setRecyclerViewOfPreviousOrders() {
        previousOrdersItems = new ArrayList<>();
        previousOrdersItems.add(new PreviousOrdersItems("yash","123","yash",12));
        previousOrdersItems.add(new PreviousOrdersItems("yash","123","yash",12));
        previousOrdersItems.add(new PreviousOrdersItems("yash","123","yash",12));
        recyclerView = (RecyclerView)findViewById(R.id.userRecyclerViewPreviousOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new PreviousOrdersAdapter(previousOrdersItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        mUser= FirebaseAuth.getInstance().getCurrentUser();

        adapter.setOnItemClickListener(
                new PreviousOrdersAdapter.OnDeleteIconClickListener() {
            @Override
            public void onDeleteClick(int position) {
                previousOrdersItems.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });
    }


    private void setFieldsOfPreviousOrders() {
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
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.nav_userprfile) {
            startActivity(new Intent(getApplicationContext(), UserProfile.class));
            finish();
        } else if (menuItem.getItemId() == R.id.nav_editdetails) {
            startActivity(new Intent(getApplicationContext(), EditDetails.class));
            finish();
        } else if (menuItem.getItemId() == R.id.nav_previousorders) {
            onBackPressed();
        } else if (menuItem.getItemId() == R.id.nav_settings) {
            startActivity(new Intent(getApplicationContext(), Settings.class));
            finish();
        } else if (menuItem.getItemId() == R.id.nav_about) {
            if(mUser.getEmail().equals("aaathorve@gmail.com")||mUser.getEmail().equals("yashtailor2000@gmail.com")||mUser.getEmail().equals("02aditya96@gmail.com")) {
                startActivity(new Intent(getApplicationContext(),DeveloperOptions.class));
                finish();
            }else {
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
}

