package com.raghavi.messmanager;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.raghavi.messmanager.IdentifyUserActivity.userEmailID;
import static com.raghavi.messmanager.SnacksFragment.dataset;

public class OrderSnacksActivity extends AppCompatActivity {

    Spinner snacksSelectionSpinner;
    ArrayAdapter<FoodItemData> snacksAdapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference snacksOrderDatabaseReference;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_snacks);

        snacksSelectionSpinner = findViewById(R.id.snacks_selection_spinner);
        snacksAdapter=new ArrayAdapter<FoodItemData>(this,android.R.layout.simple_list_item_1,dataset);
        snacksSelectionSpinner.setAdapter(snacksAdapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        snacksOrderDatabaseReference = firebaseDatabase.getReference().child("snacks_order");

    }

    public void onOrderSnakcsButtonCLick(View view)
    {
        OrderFormat obj=new OrderFormat(snacksSelectionSpinner.getSelectedItem().toString(),userEmailID);
        snacksOrderDatabaseReference.push().setValue(obj);
    }
}
