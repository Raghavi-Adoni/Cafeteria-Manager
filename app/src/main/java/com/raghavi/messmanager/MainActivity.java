package com.raghavi.messmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int RC_SIGN_IN = 1;

    protected static FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUsersDatabaseReference;
    private DatabaseReference mEmployeeDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    String userType="";


    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_Layout);
        tabLayout.addTab(tabLayout.newTab().setText("BreakFast"));
        tabLayout.addTab(tabLayout.newTab().setText("Lunch"));
        tabLayout.addTab(tabLayout.newTab().setText("Snacks"));
        tabLayout.addTab(tabLayout.newTab().setText("Dinner"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final TabsPagerAdapter adapter = new TabsPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mUsersDatabaseReference = mFirebaseDatabase.getReference().child("students");
        mEmployeeDatabaseReference= mFirebaseDatabase.getReference().child("employee");

        sharedPreferences=getApplicationContext().getSharedPreferences("com.raghavi.messmanager", Context.MODE_PRIVATE);

       // userType=getIntent().getExtras().getString("UserSelection","noneSelected");
        Log.i("user type is",userType);
        //start IdentifyUserActivity
        userType=sharedPreferences.getString("UserSelection","noneSelected");
        if(userType.equals("noneSelected"))
        {
            Intent i = new Intent(this, IdentifyUserActivity.class);
            startActivity(i);
            //userTypeSelected=true;
          //  userType=getIntent().getExtras().getString("UserSelection","noneSelected");
           //sharedPreferences.edit().putString("UserSelection",userType).apply();
            userType=sharedPreferences.getString("UserSelection","noneSelected");
        }




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


//
//        mMessagesDatabaseReference.addChildEventListener(mChildEventListener);
        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user==null){
                    //user is signed out
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.EmailBuilder().build(),
                                            new AuthUI.IdpConfig.GoogleBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }
                Log.i("Email id",user.getEmail());


                sharedPreferences.edit().putString("User_Email_id",user.getEmail()).apply();

                if(!sharedPreferences.getBoolean("CheckUserLoggedIn",false)) {
                    //userLoggedIn=true;
                    sharedPreferences.edit().putBoolean("CheckUserLoggedIn",true).apply();
                    if (userType.equals("Student")) {

                        mUsersDatabaseReference.push().setValue(sharedPreferences.getString("User_Email_id", "Unidentified"));
                    } else if (userType.equals("Employee"))
                        mEmployeeDatabaseReference.push().setValue(sharedPreferences.getString("User_Email_id", "Unidentified"));
                }


            }


        };

        Log.i("Logged in user is ",sharedPreferences.getString("User_Email_id","Unidentified"));
        //mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_signout) {
            AuthUI.getInstance().signOut(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(mAuthStateListener!=null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN)
        {
            if(resultCode==RESULT_OK)
            {
                Toast.makeText(this,"Signed in!",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,"Sign in Cancelled!",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        mFirebaseAuth.addAuthStateListener(mAuthStateListener);

    }

}
