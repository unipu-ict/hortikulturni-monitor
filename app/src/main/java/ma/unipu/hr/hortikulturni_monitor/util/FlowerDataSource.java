package ma.unipu.hr.hortikulturni_monitor.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Mihovil
 */
public class FlowerDataSource {

    SQLiteOpenHelper dbHelper;
    SQLiteDatabase database;

    private static final String[] allFlowerColumns = {
        DBHelper.FLOWER_ID,
        DBHelper.FLOWER_NAME,
        DBHelper.FLOWER_TEMP,
        DBHelper.FLOWER_SOIL,
        DBHelper.FLOWER_SUN,
        DBHelper.FLOWER_DESC,
        DBHelper.FLOWER_TIME
    };

    private static final String[] allStatusColumns = {
        DBHelper.STATUS_ID,
        DBHelper.STATUS_FLOWER_ID,
        DBHelper.STATUS_TEMP,
        DBHelper.STATUS_SOIL,
        DBHelper.STATUS_SUN,
        DBHelper.STATUS_TIME
    };

    public FlowerDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() {
        Log.i(FlowerPowerConstants.TAG, "Database opened.");
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        Log.i(FlowerPowerConstants.TAG, "Database close.");
        dbHelper.close();
    }

    public void create(Flower flower) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.FLOWER_NAME, flower.getName());
        values.put(DBHelper.FLOWER_TEMP, flower.getTemperature());
        values.put(DBHelper.FLOWER_SOIL, flower.getSoil());
        values.put(DBHelper.FLOWER_SUN, flower.getSunlight());
        values.put(DBHelper.FLOWER_DESC, flower.getDesc());
        values.put(DBHelper.FLOWER_TIME, flower.getTimeStamp());
        database.insert(DBHelper.TABLE_FLOWER, null, values);
        //long insertId = database.insert(DBHelper.TABLE_FLOWER, null, values);
        //return insertId;
    }

    public Flower[] getAllFlowers() {
        Cursor cursor = database.query(DBHelper.TABLE_FLOWER, allFlowerColumns,
                null, null, null, null, null);
        Flower[] flowers = new Flower[cursor.getCount()];
        if (cursor.getCount() > 0) {
            int i = 0;
            while (cursor.moveToNext()) {
                String flowerName = cursor.getString(cursor.getColumnIndex(DBHelper.FLOWER_NAME));
                String flowerDesc = cursor.getString(cursor.getColumnIndex(DBHelper.FLOWER_DESC));
                String flowerTimeStamp = cursor.getString(cursor.getColumnIndex(DBHelper.FLOWER_TIME));
                int flowerTemp = cursor.getInt(cursor.getColumnIndex(DBHelper.FLOWER_TEMP));
                int flowerSoil = cursor.getInt(cursor.getColumnIndex(DBHelper.FLOWER_SOIL));
                int flowerSun = cursor.getInt(cursor.getColumnIndex(DBHelper.FLOWER_SUN));
                flowers[i] = new Flower(flowerName, flowerDesc, flowerTimeStamp, flowerTemp, flowerSoil, flowerSun);
                i++;
            }
        }
        return flowers;
    }

    public int getFlowerSize() {
        Cursor cursor = database.query(DBHelper.TABLE_FLOWER, allFlowerColumns,
                null, null, null, null, null);
        if (cursor.getCount() > 0) {
            return cursor.getCount();
        }
        return 0;
    }
}


