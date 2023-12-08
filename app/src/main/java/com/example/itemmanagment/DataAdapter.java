package com.example.itemmanagment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private List<DataItem> itemList;
    private String itemCount;
    public DataAdapter(List<DataItem> itemList) {
        this.itemList = itemList;

    }

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
        holder.itemDateTextView.setText(item.getQuantity());
        itemCount = Integer.toString(item.getQuantity());
        // Update item count
        holder.itemCounterTextView.setText("Item Count: " + itemCount);

        // Set click listeners for the increment and decrement buttons
        holder.incrementButton.setOnClickListener(v -> {
            //TODO: Add code to increase quantity (setQuantity method?)
            holder.itemCounterTextView.setText("Item Count: " + itemCount);
        });

        holder.decrementButton.setOnClickListener(v -> {
            //TODO: Add code to handle quantities = 0
            if (itemCount > 0) {
                //TODO: Add code to decrease quantity (setQuantity method?)
                holder.itemCounterTextView.setText("Item Count: " + itemCount);
            }
            //TODO: Add code for sending SMS when item quantity equals 0
        });

        // Set click listener for the delete button
        holder.deleteButton.setOnClickListener(v -> {
            // Implement delete functionality here
            itemList.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView;
        TextView itemDateTextView;
        TextView itemCounterTextView;
        Button deleteButton;
        Button incrementButton;
        Button decrementButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            itemDateTextView = itemView.findViewById(R.id.itemDateTextView);
            itemCounterTextView = itemView.findViewById(R.id.itemCounterTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            incrementButton = itemView.findViewById(R.id.incrementButton);
            decrementButton = itemView.findViewById(R.id.decrementButton);
        }
    }
}
