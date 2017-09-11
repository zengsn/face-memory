package edu.hzuapps.faceunlock;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    BroadcastReceiver receiver = null;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {

        IntentFilter filter=new IntentFilter();

        filter.addAction(Intent.ACTION_SCREEN_ON);

        filter.addAction(Intent.ACTION_SCREEN_OFF);

        receiver=new MyBroadcastReceiver();

        registerReceiver(receiver, filter);
        Log.d("MyService", "服务创建完成");
    };

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
    }
}
