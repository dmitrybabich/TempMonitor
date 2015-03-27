package dmitrybabich.ru.tempmonitor;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends Activity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, BackgroundService.class));
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textViewTemp);
        Button button2 = (Button) findViewById(R.id.buttonTest);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             OnButtonClick();
            }
        });
        Button buttonRefreshTemp = (Button) findViewById(R.id.buttonRefreshTemp);
        buttonRefreshTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnRefreshTemButtonClick();
            }
        });

        Button buttonManualRefresh = (Button) findViewById(R.id.buttonManualRefresh);
        buttonManualRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnManualRefreshTemButtonClick();
            }
        });

        registerReceivers(receiver);
        UpdateTextView();
    }

    private void OnManualRefreshTemButtonClick() {
        NotificationHelper.ShowNotification();
    }

    private void OnRefreshTemButtonClick() {
        TWUtilEx current =TWUtilEx.GetCurrent();
        if (current == null)
        {
            Toast.makeText(this, "Current is null", Toast.LENGTH_LONG).show();
            return;
        }
        boolean result = current.RefreshTemp();
        Toast.makeText(this, "Result =" + result, Toast.LENGTH_LONG).show();
    }


    public void UpdateTextView()
    {
        textView.setText(NotificationHelper.GetNotificationText());

    }

    Random r = new Random();
    public void OnButtonClick()
    {
        boolean isAvailable = TWUtilEx.isTWUtilAvailable();
        String deviceID = TWUtilEx.GetDeviceID();
        String text = "Is available "  + isAvailable + " ID = " +deviceID;
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        Message message = new Message();
        message.what = 1281;
        byte value = (byte)r.nextInt(100);
        message.obj =   new byte[]{1,2,3,4,5,value,7 };
        message.arg1 = 3;
        message.arg2 = 7;
        new MyTWUtilHandler(this).handleMessage(message);
    }

    private void registerReceivers(BroadcastReceiver receiver) {
        registerReceiver(receiver, new IntentFilter(TWUtilConst.TEMPERATURE_CHANGED));

    }




    @Override
    protected void onResume() {
        super.onResume();
    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            UpdateTextView();
        }
    };


}
