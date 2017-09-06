package com.vickmwas.contentproviderdemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.amitshekhar.DebugDB;
import com.vickmwas.contentproviderdemo.FriendsAdapter.ContactClickListener;

import static com.vickmwas.contentproviderdemo.FriendsDbProvider.CONTENT_URI;
import static com.vickmwas.contentproviderdemo.FriendsDbProvider.SCHEMA.COLUMN_FIRST_NAME;
import static com.vickmwas.contentproviderdemo.FriendsDbProvider.SCHEMA.COLUMN_LAST_NAME;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    /**
     * todo using: ContentProviders , ContentResolver , CursorAdapter
     *  1. When Add is clicked, create a dialog button with edit text for FirstName and LastName
     *  2. When Delete is clicked, create a dialog with listview, and onItemSelected method that deletes the selected record.
     *  3. Edit will be added later.
     */

    ConstraintLayout addFriendLayout, mainActivityLayout;
    EditText firstNameEditText, lastNameEditText;
    RecyclerView friendsRecyclerView;
    FriendsAdapter friendsAdapter;

    private static final int CONTACTS_LOADER = 1; // identifies Loader
    // used to inform the MainActivity when a contact is selected
    private FriendSelectedListener listener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeLayouts();

        showMainActivityView();

        getSupportLoaderManager().initLoader(CONTACTS_LOADER , null , this);

        String address = DebugDB.getAddressLog();
        Log.e("LOG ADDRESS", address);
    }



    public void initializeLayouts(){
        addFriendLayout = (ConstraintLayout) findViewById(R.id.addFriendLayout);
        mainActivityLayout = (ConstraintLayout) findViewById(R.id.mainActivityView);

        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);

        friendsRecyclerView = (RecyclerView) findViewById(R.id.listView);
        friendsRecyclerView.setLayoutManager(
                new LinearLayoutManager(this.getBaseContext()));
        // create recyclerView's adapter and item click listener
        friendsAdapter = new FriendsAdapter(new ContactClickListener() {
                    @Override
                    public void onClick(Uri contactUri) {
                        listener.onFriendSelected(contactUri);
                    }
                }
        );
        friendsRecyclerView.setAdapter(friendsAdapter); // set the adapter
        // improves performance if RecyclerView's layout size never changes
        friendsRecyclerView.setHasFixedSize(true);
    }

    void showAddFriendLayout(){
        addFriendLayout.setVisibility(View.VISIBLE);
        mainActivityLayout.setVisibility(View.GONE);
    }

    void showMainActivityView(){
        addFriendLayout.setVisibility(View.GONE);
        mainActivityLayout.setVisibility(View.VISIBLE);
    }


    public void editFriend(View view) {
        Toast.makeText(this, "Editing Record", Toast.LENGTH_LONG).show();
    }

    public void deleteFriend(View view) {
        Toast.makeText(this, "Deleting Record", Toast.LENGTH_LONG).show();
    }

    public void addFriend(View view) {
        Toast.makeText(this, "Adding Record", Toast.LENGTH_LONG).show();
        showAddFriendLayout();
    }


    public void closeAddFriendView(View view) {
        showMainActivityView();
    }


    public void saveDetailsToDatabase(View view) {
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);

        Uri insertUri = getContentResolver().insert(CONTENT_URI, values);
        Toast.makeText(this, insertUri + "", Toast.LENGTH_SHORT).show();

        showMainActivityView();
    }

    /**
     * =============================================Loader Methods==============================================
     * */


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data != null)
            friendsAdapter.swapCursor(data); //swap the new cursor in.
        else
            Log.e("LOADER","OnLoadFinished: Cursor is null");

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        friendsAdapter.swapCursor(null);
    }


    interface FriendSelectedListener {
        // called when contact selected
        void onFriendSelected(Uri contactUri);

        // called when add button is pressed
        void onAddFriend    ();
    }
}
