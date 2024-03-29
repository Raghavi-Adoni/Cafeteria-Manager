package com.raghavi.messmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.raghavi.messmanager.AddItemActivity.SnacksDatabaseReference;
import static com.raghavi.messmanager.MainActivity.mFirebaseDatabase;

import  static com.raghavi.messmanager.MainActivity.tabType;
import static com.raghavi.messmanager.MainActivity.userType;

/**
 * Created by Raghavi on 6/3/2018.
 */

public class SnacksFragment extends Fragment {
    public static ArrayList<FoodItemData> dataset=new ArrayList<>();


    private RecyclerView mRecyclerView;
    private MessMenuAdapter adapter;
    ValueEventListener eventListener;
    DatabaseReference ref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        SnacksDatabaseReference=mFirebaseDatabase.getReference().child("snacks");

        dataset.clear();

        eventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    FoodItemData obj = new FoodItemData(String.valueOf(ds.getValue()));
                    Log.i("FOOD",String.valueOf(obj));
                    dataset.add(obj);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        SnacksDatabaseReference.addListenerForSingleValueEvent(eventListener);

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

        adapter = new MessMenuAdapter(dataset,getContext());
        mRecyclerView.setAdapter(adapter);

        delete();
        return view;
    }

    private void delete() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {

                new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.Theme_AppCompat))
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("Do you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //TODO: Remove variable name from list if the command contains one

                                String name = adapter.getObject(viewHolder.getAdapterPosition());
                                Log.i("Name to be", name);
                                removeItem(name);
                                dataset.remove(viewHolder.getAdapterPosition());
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .show();

            }
        });
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SnacksDatabaseReference.removeEventListener(eventListener);
        eventListener=null;

    }
    public void orderSnacks()
    {
        startActivity(new Intent(getContext(),OrderSnacksActivity.class));
    }

    public void changeMenu()
    {
        Intent i = new Intent(getContext(),AddItemActivity.class);
        tabType="Snacks";
        startActivity(i);
    }


    public void viewOrders()
    {
        startActivity(new Intent(getContext(),ViewOrderActivity.class));
    }

    public void removeItem(String name) {
        ref = FirebaseDatabase.getInstance().getReference();
        Query foodItemQuery = ref.child("snacks").orderByValue().equalTo(name);

        foodItemQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();
                    break;
                }
            }

            @Override

            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

