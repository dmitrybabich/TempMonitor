package dmitrybabich.ru.tempmonitor;

import android.app.Application;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


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
        e.printStackTrace();


        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stack = sw.toString();
        DateFormat df = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");


        Date today = Calendar.getInstance().getTime();

        String reportDate = df.format(today);
       generateNoteOnSD(reportDate +".txt" ,stack);

        System.exit(0);
    }


    public void generateNoteOnSD(String sFileName, String sBody){
        try
        {
            File root = new File(Environment.getExternalStorageDirectory(), "TMExceptions");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        }
        catch(IOException e)
        {

        }
    }

}
