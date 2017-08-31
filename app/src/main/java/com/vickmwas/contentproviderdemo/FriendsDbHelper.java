package com.vickmwas.contentproviderdemo;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FriendsDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    //destination path (location) of our database on device
    private static String DB_PATH = "";
    private static String DB_NAME = "Friends.db";// Database name
    private final Context mContext;
    private String TAG = "FriendsDbHelper";
    private SQLiteDatabase mDataBase;

    public FriendsDbHelper(Context context) {
        super(context, DB_NAME, null, 1);// 1? Its database Version
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/filepath/filepath/" + context.getPackageName() + "/databases/";
        }
        this.mContext = context;

        createDataBase();
    }

    void createDataBase(){
        //If the database does not exist, copy it from the assets.
        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            this.close();

            try {
                //Copy the database from assets
                copyDataBase();
                Log.e(TAG, "database created");
            } catch (IOException mIOException) {
                Log.e(TAG, "ErrorCopyingDataBase");
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    //Check that the database exists here: /filepath/filepath/your package/databases/Da Name
    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        boolean exists = dbFile.exists();
        if (exists)
            Log.e(TAG, "Database Exists");
        else
            Log.e(TAG, "Database does not exist");

        return exists;
//        return dbFile.exists();
    }

    //Copy the database from assets
    private void copyDataBase() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    //Open the database, so we can query it
    SQLiteDatabase openDataBase() throws SQLException {
        String mPath = DB_PATH + DB_NAME;
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

}