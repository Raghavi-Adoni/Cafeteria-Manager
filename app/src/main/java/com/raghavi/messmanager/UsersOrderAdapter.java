package com.raghavi.messmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Raghavi on 6/17/2018.
 */
//used to display the user's orders to the user
public class UsersOrderAdapter extends RecyclerView.Adapter<UsersOrderAdapter.ViewHolder>{

    private ArrayList<UsersViewOrderFormat> data;

    Context context;

    public UsersOrderAdapter(ArrayList<UsersViewOrderFormat> data,Context context) {
        this.data = data;
        this.context = context;
    }
    @NonNull
    @Override
    public UsersOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View listItem = layoutInflater.inflate(R.layout.userside_single_order, parent, false);
        UsersOrderAdapter.ViewHolder viewHolder = new UsersOrderAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsersOrderAdapter.ViewHolder holder, int position) {

        holder.userViewOrderfoodItemTextview.setText(String.valueOf(data.get(position).getFoodItemName()));
        holder.orderStatus.setText(String.valueOf(data.get(position).getOrderStatus()));
        holder.userOrderTime.setText(String.valueOf(data.get(position).getTime()));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView userViewOrderfoodItemTextview;
        private TextView orderStatus;
        private TextView userOrderTime;
        public Button orderRecievedOkayButton;
        DatabaseReference ref;


        public ViewHolder(View itemView) {
            super(itemView);

            this.userViewOrderfoodItemTextview = (TextView) itemView.findViewById(R.id.ordered_item_text_view_user_side);
            this.orderStatus = (TextView) itemView.findViewById(R.id.order_status_text_view_user_side);
            this.userOrderTime = (TextView) itemView.findViewById(R.id.order_ready_time_text_view_user_side);

            this.orderRecievedOkayButton=(Button)itemView.findViewById(R.id.user_side_okay_button);

            orderRecievedOkayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeItem();
                }
            });

        }
        public void removeItem()
        {
            ref = FirebaseDatabase.getInstance().getReference();
            Query foodItemQuery= ref.child("order_viewed").orderByChild("foodItemName").equalTo(userViewOrderfoodItemTextview.getText().toString());

            foodItemQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {

                        snapshot.getRef().removeValue();
                        break;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            data.remove(getAdapterPosition());
            notifyDataSetChanged();
        }

    }
}
