package com.android.quickjob;

import android.content.Context;
import android.content.SharedPreferences;

public class NotificationManager {
    SharedPreferences sharedPreferences;
    Context context;
    SharedPreferences.Editor editor;

    public NotificationManager(String email,Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences(email,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    public void setAppId(String id) {
        editor.putString("userid",id);
        editor.apply();
    }

    public String getAppId() {
        return sharedPreferences.getString("userid",null);
    }
}
