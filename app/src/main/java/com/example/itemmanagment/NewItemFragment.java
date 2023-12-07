package com.example.itemmanagment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewItemFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ItemManagementDatabase dbHelper;
    private EditText itemNameEditText, itemCountEditText, itemDescriptionEditTextMultiLine;
    public NewItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewItemFragment newInstance(String param1, String param2) {
        NewItemFragment fragment = new NewItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_item, container, false);

        itemNameEditText = view.findViewById(R.id.itemNameEditText);
        itemCountEditText= view.findViewById(R.id.itemCountEditText);
        itemDescriptionEditTextMultiLine = view.findViewById(R.id.itemDescriptionEditTextMultiLine);

        Button addButton = view.findViewById(R.id.createItem);
        addButton.setOnClickListener(new View.OnClickListener() {
            // Implement add functionality here
            @Override
            public void onClick(View v){
                String itemName = itemNameEditText.getText().toString().trim();
                String itemDescription = itemDescriptionEditTextMultiLine.getText().toString().trim();
                int quantity = Integer.parseInt(itemCountEditText.getText().toString().trim());

                if(createItem(itemName, itemDescription, quantity)){
                    showToast("Item Added!");

                }else{
                    showToast("Item already exists!");
                }
                closeFragment();
            }

        });
        Button cancelButton = view.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            // Implement close functionality here
            @Override
            public void onClick(View v){
                closeFragment();
            }

        });

        return view;
    }

    private boolean createItem(String itemName, String description, int quantity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Check if the username already exists
        String[] projection = {ItemManagementDatabase.Items.COL_ID};
        String selection = ItemManagementDatabase.Items.COL_ITEMNAME + "=?";
        String[] selectionArgs = {itemName};
        Cursor cursor = db.query(ItemManagementDatabase.Items.TABLE, projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            return false; // Item Name already exists
        }

        // Insert the new item into the database
        ContentValues values = new ContentValues();
        values.put(ItemManagementDatabase.Items.COL_ITEMNAME, itemName);
        values.put(ItemManagementDatabase.Items.COL_DESCRIPTION, description);
        values.put(ItemManagementDatabase.Items.COL_QUANTITY, quantity);
        long newRowId = db.insert(ItemManagementDatabase.Items.TABLE, null, values);

        cursor.close();
        return newRowId != -1; // Return true if the insert was successful
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
    private void closeFragment() {
        // Use the FragmentManager to begin a transaction
        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction()
                    .remove(this) // Remove the current fragment
                    .commit();
        }
    }
}