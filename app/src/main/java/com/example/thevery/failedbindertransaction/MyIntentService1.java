package com.example.thevery.failedbindertransaction;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

public class MyIntentService1 extends Service {

    public static void start(Context context) {
        Intent intent = new Intent(context, MyIntentService1.class);
        context.startService(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(5000);
                stopSelf(startId);
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }
}
