package dmitrybabich.ru.tempmonitor;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;

public class MyTWUtilHandler extends android.os.Handler {
    private int b = -40;
    private int c = 0;
    private int d = 0;
    private Context mContext;


    private void SendBroadcastAction(String action) {
        Log.d ("TWUtilEx", "SendBroadcastAction ");
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.setAction(action);
        App.getInstance ().sendBroadcast(intent);
    }

    private void SendBroadcastAction(String action, String key, float value) {
        Log.d ("TWUtilEx", "SendBroadcastAction ");
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        if ( key != null ) {
            intent.putExtra(key, value);
        }
        intent.setAction(action);
        App.getInstance ().sendBroadcast(intent);
    }

    private void SendBroadcastAction(String action, String key, String value) {
        Log.d ("TWUtilEx", "SendBroadcastAction ");
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        if ( key != null ) {
            intent.putExtra(key, value);
        }
        intent.setAction(action);
        App.getInstance ().sendBroadcast(intent);
    }

    private void SendBroadcastAction(String action, String key, byte[] value) {
        Log.d ("TWUtilEx", "SendBroadcastAction ");
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        if ( key != null ) {
            intent.putExtra(key, value);
        }
        intent.setAction(action);
        App.getInstance ().sendBroadcast(intent);
    }



    public MyTWUtilHandler(Context paramContext)
    {
        this.mContext = paramContext;
    }





    public void handleMessage(Message paramMessage)
    {
        if (paramMessage.what != 1281)
            return;
        if (paramMessage.arg1 != 3)
            return;
        if (paramMessage.arg2 != 7)
            return;
        byte[] arrayOfByte = (byte[])paramMessage.obj;
        float value=  arrayOfByte[5];
        SendBroadcastAction(TWUtilConst.MyNotification, "Temp", value);
    }
}

