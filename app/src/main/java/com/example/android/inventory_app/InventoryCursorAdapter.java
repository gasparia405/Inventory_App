package com.example.android.inventory_app;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventory_app.sampledata.InventoryContract;

public class InventoryCursorAdapter extends CursorAdapter {

    public InventoryCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.stock_list_item, parent, false);
    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.item_name);
        TextView priceTextView = (TextView) view.findViewById(R.id.item_price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.item_quantity);

        // Find the columns of pet attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_ITEM_PRODUCT);
        int priceColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_ITEM_PRICE);
        int quantityColumnIndex = cursor.getColumnIndexOrThrow(InventoryContract.InventoryEntry.COLUMN_ITEM_QUANTITY);

        // Read the pet attributes from the Cursor for the current pet
        String itemName = cursor.getString(nameColumnIndex);
        String itemPrice = String.format("%s%s", context.getResources().getString(R.string.dollar_sign), cursor.getString(priceColumnIndex));
        String itemQuantity = String.format("%s %s", cursor.getString(quantityColumnIndex), context.getResources().getString(R.string.quantity_statement));

        // If the pet breed is empty string or null, then use some default text
        // that says "Unknown breed", so the TextView isn't blank.
        if (TextUtils.isEmpty(itemQuantity)) {
            itemQuantity = "0";
        }

        // Update the TextViews with the attributes for the current pet
        nameTextView.setText(itemName);
        priceTextView.setText(itemPrice);
        quantityTextView.setText(itemQuantity);

        Button saleButton = (Button) view.findViewById(R.id.sale_button);
        saleButton.setText(R.string.sale_button);
        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int columnIdIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_ITEM_ID);
                int quantityIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_ITEM_QUANTITY);
                String col= cursor.getString(columnIdIndex);
                String quan= cursor.getString(quantityIndex);
                decreaseQuantity(context, Integer.valueOf(col), Integer.valueOf(quan));
            }
        });
    }

    private void decreaseQuantity(Context context, int columnId, int quantity) {

        if (quantity > 0) {
            quantity--;
        } else {
            Toast.makeText(context,
                    context.getResources().getString(R.string.decrement_quantity_error),
                    Toast.LENGTH_SHORT).show();
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(InventoryContract.InventoryEntry.COLUMN_ITEM_QUANTITY, quantity);

        Uri updateUri = ContentUris.withAppendedId(InventoryContract.InventoryEntry.CONTENT_URI, columnId);

        int rowsAffected = context.getContentResolver().update(updateUri, contentValues, null, null);
    }
}
