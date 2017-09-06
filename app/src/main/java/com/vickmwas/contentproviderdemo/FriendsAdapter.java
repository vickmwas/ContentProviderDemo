package com.vickmwas.contentproviderdemo;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder>{

    // ContactsAdapter instance variables
    private Cursor cursor;
    private final ContactClickListener clickListener;
    // constructor
    public FriendsAdapter(ContactClickListener clickListener) {
        this.clickListener = clickListener;
    }


    // interface implemented to respond when the user touches an item in the Friend's List
    interface ContactClickListener {
        void onClick(Uri friendUri);
    }

    // nested subclass of RecyclerView.ViewHolder used to implement the view-holder pattern in the context of a RecyclerView
    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private long rowID;

        // configures a RecyclerView item's ViewHolder
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClick(FriendsDbProvider.buildFriendUri(rowID));
                }
            });
        }


        // set the database row ID for the contact in this ViewHolder
        void setRowID(long rowID) {
            this.rowID = rowID;
        }
}


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view); // return current item's ViewHolder
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        holder.setRowID(cursor.getLong(cursor.getColumnIndex(FriendsDbProvider.SCHEMA.COLUMN_ID)));
        String firstName = cursor.getString(cursor.getColumnIndex(FriendsDbProvider.SCHEMA.COLUMN_FIRST_NAME));
        String lastName = cursor.getString(cursor.getColumnIndex(FriendsDbProvider.SCHEMA.COLUMN_LAST_NAME));

        holder.textView.setText(firstName + " " + lastName);
    }

    @Override
    public int getItemCount() {
        return (cursor != null) ? cursor.getCount() : 0;
    }


    // swap this adapter's current Cursor for a new one
    public void swapCursor(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }











}
