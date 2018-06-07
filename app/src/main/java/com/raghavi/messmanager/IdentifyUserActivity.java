package com.raghavi.messmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.google.firebase.database.DatabaseReference;

import static com.raghavi.messmanager.MainActivity.mFirebaseDatabase;

public class IdentifyUserActivity extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    private DatabaseReference mUsersDatabaseReference;
    private DatabaseReference mEmployeeDatabaseReference;
    static String userTypeChosen="none";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_user);
        sharedPreferences=getApplicationContext().getSharedPreferences("com.raghavi.messmanager", Context.MODE_PRIVATE);

      //  mUsersDatabaseReference = mFirebaseDatabase.getReference().child("students");
        //mEmployeeDatabaseReference= mFirebaseDatabase.getReference().child("employee");

        sharedPreferences.edit().putBoolean(" userTypeChosen",true).apply();

    }

   public void onMessPersonButtonClick(View view)
    {
       // mUsersDatabaseReference.push().setValue(sharedPreferences.getString("User_Email_id", "Unidentified"));

        Intent i = new Intent(this, MainActivity.class);
        sharedPreferences.edit().putString("UserType","Employee").apply();
        startActivity(i);
        finish();

    }

    public void onStudentRadioButtonClick(View view) {
        //mEmployeeDatabaseReference.push().setValue(sharedPreferences.getString("User_Email_id", "Unidentified"));

        Intent i = new Intent(this, MainActivity.class);
        sharedPreferences.edit().putString("UserType","Student").apply();
        startActivity(i);
        finish();
    }
}
