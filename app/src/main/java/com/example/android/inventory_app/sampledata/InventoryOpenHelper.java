package com.example.android.inventory_app.sampledata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.inventory_app.sampledata.InventoryContract.InventoryEntry;

public class InventoryOpenHelper extends SQLiteOpenHelper {

    // constant for database table name and version
    public static final String DATABASE_NAME = InventoryContract.InventoryEntry.TABLE_NAME + ".db";
    public static final int DATABASE_VERSION = 1;

    // create constructor
    public InventoryOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Implement onCreate()
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the stock table
        final String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + InventoryEntry.TABLE_NAME + " ("
                + InventoryEntry.COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + InventoryEntry.COLUMN_ITEM_PRODUCT + " TEXT NOT NULL, "
                + InventoryEntry.COLUMN_ITEM_PRICE + " INTEGER, "
                + InventoryEntry.COLUMN_ITEM_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
                + InventoryEntry.COLUMN_ITEM_SUPPLIER + " TEXT, "
                + InventoryEntry.COLUMN_ITEM_SUPPLIER_NUMBER + " TEXT);";

        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // create a new table and create new db
    }
}
