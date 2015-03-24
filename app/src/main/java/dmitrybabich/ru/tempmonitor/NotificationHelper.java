package dmitrybabich.ru.tempmonitor;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

/**
 * Created by babich on 3/23/2015.
 */
public  class NotificationHelper {


    public  static void ShowNotification(float temp)
    {
        Context context = App.getInstance().getApplicationContext();
        Notification notification = new Notification.Builder(context)
                .setContentTitle("Temperature Monitor")
                .setContentText(Float.toString(temp))
                .setSmallIcon(R.drawable.notif_icon)
                .build();
        notification.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
        NotificationManager notifier = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notifier.notify(1, notification);
    }
}
