package abc.akshay.com.aqua;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import static android.app.Notification.VISIBILITY_PUBLIC;

public class Main2Activity extends WearableActivity implements View.OnClickListener {

    private Context mContext;
    private Button startButton;
    private Button stopButton;
    private AlarmManager am;
    private PendingIntent pendingIntent;
    private NotificationManagerCompat mNotificationManagerCompat;
    public static final int NOTIFICATION_ID = 888;
    public static final String TAG = Main2Activity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mContext = this;
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        mNotificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());

        setAmbientEnabled();
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.startButton:
                Intent intent = new Intent(mContext, ScheduleAlarmReceiver.class);
                intent.setClass(mContext, ScheduleAlarmReceiver.class);
                Log.d("main2activity", "onClick: ");
                pendingIntent = PendingIntent.getBroadcast(mContext, AppConstants.REQ_CODE_FIRE_ALARM, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), AlarmManager.INTERVAL_HOUR, pendingIntent);
                break;

            case R.id.stopButton:
                Log.d("Cancel alarm", "onClick: ");
                am.cancel(pendingIntent);

                break;
        }

    }

}
