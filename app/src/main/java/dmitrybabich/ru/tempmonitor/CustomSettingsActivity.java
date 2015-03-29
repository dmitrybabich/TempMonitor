package dmitrybabich.ru.tempmonitor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;


public class CustomSettingsActivity extends Activity implements SeekBar.OnSeekBarChangeListener{

    NotificationAppearanceSettings settings = NotificationAppearanceSettings.GetCurrent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_settings);
        Button btnBackground =(Button) findViewById(R.id.buttonSetBackground);
        btnBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnSetBackgroundButtonClick();
            }
        });
        Button btnForeground =(Button) findViewById(R.id.buttonChangeForeground);
        btnForeground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnSetForegroundButtonClick();
            }
        });
        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBarFontSize);
        seekBar.setProgress(NotificationAppearanceSettings.GetCurrent().FontSize);

        SeekBar seekBarLeftPadding = (SeekBar)findViewById(R.id.seekBarLeftPadding);
        SeekBar seekBarRightPadding = (SeekBar)findViewById(R.id.seekBarRightPadding);
        SeekBar seekBarTopPadding = (SeekBar)findViewById(R.id.seekBarTopPadding);
        SeekBar seekBarBottomPadding = (SeekBar)findViewById(R.id.seekBarBottomPadding);
        seekBarLeftPadding.setProgress(NotificationAppearanceSettings.GetCurrent().PaddingLeft);
        seekBarRightPadding.setProgress(NotificationAppearanceSettings.GetCurrent().PaddingRight);
        seekBarTopPadding.setProgress(NotificationAppearanceSettings.GetCurrent().PaddingTop);
        seekBarBottomPadding.setProgress(NotificationAppearanceSettings.GetCurrent().PaddingBottom);


        seekBar.setOnSeekBarChangeListener(this);
        seekBarLeftPadding.setOnSeekBarChangeListener(this);
        seekBarRightPadding.setOnSeekBarChangeListener(this);
        seekBarTopPadding.setOnSeekBarChangeListener(this);
        seekBarBottomPadding.setOnSeekBarChangeListener(this);
    }


    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser)
    {
        if (fromUser)
        {
            switch (seekBar.getId())
            {
                case R.id.seekBarFontSize: NotificationAppearanceSettings.GetCurrent().FontSize = progress; break;
                case R.id.seekBarLeftPadding: NotificationAppearanceSettings.GetCurrent().PaddingLeft = progress; break;
                case R.id.seekBarRightPadding: NotificationAppearanceSettings.GetCurrent().PaddingRight = progress; break;
                case R.id.seekBarTopPadding: NotificationAppearanceSettings.GetCurrent().PaddingTop = progress; break;
                case R.id.seekBarBottomPadding: NotificationAppearanceSettings.GetCurrent().PaddingBottom = progress; break;
            }
            NotificationHelper.RefreshNotificationAppearance();
        }
    }

    private void OnSetForegroundButtonClick() {
        isBackColor =false;
        OnChangeColorButtonClickCore();
    }

    int colorToAccept=-1;
    Boolean isBackColor;
    private void OnSetBackgroundButtonClick() {
    isBackColor =true;
        OnChangeColorButtonClickCore();
    }

    public void OnChangeColorButtonClickCore()
    {
        colorToAccept = -1;
        ShowColorDialog();
    }

    public  void ShowColorDialog()
    {

            AlertDialog.Builder colorDialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = LayoutInflater.from(this);
            View dialogview = inflater.inflate(R.layout.color_dialog_layout, null);
            final ColorPicker picker = (ColorPicker) dialogview.findViewById(R.id.picker);
        SVBar svBar = (SVBar) dialogview.findViewById(R.id.svbar);
        OpacityBar opacityBar = (OpacityBar) dialogview.findViewById(R.id.opacitybar);
            picker.addSVBar(svBar);
            picker.addOpacityBar(opacityBar);
            picker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener()
            {
                @Override
                public void onColorChanged(int color) {
                    colorToAccept = color;
                    CheckApplyColor();
                }
            });
            colorDialogBuilder.setTitle("Choose Text Color");
            colorDialogBuilder.setView(dialogview);
            colorDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        /*    colorPickerView.setTextColor(picker.getColor());
                            picker.setOldCenterColor(picker.getColor());*/
                        }
                    });
       /*     colorDialogBuilder.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            colorToAccept = -1;
                            CheckApplyColor();
                        }
                    });*/
            AlertDialog colorPickerDialog = colorDialogBuilder.create();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(colorPickerDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            colorPickerDialog.show();
        colorPickerDialog.getWindow().setAttributes(lp);
    }

    private void CheckApplyColor() {
        if (colorToAccept != -1)
        {
            if (isBackColor)
                settings.SetBackColor(colorToAccept);
            else
                settings.SetForeColor(colorToAccept);
            NotificationHelper.RefreshNotificationAppearance();
        }
    }

    private void LoadSettings() {
/*        UpdateBackColor();*/
    }

/*    private void UpdateBackColor() {
        UpdateColorLabel(R.id.textViewBackColor, settings.BackColor);
    }*/

/*    private  void UpdateColorLabel(int name, int value)
    {
        String text = "";
        if (value == -1)
            text = "default";
        else
            text = String.valueOf(value);
        ((TextView) findViewById(name)).setText(text);
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_custom_settings, menu);
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
}
