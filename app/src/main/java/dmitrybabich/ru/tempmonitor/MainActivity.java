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


public class MainActivity extends Activity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, BackgroundService.class));
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textView1);
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             OnButtonClick();
            }
        });
        Button btnCheckService= (Button) findViewById(R.id.buttonCheckService);
        btnCheckService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OnButtonCheckServiceClick();
            }
        });

        registerReceivers(receiver);
        UpdateTextView();
    }

    public void OnButtonCheckServiceClick()
    {
        boolean isAvailable = TWUtilEx.isTWUtilAvailable();
        String deviceID = TWUtilEx.GetDeviceID();
        textView.setText("Is available "  + isAvailable + " ID = " +deviceID);
    }

    public void UpdateTextView()
    {
        float temp = TemperatureStorage.getInstance().CurrentTemperature;
        textView.setText(textView.getText() + "; "  + temp );

    }

    public void OnButtonClick()
    {
        Toast.makeText(this, "Зачем вы нажали?", Toast.LENGTH_SHORT).show();
        Message message = new Message();
        message.what = 1281;
        message.obj =   new byte[]{1,2,3,4,5,6,7 };
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
