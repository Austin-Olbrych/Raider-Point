package com.example.hails.raiderpointv2;

/**
 * Created by Hails on 4/21/2018.
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
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {



        //location table
        String sqlStatement1 = "CREATE TABLE Location(location VARCHAR(20) PRIMARY KEY, latitude INTEGER, longitude INTEGER)";
        String sqlStatement2 = "INSERT INTO Location VALUES ('Nothing', 4090414, -8110800)";
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
        String sqlStatement63= "INSERT INTO Location VALUES ('KHIC', 4090414, -8110800)";

        //organization table
        String sqlStatement51 = "CREATE TABLE Org (org VARCHAR(20) PRIMARY KEY)";
        String sqlStatement52 = "INSERT INTO Org VALUES ('Nothing')";
        String sqlStatement53 = "INSERT INTO Org VALUES ('Alpha Delta Pi')";
        String sqlStatement54 = "INSERT INTO Org VALUES ('UMU Athletics')";
        String sqlStatement55 = "INSERT INTO Org VALUES ('Business Club')";
        String sqlStatement56 = "INSERT INTO Org VALUES ('Physics Club')";
        String sqlStatement57 = "INSERT INTO Org VALUES ('UMU Music Group')";
        String sqlStatement58 = "INSERT INTO Org VALUES ('Sigma Nu')";
        String sqlStatement59 = "INSERT INTO Org VALUES ('Spiritual Leadership')";
        String sqlStatement60 = "INSERT INTO Org VALUES ('Office of Student Involvement')";
        String sqlStatement61 = "INSERT INTO Org VALUES ('IC')";
        String sqlStatement62 = "INSERT INTO Org VALUES ('UMU Academic Office')";

        //genre table
        String sqlStatement64 = "CREATE TABLE Genre (genre VARCHAR(20) PRIMARY KEY)";
        String sqlStatement65 = "INSERT INTO Genre VALUES ('Nothing')";
        String sqlStatement66 = "INSERT INTO Genre VALUES ('academics')";
        String sqlStatement67 = "INSERT INTO Genre VALUES ('music')";
        String sqlStatement68 = "INSERT INTO Genre VALUES ('greek life')";
        String sqlStatement69 = "INSERT INTO Genre VALUES ('sport')";
        String sqlStatement70 = "INSERT INTO Genre VALUES ('student involvement')";
        String sqlStatement71 = "INSERT INTO Genre VALUES ('faith')";

        // Possibly add the 'Category' of the event for the future settings
        //event table
        String sqlStatement16 = "CREATE TABLE Event(" +
                                "EventID INTEGER NOT NULL, " +
                                "EName VARCHAR (20) NOT NULL, " +
                                "BName VARCHAR(10) NOT NULL, " +
                                "Month int NOT NULL, " +
                                "Day int NOT NULL, " +
                                "Year int NOT NULL, " +
                                "HourTime int NOT NULL, " +
                                "MinuteTime int NOT NULL, " +
                                "Org VARCHAR(20) NOT NULL, " +
                                "Genre VARCHAR(20) NOT NULL, " +
                                "Description VARCHAR(100) NOT NULL, " +
                                "PRIMARY KEY (EventID), " +
                                "FOREIGN KEY (BName) REFERENCES Location(location), " +
                                "FOREIGN KEY (Org) REFERENCES Org(org), " +
                                "FOREIGN KEY (Genre) REFERENCES Genre(genre))";
        String sqlStatement17 = "INSERT INTO Event VALUES (1, 'Reading Day', 'KHIC', 4, 25, 2018, 10, 30, 'Alpha Delta Pi', 'academics', 'reading day')";
        String sqlStatement18 = "INSERT INTO Event VALUES (2, 'WOC Portfolio Help', 'KHIC', 5, 5, 2018, 16, 00, 'IC', 'academics', 'get help for your WOC portfolio due at the end of your sophomore year')";
        String sqlStatement19 = "INSERT INTO Event VALUES (3, 'Destress Fest', 'HPCC', 4, 20, 2018, 13, 00, 'Office of Student Involvement', 'student involvement', 'some destress with puppies and popcorn')";
        String sqlStatement20 = "INSERT INTO Event VALUES (4, 'Ghost Tours', 'Chapman', 8, 25, 2018, 23, 15, 'Alpha Delta Pi', 'greek life', 'take a tour with ghosts')";
        String sqlStatement21 = "INSERT INTO Event VALUES (5, 'Floor Meeting', 'McMaster', 8, 25, 2018, 21, 15, 'Office of Student Involvement', 'student involvement', 'McMaster residents required floor meeting')";
        String sqlStatement22 = "INSERT INTO Event VALUES (6, 'Book Sale', 'HPCC', 10, 31, 2018, 15, 15, 'UMU Academic Office', 'student involvement', 'come sell your books')";
        String sqlStatement23 = "INSERT INTO Event VALUES (7, 'Lab Safety Day 1', 'Bracy', 6, 25, 2018, 14, 30, 'Physics Club', 'academics', 'learn about safety in labs')";
        String sqlStatement24 = "INSERT INTO Event VALUES (8, '12 Days of Science', 'Bracy', 12, 20, 2018, 10, 00, 'Physics Club', 'academics', 'learn a new physics concept each day for 12 days')";
        String sqlStatement25 = "INSERT INTO Event VALUES (9, 'Amnesty Day', 'KHIC', 5, 10, 2018, 8, 30, 'Spiritual Leadership', 'faith', 'come pray with us')";
        String sqlStatement26 = "INSERT INTO Event VALUES (10, 'DWOC Tutorial Session 1', 'KHIC', 4, 25, 2018, 14, 00, 'IC', 'academics', 'learn about how the DWOC can help you')";
        String sqlStatement27 = "INSERT INTO Event VALUES (11, 'Freshman Orientation', 'MAAC', 8, 14, 2018, 9, 00, 'Office of Student Involvement', 'student involvement', 'orientation for freshmen')";
        String sqlStatement28 = "INSERT INTO Event VALUES (12, 'DWOC Tutorial Session 2', 'KHIC', 9, 30, 2018, 15, 00, 'IC', 'academics', 'learn about how the DWOC can help you')";
        String sqlStatement29 = "INSERT INTO Event VALUES (13, 'Schooler Lecture', 'MAAC', 4, 30, 2018, 19, 00, 'Office of Student Involvement', 'academics', 'hear Bill Gates talk at this year''s Schooler Lecture')";
        String sqlStatement30 = "INSERT INTO Event VALUES (14, 'Swimming Tutorial', 'MAAC', 9, 21, 2018, 10, 00, 'UMU Athletics', 'sport', 'get free swimming lessons from UMU swim team')";
        String sqlStatement31 = "INSERT INTO Event VALUES (15, 'Men''s Volleyball Tryouts', 'MAAC', 10, 5, 2018, 9, 45, 'UMU Athletics', 'sport', 'try out for this season''s men''s volleyball team')";
        String sqlStatement32 = "INSERT INTO Event VALUES (16, 'Movie -- The Incredibles', 'MAAC', 10, 13, 2018, 20, 00, 'Office of Student Involvement', 'student involvement', 'relax tonight with a great movie')";
        String sqlStatement33 = "INSERT INTO Event VALUES (17, 'Trivia', 'MAAC', 8, 25, 2018, 18, 00, 'Alpha Delta Pi', 'greek life', 'show off your trivia skills for cool prizes')";
        String sqlStatement34 = "INSERT INTO Event VALUES (18, 'Relay For Life -- Spring Event', 'MAAC', 6, 14, 2018, 18, 30, 'Office of Student Involvement', 'student involvement', 'help fight and end cancer')";
        String sqlStatement35 = "INSERT INTO Event VALUES (19, 'Movie -- Finding Nemo', 'Quad', 8, 27, 2018, 20, 30, 'Office of Student Involvement', 'student involvement', 'relax tonight with a great movie')";
        String sqlStatement36 = "INSERT INTO Event VALUES (20, 'Kickball', 'Quad', 9, 14, 2018, 13, 30, 'Sigma Nu', 'greek life', 'kickball for al students')";
        String sqlStatement37 = "INSERT INTO Event VALUES (21, 'Inflatables', 'Quad', 9, 22, 2018, 14, 00, 'Sigma Nu', 'greek life', 'come have fun with giant inflatables')";
        String sqlStatement38 = "INSERT INTO Event VALUES (22, 'Memorial', 'Quad', 9, 11, 2018, 10, 00, 'Sigma Nu', 'greek life', 'memorial')";
        String sqlStatement39 = "INSERT INTO Event VALUES (23, 'Easter Mass', 'Dewald Chapel', 4, 22, 2018, 9, 00, 'Spiritual Leadership', 'faith', 'Church services')";
        String sqlStatement40 = "INSERT INTO Event VALUES (24, 'Diversity Day', 'Dewald Chapel', 9, 10, 2018, 8, 00, 'Office of Student Involvement', 'student involvement', 'learn about different cultures from around the world')";
        String sqlStatement41 = "INSERT INTO Event VALUES (25, 'Fun Run', 'Mount Union Stadium', 10, 22, 2018, 9, 00, 'Office of Student Involvement', 'sport', 'do a half-marathon to raise money for charity')";
        String sqlStatement42 = "INSERT INTO Event VALUES (27, 'Football Game', 'Mount Union Stadium', 10, 2, 2018, 14, 15, 'UMU Athletics', 'sport', 'UMU football at-home game')";
        String sqlStatement43 = "INSERT INTO Event VALUES (28, 'Relay For Life -- Fall Event', 'Mount Union Stadium', 11, 1, 2018, 19, 00, 'Office of Student Involvement', 'student involvement', 'help fight and end cancer')";
        String sqlStatement44 = "INSERT INTO Event VALUES (29, 'PA Visit Day', 'Gallahar Hall', 8, 16, 2018, 12, 00, 'Office of Student Involvement', 'student involvement', 'visiting day')";
        String sqlStatement45 = "INSERT INTO Event VALUES (30, 'Ben Hayes Recital', 'Giese Center', 4, 26, 2018, 17, 30, 'UMU Music Group', 'music', 'UMU choir performance')";
        String sqlStatement46 = "INSERT INTO Event VALUES (31, 'Ekey Lecture', 'Bracy', 9, 10, 2018, 18, 00, 'Physics Club', 'academics', 'Learn about Ekey''s latest research')";
        String sqlStatement47 = "INSERT INTO Event VALUES (32, 'Presidents Lunch', 'Beegly', 12, 2, 2018, 15, 00, 'Sigma Nu', 'greek life', 'Lunch for Honors students with the president')";
        String sqlStatement48 = "INSERT INTO Event VALUES (33, 'Business Club Meeting', 'Engineering/Business', 11, 12, 2018, 18, 00, 'Business Club', 'academics', 'Business Club meeting -- new members welcomed')";
        String sqlStatement49 = "INSERT INTO Event VALUES (34, 'Lab Safety Day 2', 'Engineering/Business', 9, 12, 2018, 9, 00, 'Physics Club', 'academics', '2nd day for lab safety training')";
        String sqlStatement50 = "INSERT INTO Event VALUES (35, 'Math Club', 'Tolerton & Hood', 9, 15, 2018, 14, 00, 'Office of Student Involvement', 'academics', 'Math club meeting -- new members welcomed')";

        //execute sql statements
        database.execSQL(sqlStatement1);
        database.execSQL(sqlStatement2);
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
        database.execSQL(sqlStatement16);
        database.execSQL(sqlStatement17);
        database.execSQL(sqlStatement18);
        database.execSQL(sqlStatement19);
        database.execSQL(sqlStatement20);
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
        database.execSQL(sqlStatement35);
        database.execSQL(sqlStatement36);
        database.execSQL(sqlStatement37);
        database.execSQL(sqlStatement38);
        database.execSQL(sqlStatement39);
        database.execSQL(sqlStatement40);
        database.execSQL(sqlStatement41);
        database.execSQL(sqlStatement42);
        database.execSQL(sqlStatement43);
        database.execSQL(sqlStatement44);
        database.execSQL(sqlStatement45);
        database.execSQL(sqlStatement46);
        database.execSQL(sqlStatement47);
        database.execSQL(sqlStatement48);
        database.execSQL(sqlStatement49);

        database.execSQL(sqlStatement50);
        database.execSQL(sqlStatement51);
        database.execSQL(sqlStatement52);
        database.execSQL(sqlStatement53);
        database.execSQL(sqlStatement54);
        database.execSQL(sqlStatement55);
        database.execSQL(sqlStatement56);
        database.execSQL(sqlStatement57);
        database.execSQL(sqlStatement58);
        database.execSQL(sqlStatement59);
        database.execSQL(sqlStatement60);
        database.execSQL(sqlStatement61);
        database.execSQL(sqlStatement62);

        database.execSQL(sqlStatement63);

        database.execSQL(sqlStatement64);
        database.execSQL(sqlStatement65);
        database.execSQL(sqlStatement66);
        database.execSQL(sqlStatement67);
        database.execSQL(sqlStatement68);
        database.execSQL(sqlStatement69);
        database.execSQL(sqlStatement70);
        database.execSQL(sqlStatement71);

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

    public Cursor getOrg()
    {
        String selectQuery = "SELECT * FROM Org";

        SQLiteDatabase db3 = this.getWritableDatabase();
        Cursor res3 = db3.rawQuery(selectQuery, null);
        return res3;
    }

    public Cursor getGenre()
    {
        String selectQuery = "SELECT * FROM Genre";

        SQLiteDatabase db4 = this.getWritableDatabase();
        Cursor res4 = db4.rawQuery(selectQuery, null);
        return res4;
    }

}


