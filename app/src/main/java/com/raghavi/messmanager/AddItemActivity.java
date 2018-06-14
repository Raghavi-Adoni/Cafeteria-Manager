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
import com.google.firebase.database.FirebaseDatabase;

import static com.raghavi.messmanager.MainActivity.mFirebaseDatabase;
import  static com.raghavi.messmanager.MainActivity.tabType;
import static java.lang.Integer.parseInt;

public class AddItemActivity extends AppCompatActivity {


    public EditText addItemEditText;
    Button addItemButton;
    SharedPreferences sharedPreferences;

    protected static DatabaseReference SnacksDatabaseReference;
    private FirebaseDatabase firebaseDatabase;
    protected static DatabaseReference BreakFastDatabaseReference;
    protected static DatabaseReference LunchDatabaseReference;
    protected static DatabaseReference DinnerDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        initialise();

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String foodItemNameString = addItemEditText.getText().toString();

                FoodItemData obj = new FoodItemData();
                obj.setFoodItemName(foodItemNameString);

                if(foodItemNameString.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please enter the name",Toast.LENGTH_SHORT).show();

                }
                else {
                    if (tabType.equals("Snacks")) {
                        SnacksFragment.dataset.add(obj);
                        SnacksDatabaseReference.push().setValue(foodItemNameString);
                    } else if (tabType.equals("BreakFast")) {
                        BreakFastFragment.dataset.add(obj);
                        BreakFastDatabaseReference.push().setValue(foodItemNameString);
                    } else if (tabType.equals("Lunch")) {
                        LunchFragment.dataset.add(obj);
                        LunchDatabaseReference.push().setValue(foodItemNameString);
                    } else if (tabType.equals("Dinner")) {
                        DinnerFragment.dataset.add(obj);
                        DinnerDatabaseReference.push().setValue(foodItemNameString);
                    }
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

        firebaseDatabase = FirebaseDatabase.getInstance();
        SnacksDatabaseReference = firebaseDatabase.getReference().child("snacks");
        BreakFastDatabaseReference = firebaseDatabase.getReference().child("breakfast");
        LunchDatabaseReference = firebaseDatabase.getReference().child("lunch");
        DinnerDatabaseReference = firebaseDatabase.getReference().child("dinner");

        sharedPreferences=getApplicationContext().getSharedPreferences("com.raghavi.messmanager", Context.MODE_PRIVATE);
    }
}
