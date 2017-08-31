package com.vickmwas.contentproviderdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    /**
     * todo using: ContentProviders , ContentResolver , CursorAdapter
     *  1. When Add is clicked, create a dialog button with edit text for FirstName and LastName
     *  2. When Delete is clicked, create a dialog with listview, and onItemSelected method that deletes the selected record.
     *  3. Edit will be added later.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("Hello","World");
    }

    public void editFriend(View view) {
        Toast.makeText(this, "Editing Record", Toast.LENGTH_LONG).show();
    }

    public void deleteFriend(View view) {
        Toast.makeText(this, "Deleting Record", Toast.LENGTH_LONG).show();
    }

    public void addFriend(View view) {
        Toast.makeText(this, "Adding Record", Toast.LENGTH_LONG).show();
    }
}
