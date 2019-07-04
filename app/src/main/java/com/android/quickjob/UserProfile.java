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

<<<<<<< Updated upstream
=======
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
>>>>>>> Stashed changes
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

<<<<<<< Updated upstream
=======
import java.io.ByteArrayOutputStream;
import java.io.File;
>>>>>>> Stashed changes
import java.net.URI;
import java.net.URISyntaxException;

<<<<<<< Updated upstream
import static android.content.Intent.ACTION_GET_CONTENT;
=======
import static android.content.Intent.ACTION_AIRPLANE_MODE_CHANGED;
import static android.content.Intent.ACTION_CAMERA_BUTTON;
import static android.content.Intent.ACTION_GET_CONTENT;
import static android.content.Intent.ACTION_PICK;
import static android.content.Intent.ACTION_SEND_MULTIPLE;
import static android.content.Intent.createChooser;
>>>>>>> Stashed changes

public class UserProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final int REQUEST_FOR_GALLERY_IMAGE = 2;
    private static final int REQUEST_FOR_CAMERA_IMAGE = 3;
    private static final int REQUEST_CODE_SCANNER = 4 ;
    private DrawerLayout drawer;
    private Button btnAdd, btnGallery, btnFileManager, btnCamScanner, btnCamera,btnVendorList;
    private RecyclerView recyclerView;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    private static final int REQUEST_CODE_FILES = 1;
    Uri file;
    UploadTask uploadTask;
    FirebaseUser user;

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

        } else if(v == btnCamScanner){
            Intent intent = getPackageManager().getLaunchIntentForPackage("com.intsig.camscanner");
            startActivityForResult(intent, REQUEST_CODE_SCANNER);

        }
    }
<<<<<<< Updated upstream
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
=======

    public void setTheFields () {
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
            user = FirebaseAuth.getInstance().getCurrentUser();
            storageReference = FirebaseStorage.getInstance().getReference(user.getEmail());
            databaseReference = FirebaseDatabase.getInstance().getReference("Files");
            navigationView.bringToFront();
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
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

        public void uploadImage (Intent data){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] array = baos.toByteArray();
            String id = System.currentTimeMillis() + "image";
            StorageReference upload = storageReference.child(id);
            uploadTask = (UploadTask) upload.putBytes(array).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(UserProfile.this, "Added to cart", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UserProfile.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

>>>>>>> Stashed changes

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void uploadFiles (Intent data){
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

        public void putFileInStorage (Uri file){
            StorageReference upload = storageReference.child(databaseReference.push().getKey());
            uploadTask = (UploadTask) upload.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
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

