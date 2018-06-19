package com.raghavi.messmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class PasswordActivity extends AppCompatActivity {

    EditText passwordEditText;
    Button passwordButton;
    String password="1234";
    Long writtenTime;

    SharedPreferences sharedPreferences;
    int loginAttempts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        int defaultTime=0;
        sharedPreferences = getPreferences(MODE_PRIVATE);

        try {
            writtenTime = sharedPreferences.getLong("timer", defaultTime);
        } catch (ClassCastException e) {}

        passwordEditText=(EditText) findViewById(R.id.password_edit_Text);
        passwordButton=(Button) findViewById(R.id.password_button);
        loginAttempts=0;

    }

    protected void onClick(View view)
    {
        loginAttempts++;
        Log.i("Login Attempt",String.valueOf(loginAttempts));

        if(loginAttempts>=3)
        {
            passwordButton.setEnabled(false);
            Long current = Calendar.getInstance().getTime().getTime();
            long interval = (writtenTime == null)? 30000L : current - writtenTime;

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("timer", current);
            editor.apply();

            // countdown and enable button on finish
            new CountDownTimer(current+interval, interval) {
                public void onTick(long millisUntilFinished) {}

                public void onFinish() {
                    passwordButton.setEnabled(true);
                }
            }.start();
        }
        if(passwordEditText.getText().toString().equals(password))
        {
           //14 Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,MainActivity.class));
        }
        else {
            Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
            passwordEditText.setText("");
        }

    }
}
