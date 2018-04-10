package com.example.hails.GPS13;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    DBHelper mDBHelper;
    Button btnLocationTable;
    Button btnEventTable;
    Button btnGPS;
    private LocationManager locationManager;
    private LocationListener listener;
    private Integer counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mDBHelper = new DBHelper(this);

        btnLocationTable = (Button) findViewById(R.id.button);
        locationTable();

        btnEventTable = (Button) findViewById(R.id.button2);
        eventTable();

        btnGPS = (Button) findViewById(R.id.button3);
        findEvents();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //counter = 1;
                //if(counter == 1)
                //{
                    //get events nearby
                    //Log.d("test", "inside onLocationChanged");
                    Log.d("test", "latitude" + (location.getLatitude() * 100000));
                    Log.d("test", "longitude" + (location.getLongitude() * 100000));
                /*
                StringBuffer buffer = new StringBuffer();

                buffer.append("latitude: " + (location.getLatitude() * 1000000000) + " longitude: " + (location.getLongitude() * 1000000000) + "\n\n latitude2: " + (location.getLatitude() - 0.00009));
                showMessage("coordinates", buffer.toString());


                */

                    //pass lat and long into DBHelper method that finds events nearby

                    //display events in showMessage()

                    Cursor res = mDBHelper.getLocation();
                    if(res.getCount() == 0)
                    {
                        //error
                        showMessage("Error", "nothing found");
                        return;
                    }

                    //StringBuffer buffer = new StringBuffer();

                    while (res.moveToNext())
                    {
                        if((res.getInt(1) < ((location.getLatitude() * 100000) + 90)) &&   // upper bound
                                (res.getInt(1) > ((location.getLatitude() * 100000) - 90)) &&  //lower bound
                                (res.getInt(2) < ((location.getLongitude() * 100000) + 90)) && // right bound
                                (res.getInt(2) < ((location.getLongitude() * 100000) - 90))    //left bound
                                )
                        {
                            String eventBuilding = res.getString(0);
                            Cursor res2 = mDBHelper.getEvent();
                            StringBuffer buffer2 = new StringBuffer();
                            while (res2.moveToNext())
                            {
                                if(res2.getString(2).equals(eventBuilding))
                                {
                                    buffer2.append("EventID: " + res2.getInt(0) + "\n");
                                    buffer2.append("Event Name: " + res2.getString(1) + "\n");
                                    buffer2.append("Building Name: " + res2.getString(2) + "\n");
                                    buffer2.append("Date: " + res2.getInt(3) + "." + res2.getInt(4) + "." + res2.getInt(5) + "\n");
                                    if(res2.getInt(6) > 12 || res2.getInt(6) == 12)  //PM
                                    {
                                        int hour;
                                        if(res2.getInt(6) > 12)
                                        {
                                            hour = res2.getInt(6) - 12;
                                        }
                                        else
                                        {
                                            hour = 12;
                                        }

                                        if(res2.getInt(7) == 0)
                                        {
                                            buffer2.append("Time: " + hour + ":00 PM\n\n");
                                        }
                                        else
                                        {
                                            buffer2.append("Time: " + hour + ":" + res2.getInt(7) + " PM\n\n");
                                        }
                                    }
                                    else    //AM
                                    {
                                        if(res2.getInt(7) == 0)
                                        {
                                            buffer2.append("Time: " + res2.getInt(6) + ":00 AM\n\n");
                                        }
                                        else
                                        {
                                            buffer2.append("Time: " + res2.getInt(6) + ":" + res2.getInt(7) + " AM\n\n");
                                        }
                                    }
                                }
                            }
                            showMessage("Events Nearby", buffer2.toString());
                        }
                    }
                    Log.d("test", "end of onLocationChanged");
                //}



            }   //end onLocationChanged


                /*
                Log.d("test", "onLocationChanged: right before the append");
                upper = location.getLatitude() + 0.0009000009; //upper bound
                lower = location.getLatitude() - 0.0009000009; //lower bound
                right = location.getLongitude() + 0.0009000009; //right bound
                left = location.getLongitude() - 0.0009000009; //left bound
                t.append("\n latitude: " + location.getLatitude() + "\n longitude: "
                        + location.getLongitude() + "\n upper bound: " + upper + "\n lower bound: " + lower + "\n right bound: " + right + "\n left bound: " + left + "\n");
                Log.d("test", "onLocationChanged: right after the append");
                */


            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        configure_button();
    }


    public void locationTable()
    {
        btnLocationTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Cursor res = mDBHelper.getLocation();
            if(res.getCount() == 0)
            {
                //error
                showMessage("Error", "nothing found");
                return;
            }

            StringBuffer buffer = new StringBuffer();
            while(res.moveToNext())
            {
                buffer.append("Building Name: " + res.getString(0) + "\n");
                buffer.append("Latitude: " + res.getString(1) + "\n");
                buffer.append("Longitude: " + res.getString(2) + "\n\n");
            }

            //show data
            showMessage("Locations", buffer.toString());
            }
        });
    }

    public void eventTable()
    {
        btnEventTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = mDBHelper.getEvent();
                if(res.getCount() == 0)
                {
                    //error
                    showMessage("Error", "nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext())
                {
                    buffer.append("EventID: " + res.getInt(0) + "\n");
                    buffer.append("Event Name: " + res.getString(1) + "\n");
                    buffer.append("Building Name: " + res.getString(2) + "\n");
                    buffer.append("Date: " + res.getInt(3) + "." + res.getInt(4) + "." + res.getInt(5) + "\n");

                    if(res.getInt(6) > 12 || res.getInt(6) == 12)  //PM
                    {
                        int hour;
                        if(res.getInt(6) > 12)
                        {
                            hour = res.getInt(6) - 12;
                        }
                        else
                        {
                            hour = 12;
                        }

                        if(res.getInt(7) == 0)
                        {
                            buffer.append("Time: " + hour + ":00 PM\n\n");
                        }
                        else
                        {
                            buffer.append("Time: " + hour + ":" + res.getInt(7) + " PM\n\n");
                        }
                    }
                    else    //AM
                    {
                        if(res.getInt(7) == 0)
                        {
                            buffer.append("Time: " + res.getInt(6) + ":00 AM\n\n");
                        }
                        else
                        {
                            buffer.append("Time: " + res.getInt(6) + ":" + res.getInt(7) + " AM\n\n");
                        }
                    }
                }
                //show data
                showMessage("Events", buffer.toString());
            }
        });
    }

    public void findEvents()
    {
        btnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void showMessage(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        btnGPS.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                //noinspection MissingPermission
                Log.d("test", "onClick: hey, you clicked the GPS button");
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);

                Log.d("test", "onClick: this is after the request of coordinates");
            }
        });
    }
}
