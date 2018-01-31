package com.example.thevery.failedbindertransaction;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Date;

public class MyContentProvider extends ContentProvider {
    public final static String AUTHORITY = "com.example.thevery.failedbindertransaction";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    private MySQLiteHelper helper;


    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        return "dummy";
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        helper = new MySQLiteHelper(getContext());
        return true;
    }

    @Override
    public Bundle call(@NonNull String method, String arg, Bundle extras) {
//        if ("0".equals(arg)) {
            selfKill();
//        }
        Bundle bundle = new Bundle();
        bundle.putString("time", new Date().toString());
        return bundle;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final int pid = Process.myPid();
        Log.d(MainActivity.TAG, "MyContentProvider.query: pid = " + pid);
        SQLiteDatabase database = helper.getReadableDatabase();
        selfKill();
        return database.query(MySQLiteHelper.ACCOUNTS_TABLE, null, null, null, null, null, null);
    }

    private void selfKill() {
        System.out.println("MyContentProvider.selfKill");
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(888);
                kill(Process.myPid());
            }
        }).start();
    }

    private void kill(int pid) {
        Log.d(MainActivity.TAG, "MyContentProvider: kill!");
        Process.killProcess(pid);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
