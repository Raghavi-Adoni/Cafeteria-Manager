package com.raghavi.messmanager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

import static com.raghavi.messmanager.IdentifyUserActivity.userEmailID;


/**
 * Created by Raghavi on 6/10/2018.
 */

public class MessOrderAdapter extends RecyclerView.Adapter<MessOrderAdapter.ViewHolder> {
    private ArrayList<OrderFormat> data;

    Context context;

    public MessOrderAdapter(ArrayList<OrderFormat> data,Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public MessOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View listItem = layoutInflater.inflate(R.layout.mess_single_order, parent, false);
        MessOrderAdapter.ViewHolder viewHolder = new MessOrderAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MessOrderAdapter.ViewHolder holder, int position) {
        holder.orderedFoodItemTextView.setText(String.valueOf(data.get(position).getFoodItemName()));
        holder.userTextView.setText(String.valueOf(data.get(position).getUserID()));
        holder.orderTimeTextView.setText(String.valueOf(data.get(position).getTime()));

    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView orderedFoodItemTextView;
        public TextView userTextView;
        public TextView orderTimeTextView; 
        public Button cancelOrderButton;
        public Button orderReadyButton;

        String uniqueID;

        private DatabaseReference ref;
        FirebaseDatabase firebaseDatabase;
        DatabaseReference orderViewedDatabaseReference;

        SharedPreferences sharedPreferences;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.orderedFoodItemTextView = (TextView) itemView.findViewById(R.id.ordered_item_text_view_mess_side);
            this.userTextView = (TextView) itemView.findViewById(R.id.user_text_view);
            this.orderTimeTextView = (TextView) itemView.findViewById(R.id.order_time_text_view);

            this.cancelOrderButton = (Button) itemView.findViewById(R.id.cancel_order_button);
            this.orderReadyButton = (Button) itemView.findViewById(R.id.order_ready_button);


         //   sharedPreferences.getSharedPreferences("com.raghavi.messmanager", Context.MODE_PRIVATE);

            Log.i("VALUE",userTextView.getText().toString());

            orderReadyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("TEXTVIEW VALUE", orderedFoodItemTextView.getText().toString());
                    updateOrderStatus("Ready");
                    removeItem();
                }
            });

           cancelOrderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(itemView.getContext());
                    LayoutInflater inflater = LayoutInflater.from((Activity) context);
                    dialogBuilder.setMessage("Available Quantity?");
                    dialogBuilder.setCancelable(false);

                    View dialogView = inflater.inflate(R.layout.alert_dialog_cancel_snacks, null);
                    dialogBuilder.setView(dialogView);

                    final Spinner cancelSpinner = (Spinner) dialogView.findViewById(R.id.cancel_order_spinner);
                    String[] arraySpinner = new String[]{"0","1", "2", "3", "4", "5"};
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(view.getRootView().getContext(),
                            android.R.layout.simple_list_item_1, arraySpinner);
                    cancelSpinner.setAdapter(spinnerAdapter);


                    dialogBuilder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            updateOrderStatus("CANCELLED (Availability :"+ cancelSpinner.getSelectedItem().toString()+" )");
                            dialog.cancel();
                            removeItem();
                        }
                    });
                    dialogBuilder.setNegativeButton("DENY", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                }
            });

        }
        public void removeItem()
        {
            ref = FirebaseDatabase.getInstance().getReference();
            Query foodItemQuery= ref.child("snacks_order").orderByChild("foodItemName").equalTo(orderedFoodItemTextView.getText().toString());

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

        public void updateOrderStatus(String orderStatus)
        {
            firebaseDatabase=FirebaseDatabase.getInstance();
            orderViewedDatabaseReference=firebaseDatabase.getReference().child("order_viewed");
             uniqueID=orderTimeTextView.getText().toString().substring(10)+userTextView.getText().toString().substring(5);
            OrderFormat obj1=new OrderFormat(orderedFoodItemTextView.getText().toString(),userTextView.getText().toString(),orderTimeTextView.getText().toString(),orderStatus,uniqueID);
            orderViewedDatabaseReference.push().setValue(obj1);
        }

    }


}
