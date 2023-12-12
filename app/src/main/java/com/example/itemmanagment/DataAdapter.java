package com.example.itemmanagment;

import static androidx.core.content.ContextCompat.startActivity;
import static com.google.android.material.internal.ContextUtils.getActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.content.ContextCompat;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private static final int SMS_THRESHOLD = 5;
    private Context context;
    private boolean isSmsPermissionAccepted;
    private List<DataItem> itemList;
    private String itemCount;
    public DataAdapter(List<DataItem> itemList, ItemManagementDatabase dbHelper, boolean isSmsPermissionAccepted, Context context) {
        this.itemList = itemList;
        this.dbHelper = dbHelper;
        this.isSmsPermissionAccepted = isSmsPermissionAccepted;
        this.context = context;
    }
    public DataAdapter(List<DataItem> itemList, ItemManagementDatabase dbHelper) {
        this.itemList = itemList;
        this.dbHelper = dbHelper;
    }
    private ItemManagementDatabase dbHelper;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataItem item = itemList.get(position);

        // Set other views
        holder.itemNameTextView.setText(item.getItemName());
        holder.itemDescriptionView.setText(item.getDescription());
        itemCount = Integer.toString(item.getQuantity());
        // Update item count
        holder.itemCounterTextView.setText("Item Count: " + itemCount);

        // Set click listeners for the increment and decrement buttons
        holder.incrementButton.setOnClickListener(v -> {
            //TODO: Add code to increase quantity (setQuantity method?)
            int updatedQuantity = item.getQuantity() + 1;
            dbHelper.updateItemQuantity(item.getItemId(), updatedQuantity);
            item.setQuantity(updatedQuantity);
            holder.itemCounterTextView.setText("Item Count: " + updatedQuantity);
            notifyDataSetChanged();

        });

        holder.decrementButton.setOnClickListener(v -> {
            //TODO: Add code to handle quantities = 0
            if (Integer.parseInt(itemCount) > 0) {
                //TODO: Add code to decrease quantity (setQuantity method?)
                int updatedQuantity = item.getQuantity() - 1;
                dbHelper.updateItemQuantity(item.getItemId(), updatedQuantity);
                //itemCount = Integer.toString(item.getQuantity());
                item.setQuantity(updatedQuantity);
                holder.itemCounterTextView.setText("Item Count: " + updatedQuantity);
                checkAndSendSMS(updatedQuantity);
                notifyDataSetChanged();
            }
            //TODO: Add code for sending SMS when item quantity equals 0
        });

        // Set click listener for the delete button
        holder.deleteButton.setOnClickListener(v -> {
            // Implement delete functionality here
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                itemList.remove(adapterPosition);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
    private void checkAndSendSMS(int updatedQuantity) {
        if (isSmsPermissionAccepted && updatedQuantity < SMS_THRESHOLD) {
            sendSms(); // Call your method to send SMS
        }
    }

    private void sendSms() {
        String phoneNumber = "+1234567890"; // Replace with the recipient's phone number
        String message = "Hello from InventoryIQ! You have low inventory on an item. Please check your inventory!"; // Replace with your desired message

        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.setData(Uri.parse("smsto:" + Uri.encode(phoneNumber)));
        smsIntent.putExtra("sms_body", message);

        if (smsIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(smsIntent);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView;
        TextView itemDescriptionView;
        TextView itemCounterTextView;
        Button deleteButton;
        Button incrementButton;
        Button decrementButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            itemDescriptionView = itemView.findViewById(R.id.itemDescriptionView);
            itemCounterTextView = itemView.findViewById(R.id.itemCounterTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            incrementButton = itemView.findViewById(R.id.incrementButton);
            decrementButton = itemView.findViewById(R.id.decrementButton);
        }
    }
}
