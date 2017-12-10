package com.heavenlyhell.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SandyDbAdapter {

    SQLiteDatabase sqLiteDatabase;
    public SandyDbAdapter(Context context) {
        sqLiteDatabase = new SandyDbHelper(context).getWritableDatabase();
    }

    public long insert(String username, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        return sqLiteDatabase.insert(SandyDbHelper.TABLE_NAME, null, contentValues);
    }

    public Cursor select() {
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + SandyDbHelper.TABLE_NAME, null);
        return cursor;
    }

    static class SandyDbHelper extends SQLiteOpenHelper{
        private static final String TAG = "database";
        private static final String DATABASE_NAME = "logindatabase";
        private static final String TABLE_NAME = "PERSON";
        private static final int DATABASE_VERSION = 1;
        private static final String UID = "_id";
        private static final String USERNAME = "username";
        private static final String PASSWORD = "password";
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                USERNAME+" VARCHAR(255), "+PASSWORD+" VARCHAR(255));";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private Context context;

        public SandyDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            Message.displayMessage(context, "Constructor !!");
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                Message.displayMessage(context, "onCreate !!");
                sqLiteDatabase.execSQL(CREATE_TABLE);
            } catch (SQLException exception) {
                Log.d(TAG, exception.getMessage());
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            try {
                Message.displayMessage(context, "onUpgrade !!");
                sqLiteDatabase.execSQL(DROP_TABLE);
                onCreate(sqLiteDatabase);
            } catch (SQLException exception) {
                Log.d(TAG, exception.getMessage());
            }
        }
    }


}
