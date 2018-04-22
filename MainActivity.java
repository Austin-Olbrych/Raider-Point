package com.example.hails.raiderpoint;

import android.Manifest;
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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    DBHelper mDBHelper;
    private LocationManager locationManager;
    private LocationListener listener;
    private Button allEvents;

    // Array of strings...
    ArrayList<String> eventList = new ArrayList<>();
    ArrayList<Integer> eventIDList = new ArrayList<>();
    ArrayList<Integer> eventListAlpha = new ArrayList<>();
    ArrayList<Integer> eventIDListAlpha = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBHelper = new DBHelper(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        listener = new LocationListener()
        {
            @Override
            public void onLocationChanged(Location location)
            {
                eventList.clear();
                eventIDListAlpha.clear();
                eventIDList.clear();
                final Cursor res = mDBHelper.getLocation();
                final Cursor res2 = mDBHelper.getEvent();

                //check for nearby location
                while (res.moveToNext())
                {
                    if ((res.getInt(1) < ((location.getLatitude() * 100000) + 90)) &&   // upper bound
                            (res.getInt(1) > ((location.getLatitude() * 100000) - 90)) &&  //lower bound
                            (res.getInt(2) < ((location.getLongitude() * 100000) + 90)) && // right bound
                            (res.getInt(2) > ((location.getLongitude() * 100000) - 90))    //left bound
                            )
                    {
                        //check for events in nearby locations

                        String eventBuilding = res.getString(0);
                        res2.moveToFirst();
                        while (res2.moveToNext())
                        {
                            if (res2.getString(2).equals(eventBuilding))
                            {
                                eventList.add(res2.getString(1));
                                eventIDList.add(res2.getInt(0));
                            }
                        }
                    }
                }

                final ArrayAdapter adapter = new ArrayAdapter<>(MainActivity.this,
                        R.layout.activity_listview, eventList);
                final ListView listView = (ListView) findViewById(R.id.mobile_list);
                Collections.sort(eventList);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        Intent myIntent = new Intent(MainActivity.this, DetailView.class);
                        //String eventName = eventList.get(position);
                        //Integer eventID = eventIDListAlpha.get(position);
                        String eventName = eventList.get(position);
                        myIntent.putExtra("EventName", eventName);
                        startActivity(myIntent);
                    }
                });
                //getApplicationContext()

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

        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
    }
}
