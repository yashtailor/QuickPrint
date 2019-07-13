package com.android.quickjob;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        final boolean flag1 = sharedPreferences.getBoolean("flag1", false);
        final boolean flag2 = sharedPreferences.getBoolean("flag2", false);

        Thread thread=new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if(flag1 && flag2) {
                        startActivity(new Intent(getApplicationContext(),UserProfile.class));
                    } else if(flag1 && !flag2) {
                        startActivity(new Intent(getApplicationContext(),VendorProfile.class));
                    } else {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                }
            }
        };
        thread.start();
        //startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
