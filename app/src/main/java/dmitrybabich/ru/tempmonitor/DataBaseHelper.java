package dmitrybabich.ru.tempmonitor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.BaseAdapter;


import java.util.Date;

public class DataBaseHelper  {

    private static final String DB_NAME = "tempmonitor.sqlite3";
    private static final String TABLE_NAME = "history";
    private static final int DB_VESION = 1;
    private static final String KEY_ID = "_id";
    private static final int ID_COLUMN = 0;
    private static final int Date_COLUMN = 1;
    private static final int TEMP_COLUMN = 2;
    private static final String KEY_TEMP = "temp";
    private static final String KEY_DATE = "date";



    private SQLiteDatabase database;
    private DbOpenHelper dbOpenHelper;
    private Context context;



    public DataBaseHelper(Context context) {
        super();
        this.context = context;
        init();
    }



    public Cursor getAllEntries() {
        //Список колонок базы, которые следует включить в результат
        String[] columnsToTake = { KEY_ID, KEY_TEMP,KEY_DATE  };
        // составляем запрос к базе
        return database.query(TABLE_NAME, columnsToTake,
                null, null, null, null, KEY_ID);
    }

    public long addItem(TempItem item) {
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, item.getDate().getTime());
        values.put(KEY_TEMP, item.getTemp());
        long id = database.insert(TABLE_NAME, null, values);
        return id;
    }

/*
    public boolean removeItem(Name nameToRemove) {
        boolean isDeleted = (database.delete(TABLE_NAME, KEY_NAME + "=?",
                new String[] { nameToRemove.getName() })) > 0;
        refresh();
        return isDeleted;
    }

    public boolean updateItem(long id, String newName) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, newName);
        boolean isUpdated = (database.update(TABLE_NAME, values, KEY_ID + "=?",
                new String[] {id+""})) > 0;
        return isUpdated;
    }
*/

    //Прочие служебные методывфзеук

    public void onDestroy() {
        dbOpenHelper.close();
    }


    // Инициализация адаптера: открываем базу и создаем курсор
    private void init() {
        dbOpenHelper = new DbOpenHelper(context, DB_NAME, null, DB_VESION);
        try {
            database = dbOpenHelper.getWritableDatabase();
        } catch (SQLException e) {
            Log.e(this.toString(), "Error while getting database");
        }

    }


    private static class DbOpenHelper extends SQLiteOpenHelper {

        public DbOpenHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        // Вызывается при создании базы на устройстве
        public void onCreate(SQLiteDatabase db) {
            // Посроим стандартный sql-запрос для создания таблицы
            final String CREATE_DB = "CREATE TABLE " + TABLE_NAME + " ("
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_DATE + " REAL,"
                    + KEY_TEMP + " REAL"
                    +");";
            db.execSQL(CREATE_DB);
        }

        @Override

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Тут можно организовать миграцию данных из старой базы в новую
            // или просто "выбросить" таблицу и создать заново
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}


