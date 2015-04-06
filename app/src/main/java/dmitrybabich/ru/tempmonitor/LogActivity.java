package dmitrybabich.ru.tempmonitor;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class LogActivity extends Activity {

    TextView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        registerReceivers(receiver);
        view =(TextView) this.findViewById(R.id.tvLog);
    }

    private void registerReceivers(BroadcastReceiver receiver) {
        registerReceiver(receiver, new IntentFilter(TWUtilConst.MESSAGE_RECIEVED));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String text ="";
            text += intent.getExtra("What") + ";";
            text += intent.getExtra("Arg1") + ";";
            text += intent.getExtra("Arg2") + ";";
            byte[] bytes = intent.getByteArrayExtra("Bytes");
            if (bytes != null) {
                text += "[";
             for (int i=0; i < bytes.length;i++ )
             {
                 text += bytes[i] +" - ";
             }
                text += "]";
            }
            text += "%n";
            view.append(text);
        }
    };

}
