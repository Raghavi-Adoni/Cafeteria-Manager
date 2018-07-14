package com.raghavi.messmanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

/**
 * Created by Raghavi on 6/3/2018.
*/


public class MessMenuAdapter extends RecyclerView.Adapter<MessMenuAdapter.ViewHolder> {

    private ArrayList<FoodItemData> data;

    Context context;


    public MessMenuAdapter(ArrayList<FoodItemData> data,Context context) {
        this.data = data;
        this.context=context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.single_row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.singleFoodItemTextView.setText(String.valueOf(data.get(position).getFoodItemName()));
    }


    @Override
    public int getItemCount() {
//       notifyItemInserted(getItemCount()+1);
        return data.size();
    }

    public String getObject(int pos)
    {
        FoodItemData obj= data.get(pos);
        return obj.getFoodItemName();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView singleFoodItemTextView;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.singleFoodItemTextView = (TextView) itemView.findViewById(R.id.single_food_item_textview);
        }
    }
}
