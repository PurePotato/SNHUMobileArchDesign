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

public class Database extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DataAdapter adapter;
    private List<DataItem> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.dataRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up sample data
        itemList = new ArrayList<>();
        itemList.add(new DataItem("Item 1", new Date()));
        itemList.add(new DataItem("Item 2", new Date()));
        itemList.add(new DataItem("Item 3", new Date()));

        // Initialize and set the adapter
        adapter = new DataAdapter(itemList);
        recyclerView.setAdapter(adapter);
        AtomicInteger itemNumber = new AtomicInteger(3);
        // Add Button Click Listener
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            // Implement add functionality here
            itemNumber.addAndGet(1);
            itemList.add(new DataItem("New Item " + itemNumber, new Date()));
            adapter.notifyDataSetChanged();
        });
    }
}

