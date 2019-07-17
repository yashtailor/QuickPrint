package com.android.quickjob;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class VendorPendingOrders extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , VendorPendingOrderAdapter.OnCheckBoxPressed {

    private static final int DONE_WITH_ORDER = 1;
    private DrawerLayout drawer;
    private RecyclerView vPendingRecyclerView;
    private VendorPendingOrderAdapter vPendingAdapter;
    private RecyclerView.LayoutManager vPendingLayoutManager;
    private DatabaseReference databaseReference;
    private DatabaseReference orderReferenceDatabase;
    private DatabaseReference userPreviousReference;
    private FirebaseAuth firebaseAuth;
    private ArrayList<OrderData> aod1 = new ArrayList<>();
    private int position;
    private ArrayList<String> orderIdList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_pending_orders);
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

        vPendingRecyclerView = (RecyclerView) findViewById(R.id.vendorPendingRecyclerView);
        vPendingRecyclerView.setHasFixedSize(true);
        vPendingLayoutManager = new LinearLayoutManager(this);
        vPendingAdapter = new VendorPendingOrderAdapter(aod1);
        vPendingAdapter.setOnCheckBoxPressed(this);
        vPendingRecyclerView.setLayoutManager(vPendingLayoutManager);
        vPendingRecyclerView.setAdapter(vPendingAdapter);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Vendors").child(firebaseAuth.getCurrentUser().getUid()).child("orders");
        orderReferenceDatabase = FirebaseDatabase.getInstance().getReference("Vendors").child(firebaseAuth.getCurrentUser().getUid()).child("previousOrders");


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                aod1.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String value = ds.getValue(String.class);
                    //OrderData orderData = ds.getValue(OrderData.class);
                    Log.e("value",value);
                    OrderData orderData = new OrderData(value);
                    aod1.add(orderData);
                    vPendingAdapter.notifyItemInserted(aod1.size());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

    @Override
    public void onCheck(final int pos) {

        position = pos;
        AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
        a_builder.setMessage("Is the order Completed?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // notificationManager=new NotificationManager(data.get(position).getVendorEmail(),getApplicationContext());
                        // databaseReference.child(notificationManager.getAppId()).child("Notifications").setValue("You have new orders");
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        String[] userEmail = {aod1.get(pos).getRecyclerOrderName()};
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, userEmail );
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Order completion");
                        emailIntent.setType("message/rfc822");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "Your Order Has Been Completed Through QuickJobApp");
                        startActivityForResult(Intent.createChooser(emailIntent, "Send email..."), DONE_WITH_ORDER);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = a_builder.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DONE_WITH_ORDER) {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    int index = 0;
                    for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                       if(index == position){
                           ds.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void aVoid) {
                                   makeHistory();
                                   aod1.remove(position);
                                   vPendingAdapter.notifyItemRemoved(position);
                                   Toast.makeText(getApplicationContext(), "Moved to History", Toast.LENGTH_SHORT).show();
                               }
                           }).addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {
                                   Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           });
                       }
                       index++;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void makeHistory() {
        Date date = Calendar.getInstance().getTime();
        String previousOrderID = orderReferenceDatabase.push().getKey();

        VendorPreviousOrdersItems previousOrdersItems = new VendorPreviousOrdersItems(date,aod1.get(position).getRecyclerOrderName());
        orderReferenceDatabase.child(previousOrderID).setValue(previousOrdersItems);

        NotificationManager notificationManager = new NotificationManager(aod1.get(position).getRecyclerOrderName(), this);
        userPreviousReference = FirebaseDatabase.getInstance().getReference("Users").child(notificationManager.getAppId()).child("previousOrders");
        String userPreviousOrderID = userPreviousReference.push().getKey();
        VendorPreviousOrdersItems userPreviousOrdersItems = new VendorPreviousOrdersItems(date,firebaseAuth.getCurrentUser().getEmail());
        userPreviousReference.child(userPreviousOrderID).setValue(userPreviousOrdersItems);
    }

}


