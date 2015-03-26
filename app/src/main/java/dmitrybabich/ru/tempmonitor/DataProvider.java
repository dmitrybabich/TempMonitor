package dmitrybabich.ru.tempmonitor;

import java.util.Date;

/**
 * Created by babich on 3/25/2015.
 */

public  class DataProvider
{
    static  DataBaseHelper helper;
    public  static  DataBaseHelper GetProvider()
    {
        if (helper == null)
        {
            helper = new DataBaseHelper(App.getInstance().getApplicationContext());
        }
        return helper;
    }

    public static void SaveTemp(float value) {
        GetProvider().addItem(new TempItem(new Date(), value ));

    }
}
