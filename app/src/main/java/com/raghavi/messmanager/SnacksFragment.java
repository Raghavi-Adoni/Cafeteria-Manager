package com.raghavi.messmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.raghavi.messmanager.AddItemActivity.SnacksDatabaseReference;
import static com.raghavi.messmanager.MainActivity.mFirebaseDatabase;

/**
 * Created by Raghavi on 6/3/2018.
 */

public class SnacksFragment extends Fragment {
    public static ArrayList<FoodItemData> dataset=new ArrayList<>();

    private RecyclerView mRecyclerView;
    private MessMenuAdapter adapter;
    SharedPreferences sharedPreferences;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        sharedPreferences=this.getActivity().getSharedPreferences("com.raghavi.messmanager", Context.MODE_PRIVATE);
//

        SnacksDatabaseReference=mFirebaseDatabase.getReference().child("snacks");
      SnacksDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot arraySnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post

                    FoodItemData obj = new FoodItemData(arraySnapshot.getValue(FoodItemData.class));
                    //FoodItemData obj=dataSnapshot.getValue(FoodItemData.class);
                    Log.i("Data Read :", String.valueOf(arraySnapshot.getValue(FoodItemData.class)));
                    dataset.add(obj);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    /*  SnacksDatabaseReference.addChildEventListener(new ChildEventListener() {
          @Override
          public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
              FoodItemData obj=new FoodItemData(String.valueOf(dataSnapshot.getValue(FoodItemData.class)));
              //FoodItemData obj=dataSnapshot.getValue(FoodItemData.class);
              Log.i("Data Read :", String.valueOf(dataSnapshot.getValue(FoodItemData.class)));
              dataset.add(obj);
              adapter.notifyDataSetChanged();
          }

          @Override
          public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
          @Override
          public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}
          @Override
          public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {}
      });

*/
        View view = inflater.inflate(R.layout.fragment_layout, container, false);

        FloatingActionButton fab=(FloatingActionButton)view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),AddItemActivity.class);
                sharedPreferences.edit().putString("CurrentFragment","Snacks").apply();
                startActivity(i);
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

}

