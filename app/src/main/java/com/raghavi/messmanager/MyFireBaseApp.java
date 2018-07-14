package com.raghavi.messmanager;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Raghavi on 7/12/2018.
 */

public class MyFireBaseApp extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
    /* Enable disk persistence  */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}