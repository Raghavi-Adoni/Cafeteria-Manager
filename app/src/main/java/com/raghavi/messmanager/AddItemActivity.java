package com.raghavi.messmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import static com.raghavi.messmanager.MainActivity.mFirebaseDatabase;
import static java.lang.Integer.parseInt;

public class AddItemActivity extends AppCompatActivity {


    public EditText addItemEditText;
    Button addItemButton;
    SharedPreferences sharedPreferences;

    protected static DatabaseReference SnacksDatabaseReference;
    private DatabaseReference BreakFastDatabaseReference;
    private DatabaseReference LunchDatabaseReference;
    private DatabaseReference DinnerDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        initialise();

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Float per;

                String foodItemNameString = addItemEditText.getText().toString();

                FoodItemData obj = new FoodItemData();
                obj.setFoodItemName(foodItemNameString);

                if(sharedPreferences.getString("CurrentFragment","none").equals("Snacks")) {
                    SnacksFragment.dataset.add(obj);
                    SnacksDatabaseReference.push().setValue(foodItemNameString);
                }
                else
                if(sharedPreferences.getString("CurrentFragment","none").equals("BreakFast")) {
                    BreakFastFragment.dataset.add(obj);
                    BreakFastDatabaseReference.push().setValue(foodItemNameString);
                }
                else
                if(sharedPreferences.getString("CurrentFragment","none").equals("Lunch")) {
                    LunchFragment.dataset.add(obj);
                    LunchDatabaseReference.push().setValue(foodItemNameString);
                }
                else
                if(sharedPreferences.getString("CurrentFragment","none").equals("Dinner")) {
                    DinnerFragment.dataset.add(obj);
                    DinnerDatabaseReference.push().setValue(foodItemNameString);
                }

                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);

            }
        });


    }

    public void initialise()
    {
        addItemButton= (Button) findViewById(R.id.add_item_button);
        addItemEditText = (EditText) findViewById(R.id.add_item_edit_text);
        SnacksDatabaseReference = mFirebaseDatabase.getReference().child("snacks");
        BreakFastDatabaseReference = mFirebaseDatabase.getReference().child("breakfast");
        LunchDatabaseReference = mFirebaseDatabase.getReference().child("lunch");
        DinnerDatabaseReference = mFirebaseDatabase.getReference().child("dinner");

        sharedPreferences=getApplicationContext().getSharedPreferences("com.raghavi.messmanager", Context.MODE_PRIVATE);
    }
}
