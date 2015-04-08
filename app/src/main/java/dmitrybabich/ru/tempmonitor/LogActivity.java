package dmitrybabich.ru.tempmonitor;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class LogActivity extends Activity {

    TextView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        registerReceivers(receiver);
        view =(TextView) this.findViewById(R.id.tvLog);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                OnLongClick();
                return true;
            }
        });

        Button t1 =(Button) this.findViewById(R.id.t1);
        Button t2 =(Button) this.findViewById(R.id.t2);
        Button t3 =(Button) this.findViewById(R.id.t3);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnT1Click();
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnT2Click();
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnT3Click();
            }
        });
    }

    private void OnT3Click() {
        a(144, 65, 3);
    }


    private void OnT2Click() {

        a(144, 65, 2);
    }

    private void OnT1Click() {
        a(144, 65, 1);
    }


    public int a(int i, int j)
    {
        int result =  a(i, j, 0);
        LogText(result);
        return  result;
    }

    public int a(int i, int j, int k)
    {
        byte abyte0[] = new byte[2];
        abyte0[0] = (byte)j;
        abyte0[1] = (byte)k;
        return write(1281, i, 2, abyte0);
    }

    private int write(int i, int i1, int i2, byte[] abyte0) {
        return TWUtilEx.GetCurrent().mTWUtil.write(i, i1, i2, abyte0);
    }

    public int b(int i, int j)
    {
        byte abyte0[] = new byte[1];
        abyte0[0] = (byte)j;
        return write(1281, 1, i, abyte0);
    }

    private void OnLongClick() {
        /*Message m = new Message();
        m.what=1281;
        m.arg1 = 38;
        m.arg2 = 6;
        m.obj = new byte[]{13,3,5,11,49,0};
        new MyTWUtilHandler(getApplicationContext()).handleMessage(m);*/
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
           try
           {
               text += intent.getExtra("What") + ";";
               text += intent.getExtra("Arg1") + ";";
               text += intent.getExtra("Arg2") + ";";
               byte[] bytes = intent.getByteArrayExtra("Bytes");
               if (bytes != null) {
                   text += "[";
                   for (int i=0; i < bytes.length;i++ )
                   {
                       text += bytes[i] +", ";
                   }
                   text += "]";
               }
           }
           finally {
                LogText(text);
           }

        }
    };

    private void LogText(Object text) {
        view.append(text + ";");
    }

}
