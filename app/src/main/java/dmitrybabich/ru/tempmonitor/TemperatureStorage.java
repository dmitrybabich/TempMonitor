package dmitrybabich.ru.tempmonitor;


public class TemperatureStorage {

    static TemperatureStorage self;

    public static TemperatureStorage getInstance() {
       if (self == null) {
        self = new TemperatureStorage();
       }
        return self;
    }

    public  float CurrentTemperature = TWUtilConst.UNKNOWN_TEMP_VALUE;
}
