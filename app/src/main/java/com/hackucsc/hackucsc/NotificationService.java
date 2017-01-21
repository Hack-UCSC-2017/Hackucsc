package com.hackucsc.hackucsc;

import android.app.Service;
import android.app.PendingIntent;
import android.content.Intent;
import android.app.Notification;
import android.os.IBinder;

public class NotificationService extends Service {

    public NotificationService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("SteadyView")
                .setContentText("Running, tap to pause")
                .setSmallIcon(R.mipmap.ic_launcher)
                .addAction(0, "Pause", null)
                .addAction(0, "Settings", null)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
