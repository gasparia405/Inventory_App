package com.example.android.inventory_app.sampledata;

import android.content.ContentResolver;
import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.ContactsContract;

public class InventoryContract {

    public InventoryContract(){}

    // Identify Content Authority
    public static final String CONTENT_AUTHORITY = "com.example.android.inventory_app";

    // Create Base Content Uri
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public final static String PATH_STOCK = "stock";

    public static final class InventoryEntry implements BaseColumns {

        // Create complete Content Uri
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_STOCK);

        // set table name
        public static final String TABLE_NAME = "stock";

        // Set column names - _id, product, price, quantity, supplier, supplierNumber
        public final static String COLUMN_ITEM_ID = "_id";
        public final static String COLUMN_ITEM_PRODUCT = "product";
        public final static String COLUMN_ITEM_PRICE = "price";
        public final static String COLUMN_ITEM_QUANTITY = "quantity";
        public final static String COLUMN_ITEM_SUPPLIER = "supplier";
        public final static String COLUMN_ITEM_SUPPLIER_NUMBER = "supplier_name";

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STOCK;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STOCK;

    }

    /** URI matcher code for the content URI for the pets table */
    private static final int STOCK = 100;

    /** URI matcher code for the content URI for a single pet in the pets table */
    private static final int STOCK_ID = 101;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        sUriMatcher.addURI(ContactsContract.AUTHORITY, PATH_STOCK, STOCK);
        sUriMatcher.addURI(ContactsContract.AUTHORITY, PATH_STOCK, STOCK_ID);
    }

}
