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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends Activity {

    TextView textView;
TextView textViewTempIsNotAvailable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, BackgroundService.class));
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textViewTemp);
        textViewTempIsNotAvailable = (TextView)findViewById(R.id.textViewTempIsNotAvailable);
        registerReceivers(receiver);
        UpdateTextView();
        ImageButton btnSettings = (ImageButton)findViewById(R.id.imageButtonSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnSettingsButtonClick();
            }
        });
    }

    private void OnSettingsButtonClick() {
        Intent myIntent = new Intent(this, CustomSettingsActivity.class);
        this.startActivity(myIntent);
    }

    public void UpdateTextView()
    {
        textView.setText(NotificationHelper.GetNotificationText());
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
            textViewTempIsNotAvailable.setVisibility(View.INVISIBLE);
            UpdateTextView();
        }
    };


}
