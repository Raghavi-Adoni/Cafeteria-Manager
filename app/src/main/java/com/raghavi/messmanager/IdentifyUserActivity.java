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
import com.google.firebase.database.FirebaseDatabase;

import static com.raghavi.messmanager.MainActivity.mFirebaseDatabase;


public class IdentifyUserActivity extends AppCompatActivity {


    SharedPreferences sharedPreferences;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mUsersDatabaseReference;
    private DatabaseReference mEmployeeDatabaseReference;
    public static String userEmailID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_user);
        sharedPreferences=getApplicationContext().getSharedPreferences("com.raghavi.messmanager", Context.MODE_PRIVATE);

        firebaseDatabase = FirebaseDatabase.getInstance();
       mUsersDatabaseReference = firebaseDatabase.getReference().child("students");
        mEmployeeDatabaseReference= firebaseDatabase.getReference().child("employee");

        userEmailID=sharedPreferences.getString("User_Email_id", "Unidentified");
    }

   public void onMessPersonButtonClick(View view)
    {
        //mUsersDatabaseReference.push().setValue(sharedPreferences.getString("User_Email_id", "Unidentified"));

        mEmployeeDatabaseReference.push().setValue(userEmailID);

        Intent i = new Intent(this, PasswordActivity.class);
        sharedPreferences.edit().putString("UserType","Employee").apply();
        startActivity(i);


    }

    public void onStudentRadioButtonClick(View view) {


        mUsersDatabaseReference.push().setValue(userEmailID);
        Intent i = new Intent(this, MainActivity.class);
        sharedPreferences.edit().putString("UserType","Student").apply();
        startActivity(i);
        finish();

       // userEmailID=sharedPreferences.getString("User_Email_id", "Unidentified");
    }
    public void onClick(View view)
    {
        startActivity(new Intent(this,MainActivity.class));
    }
}
