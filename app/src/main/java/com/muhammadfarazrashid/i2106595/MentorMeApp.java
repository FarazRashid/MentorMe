package com.muhammadfarazrashid.i2106595;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class MentorMeApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /* Enable disk persistence  */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
