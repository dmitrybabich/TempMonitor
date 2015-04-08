package dmitrybabich.ru.tempmonitor;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends Activity {

    TextView textView;
TextView textViewTempIsNotAvailable;
    ImageButton btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, BackgroundService.class));
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textViewTemp);
        textViewTempIsNotAvailable = (TextView)findViewById(R.id.textViewTempIsNotAvailable);

        registerReceivers(receiver);
        UpdateTextView();
     /*   btnSettings = (ImageButton)findViewById(R.id.imageButtonSettings);
        btnSettings.setVisibility(View.INVISIBLE);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnSettingsButtonClick();
            }
        });*/

        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
                public boolean onLongClick(View v) {
               OnLongSettingsClick();
return true;
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              MyTWUtilHandler.ProcessTempChanged(TWUtilConst.UNKNOWN_TEMP_VALUE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        menu.add("Test");
        return true;
    }




    private void OnLongSettingsClick() {
        Intent myIntent = new Intent(this, LogActivity.class);
        this.startActivity(myIntent);
    }

    private void OnSettingsButtonClick() {
        NotificationHelper.ShowNotification();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                OnSettingsButtonClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    @Override
    protected void onResume() {
        super.onResume();
    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            textViewTempIsNotAvailable.setVisibility(View.INVISIBLE);
            btnSettings.setVisibility(View.VISIBLE);
            UpdateTextView();
        }
    };


}
