package com.vickmwas.contentproviderdemo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;


public class FriendsDbProvider extends ContentProvider{
    FriendsDbHelper dbHelper;
    SQLiteDatabase db;
    String TAG = "FriendsDbProvider";

    static final String PROVIDER_NAME = "com.vickmwas.contentproviderdemo.FriendsDbProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/friends";
    static final Uri CONTENT_URI = Uri.parse(URL);


    public static class SCHEMA{
        static final String TABLE_NAME = "friends";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_FIRST_NAME = "first_name";
        public static final String COLUMN_NAME_NAME = "last_name";

    }

    //todo Write Insert query

    @Override
    public boolean onCreate() {
        dbHelper = new FriendsDbHelper(getContext());
        db = dbHelper.openDataBase();

        Log.e(TAG,"Invoked onCreate(). Db Instance opened");
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long rowId = db.insert(SCHEMA.TABLE_NAME, "",values);

        if (rowId > 0){
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }

        throw new SQLException("Failed to add a record into " + uri);


    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public void shutdown() {
        super.shutdown();
        Log.e(TAG,"Invoked shutdown(); Closing Db Instance");
        dbHelper.close();
    }
}
