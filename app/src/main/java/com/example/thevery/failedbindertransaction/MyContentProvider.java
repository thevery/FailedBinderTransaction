package com.example.thevery.failedbindertransaction;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Process;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyContentProvider extends ContentProvider {
    public final static String AUTHORITY = "com.example.thevery.failedbindertransaction";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    private MySQLiteHelper helper;


    public MyContentProvider() {
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    @Nullable
    public String getType(@NonNull Uri uri) {
        return "dummy";
    }

    @Override
    @Nullable
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        // Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        helper = new MySQLiteHelper(getContext());
        return true;
    }

    @Override
    @Nullable
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int pid = Process.myPid();
        Log.d(MainActivity.TAG, "MyContentProvider.query: pid = " + pid);
        SQLiteDatabase database = helper.getReadableDatabase();
/*

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                kill(pid);
            }
        }).start();

*/
        return database.query(MySQLiteHelper.ACCOUNTS_TABLE, null, null, null, null, null, null);
    }

    private void kill(int pid) {
        Log.d(MainActivity.TAG, "MyContentProvider: kill!");
        Process.killProcess(pid);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        // Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
