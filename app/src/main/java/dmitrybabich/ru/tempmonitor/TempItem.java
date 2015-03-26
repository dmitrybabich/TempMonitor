package dmitrybabich.ru.tempmonitor;

import java.util.Date;

public class TempItem {
    private long id;
    private Date date;
    private float temp;

    public TempItem(Date date, float temp) {
        this.date = date;
        this.temp =temp;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return date + "-" + temp;
    }

    public Date getDate() {
        return date;
    }

    public float getTemp() {
        return temp;
    }
}
