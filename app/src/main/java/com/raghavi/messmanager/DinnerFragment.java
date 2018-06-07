package com.raghavi.messmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import  static com.raghavi.messmanager.MainActivity.tabType;
/**
 * Created by Raghavi on 6/3/2018.
 */

public class DinnerFragment extends Fragment {
    public static ArrayList<FoodItemData> dataset=new ArrayList<>();


    private RecyclerView mRecyclerView;
    private MessMenuAdapter adapter;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

       // sharedPreferences=this.getActivity().getSharedPreferences("com.raghavi.messmanager", Context.MODE_PRIVATE);

        View view = inflater.inflate(R.layout.fragment_layout, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        FloatingActionButton fab=(FloatingActionButton)view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),AddItemActivity.class);
                //sharedPreferences.edit().putString("CurrentFragment","Dinner").apply();
                tabType="Dinner";
                startActivity(i);
            }
        });



        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);




        adapter = new MessMenuAdapter(dataset);
        mRecyclerView.setAdapter(adapter);

        return view;
    }
}
