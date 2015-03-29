package dmitrybabich.ru.tempmonitor;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Dmitry on 28.03.2015.
 */
public class NotificationAppearanceSettings {

    public static final String APP_PREFERENCES_BackColor = "BackColor";
    public static final String APP_PREFERENCES_ForeColor = "ForeColor";
    public static final String APP_PREFERENCES_PaddingLeft = "PaddingLeft";
    public static final String APP_PREFERENCES_PaddingRight = "PaddingRight";
    public static final String APP_PREFERENCES_PaddingTop = "PaddingTop";
    public static final String APP_PREFERENCES_PaddingBottom = "PaddingBottom";
    public static final String APP_PREFERENCES_FontSize = "FontSize";

    static NotificationAppearanceSettings self;

    public static NotificationAppearanceSettings GetCurrent() {
        if (self == null)
            self = new NotificationAppearanceSettings();
        return self;
    }


    public int BackColor;
    public int ForeColor;
    public int PaddingLeft;
    public int PaddingRight;
    public int PaddingTop;
    public int PaddingBottom;
    public int FontSize;
    SharedPreferences mySharedPreferences;
    public NotificationAppearanceSettings()
    {
        self = this;
        mySharedPreferences = App.getInstance().getSharedPreferences("AppearanceSettings", Context.MODE_PRIVATE);
        BackColor = mySharedPreferences.getInt(APP_PREFERENCES_BackColor, -1);
        ForeColor = mySharedPreferences.getInt(APP_PREFERENCES_ForeColor, -1);
        PaddingLeft = mySharedPreferences.getInt(APP_PREFERENCES_PaddingLeft, -1);
        PaddingRight = mySharedPreferences.getInt(APP_PREFERENCES_PaddingRight, -1);
        PaddingTop = mySharedPreferences.getInt(APP_PREFERENCES_PaddingTop, -1);
        PaddingBottom = mySharedPreferences.getInt(APP_PREFERENCES_PaddingBottom, -1);
        FontSize = mySharedPreferences.getInt(APP_PREFERENCES_FontSize, -1);
    }

    private  void SetSettings(String name, int value)
    {
        SharedPreferences.Editor edit = mySharedPreferences.edit();
        edit.putInt(name, value);
        edit.apply();
    }

    public void SetBackColor(int value)
    {
        BackColor = value;
        SetSettings(APP_PREFERENCES_BackColor, value);
    }

    public void SetForeColor(int value) {
        ForeColor = value;
        SetSettings(APP_PREFERENCES_ForeColor, value);
    }
}
