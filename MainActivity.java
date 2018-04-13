package com.example.hails.raiderpoint;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHelper mDBHelper;
    private LocationManager locationManager;
    private LocationListener listener;

    // Array of strings...
    ArrayList<String> eventList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mDBHelper = new DBHelper(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("test", "lat: " + location.getLatitude() + " long: " + location.getLongitude());
                StringBuffer buffer2 = new StringBuffer();
                Cursor res = mDBHelper.getLocation();
                if (res.getCount() == 0) {
                    //error
                    eventList.add("no events in events table");
                    return;
                }

                while (res.moveToNext()) {
                    if ((res.getInt(1) < ((location.getLatitude() * 100000) + 90)) &&   // upper bound
                            (res.getInt(1) > ((location.getLatitude() * 100000) - 90)) &&  //lower bound
                            (res.getInt(2) < ((location.getLongitude() * 100000) + 90)) && // right bound
                            (res.getInt(2) > ((location.getLongitude() * 100000) - 90))    //left bound
                            )
                    {
                        String eventBuilding = res.getString(0);
                        Cursor res2 = mDBHelper.getEvent();

                        while (res2.moveToNext()) {
                            if (res2.getString(2).equals(eventBuilding))
                            {
                                eventList.add(res2.getString(1));
                            }
                        }

                    }
                    else
                    {
                        eventList.add("No event in this location: " + res.getString(0));
                    }

                    ArrayAdapter adapter = new ArrayAdapter<>(MainActivity.this,
                            R.layout.activity_listview, eventList);

                    ListView listView = (ListView) findViewById(R.id.mobile_list);
                    listView.setAdapter(adapter);
                }



                Log.d("test", "end of onLocationChanged");

            }   //end onLocationChanged

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
    /*
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
    */
    /*
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
    */
    /*
    public void findEvents()
    {
        btnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    */
    /*
    public void showMessage(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    */

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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);

            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
        /*
        btnGPS.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                //noinspection MissingPermission
                Log.d("test", "onClick: hey, you clicked the GPS button");


                Log.d("test", "onClick: this is after the request of coordinates");
            }
        });
        */
    }
}
