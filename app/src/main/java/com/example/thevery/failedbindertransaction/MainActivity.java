package com.example.thevery.failedbindertransaction;

import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "FBT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.run_query_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runQuery();

            }
        });

        findViewById(R.id.kill_cp_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                killCp();
            }
        });
    }

    private void runQuery() {
        final int pid = android.os.Process.myPid();
        Log.d(MainActivity.TAG, "MainActivity: pid = " + pid);

        final Cursor cursor = getContentResolver().query(MyContentProvider.CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Log.d(TAG, "getCount() = " + cursor.getCount());
            Log.d(TAG, "getInt(0) = " + cursor.getInt(0));
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    private void killCp() {
        try {
            Process process = Runtime.getRuntime().exec("/system/bin/ps");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            Integer pid = null;
            while ((line = reader.readLine()) != null) {
                if (line.contains("com.example.thevery.failedbindertransaction")) {
                    Log.d(TAG, "ps: " + line);
                }
                if (line.endsWith("com.example.thevery.failedbindertransaction:cp")) {
                    String[] str = line.split("\\s+");
                    if (pid != null) {
                        throw new RuntimeException("pid already found");
                    }
                    pid = Integer.parseInt(str[1]);
                    Log.d(TAG, "pid: " + pid);
                }
            }
            reader.close();
            process.waitFor();
            if (pid != null) {
                android.os.Process.killProcess(pid);
            }
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
