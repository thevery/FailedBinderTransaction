package com.example.thevery.failedbindertransaction;

import android.content.ContentProviderClient;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "FBT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start();
    }

    private void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                ContentProviderClient client = getContentResolver().acquireUnstableContentProviderClient(MyContentProvider.CONTENT_URI);
                for (int i = 0; i < 1000; i++) {
                    try {
                        System.out.println("i = " + i);
                        SystemClock.sleep(100);
//                        Cursor cursor = client.query(MyContentProvider.CONTENT_URI, null, null, null, null);
//                        System.out.println("cursor.getCount() = " + cursor.getCount());
//                        cursor.close();
//                        Cursor cursor = getContentResolver().call((MyContentProvider.CONTENT_URI, "hello", null, null, null);
                        Bundle hello = getContentResolver().call(MyContentProvider.CONTENT_URI, "hello", String.valueOf(i), null);
//                    System.out.println("hello = " + hello.getString("time"));
                        System.out.println("hello = " + hello);
                    } catch (Exception e) {
                        System.out.println("exception!");
                        e.printStackTrace();
//                        client = getContentResolver().acquireUnstableContentProviderClient(MyContentProvider.CONTENT_URI);
                    }
                }
            }
        }).start();
    }

    private class CursorLoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {

        @Override
        public Loader<Cursor> onCreateLoader(final int id, Bundle args) {
            Log.d(TAG, "CursorLoaderCallback.onCreateLoader");
            CursorLoader cursorLoader = new CursorLoader(MainActivity.this);
            cursorLoader.setUri(MyContentProvider.CONTENT_URI);
            return cursorLoader;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            Log.d(TAG, "CursorLoaderCallback.onLoadFinished: cursor = " + data);
            if (data == null) {
                Toast.makeText(MainActivity.this, "FBT!", Toast.LENGTH_SHORT).show();
            }
//            if (data.moveToFirst()) {
//                //todo
//            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            Log.d(TAG, "CursorLoaderCallback.onLoaderReset");
        }
    }
}
