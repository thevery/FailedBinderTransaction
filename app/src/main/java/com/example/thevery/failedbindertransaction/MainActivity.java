package com.example.thevery.failedbindertransaction;

import android.database.Cursor;
import android.os.Bundle;
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
        getSupportLoaderManager().restartLoader(0, null, new CursorLoaderCallback());
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
