package com.example.itemmanagment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class ItemManagementDatabase extends SQLiteOpenHelper {

    private static final String databaseName = "ItemManagement.db";
    private static final int version = 1;
   // private final Context context;

    public ItemManagementDatabase(Context context){
        super(context,databaseName,null,version);
    }


    public static final class Items{
        public static final String TABLE = "items";
        public static final String COL_ID = "_id";
        public static final String COL_ITEMNAME = "itemName";
        public static final String COL_DESCRIPTION = "description";
        public static final String COL_QUANTITY = "quantity";
        public static final String COL_IMAGE = "image";

    }

    public static final class Users{
        public static final String TABLE = "users";
        public static final String COL_ID = "_id";
        public static final String COL_USERNAME = "username";
        public static final String COL_PASSWORD = "password";
        public static final String COL_NOTIFS = "notifs";

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the items table
        db.execSQL("CREATE TABLE " + Items.TABLE + " (" +
                Items.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Items.COL_ITEMNAME + " TEXT, " +
                Items.COL_DESCRIPTION + " TEXT, " +
                Items.COL_QUANTITY + " INTEGER, " +
                Items.COL_IMAGE + " BLOB)");

        // Create the users table
        db.execSQL("CREATE TABLE " + Users.TABLE + " (" +
                Users.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Users.COL_USERNAME + " TEXT, " +
                Users.COL_PASSWORD + " TEXT, " +
                Users.COL_NOTIFS + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing tables
        db.execSQL("DROP TABLE IF EXISTS " + Items.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Users.TABLE);

        // Recreate the tables
        onCreate(db);
    }

    //Create
    public long addItem(String itemName, String description, int quantity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Items.COL_ITEMNAME, itemName);
        values.put(Items.COL_DESCRIPTION, description);
        values.put(Items.COL_QUANTITY, quantity);

        long itemId = (db.insert(Items.TABLE, null, values));
        return itemId;
    }

    //Read
    public Cursor getLowCountItem(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(Items.TABLE, null, Items.COL_QUANTITY + " <= ?", new String[]{"5"}, null, null, null);
    }
    //Update
    public int updateItemQuantity(long itemId, int newQuantity){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Items.COL_QUANTITY, newQuantity);

        String itemSelect = Items.COL_ID + "=?";
        String[] args = {String.valueOf(itemId)};

        return db.update(Items.TABLE, values, itemSelect, args);
    }
    //Delete
    public int deleteItem(long itemId){
        SQLiteDatabase db = this.getWritableDatabase();
        String itemSelect = Items.COL_ID + "=?";
        String[] args = {String.valueOf(itemId)};
        return db.delete(Items.TABLE, itemSelect, args);
    }

    public Cursor getAllItems(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] project = {Items.COL_ID, Items.COL_ITEMNAME, Items.COL_DESCRIPTION, Items.COL_QUANTITY};
        return db.query(Items.TABLE, project, null, null, null, null, null);
    }

    public List<DataItem> fetchData() {
        List<DataItem> dataList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        if (db != null) {
            Cursor cursor = null;
            try {
                String[] projection = {
                        Items.COL_ITEMNAME,
                        Items.COL_DESCRIPTION,
                        Items.COL_QUANTITY,
                        // Add other columns as needed
                };

                cursor = db.query(
                        Items.TABLE,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        null
                );

                while (cursor.moveToNext()) {
                    String itemName = cursor.getString(cursor.getColumnIndexOrThrow(Items.COL_ITEMNAME));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow(Items.COL_DESCRIPTION));
                    int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(Items.COL_QUANTITY));

                    DataItem dataItem = new DataItem(itemName, description, quantity);
                    dataList.add(dataItem);
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

        return dataList;
    }

}


