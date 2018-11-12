package com.example.android.inventory_app;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.example.android.inventory_app.sampledata.InventoryContract.InventoryEntry;
import com.example.android.inventory_app.sampledata.InventoryOpenHelper;

public class MainActivity extends AppCompatActivity {

    private InventoryOpenHelper mDbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDbHelper = new InventoryOpenHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    // Insert data into the database
    private void insertData() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COLUMN_ITEM_PRODUCT, "Oreos");
        values.put(InventoryEntry.COLUMN_ITEM_PRICE, 3);
        values.put(InventoryEntry.COLUMN_ITEM_QUANTITY, 20);
        values.put(InventoryEntry.COLUMN_ITEM_SUPPLIER, "Nabisco");
        values.put(InventoryEntry.COLUMN_ITEM_SUPPLIER_NUMBER, "413-566-2668");

        long newRowId = db.insert(InventoryEntry.TABLE_NAME, null, values);

    }

    // Read info from the database
    private void displayDatabaseInfo() {
        // Create readable database instance
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Create projection that defines what we want to read from the database
        String[] projection = {
                InventoryEntry.COLUMN_ITEM_ID,
                InventoryEntry.COLUMN_ITEM_PRODUCT,
                InventoryEntry.COLUMN_ITEM_PRICE,
                InventoryEntry.COLUMN_ITEM_QUANTITY,
                InventoryEntry.COLUMN_ITEM_SUPPLIER,
                InventoryEntry.COLUMN_ITEM_SUPPLIER_NUMBER
        };

        // Create Cursor object to read from the database
        Cursor cursor = db.query(
                InventoryEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            String tableHeader =
                    InventoryEntry.COLUMN_ITEM_ID + " - " +
                            InventoryEntry.COLUMN_ITEM_PRODUCT + " - " +
                            InventoryEntry.COLUMN_ITEM_PRICE + " - " +
                            InventoryEntry.COLUMN_ITEM_QUANTITY + " - " +
                            InventoryEntry.COLUMN_ITEM_SUPPLIER + " - " +
                            InventoryEntry.COLUMN_ITEM_SUPPLIER_NUMBER + "\n";

            int idColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_ITEM_ID);
            int productColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_ITEM_PRODUCT);
            int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_ITEM_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_ITEM_QUANTITY);
            int supplierColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_ITEM_SUPPLIER);
            int supplierNumberColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_ITEM_SUPPLIER_NUMBER);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentProduct = cursor.getString(productColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSupplier = cursor.getString(supplierColumnIndex);
                String currentSupplierNumber = cursor.getString(supplierNumberColumnIndex);

                String currentItemString = "\n" + currentID + " - " +
                        currentProduct + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        currentSupplier + " - " +
                        currentSupplierNumber;
            }

        } finally {
            cursor.close();
        }

    }
}
