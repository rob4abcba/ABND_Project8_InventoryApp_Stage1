package com.example.android.abnd_project8_inventoryapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.abnd_project8_inventoryapp.data.InventoryContract.InventoryEntry;
import com.example.android.abnd_project8_inventoryapp.data.InventoryDBHelper;

public class MainActivity extends AppCompatActivity {

    /** Database helper that will provide us access to the database */
    private InventoryDBHelper dbHelper;

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new InventoryDBHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        insertItem();
        queryDB();

    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    private void queryDB() {

        // Create and/or open a database to read from it
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        String[] projection = {
                InventoryEntry._ID,
                InventoryEntry.COLUMN_PRODUCT_NAME,
                InventoryEntry.COLUMN_PRODUCT_QUANTITY,
                InventoryEntry.COLUMN_PRODUCT_PRICE,
                InventoryEntry.COLUMN_PRODUCT_SUPPLIER,
                InventoryEntry.COLUMN_PRODUCT_SUPPLIER_PHONE
        };

        Cursor cursor =db.query(
                InventoryEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        try {

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(InventoryEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
            int quantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_QUANTITY);
            int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_PRICE);
            int supplierColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_SUPPLIER);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(InventoryEntry
                    .COLUMN_PRODUCT_SUPPLIER_PHONE);


            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the item
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                float currentPrice = cursor.getFloat(priceColumnIndex);
                String currentSupplier = cursor.getString(supplierColumnIndex);
                String currentSupplierPhone = cursor.getString(supplierPhoneColumnIndex);

                Log.e(LOG_TAG, "***Begin Database Dump***");
                Log.e(LOG_TAG, "ID: " + currentID);
                Log.e(LOG_TAG, "current name: " + currentName);
                Log.e(LOG_TAG, "current quantity: " + currentQuantity);
                Log.e(LOG_TAG, "current price: " + currentPrice);
                Log.e(LOG_TAG, "current supplier: " + currentSupplier);
                Log.e(LOG_TAG, "current supplier phone: " + currentSupplierPhone);
                Log.e(LOG_TAG, "***End Database Dump***");
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }



    /**
     * Helper method to insert hardcoded data into the database.
     */
    private void insertItem() {
        // Gets the database in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COLUMN_PRODUCT_NAME, "Widget");
        values.put(InventoryEntry.COLUMN_PRODUCT_PRICE, 12.95f);
        values.put(InventoryEntry.COLUMN_PRODUCT_QUANTITY, 10);
        values.put(InventoryEntry.COLUMN_PRODUCT_SUPPLIER, "Jimmy's House of Widgets");
        values.put(InventoryEntry.COLUMN_PRODUCT_SUPPLIER_PHONE, "8004943438");

        // Insert a new row in the database.
        // The first argument for db.insert() is the inventory table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto.
        db.insert(InventoryEntry.TABLE_NAME, null, values);
    }
}
