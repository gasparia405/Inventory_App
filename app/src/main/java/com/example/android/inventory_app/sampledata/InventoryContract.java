package com.example.android.inventory_app.sampledata;

import android.provider.BaseColumns;

public class InventoryContract {

    public InventoryContract(){}

    public static final class InventoryEntry implements BaseColumns {

        // set table name
        public static final String TABLE_NAME = "stock";

        // Set column names - _id, product, price, quantity, supplier, supplierNumber
        public final static String COLUMN_ITEM_ID = "_id";
        public final static String COLUMN_ITEM_PRODUCT = "product";
        public final static String COLUMN_ITEM_PRICE = "price";
        public final static String COLUMN_ITEM_QUANTITY = "quantity";
        public final static String COLUMN_ITEM_SUPPLIER = "supplier";
        public final static String COLUMN_ITEM_SUPPLIER_NUMBER = "supplier_name";
    }
}
