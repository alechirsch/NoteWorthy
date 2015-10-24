package com.example.alec.tinlip;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

/**
 * Created by Alec on 10/24/2015.
 */
public class DatabaseOperations extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "Tinlip";
    private static final String TEXT_TYPE = " TEXT";
    private static final String REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TableData.TableInfo.TABLE_NAME + " (" +
                    TableData.TableInfo._ID + " INTEGER PRIMARY KEY," +
                    TableData.TableInfo.COLUMN_NAME_LATITUDE + REAL_TYPE + COMMA_SEP +
                    TableData.TableInfo.COLUMN_NAME_LONGITUDE + REAL_TYPE + COMMA_SEP +
                    TableData.TableInfo.COLUMN_NAME_ALTITUDE + REAL_TYPE + COMMA_SEP +
                    TableData.TableInfo.COLUMN_NAME_NOTE + TEXT_TYPE + COMMA_SEP +
                    TableData.TableInfo.COLUMN_NAME_TIME_STAMP + TEXT_TYPE +

                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TableData.TableInfo.TABLE_NAME;

    public DatabaseOperations(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database operations", "Database created");
    }
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);

        Log.d("Database operations", "Database created");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void insert(DatabaseOperations dop, double latitude, double longitude, double altitude, String note){
        SQLiteDatabase db = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableData.TableInfo.COLUMN_NAME_LATITUDE, latitude);
        cv.put(TableData.TableInfo.COLUMN_NAME_LONGITUDE, longitude);
        cv.put(TableData.TableInfo.COLUMN_NAME_ALTITUDE, altitude);
        cv.put(TableData.TableInfo.COLUMN_NAME_NOTE, note);
        cv.put(TableData.TableInfo.COLUMN_NAME_TIME_STAMP, new Date().toString());
        long success = db.insert(TableData.TableInfo.TABLE_NAME, null, cv);
        Log.d("Database operations", "inserted values");
    }

    public Cursor getNote(DatabaseOperations dop){
        SQLiteDatabase db = dop.getReadableDatabase();
        String[] columns = {TableData.TableInfo.COLUMN_NAME_LATITUDE, TableData.TableInfo.COLUMN_NAME_LONGITUDE, TableData.TableInfo.COLUMN_NAME_ALTITUDE, TableData.TableInfo.COLUMN_NAME_NOTE, TableData.TableInfo.COLUMN_NAME_TIME_STAMP};
        Cursor cr = db.query(TableData.TableInfo.TABLE_NAME, columns, null, null, null, null, null);
        return cr;
    }
}
