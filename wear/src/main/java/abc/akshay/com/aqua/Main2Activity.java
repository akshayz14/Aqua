package abc.akshay.com.aqua;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends WearableActivity {

    private Context mContext;
    private Button startButton;
    private Button stopButton;
    private AlarmManager am;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mContext = this;

        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ScheduleAlarmReceiver.class);
                intent.setClass(mContext, ScheduleAlarmReceiver.class);
                Log.d("main2activity", "onClick: ");
                pendingIntent = PendingIntent.getBroadcast(mContext, AppConstants.REQ_CODE_FIRE_ALARM, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60*10, pendingIntent);
//                showNotification(mContext);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Cancel alarm", "onClick: ");
                am.cancel(pendingIntent);
            }
        });

        setAmbientEnabled();
    }



    private void showNotification(Context context) {
        int notificationId = 1;
// The channel ID of the notification.
        String id = "my_channel_01";
// Build intent for notification content
        Intent viewIntent = new Intent(this, Main2Activity.class);
        viewIntent.putExtra("eventID", 23);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, 0);

// Notification channel ID is ignored for Android 7.1.1
// (API level 25) and lower.
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, id)
                        .setSmallIcon(R.drawable.ic_drink_name)
                        .setContentTitle("Drink water")
                        .setContentText("Drink water")
                        .setContentIntent(viewPendingIntent);


        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

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
