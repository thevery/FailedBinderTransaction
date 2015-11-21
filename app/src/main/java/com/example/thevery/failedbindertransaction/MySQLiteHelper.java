package com.example.thevery.failedbindertransaction;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by thevery on 21/11/15.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "demo.db";
    private static final int DATABASE_VERSION = 1;

    private static final String INTEGER_PRIMARY_KEY_AUTOINCREMENT = " integer primary key autoincrement, ";
    private static final String TEXT_NOT_NULL = " text not null";

    public static final String ACCOUNTS_TABLE = "accounts";

    public static final String COLUMN_NAME = "name";
    private static final String DATABASE_CREATE_ACCOUNTS = "create table " + ACCOUNTS_TABLE + "(" +
            BaseColumns._ID + INTEGER_PRIMARY_KEY_AUTOINCREMENT +
            COLUMN_NAME + TEXT_NOT_NULL +
            ");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_ACCOUNTS);
        db.execSQL("INSERT INTO " + ACCOUNTS_TABLE + "(" + COLUMN_NAME + ") VALUES(\"TEST\")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //do nothing
    }
}
