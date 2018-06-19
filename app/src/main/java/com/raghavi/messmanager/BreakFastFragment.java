package com.raghavi.messmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.raghavi.messmanager.AddItemActivity.BreakFastDatabaseReference;
import static com.raghavi.messmanager.AddItemActivity.SnacksDatabaseReference;
import static com.raghavi.messmanager.MainActivity.mFirebaseDatabase;
import  static com.raghavi.messmanager.MainActivity.tabType;
import static com.raghavi.messmanager.MainActivity.userType;

/**
 * Created by Raghavi on 6/3/2018.
 */

public class BreakFastFragment extends Fragment {
    public static ArrayList<FoodItemData> dataset=new ArrayList<>();

    private RecyclerView mRecyclerView;
    private MessMenuAdapter adapter;
    ValueEventListener eventListener;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        BreakFastDatabaseReference=mFirebaseDatabase.getReference().child("breakfast");

        dataset.clear();
        eventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    FoodItemData obj = new FoodItemData(String.valueOf(ds.getValue()));

                    dataset.add(obj);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        BreakFastDatabaseReference.addListenerForSingleValueEvent(eventListener);

        FloatingActionButton fab=(FloatingActionButton)view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userType.equals("Student"))
                {
                    orderSnacks();
                }
                else if(userType.equals("Employee"))
                {
                    changeMenu();
                    // viewOrders();
                }
            }
        });


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        adapter = new MessMenuAdapter(dataset);
        mRecyclerView.setAdapter(adapter);

        return view;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        BreakFastDatabaseReference.removeEventListener(eventListener);
        eventListener=null;

    }
    public void orderSnacks()
    {
        startActivity(new Intent(getContext(),OrderSnacksActivity.class));
    }

    public void changeMenu()
    {
        Intent i = new Intent(getContext(),AddItemActivity.class);
        tabType="BreakFast";
        startActivity(i);
    }
}
