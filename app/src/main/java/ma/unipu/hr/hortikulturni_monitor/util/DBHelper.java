package ma.unipu.hr.hortikulturni_monitor.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Mihovil
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "multiflower.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_FLOWER = "flowers";
    public static final String FLOWER_ID = "id";
    public static final String FLOWER_NAME = "naziv";
    public static final String FLOWER_TEMP = "temperatura";
    public static final String FLOWER_SOIL = "tlo";
    public static final String FLOWER_SUN = "sunce";
    public static final String FLOWER_DESC = "opis";
    public static final String FLOWER_TIME = "vrijeme";

    public static final String TABLE_STATUS = "status";
    public static final String STATUS_ID = "id";
    public static final String STATUS_FLOWER_ID = "flowerId";
    public static final String STATUS_TEMP = "temperatura";
    public static final String STATUS_SOIL = "tlo";
    public static final String STATUS_SUN = "sunce";
    public static final String STATUS_TIME = "vrijeme";

    private static final String FLOWER_CREATE =
            "CREATE TABLE " + TABLE_FLOWER + " (" +
                    FLOWER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FLOWER_NAME + " TEXT, " +
                    FLOWER_TEMP + " INTEGER, " +
                    FLOWER_SOIL + " INTEGER, " +
                    FLOWER_SUN + " INTEGER, " +
                    FLOWER_DESC + " TEXT, " +
                    FLOWER_TIME + " TEXT " +
                    ")";

    private static final String STATUS_CREATE =
            "CREATE TABLE " + TABLE_STATUS + " (" +
                    STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    STATUS_FLOWER_ID + " INTEGER, " +
                    STATUS_TEMP + " INTEGER, " +
                    STATUS_SOIL + " INTEGER, " +
                    STATUS_SUN + " INTEGER, " +
                    STATUS_TIME + " TEXT " +
                    ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FLOWER_CREATE);
        Log.i(FlowerPowerConstants.TAG, "Flower table has been created.");
        db.execSQL(STATUS_CREATE);
        Log.i(FlowerPowerConstants.TAG, "Status table has been created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLOWER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATUS);
        onCreate(db);
    }
}
