package com.raghavi.messmanager;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.raghavi.messmanager.AddItemActivity.SnacksDatabaseReference;

public class UsersViewOrdersActivity extends AppCompatActivity {

    private ArrayList<UsersViewOrderFormat> userOrdersDataset=new ArrayList<>();;
    RecyclerView userOrdersRecyclerView;
    public UsersOrderAdapter usersOrderAdapter;
    ValueEventListener valueEventListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userOrdersDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_view_orders);

        firebaseDatabase = FirebaseDatabase.getInstance();
        userOrdersDatabaseReference = firebaseDatabase.getReference().child("order_viewed");





        valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    UsersViewOrderFormat obj = new UsersViewOrderFormat(String.valueOf(ds.child("foodItemName").getValue()),String.valueOf(ds.child("orderStatus").getValue()));
                    userOrdersDataset.add(obj);
                }
                usersOrderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        userOrdersDatabaseReference.addListenerForSingleValueEvent(valueEventListener);

        userOrdersRecyclerView=findViewById(R.id.users_orders_recycler_view);
        usersOrderAdapter=new UsersOrderAdapter(userOrdersDataset,this);

        userOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(UsersViewOrdersActivity.this));
        userOrdersRecyclerView.setAdapter(usersOrderAdapter);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        userOrdersDatabaseReference.removeEventListener(valueEventListener);
        valueEventListener=null;

    }
}

