package com.example.hails.GPS13;

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

    //define column names for table
    private static final String LOCATION = "location";
    private static final String LONGITUDE = "longitude";
    private static final String LATITUDE = "latitude";

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
        /*
        String sqlStatement = "CREATE TABLE " + DATABASE_LOCATION + "(" + LOCATION + " VARCHAR(20) PRIMARY KEY, " + LATITUDE + " VARCHAR(20), " + LONGITUDE + " VARCHAR(20)" + ")";
        String sqlStatement2 = "INSERT INTO Location VALUES ('KHIC', '40.90414806273015', '-81.10800772686889')";
        String sqlStatement3 = "INSERT INTO Location VALUES ('HPCC', '40.905245813699736', '-81.11115395772799')";
        String sqlStatement4 = "INSERT INTO Location VALUES ('Chapman', '40.903681798222685', '-81.10876276870658')";
        //String sqlStatement5 = "INSERT INTO Location VALUES ('Chapman', '40.903681798222685', '-81.10876276870658')";

        String sqlStatement = "CREATE TABLE Location(location VARCHAR(20) PRIMARY KEY, latitude INTEGER, longitude INTEGER)";
        String sqlStatement2 = "INSERT INTO Location VALUES ('KHIC', 4090414806273015, -8110800772686889)";
        String sqlStatement3 = "INSERT INTO Location VALUES ('HPCC', 40905245813699736, -8111115395772799)";
        String sqlStatement4 = "INSERT INTO Location VALUES ('Chapman', 40903681798222685, -8110876276870658)";
        String sqlStatement11 = "INSERT INTO Location VALUES ('McMaster', 4090542589186688, -81.11009850877372)";

        */

        String sqlStatement = "CREATE TABLE Location(location VARCHAR(20) PRIMARY KEY, latitude INTEGER, longitude INTEGER)";
        String sqlStatement2 = "INSERT INTO Location VALUES ('KHIC', 4090414, -8110800)";
        String sqlStatement3 = "INSERT INTO Location VALUES ('HPCC', 4090524, -8111115)";
        String sqlStatement4 = "INSERT INTO Location VALUES ('Chapman', 4090368, -8110876)";
        String sqlStatement5 = "INSERT INTO Location VALUES ('Quad', 4090466, -8110971)";
        String sqlStatement6 = "INSERT INTO Location VALUES ('Dewald Chapel', 4090431, -8111077)";
        String sqlStatement7 = "INSERT INTO Location VALUES ('Mount Union Stadium', 4090366, -8111196)";
        String sqlStatement8 = "INSERT INTO Location VALUES ('Gallahar Hall', 4090349, -8110617)";
        String sqlStatement9 = "INSERT INTO Location VALUES ('Bracy', 409039, -8110673)";
        String sqlStatement10 = "INSERT INTO Location VALUES ('Giese Center', 4090628, -8110652)";
        String sqlStatement11 = "INSERT INTO Location VALUES ('McMaster', 4090542, -8111009)";
        String sqlStatement12 = "INSERT INTO Location VALUES ('Beegly', 4090204, -8110875)";
        String sqlStatement13 = "INSERT INTO Location VALUES ('MAAC', 4090223, -8111174)";
        String sqlStatement14 = "INSERT INTO Location VALUES ('Engineering/Business', 4090461, -8110874)";
        String sqlStatement15 = "INSERT INTO Location VALUES ('Tolerton & Hood', 4090465, -8110828)";

        database.execSQL(sqlStatement);
        Log.d("test", "just executed 1st sql");
        database.execSQL(sqlStatement2);
        Log.d("test", "just executed 2nd sql");
        database.execSQL(sqlStatement3);
        database.execSQL(sqlStatement4);
        database.execSQL(sqlStatement5);
        database.execSQL(sqlStatement6);
        database.execSQL(sqlStatement7);
        database.execSQL(sqlStatement8);
        database.execSQL(sqlStatement9);
        database.execSQL(sqlStatement10);
        database.execSQL(sqlStatement11);
        database.execSQL(sqlStatement12);
        database.execSQL(sqlStatement13);
        database.execSQL(sqlStatement14);
        database.execSQL(sqlStatement15);


        // Possibly add the 'Catagory' of the event for the future settings
        String sqlStatement5 = "CREATE TABLE Event(EventID INTEGER, EName VARCHAR (20) NOT NULL, BName VARCHAR(10) NOT NULL, Month int NOT NULL, Day int NOT NULL, Year int NOT NULL, HourTime int NOT NULL, MinuteTime int NOT NULL, PRIMARY KEY (EventID), FOREIGN KEY (BName) REFERENCES Location(location))";
        String sqlStatement6 = "INSERT INTO Event VALUES (1, 'Reading Day', 'KHIC', 4, 25, 2018, 10, 30)";
        String sqlStatement7 = "INSERT INTO Event VALUES (2, 'WOC Portfolio Help', 'KHIC', 5, 5, 2018, 16, 00)";
        String sqlStatement8 = "INSERT INTO Event VALUES (3, 'Destress Fest', 'HPCC', 4, 20, 2018, 13, 00)";
        String sqlStatement9 = "INSERT INTO Event VALUES (4, 'Ghost Tours', 'Chapman', 8, 25, 2018, 23, 15)";
        String sqlStatement10 = "INSERT INTO Event VALUES (5, 'Floor Meeting', 'McMaster', 8, 25, 2018, 21, 15)";
        String sqlStatement11 = "INSERT INTO Event VALUES (6, 'Book Sale', 'HPCC', 10, 31, 2018, 15, 15)";
        String sqlStatement12 = "INSERT INTO Event VALUES (7, 'Lab Safety Day', 'Bracy', 6, 25, 2018, 14, 30)";
        String sqlStatement13 = "INSERT INTO Event VALUES (8, '12 Days of Science', 'Bracy', 12, 20, 2018, 10, 00)";
        String sqlStatement14 = "INSERT INTO Event VALUES (9, 'Amnesty Day', 'KHIC', 5, 10, 2018, 8, 30)";
        String sqlStatement15 = "INSERT INTO Event VALUES (10, 'DWOC Tutorial', 'KHIC', 4, 25, 2018, 14, 00)";
        String sqlStatement16 = "INSERT INTO Event VALUES (11,  Freshman'Orientation', 'MAAC', 8, 14, 2018, 9, 00)";
        String sqlStatement17 = "INSERT INTO Event VALUES (12, 'DWOC Tutorial', 'KHIC', 9, 30, 2018, 15, 00)";
        String sqlStatement18 = "INSERT INTO Event VALUES (13, 'Schooler Lecture', 'MAAC', 4, 30, 2018, 19, 00)";
        String sqlStatement19 = "INSERT INTO Event VALUES (14, 'Swimming Tutorial', 'MAAC', 9, 21, 2018, 10, 00)";
        String sqlStatement20 = "INSERT INTO Event VALUES (15, 'Mens VBall Tryouts', 'MAAC', 10, 5, 2018, 9, 45)";
        String sqlStatement21 = "INSERT INTO Event VALUES (16, 'Movie', 'MAAC', 10, 13, 2018, 20, 00)";
        String sqlStatement22 = "INSERT INTO Event VALUES (17, 'Trivia', 'MAAC', 8, 25, 2018, 18, 00)";
        String sqlStatement23 = "INSERT INTO Event VALUES (18, 'Relay For Life', 'MAAC', 4, 14, 2018, 18, 30)";
        String sqlStatement24 = "INSERT INTO Event VALUES (19, 'Movie', 'Quad', 8, 27, 2018, 20, 30)";
        String sqlStatement25 = "INSERT INTO Event VALUES (20, 'Kickball', 'Quad', 9, 14, 2018, 13, 30)";
        String sqlStatement26 = "INSERT INTO Event VALUES (21, 'Inflatables', 'Quad', 9, 22, 2018, 14, 00)";
        String sqlStatement27 = "INSERT INTO Event VALUES (22, 'Memorial', 'Quad', 9, 11, 2018, 10, 00)";
        String sqlStatement28 = "INSERT INTO Event VALUES (23, 'EAster Mass', 'Dewald Chapel', 4, 22, 2018, 9, 00)";
        String sqlStatement29 = "INSERT INTO Event VALUES (24, 'Diversity Day', 'Dewald Chapel', 9, 10, 2018, 8, 00)";
        String sqlStatement30 = "INSERT INTO Event VALUES (25, 'Fun Run', 'Mount Union Stadium', 10, 22, 2018, 9, 00)";
        String sqlStatement26 = "INSERT INTO Event VALUES (27, 'Football Game', 'Mount Union Stadium', 10, 2, 2018, 14, 15)";
        String sqlStatement27 = "INSERT INTO Event VALUES (28, 'Relay For Life', 'Mount Union Stadium', 11, 1, 2018, 19, 00)";
        String sqlStatement28 = "INSERT INTO Event VALUES (29, 'PA Vist Day', 'Gallahar Hall', 8, 16, 2018, 12, 00)";
        String sqlStatement29 = "INSERT INTO Event VALUES (30, 'Ben Hayes Recital', 'Giese Center', 4, 26, 2018, 17, 30)";
        String sqlStatement30 = "INSERT INTO Event VALUES (31, 'Cook Lecture', 'Giese Center', 9, 10, 2018, 18, 00)";
        String sqlStatement31 = "INSERT INTO Event VALUES (32, 'Presidents Tour', 'Beegly', 12, 2, 2018, 15, 00)";
        String sqlStatement32 = "INSERT INTO Event VALUES (33, 'Business Club Meeting', 'Engineering/Business', 11, 12, 2018, 18, 00)";
        String sqlStatement33 = "INSERT INTO Event VALUES (34, 'Lab Safety Day', 'Engineering/Business', 9, 12, 2018, 9, 00)";
        String sqlStatement34 = "INSERT INTO Event VALUES (35, 'Math Club', 'Tolerton & Hood', 9, 15, 2018, 14, 00)";
        database.execSQL(sqlStatement5);
        database.execSQL(sqlStatement6);
        database.execSQL(sqlStatement7);
        database.execSQL(sqlStatement8);
        database.execSQL(sqlStatement9);
        database.execSQL(sqlStatement10);
        database.execSQL(sqlStatement12);
        database.execSQL(sqlStatement13);
        database.execSQL(sqlStatement14);
        database.execSQL(sqlStatement15);
        database.execSQL(sqlStatement16);
        database.execSQL(sqlStatement17);
        database.execSQL(sqlStatement18);
        database.execSQL(sqlStatement19);
        database.execSQL(sqlStatement21);
        database.execSQL(sqlStatement22);
        database.execSQL(sqlStatement23);
        database.execSQL(sqlStatement24);
        database.execSQL(sqlStatement25);
        database.execSQL(sqlStatement26);
        database.execSQL(sqlStatement27);
        database.execSQL(sqlStatement28);
        database.execSQL(sqlStatement29);
        database.execSQL(sqlStatement30);
        database.execSQL(sqlStatement31);
        database.execSQL(sqlStatement32);
        database.execSQL(sqlStatement33);
        database.execSQL(sqlStatement34);
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

    public Cursor getEventNearby()
    {
        String selectQuery3 = "";

        SQLiteDatabase db3 = this.getWritableDatabase();
        Cursor res3 = db3.rawQuery(selectQuery3, null);
        return res3;
    }

}
