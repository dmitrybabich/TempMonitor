package dmitrybabich.ru.tempmonitor;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;


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
        // Setup handler for uncaught exceptions.
        Thread.setDefaultUncaughtExceptionHandler (new Thread.UncaughtExceptionHandler()
        {
            @Override
            public void uncaughtException (Thread thread, Throwable e)
            {
                handleUncaughtException (thread, e);
            }
        });
		Log.d ("App", "onCreate");
		self = this;
		this.startService (new Intent (this, BackgroundService.class));
	}

    public void handleUncaughtException (Thread thread, Throwable e)
    {
     //   e.printStackTrace();
        /*Intent intent =               new Intent(                       this,                       ExceptionActivity.class );
        intent.putExtra( "Message", e.getMessage() );
        intent.putExtra( "CallStack", e.getStackTrace() );
        startActivity( intent );
        */

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/html");
        emailIntent.putExtra(android.content.Intent.EXTRA_TITLE, "Exception1");
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Exception");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stack = sw.toString();
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, e.getMessage() + ";" + stack);
        emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(emailIntent);
        System.exit(0);
    }

}
