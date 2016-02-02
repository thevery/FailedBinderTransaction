package com.example.thevery.failedbindertransaction;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;

public class MyContentProvider extends ContentProvider {
    public final static String AUTHORITY = "com.example.thevery.failedbindertransaction";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    private MySQLiteHelper helper;

    @Override
    public void attachInfo(Context context, ProviderInfo info) {
        System.out.println("MyContentProvider.attachInfo>>>");
        sleep();
        super.attachInfo(context, info);
        System.out.println("MyContentProvider.attachInfo<<<");
    }

    private void sleep() {
        System.out.println("MyContentProvider.sleep>>>");
        SystemClock.sleep(5000);
        System.out.println("MyContentProvider.sleep<<<");
    }

    public MyContentProvider() {
        System.out.println("MyContentProvider.MyContentProvider>>>");
        sleep();
        System.out.println("MyContentProvider.MyContentProvider<<<");
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
        System.out.println("MyContentProvider.onCreate>>>");
        sleep();
        helper = new MySQLiteHelper(getContext());
        System.out.println("MyContentProvider.onCreate<<<");
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        System.out.println("MyContentProvider.query>>>");
        sleep();
        final int pid = Process.myPid();
//        Log.d(MainActivity.TAG, "MyContentProvider.query: pid = " + pid);
        SQLiteDatabase database = helper.getReadableDatabase();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(5000);
//                kill(pid);
            }
        }).start();
        Cursor cursor = database.query(MySQLiteHelper.ACCOUNTS_TABLE, null, null, null, null, null, null);
        System.out.println("MyContentProvider.query<<<");
        return cursor;
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
