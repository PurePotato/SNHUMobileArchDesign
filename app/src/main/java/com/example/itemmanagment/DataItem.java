package com.example.itemmanagment;

import java.util.Date;

public class DataItem {
    private String itemName;
    private Date itemDate;

    public DataItem(String itemName,  Date itemDate) {
        this.itemName = itemName;
        this.itemDate = itemDate;
    }

    public String getItemName() {
        return itemName;
    }
    public String getItemDate() {
        return itemDate.toString();
    }
}