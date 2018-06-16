package com.raghavi.messmanager;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Adapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.raghavi.messmanager.AddItemActivity.SnacksDatabaseReference;
import static com.raghavi.messmanager.MainActivity.mFirebaseDatabase;
import static java.lang.String.valueOf;

public class ViewOrderActivity extends AppCompatActivity {

    private static ArrayList<OrderFormat> ordersDataset=new ArrayList<>();;
    RecyclerView ordersRecyclerView;
    public MessOrderAdapter adapter;
    ValueEventListener valueEventListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ordersDatabaseReference;
    private DatabaseReference ordersUserIDDatabaseReference;
    private DatabaseReference ordersTimeDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        firebaseDatabase = FirebaseDatabase.getInstance();
        ordersDatabaseReference = firebaseDatabase.getReference().child("snacks_order");
        ordersUserIDDatabaseReference = firebaseDatabase.getReference().child("snacks_order").child("userID");
        ordersTimeDatabaseReference = firebaseDatabase.getReference().child("snacks_order").child("time");



        valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                   OrderFormat obj = new OrderFormat(String.valueOf(ds.child("foodItemName").getValue()),String.valueOf(ds.child("userID").getValue()),String.valueOf(ds.child("time").getValue()));
                   // OrderFormat obj=new OrderFormat(ds.getValue());
                    Log.i("FOOD",String.valueOf(obj));
                    ordersDataset.add(obj);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        ordersDatabaseReference.addListenerForSingleValueEvent(valueEventListener);



        ordersRecyclerView=findViewById(R.id.orders_recycler_view);
        adapter=new MessOrderAdapter(ordersDataset,this);

        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(ViewOrderActivity.this));
        ordersRecyclerView.setAdapter(adapter);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        SnacksDatabaseReference.removeEventListener(valueEventListener);
        valueEventListener=null;

    }
}
