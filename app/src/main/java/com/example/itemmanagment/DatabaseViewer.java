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
    private List<DataItem> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_viewer);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.dataRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize and set the adapter
        adapter = new DataAdapter(itemList);
        recyclerView.setAdapter(adapter);
        // Add Button Click Listener
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            // Implement add functionality here
            NewItemFragment nif = new NewItemFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.databaseViewer, nif)
                    .addToBackStack(null)
                    .commit();
        });
    }
}

