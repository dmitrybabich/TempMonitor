package dmitrybabich.ru.tempmonitor;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.view.WindowManager;



public class App extends Application {
	static App self;

	private WindowManager.LayoutParams mWindowManagerLayoutParams = new WindowManager.LayoutParams();
	public WindowManager.LayoutParams getWindowManagerLayoutParams() {
		return mWindowManagerLayoutParams;
	}

	public static App getInstance() {
		return self;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d ("App", "onCreate");
		self = this;
		this.startService (new Intent (this, BackgroundService.class));
	}

}
