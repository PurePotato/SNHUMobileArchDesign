package com.example.itemmanagment;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private static final int SMS_PERMISSION_REQUEST_CODE = 1;
    private static final String CHANNEL_ID = "com.example.itemmanagment.NOTIFICATION_CHANNEL";
    private boolean isSmsPermissionAccepted = false;
    private ItemManagementDatabase dbHelper = new ItemManagementDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        // Login button click
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Validate username and password
                if (!username.isEmpty() && !password.isEmpty() && authenticateUser(username, password) ) {
                    // Display a toast message for now
                    showToast("Logged in successfully!");
                    navigateToDatabaseScreen();
                } else {
                    showToast("Please enter both username and password or create an account.");
                }
            }
        });

        // Create Account button click
        Button createAccountButton = findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Validate username and password (you can add more validation)
                if (!username.isEmpty() && !password.isEmpty()) {
                    // Perform account creation logic (e.g., add to database

                    if(createAccount(username, password)){

                        //isSmsPermissionAccepted = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
                        if(ContextCompat.checkSelfPermission(MainActivity.this,android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_REQUEST_CODE);
                            if(ContextCompat.checkSelfPermission(MainActivity.this,android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                                isSmsPermissionAccepted = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;

                            }
                        }
                        showToast("Account created successfully!");

                        navigateToDatabaseScreen();
                    }else{
                        showToast("Username already exists.");
                    }
                } else {
                    showToast("Please enter both username and password.");
                }
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void navigateToDatabaseScreen(){
        Intent intent = new Intent(MainActivity.this, DatabaseViewer.class);
        startActivity(intent);
    }

    private void sendSms() {
        String phoneNumber = "1234567890"; // Replace with the recipient's phone number
        String message = "Hello from InventoryIQ!"; // Replace with your desired message

        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.setData(Uri.parse("smsto:" + Uri.encode(phoneNumber)));
        smsIntent.putExtra("sms_body", message);

        if (smsIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(smsIntent);
        } else {
            showToast("SMS app not available on this device.");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, send SMS
                sendSms();
            } else {
                showToast("SMS permission denied. Cannot send SMS.");
            }
        }
    }

    private boolean authenticateUser(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {ItemManagementDatabase.Users.COL_ID};
        String selection = ItemManagementDatabase.Users.COL_USERNAME + "=? AND " +
                ItemManagementDatabase.Users.COL_PASSWORD + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(ItemManagementDatabase.Users.TABLE, projection, selection, selectionArgs, null, null, null);

        boolean isAuthenticated = cursor.moveToFirst();
        cursor.close();
        return isAuthenticated;
    }

    private boolean createAccount(String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Check if the username already exists
        String[] projection = {ItemManagementDatabase.Users.COL_ID};
        String selection = ItemManagementDatabase.Users.COL_USERNAME + "=?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(ItemManagementDatabase.Users.TABLE, projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            return false; // Username already exists
        }

        // Insert the new account into the database
        ContentValues values = new ContentValues();
        values.put(ItemManagementDatabase.Users.COL_USERNAME, username);
        values.put(ItemManagementDatabase.Users.COL_PASSWORD, password);
        long newRowId = db.insert(ItemManagementDatabase.Users.TABLE, null, values);

        cursor.close();
        return newRowId != -1; // Return true if the insert was successful
    }
}