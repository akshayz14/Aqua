package abc.akshay.com.aqua;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;


import java.util.Date;

import static android.app.Notification.VISIBILITY_PUBLIC;
import static android.content.Context.VIBRATOR_SERVICE;


/**
 * Created by akshay on 05/06/18
 */
public class ScheduleAlarmReceiver extends BroadcastReceiver {


    private NotificationManagerCompat mNotificationManagerCompat;
    public static final int NOTIFICATION_ID = 888;

    public ScheduleAlarmReceiver() {
        super();
        mNotificationManagerCompat = NotificationManagerCompat.from(Aqua.getAppContext());

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("scheduleAlarmreceiver", "onReceive: ");
        Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        long[] vibrationPattern = {0, 500, 50, 300};
        //-1 - don't repeat
        final int indexInPatternToRepeat = -1;
        vibrator.vibrate(vibrationPattern, indexInPatternToRepeat);

        Date date = new Date();

        Date startTime = Utils.atStartTime(date);
        Date endtime = Utils.atEndTime(date);

        if (date.after(startTime) && date.before(endtime)) {
            showTextNotification(context);
        }
    }


    private void showTextNotification(Context context) {

        //Creating notification channel is important above android 7
        String notificationChannelId = Utils.createNotificationChannel(context);

        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle()
                // Overrides ContentText in the big form of the template.
                .bigText("Drink water")
                // Overrides ContentTitle in the big form of the template.
                .setBigContentTitle("Drink water")
                // Summary line after the detail section in the big form of the template.
                // Note: To improve readability, don't overload the user with info. If Summary Text
                // doesn't add critical information, you should skip it.
                .setSummaryText("Drink water");


        Intent notifyIntent = new Intent(context, Main2Activity.class);

        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent notifyPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        Intent snoozeIntent = new Intent(context, BigTextIntentService.class);
        snoozeIntent.setAction(BigTextIntentService.ACTION_SNOOZE);

        PendingIntent snoozePendingIntent = PendingIntent.getService(context, 0, snoozeIntent, 0);
        NotificationCompat.Action snoozeAction =
                new NotificationCompat.Action.Builder(
                        R.drawable.ic_drink_name,
                        "Snooze",
                        snoozePendingIntent)
                        .build();


        // Dismiss Action.
        Intent dismissIntent = new Intent(context, BigTextIntentService.class);
        dismissIntent.setAction(BigTextIntentService.ACTION_DISMISS);

        PendingIntent dismissPendingIntent = PendingIntent.getService(context, 0, dismissIntent, 0);
        NotificationCompat.Action dismissAction =
                new NotificationCompat.Action.Builder(
                        R.drawable.ic_drink_name,
                        "Dismiss",
                        dismissPendingIntent)
                        .build();


        NotificationCompat.Builder notificationCompatBuilder =
                new NotificationCompat.Builder(
                        Aqua.getAppContext(), notificationChannelId);


        GlobalNotificationBuilder.setNotificationCompatBuilderInstance(notificationCompatBuilder);

        Notification notification = notificationCompatBuilder
                // BIG_TEXT_STYLE sets title and content for API 16 (4.1 and after).
                .setStyle(bigTextStyle)
                // Title for API <16 (4.0 and below) devices.
                .setContentTitle("Drink water")
                // Content for API <24 (7.0 and below) devices.
                .setContentText("Drink water")
                .setSmallIcon(R.drawable.ic_drink_name)
                .setLargeIcon(BitmapFactory.decodeResource(
                        context.getResources(),
                        R.drawable.ic_drink_name))
                .setContentIntent(notifyPendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                // Set primary color (important for Wear 2.0 Notifications).
                .setColor(ContextCompat.getColor(Aqua.getAppContext(), R.color.dark_red))

                // SIDE NOTE: Auto-bundling is enabled for 4 or more notifications on API 24+ (N+)
                // devices and all Wear devices. If you have more than one notification and
                // you prefer a different summary notification, set a group key and create a
                // summary notification via
                // .setGroupSummary(true)
                // .setGroup(GROUP_KEY_YOUR_NAME_HERE)

                .setCategory(Notification.CATEGORY_REMINDER)

                // Sets priority for 25 and below. For 26 and above, 'priority' is deprecated for
                // 'importance' which is set in the NotificationChannel. The integers representing
                // 'priority' are different from 'importance', so make sure you don't mix them.
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                // Sets lock-screen visibility for 25 and below. For 26 and above, lock screen
                // visibility is set in the NotificationChannel.
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .addAction(snoozeAction)
                .addAction(dismissAction)
                // Adds additional actions specified above.

                .build();

        mNotificationManagerCompat.notify(NOTIFICATION_ID, notification);


    }

}
