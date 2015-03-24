package dmitrybabich.ru.tempmonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import java.util.List;

public class TWUtilBroadcastReceiver extends BroadcastReceiver {




    public TWUtilBroadcastReceiver () {

    }

	@Override
	public void onReceive (Context context, Intent intent) {

		String action = intent.getAction();


        // устройство загрузилось, запустим фоновый сервис
		if ( action.equals (Intent.ACTION_BOOT_COMPLETED ) ) {
			context.startService(new Intent(context, BackgroundService.class));
          /*  Intent mIntent = new Intent(context, MainActivity.class);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mIntent);*/
		}

	}

}
