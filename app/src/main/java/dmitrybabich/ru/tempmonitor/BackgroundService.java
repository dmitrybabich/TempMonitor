package dmitrybabich.ru.tempmonitor;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
import android.app.Notification;

public class BackgroundService extends Service {

	private TWUtilProcessingThread twUtilProcessingThread;



	public BackgroundService () {
		twUtilProcessingThread = null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public IBinder onBind (Intent intent) {
		return null;
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d ("BackgroundService", "onStartCommand");
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences (App.getInstance ());
		startTWUtilProcessingThread();
        return Service.START_STICKY;
	}

	private synchronized void startTWUtilProcessingThread(){
		Log.d ("BackgroundService", "startTWUtilProcessingThread");
		if ( twUtilProcessingThread != null ) return;
		twUtilProcessingThread = new TWUtilProcessingThread ();
		twUtilProcessingThread.start();
	}

	private synchronized void stopTWUtilProcessingThread(){
		Log.d ("BackgroundService", "stopTWUtilProcessingThread");
		if ( twUtilProcessingThread == null) return;
		twUtilProcessingThread.finish();
		twUtilProcessingThread = null;
	}




	@Override
	public void onDestroy() {
		Toast.makeText(getApplicationContext(), "TempMonitor is stopped", Toast.LENGTH_LONG).show();
		Log.d ("BackgroundService", "onDestroy");
		stopForeground(true);
		stopTWUtilProcessingThread();
		super.onDestroy();
	}

}

