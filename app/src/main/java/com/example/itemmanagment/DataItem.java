package com.example.itemmanagment;

public class DataItem {
    private long id;
    private String itemName;
    private String description;
    private int quantity;

    // Constructor
    public DataItem(String itemName, String description, int quantity) {
        this.itemName = itemName;
        this.description = description;
        this.quantity = quantity;
    }

    // Getters
    public String getItemName() {
        return itemName;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setters
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getItemId() {
        return id;
    }
}