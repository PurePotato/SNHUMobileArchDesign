package com.example.itemmanagment;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseViewer extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DataAdapter adapter;

    private ItemManagementDatabase dbHelper;
    List<DataItem> itemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_viewer);
        dbHelper = new ItemManagementDatabase(this);
        // Initialize RecyclerView
        recyclerView = findViewById(R.id.dataRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();
        // Initialize and set the adapter
        adapter = new DataAdapter(itemList, dbHelper);
        recyclerView.setAdapter(adapter);
        // Add Button Click Listener
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            // Opens a New Item fragment
            NewItemFragment nif = new NewItemFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.databaseViewer, nif)
                    .addToBackStack(null)
                    .commit();
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Update the data in the adapter (you may fetch updated data from your database)
        List<DataItem> updatedData = dbHelper.fetchData(); // Replace with your data fetching logic
        itemList.clear();
        itemList.addAll(updatedData);

        // Notify the adapter of the data change
        adapter.notifyDataSetChanged();
    }
    public void updateRecyclerView(List<DataItem> updatedData) {
        itemList.clear();
        itemList.addAll(updatedData);
        adapter.notifyDataSetChanged();
    }
}

