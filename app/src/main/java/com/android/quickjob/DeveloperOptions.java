package com.android.quickjob;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DeveloperOptions extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static ArrayList<VendorAddVerify> mVendorList=new ArrayList<>();
    private VendorVerificationAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FirebaseAuth mAuth;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_options);
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
        mAuth=FirebaseAuth.getInstance();
        buildRecyclerView();
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
            startActivity(new Intent(getApplicationContext(), PreviousOrders.class));
            finish();
        } else if (menuItem.getItemId() == R.id.nav_settings) {
            startActivity(new Intent(getApplicationContext(), Settings.class));
            finish();
        } else if (menuItem.getItemId() == R.id.nav_about) {
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
    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerViewVerification);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new VendorVerificationAdapter(mVendorList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new VendorVerificationAdapter.OnItemClickListener() {
            @Override
            public void onAddClick(final int position) {
                AlertDialog.Builder a_builder = new AlertDialog.Builder(DeveloperOptions.this);
                a_builder.setMessage("Do you want to add this vendor")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mAuth.createUserWithEmailAndPassword(mVendorList.get(position).getEmail(),mVendorList.get(position).getVendorNumber())
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if(task.isSuccessful()) {
                                                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Vendors");
                                                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                                                    String uid=user.getUid();
                                                    databaseReference.child(uid).setValue(mVendorList.get(position));
                                                    Toast.makeText(DeveloperOptions.this,"Added successfully",Toast.LENGTH_LONG).show();
                                                    mVendorList.remove(position);
                                                    mAdapter.notifyItemRemoved(position);
                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(DeveloperOptions.this,e.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                mVendorList.remove(position);
                                mAdapter.notifyItemRemoved(position);
                            }
                        });
                AlertDialog alert = a_builder.create();
                alert.show();
            }
        });
    }
}
