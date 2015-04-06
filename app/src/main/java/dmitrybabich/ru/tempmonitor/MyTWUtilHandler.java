package dmitrybabich.ru.tempmonitor;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;

public class MyTWUtilHandler extends android.os.Handler {
    private int b = -40;
    private int c = 0;
    private int d = 0;
    private Context mContext;


    private static void SendBroadcastAction(String action) {
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



    private void SendBroadcastAction(String action, Message paramMessage) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        if ( paramMessage != null ) {
            intent.putExtra("What", paramMessage.what);
            intent.putExtra("Arg1", paramMessage.arg1);
            intent.putExtra("Arg2", paramMessage.arg2);
            if (paramMessage.obj instanceof  byte[]) {
                intent.putExtra("Bytes",(byte[]) paramMessage.obj);
            }
        }
        intent.setAction(action);
        App.getInstance ().sendBroadcast(intent);
    }


    public MyTWUtilHandler(Context paramContext)
    {
        this.mContext = paramContext;

    }




    float temperature = TWUtilConst.UNKNOWN_TEMP_VALUE;

    public void handleMessage(Message paramMessage)
    {
        SendBroadcastAction(TWUtilConst.MESSAGE_RECIEVED, paramMessage);
        if (paramMessage.what != 1281)
            return;
        if (ExtractTemp(paramMessage))
            ProcessTempChanged(temperature);
    }


    private boolean ExtractTemp(Message paramMessage) {
        try {
            byte[] bytes = (byte[]) paramMessage.obj;
            int i1 = 0x7f & bytes[0];
            if ((0x80 & bytes[0]) == 128) {
                b = -i1;
            } else {
                b = i1;
            }
            if (b <= -40 || b >= 127)            {           } else {
                temperature = Integer.valueOf(b);
                return true;
            }
            byte temp = bytes[5];
            if (temp <= -40 || temp >= 86) {}  else {
                temperature = Integer.valueOf(temp);
                return true;
            }
        }
        finally {

        }
        return false;
    }

    public static void ProcessTempChanged(float value) {
        TemperatureStorage.getInstance().CurrentTemperature = value;
        DataProvider.SaveTemp(value);
        NotificationHelper.ShowNotification();
        SendBroadcastAction(TWUtilConst.TEMPERATURE_CHANGED);
    }
}

