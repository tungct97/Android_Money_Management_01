package com.framgia.moneymanagement.screen.alarm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.framgia.moneymanagement.R;
import com.framgia.moneymanagement.data.model.Alarm;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    public static final String EXTRA_ALARM = "EXTRA_ALARM";
    public static final int REQUEST_CODE_ALARM = 100;

    @Override
    public void onReceive(Context context, Intent intent) {
        Alarm alarm = intent.getParcelableExtra(EXTRA_ALARM);
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent alarmIntent = new Intent(context, AlarmActivity.class);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, REQUEST_CODE_ALARM,
                alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context).setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.ic_image_notification)
                        .setContentTitle(context.getResources().getString(R.string.title_alarm))
                        .setContentText(alarm.getAlarmName())
                        .setAutoCancel(true);
        notificationManager.notify(REQUEST_CODE_ALARM, builder.build());
        intent = new Intent(context, AlarmService.class);
        context.startService(intent);
    }
}
