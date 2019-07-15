package com.android.quickjob;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static android.content.Intent.ACTION_GET_CONTENT;
import static android.content.Intent.ACTION_PICK;
import static android.content.Intent.createChooser;


public class UserProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final int REQUEST_FOR_GALLERY_IMAGE = 2;
    private static final int REQUEST_FOR_CAMERA_IMAGE = 3;
    private static final int REQUEST_CODE_SCANNER = 4;
    private DrawerLayout drawer;
    private Button btnAdd, btnGallery, btnFileManager, btnCamScanner, btnCamera, btnVendorList;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    private static final int REQUEST_CODE_FILES = 1;
    Uri file;
    UploadTask uploadTask;
    FirebaseUser user;
    private static ArrayList<FileItems> fileItems;
    private String path;
    private String fileId;
    private String fileName;
    private int requestCode;
    private int resultCode;
    @Nullable
    private Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setTheFields();
        setRecyclerView();
        vendorList();
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

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


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        if (v == btnFileManager) {
            Intent intent = new Intent();
            intent.setType("*/*");
            intent.setAction(ACTION_GET_CONTENT);
            startActivityForResult(createChooser(intent, "Select File(s)"), REQUEST_CODE_FILES);
        } else if (v == btnGallery) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(ACTION_PICK);
            startActivityForResult(createChooser(intent, "Pick Image"), REQUEST_FOR_GALLERY_IMAGE);

        } else if (v == btnCamera) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(createChooser(intent, "Pick Image"), REQUEST_FOR_CAMERA_IMAGE);

        } else if (v == btnCamScanner) {
            Intent intent = getPackageManager().getLaunchIntentForPackage("com.intsig.camscanner");
            startActivity(intent);

        }
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void uploadFiles(Intent data) {
        if (data.getClipData() != null) {
            for (int i = 0; i < data.getClipData().getItemCount(); ++i) {
                file = data.getClipData().getItemAt(i).getUri();
                putFileInStorage(file);

            }
        } else {
            file = data.getData();
            putFileInStorage(file);
        }
    }


    public void setRecyclerView() {
        fileItems = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewItems);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new RecyclerViewAdapter(fileItems, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
                StorageReference desertRef = storageReference.child(fileItems.get(position).getPath());
                // Delete the file
                desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // File deleted successfully
                        Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Uh-oh, an error occurred!
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                fileItems.remove(position);
                adapter.notifyItemRemoved(position);

            }

            @Override
            public void onRadioClick(int position, int index) {
                if (index == 0)
                    fileItems.get(position).setFileCost(1);
                else if (index == 1)
                    fileItems.get(position).setFileCost(2);

                adapter.notifyItemChanged(position);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;

    }

    public void vendorList() {
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VendorList.class);
                startActivity(intent);
            }
        });
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
        navigationView.bringToFront();
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
        user = FirebaseAuth.getInstance().getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference(user.getEmail());
        databaseReference = FirebaseDatabase.getInstance().getReference("Files");
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FOR_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uploadFiles(data);
        } else if (requestCode == REQUEST_FOR_CAMERA_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uploadImage(data);
        } else if (requestCode == REQUEST_CODE_FILES && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uploadFiles(data);
        } else if (requestCode == REQUEST_CODE_SCANNER && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Toast.makeText(getApplicationContext(), "Added baki", Toast.LENGTH_LONG).show();
            Intent file = data.getParcelableExtra(Intent.EXTRA_STREAM);
            uploadFiles(file);
        }
    }

    public void uploadImage(Intent data) {
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] array = baos.toByteArray();
        fileId = databaseReference.push().getKey();
        StorageReference upload = storageReference.child(fileId);
        uploadTask = (UploadTask) upload.putBytes(array).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileItems.add(new FileItems(getFileName(file),1,1,fileId));
                adapter.notifyDataSetChanged();

                Toast.makeText(UserProfile.this, "Added to cart", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserProfile.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void putFileInStorage(final Uri file) {
        fileId = databaseReference.push().getKey();
        StorageReference upload = storageReference.child(fileId);
        uploadTask = (UploadTask) upload.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileItems.add(new FileItems(getFileName(file),1,1,fileId));
                adapter.notifyDataSetChanged();
                Toast.makeText(UserProfile.this, "Added in cart", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserProfile.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}