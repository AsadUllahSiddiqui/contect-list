package com.example.sqliteex;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {
    public MyDBHelper(@Nullable Context context) {
        super(context, ContactContract.DB_NAME, null, ContactContract.DB_VERSION);
    }

    String CREATE_TABLE="CREATE TABLE "
            +ContactContract.Contact._ContactTable +" ("+
            ContactContract.Contact._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            ContactContract.Contact._Name+" TEXT, "+
            ContactContract.Contact._Phno+" TEXT, "+
            ContactContract.Contact._Email+" TEXT, "+
            ContactContract.Contact._Address+" TEXT "+
        ");";

    String DROP_TABLE="DROP TABLE IF EXISTS "+ContactContract.Contact._ContactTable;


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}
