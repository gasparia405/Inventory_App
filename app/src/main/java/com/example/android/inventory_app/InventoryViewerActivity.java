package com.example.android.inventory_app;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.inventory_app.sampledata.InventoryContract.InventoryEntry;

public class InventoryViewerActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ITEM_LOADER = 0;

    InventoryCursorAdapter mInventoryCursorAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_viewer);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InventoryViewerActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        ListView inventoryView = (ListView) findViewById(R.id.stock_list);

        View emptyView = findViewById(R.id.empty_view);
        inventoryView.setEmptyView(emptyView);

        mInventoryCursorAdapter = new InventoryCursorAdapter(this,null);

        // Attach PetCursorAdapter to the ListView
        inventoryView.setAdapter(mInventoryCursorAdapter);

        inventoryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(InventoryViewerActivity.this, EditorActivity.class);

                Uri currentPetUri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, id);

                intent.setData(currentPetUri);

                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(ITEM_LOADER, null, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    // Insert data into the database via Insert Dummy Data menu option
    private void insertData() {

        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COLUMN_ITEM_PRODUCT, "Oreos");
        values.put(InventoryEntry.COLUMN_ITEM_PRICE, 3);
        values.put(InventoryEntry.COLUMN_ITEM_QUANTITY, "20");
        values.put(InventoryEntry.COLUMN_ITEM_SUPPLIER, "Nabisco");
        values.put(InventoryEntry.COLUMN_ITEM_SUPPLIER_NUMBER, "413-566-2668");

        Uri newUri = getContentResolver().insert(InventoryEntry.CONTENT_URI, values);


    }

    /**
     * Helper method to delete all pets in the database.
     */
    private void deleteAllInventory() {
        int rowsDeleted = getContentResolver().delete(InventoryEntry.CONTENT_URI, null, null);
        Log.v("InventoryViewerActivity", rowsDeleted + " rows deleted from pet database");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertData();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                deleteAllInventory();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                InventoryEntry.COLUMN_ITEM_ID,
                InventoryEntry.COLUMN_ITEM_PRODUCT,
                InventoryEntry.COLUMN_ITEM_PRICE
        };

        return new CursorLoader(this,
                InventoryEntry.CONTENT_URI,   // The content URI of the words table
                projection,             // The columns to return for each row
                null,                   // Selection criteria
                null,                   // Selection criteria
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mInventoryCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mInventoryCursorAdapter.swapCursor(null);
    }
}

