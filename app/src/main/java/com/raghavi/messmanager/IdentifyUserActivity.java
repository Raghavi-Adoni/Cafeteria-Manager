package com.raghavi.messmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.raghavi.messmanager.MainActivity.mFirebaseDatabase;


public class IdentifyUserActivity extends AppCompatActivity {


    SharedPreferences sharedPreferences;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mUsersDatabaseReference;
    private DatabaseReference mEmployeeDatabaseReference;
    public static String userEmailID;
    DatabaseReference ref;
    int flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_user);
        sharedPreferences = getApplicationContext().getSharedPreferences("com.raghavi.messmanager", Context.MODE_PRIVATE);

        flag=0;
        firebaseDatabase = FirebaseDatabase.getInstance();
        mUsersDatabaseReference = firebaseDatabase.getReference().child("students");
        mEmployeeDatabaseReference = firebaseDatabase.getReference().child("employee");

        userEmailID = sharedPreferences.getString("User_Email_id", "Unidentified");
//To:do

    }

    public void onMessPersonButtonClick(View view) {
        //mUsersDatabaseReference.push().setValue(sharedPreferences.getString("User_Email_id", "Unidentified"));


        if (!userEmailID.endsWith("@cumminscollege.in")) {
            mEmployeeDatabaseReference.push().setValue(userEmailID);
            Intent i = new Intent(this, PasswordActivity.class);
            sharedPreferences.edit().putString("UserType", "Employee").apply();
            startActivity(i);
        } else {
            Toast.makeText(this, "Please use your proper email id", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    public void onStudentRadioButtonClick(View view) {


            if(userEmailID.endsWith("@cumminscollege.in")){
            ref = FirebaseDatabase.getInstance().getReference();
            Query foodItemQuery = ref.child("studentsData").orderByChild("email").equalTo(userEmailID);

            foodItemQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        flag=1;
                        mUsersDatabaseReference.push().setValue(userEmailID);
                        Intent i = new Intent(getApplication(), MainActivity.class);
                        sharedPreferences.edit().putString("UserType", "Student").apply();
                        startActivity(i);
                        finish();
                    }
                    if(flag!=1)
                    {
                        Toast.makeText(getApplicationContext(), "Invalid id or unregistered user", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

            /*if(!userEmailID.endsWith("@cumminscollege.in"))
            {
                Toast.makeText(this, "Please use cummins college email id", Toast.LENGTH_SHORT).show();
                finish();
            }
           /* if(flag==1) {
                mUsersDatabaseReference.push().setValue(userEmailID);
                Intent i = new Intent(this, MainActivity.class);
                sharedPreferences.edit().putString("UserType", "Student").apply();
                startActivity(i);
                finish();
            }
            else
           if(flag!=1)
            {
                Toast.makeText(this, "Access allowed to only hostel students", Toast.LENGTH_SHORT).show();
                finish();
            }*/
    }



    public void onClick(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
