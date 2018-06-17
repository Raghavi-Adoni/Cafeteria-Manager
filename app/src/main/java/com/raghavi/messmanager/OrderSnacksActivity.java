package com.raghavi.messmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import static com.raghavi.messmanager.IdentifyUserActivity.userEmailID;
import static com.raghavi.messmanager.SnacksFragment.dataset;

public class OrderSnacksActivity extends AppCompatActivity {

    Spinner snacksSelectionSpinner;
    ArrayAdapter<FoodItemData> snacksAdapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference snacksOrderDatabaseReference;

    public String uniqueID;

    SharedPreferences sharedPreferences;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_snacks);

        snacksSelectionSpinner = findViewById(R.id.snacks_selection_spinner);
        sharedPreferences=getApplicationContext().getSharedPreferences("com.raghavi.messmanager", Context.MODE_PRIVATE);

        snacksAdapter=new ArrayAdapter<FoodItemData>(this,R.layout.spinner_item,dataset);
        snacksSelectionSpinner.setAdapter(snacksAdapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        snacksOrderDatabaseReference = firebaseDatabase.getReference().child("snacks_order");


    }

    public void onOrderSnacksButtonCLick(View view)
    {
       // String mytime = java.text.DateFormat.getTimeInstance().format(Calendar.getInstance().getTime());
        String dateTime = (DateFormat.format("dd-MM-yyyy hh:mm:ss", new java.util.Date()).toString());
        String userID=sharedPreferences.getString("User_Email_id", "Unidentified");
        String uniqueID=dateTime.substring(10)+userID.substring(5);

        OrderFormat obj1=new OrderFormat(snacksSelectionSpinner.getSelectedItem().toString(),userID,dateTime,"Not Ready",uniqueID);
        snacksOrderDatabaseReference.push().setValue(obj1);

       // obj.onTokenRefresh();
    }
}
