package com.SteadyView.SteadyView;

import android.app.NotificationManager;
import android.app.Service;
import android.app.PendingIntent;
import android.content.Intent;
import android.app.Notification;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.SteadyView.SteadyView.*;

public class NotificationService extends Service {

    public NotificationService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("\n\n\nNotificationService: Constants.text: " + Constants.text);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction("startMain");
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Intent changeIntent = new Intent(this, NotificationService.class);
        changeIntent.setAction("startChange");
        PendingIntent pChangeIntent = PendingIntent.getService(this, 0, changeIntent, 0);

        if (intent.getAction().equals("startChange")) {
            if (Constants.text.equals("Running")) {
                Constants.text = "Paused";
                Constants.change = "Resume";
            } else {
                Constants.text = "Running";
                Constants.change = "Pause";
            }
        }

        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("SteadyView")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(Constants.text)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .addAction(0, Constants.change, pChangeIntent)
                .setContentIntent(pendingIntent)
                .build();

        if (intent.getAction().equals("startNotification"))
            startForeground(Constants.nId, notification);
        else
            nm.notify(Constants.nId, notification);

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
