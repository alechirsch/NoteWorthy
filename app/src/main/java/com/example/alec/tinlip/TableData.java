package com.example.alec.tinlip;
import android.provider.BaseColumns;

/**
 * Created by Alec on 10/24/2015.
*/
public final class TableData {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public TableData() {}

    /* Inner class that defines the table contents */
    public static abstract class TableInfo implements BaseColumns {
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_NAME_NOTE = "note";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
        public static final String COLUMN_NAME_ALTITUDE = "altitude";
        public static final String COLUMN_NAME_TIME_STAMP = "timestamp";
    }
}