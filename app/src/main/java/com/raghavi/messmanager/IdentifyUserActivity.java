package com.raghavi.messmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class IdentifyUserActivity extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_user);
        sharedPreferences=getApplicationContext().getSharedPreferences("com.raghavi.messmanager", Context.MODE_PRIVATE);
    }

   public void onMessPersonButtonClick(View view)
    {
        Intent i = new Intent(this, MainActivity.class);
        sharedPreferences.edit().putString("UserSelection","Employee").apply();
        //i.putExtra("UserSelection","Employee");
        startActivity(i);
        finish();

    }

    public void onStudentRadioButtonClick(View view) {
        Intent i = new Intent(this, MainActivity.class);
        sharedPreferences.edit().putString("UserSelection","Student").apply();
        //i.putExtra("UserSelection","Student");
        startActivity(i);
        finish();
    }
}
