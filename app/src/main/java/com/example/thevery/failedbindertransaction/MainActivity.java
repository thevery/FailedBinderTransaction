package com.example.thevery.failedbindertransaction;

import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "FBT";

    private int backCounter = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.start_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "start", Toast.LENGTH_SHORT).show();
                while (backCounter-- > 0) {
                    Log.d(MainActivity.TAG, "MainActivity: backCounter = " + backCounter);
                    runQuery();
                    SystemClock.sleep(100);
                    killCp();
                }
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
            Log.d(TAG, "++ cursor");
            Toast.makeText(this, "query ok", Toast.LENGTH_SHORT).show();
        } else {
            Log.d(TAG, "!! cursor = " + cursor);
            Toast.makeText(this, "query failed", Toast.LENGTH_SHORT).show();
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
