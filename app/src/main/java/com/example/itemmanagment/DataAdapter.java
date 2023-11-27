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
    private int itemCount;
    public DataAdapter(List<DataItem> itemList) {
        this.itemList = itemList;
        this.itemCount = 0;
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
        holder.itemDateTextView.setText(item.getItemDate());

        // Update item count
        holder.itemCounterTextView.setText("Item Count: " + itemCount);

        // Set click listeners for the increment and decrement buttons
        holder.incrementButton.setOnClickListener(v -> {
            itemCount++;
            holder.itemCounterTextView.setText("Item Count: " + itemCount);
        });

        holder.decrementButton.setOnClickListener(v -> {
            if (itemCount > 0) {
                itemCount--;
                holder.itemCounterTextView.setText("Item Count: " + itemCount);
            }
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
