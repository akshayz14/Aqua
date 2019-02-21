package abc.akshay.com.aqua;

import android.support.v4.app.NotificationCompat;

/**
 * Created by akshay on 06/06/18
 */
public final class GlobalNotificationBuilder {

    private static NotificationCompat.Builder sGlobalNotificationCompatBuilder = null;

    /*
     * Empty constructor - We don't initialize builder because we rely on a null state to let us
     * know the Application's process was killed.
     */
    private GlobalNotificationBuilder() { }

    public static void setNotificationCompatBuilderInstance (NotificationCompat.Builder builder) {
        sGlobalNotificationCompatBuilder = builder;
    }

    public static NotificationCompat.Builder getNotificationCompatBuilderInstance(){
        return sGlobalNotificationCompatBuilder;
    }
}

