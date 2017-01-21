package com.hackucsc.hackucsc;

import android.app.Service;
import android.app.PendingIntent;
import android.content.Intent;
import android.app.Notification;
import android.os.IBinder;

public class NotificationService extends Service {
    Notification.Action change = new Notification.Action(0, "Pause", null);
//    Notification.Action settings = new Notification.Action(0, "Settings", null);

    public NotificationService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("SteadyView")
                .setContentText("Running")
                .setSmallIcon(R.mipmap.ic_launcher)
                .addAction(change)
//                .addAction(settings)
                .setContentIntent(pendingIntent)
                .build();

        int nId = (int) System.currentTimeMillis();
        startForeground(nId, notification);
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
