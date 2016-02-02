package com.example.thevery.failedbindertransaction;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "FBT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyIntentService1.start(MainActivity.this);
            }
        });
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyIntentService2.start(MainActivity.this);
            }
        });
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MyIntentService1.start(MainActivity.this);
                makeQuery(MainActivity.this);
//                getSupportLoaderManager().restartLoader(0, null, new CursorLoaderCallback());

            }
        });
    }

    public static void makeQuery(final Context context) {
        for (int i = 0; i < 2; i++) {
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("MyContentProvider.getContentResolver().query>>>");
                    Cursor cursor = context.getContentResolver()
                            .query(MyContentProvider.CONTENT_URI, null, null, null, null);
                    System.out.println("MyContentProvider.getContentResolver().query<<<");
                    if (cursor == null) {
                        Log.d("FBT", "count = " + finalI);
                    } else {
                        Log.d("NOFBT", "count = " + finalI);
                        cursor.close();
                    }
                }
            }).start();
        }
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
            checkCursor(data);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            Log.d(TAG, "CursorLoaderCallback.onLoaderReset");
        }
    }

    private void checkCursor(Cursor data) {
        Log.d(TAG, "CursorLoaderCallback.onLoadFinished: cursor = " + data);
        if (data == null) {
            Toast.makeText(MainActivity.this, "FBT!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Cursor OK!", Toast.LENGTH_SHORT).show();
        }
    }
}
