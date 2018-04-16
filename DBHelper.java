package com.example.hails.raiderpoint;

/**
 * Created by Hails on 4/13/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Hails on 4/3/2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    //define database and table
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "RaiderPoint";
    private static final String DATABASE_LOCATION = "Location";

    SQLiteDatabase db;

    public DBHelper (Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getReadableDatabase();
        Log.d("test", "constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        String sqlStatement = "CREATE TABLE Location(location VARCHAR(20) PRIMARY KEY, latitude INTEGER, longitude INTEGER)";
        String sqlStatement2 = "INSERT INTO Location VALUES ('KHIC', 4090414, -8110800)";
        String sqlStatement3 = "INSERT INTO Location VALUES ('HPCC', 4090524, -8111115)";
        String sqlStatement4 = "INSERT INTO Location VALUES ('Chapman', 4090368, -8110876)";
        String sqlStatement11 = "INSERT INTO Location VALUES ('McMaster', 4090542, -8111009)";

        database.execSQL(sqlStatement);
        Log.d("test", "just executed 1st sql");
        database.execSQL(sqlStatement2);
        Log.d("test", "just executed 2nd sql");
        database.execSQL(sqlStatement3);
        database.execSQL(sqlStatement4);
        database.execSQL(sqlStatement11);

        String sqlStatement5 = "CREATE TABLE Event(EventID INTEGER, EName VARCHAR (20) NOT NULL, BName VARCHAR(10) NOT NULL, Month int NOT NULL, Day int NOT NULL, Year int NOT NULL, HourTime int NOT NULL, MinuteTime int NOT NULL, PRIMARY KEY (EventID), FOREIGN KEY (BName) REFERENCES Location(location))";
        String sqlStatement6 = "INSERT INTO Event VALUES (1, 'Event1', 'KHIC', 4, 25, 2018, 6, 30)";
        String sqlStatement7 = "INSERT INTO Event VALUES (2, 'Event2', 'KHIC', 5, 5, 2018, 9, 0)";
        String sqlStatement8 = "INSERT INTO Event VALUES (3, 'Event3', 'HPCC', 4, 15, 2018, 17, 0)";
        String sqlStatement9 = "INSERT INTO Event VALUES (4, 'Event4', 'Chapman', 8, 25, 2018, 12, 15)";
        String sqlStatement10 = "INSERT INTO Event VALUES (5, 'Boy''s Track Meet', 'McMaster', 8, 25, 2018, 12, 0)";
        String sqlStatement12 = "INSERT INTO Event VALUES (6, 'Event6', 'HPCC', 10, 31, 2018, 15, 15)";
        String sqlStatement13 = "INSERT INTO Event VALUES (7, 'Girl''s Volleyball', 'McMaster', 10, 31, 2018, 19, 0)";
        database.execSQL(sqlStatement5);
        database.execSQL(sqlStatement6);
        database.execSQL(sqlStatement7);
        database.execSQL(sqlStatement8);
        database.execSQL(sqlStatement9);
        database.execSQL(sqlStatement10);
        database.execSQL(sqlStatement12);
        database.execSQL(sqlStatement13);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
    {
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_LOCATION);
        onCreate(database);
    }

    public Cursor getLocation()
    {
        String selectQuery = "SELECT * FROM " + DATABASE_LOCATION;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(selectQuery, null);
        return res;
    }

    public Cursor getEvent()
    {
        String selectQuery2 = "SELECT * FROM Event";

        SQLiteDatabase db2 = this.getWritableDatabase();
        Cursor res2 = db2.rawQuery(selectQuery2, null);
        return res2;
    }

}

