package com.example.urltest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static  final String DATABASE_NAME = "EBook.db";
    public static  final String TABLE_NAME = "Downloaded_Books";
    public static  final String COL_1 = "ID";
    public static  final String COL_2 = "Title";
    public static  final String COL_3 = "Publisher";

    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+"("+COL_1+" TEXT PRIMARY KEY, "+COL_2+" TEXT, "+COL_3+" TEXT)");
        Log.v("DB", "DatabaseCreated");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
