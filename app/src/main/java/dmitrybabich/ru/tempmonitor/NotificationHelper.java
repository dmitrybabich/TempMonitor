package dmitrybabich.ru.tempmonitor;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by babich on 3/23/2015.
 */
public  class NotificationHelper {

    static Dialog dialog;
    private static TextView mFloatView;
static  Context context;

    /*public  static void ShowNotification(float temp)
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
    }*/

    public static void ShowNotification() {
        if (context == null)
            context =  App.getInstance().getApplicationContext();
        if (mFloatView == null)
            createFloatView();
        mFloatView.setText(GetNotificationText());
    }


    private static void createFloatView() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);

     /*   WindowManager wm = (WindowManager)context. getSystemService(context.WINDOW_SERVICE);
        LayoutInflater inflater = (LayoutInflater)context. getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View myView = inflater.inflate(R.layout.overlay_window, null);
        mFloatView = (Button) myView.findViewById(R.id.btnFload);


        // Add layout to window manager
        wm.addView(mFloatView, params);*/

        SharedPreferences mSharedPreferences = context.getSharedPreferences("SharedPreferences", 0);
        int Lx = mSharedPreferences.getInt("x", -1);
        int Ly = mSharedPreferences.getInt("y", -1);
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();

        WindowManager mWindowManager = (WindowManager) context.getSystemService("window");
        wmParams.type = 2010;
        wmParams.format = 1;
        wmParams.flags = 1288;
        wmParams.gravity = 49;
        wmParams.x = 0;
        wmParams.y = 0;
        wmParams.width = -2;
        wmParams.height = -2;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View oView = layoutInflater.inflate(R.layout.overlay_window, null);
        LinearLayout mFloatLayout = (LinearLayout) oView;
        mWindowManager.addView(mFloatLayout, wmParams);
        mFloatView = (TextView) mFloatLayout.findViewById(R.id.textTemp);
        mFloatView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(context, "Opening main view. Please wait", Toast.LENGTH_LONG);
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return false;
            }
        });
        ApplyFloatViewAppearanceSettings();
        mFloatLayout.measure(android.view.View.MeasureSpec.makeMeasureSpec(0, 0), android.view.View.MeasureSpec.makeMeasureSpec(0, 0));
        int statusbar = getStatusBarHeight(context);
        if (Lx != -1 && Ly != -1) {
            wmParams.x = Lx;
            wmParams.y = Ly;
            if (Ly < statusbar) {
                mFloatView.setTextSize(19F);
            } else {
                mFloatView.setTextSize(27F);
            }
            mWindowManager.updateViewLayout(mFloatLayout, wmParams);
        }
    }

    private static void ApplyFloatViewAppearanceSettings() {
        NotificationAppearanceSettings settings = NotificationAppearanceSettings.GetCurrent();
        if (settings.BackColor != -1)
            mFloatView.setBackgroundColor(settings.BackColor);
        if (settings.ForeColor != -1)
            mFloatView.setTextColor(settings.ForeColor);
        if (settings.FontSize != -1)
            mFloatView.setTextSize(settings.FontSize);
        int[] paddings = new int[]{mFloatView.getPaddingLeft(),mFloatView.getPaddingTop(),mFloatView.getPaddingRight(),mFloatView.getPaddingBottom() };
        if (settings.PaddingLeft != -1)
            paddings[0] = settings.PaddingLeft;
        if (settings.PaddingTop != -1)
            paddings[1] = settings.PaddingTop;
        if (settings.PaddingRight != -1)
            paddings[2] = settings.PaddingRight;
        if (settings.PaddingBottom != -1)
            paddings[3] = settings.PaddingBottom;
        mFloatView.setPadding(paddings[0],paddings[1],paddings[2],paddings[3]);
    }

    public static int getStatusBarHeight(Context context) {
        int j;
        try {
            Class class1 = Class.forName("com.android.internal.R$dimen");
            Object obj = class1.newInstance();
            int i = Integer.parseInt(class1.getField("status_bar_height").get(obj).toString());
            j = context.getResources().getDimensionPixelSize(i);
        } catch (Exception exception) {
            exception.printStackTrace();
            return 0;
        }
        return j;
    }

    public static String GetNotificationText() {
        float currentTemp = TemperatureStorage.getInstance().CurrentTemperature;
        if (currentTemp == TWUtilConst.UNKNOWN_TEMP_VALUE)
            return "?";
        int text = Math.round(currentTemp);
        return text +"º";
    }

    public static void RefreshNotificationAppearance() {
        ShowNotification();
        ApplyFloatViewAppearanceSettings();
    }
}

