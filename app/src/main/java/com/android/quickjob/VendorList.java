package com.android.quickjob;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VendorList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int OPENING_PAYEMENTS = 1;
    private DrawerLayout drawer;
    private RecyclerView mRecyclerView;
    private VendorListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    static ArrayList<VendorData> data=new ArrayList<>();
    private DatabaseReference databaseReference;
    //NotificationManager notificationManager;
    //private StorageReference firebaseStorage;
    private FirebaseAuth user;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_list);
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
        buildRecyclerView(data);
        user = FirebaseAuth.getInstance();

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
            startActivity(new Intent(getApplicationContext(), DeveloperOptions.class));
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
    public void buildRecyclerView(final ArrayList<VendorData> data) {
        databaseReference= FirebaseDatabase.getInstance().getReference("Vendors");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                data.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                    VendorAddVerify vendorAddVerify=ds.getValue(VendorAddVerify.class);
                    data.add(new VendorData(vendorAddVerify.getVendorName(),vendorAddVerify.getVendorNumber(),vendorAddVerify.getEmail()));
                    mAdapter.notifyItemInserted(data.size());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mRecyclerView=findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mAdapter=new VendorListAdapter(data);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnClickListener(new VendorListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {
                pos = position;
                AlertDialog.Builder a_builder=new AlertDialog.Builder(VendorList.this);
                a_builder.setMessage("Do you want to send this order?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               // notificationManager=new NotificationManager(data.get(position).getVendorEmail(),getApplicationContext());
                               // databaseReference.child(notificationManager.getAppId()).child("Notifications").setValue("You have new orders");
                                Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                                int total_attachments = UserProfile.fileItems.size();
                                String[] vendorEmail = {data.get(position).getVendorEmail()};
                                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Files from "+vendorEmail);
                                emailIntent.setType("message/rfc822");
                                emailIntent.putExtra(Intent.EXTRA_EMAIL,vendorEmail);
                                ArrayList<Uri> uris = new ArrayList<>();
                                for(int i = 0;i<total_attachments;i++) {
                                    uris.add(UserProfile.fileItems.get(i).getPath());
                                }
                                emailIntent.putExtra(Intent.EXTRA_STREAM, uris);
                                startActivityForResult(Intent.createChooser(emailIntent, "Send email..."),OPENING_PAYEMENTS);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert=a_builder.create();
                alert.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == OPENING_PAYEMENTS){
            String orderID = databaseReference.push().getKey();
            databaseReference.child(new NotificationManager(data.get(pos).getVendorEmail(), getApplicationContext()).getAppId()).child("orders").child(orderID).setValue(user.getCurrentUser().getEmail());
            databaseReference.child(new NotificationManager(data.get(pos).getVendorEmail(), getApplicationContext()).getAppId()).child("ordersId").child(orderID).setValue(orderID);
            Toast.makeText(getApplicationContext(), "Redirecting to pay the amount", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), payments.class));
        }
    }
}
