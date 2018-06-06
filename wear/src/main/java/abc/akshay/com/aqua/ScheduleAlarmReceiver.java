package abc.akshay.com.aqua;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;


import static android.content.Context.VIBRATOR_SERVICE;


/**
 * Created by akshay on 05/06/18
 */
public class ScheduleAlarmReceiver extends BroadcastReceiver {


    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("scheduleAlarmreceiver", "onReceive: ");
        Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        long[] vibrationPattern = {0, 500, 50, 300};
        //-1 - don't repeat
        final int indexInPatternToRepeat = -1;
        vibrator.vibrate(vibrationPattern, indexInPatternToRepeat);


        Intent displayIntent = new Intent(context, Main2Activity.class);

        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_drink_name)
                .setContentTitle("Drink water")
                .extend(new Notification.WearableExtender()
                        .setDisplayIntent(PendingIntent.getActivity(context, 0, displayIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT)))
                .build();

        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                .notify(0, notification);

        showNotification1(context);
    }

    private void showNotification1(Context context) {


        int notificationID = 0;
        Intent callPhoneIntent = new Intent(context, Main2Activity.class);

        PendingIntent callPhonePendingIntent = PendingIntent.getActivity(context, 0,
                callPhoneIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action callPhoneAction =
                new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "Call Phone",
                        callPhonePendingIntent)
                        .build();

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Notification")
                        .setContentText("Call Phone")
                        .addAction(callPhoneAction);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID, builder.build());


    }

    private void showNotification(Context context) {
        int notificationId = 001;
// The channel ID of the notification.
        String id = "my_channel_01";
// Build intent for notification content
        Intent viewIntent = new Intent(context, Main2Activity.class);
//        viewIntent.putExtra("eventID", 23);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(context, 0, viewIntent, 0);

// Notification channel ID is ignored for Android 7.1.1
// (API level 25) and lower.
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, id)
                        .setSmallIcon(R.drawable.ic_drink_name)
                        .setContentTitle("Drink water")
                        .setContentText("Drink water")
                        .setContentIntent(viewPendingIntent);


        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);

// Issue the notification with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());




//        NotificationCompat.WearableExtender wearableExtender =
//                new NotificationCompat.WearableExtender()
//                        .setHintShowBackgroundOnly(true);
//
//        Notification notification =
//                new NotificationCompat.Builder(context)
//                        .setSmallIcon(R.drawable.ic_drink_name)
//                        .setContentTitle("Drink water")
//                        .setContentText("Drink water")
//                        .extend(wearableExtender)
//                        .build();
//
//        NotificationManagerCompat notificationManager =
//                NotificationManagerCompat.from(context);
//
//        int notificationId = 1;
//
//        notificationManager.notify(notificationId, notification);


    }
}
