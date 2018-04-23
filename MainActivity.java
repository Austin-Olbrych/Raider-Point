package com.example.hails.raiderpointv2;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DBHelper mDBHelper;
    private LocationManager locationManager;
    private LocationListener listener;
    private Spinner s1;
    private Spinner s2;

    // Array of strings...
    ArrayList<String> eventList = new ArrayList<>();
    ArrayList<Integer> eventIDList = new ArrayList<>();

    ArrayList<Integer> eventIDListAlpha = new ArrayList<>();

    ArrayList<String> allEventsList = new ArrayList<>();
    ArrayList<String> buildingEventsList = new ArrayList<>();
    ArrayList<String> orgEventsList = new ArrayList<>();
    ArrayList<String> genreEventsList = new ArrayList<>();

    Boolean basedOnLocation = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBHelper = new DBHelper(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        final Cursor res = mDBHelper.getLocation();
        final Cursor res2 = mDBHelper.getEvent();
        final Cursor res3 = mDBHelper.getOrg();
        final Cursor res4 = mDBHelper.getGenre();

        s1 = (Spinner) findViewById(R.id.spinner1);
        s2 = (Spinner) findViewById(R.id.spinner2);

        final ArrayAdapter adapter = new ArrayAdapter<>(MainActivity.this,
                R.layout.activity_listview, eventList);

        final ArrayAdapter adaptorAllEvents = new ArrayAdapter<>(MainActivity.this,
                R.layout.activity_listview, allEventsList);

        final ArrayAdapter adaptorBuildingEvents = new ArrayAdapter<>(MainActivity.this,
                R.layout.activity_listview, buildingEventsList);

        final ArrayAdapter adaptorOrgEvents = new ArrayAdapter<>(MainActivity.this,
                R.layout.activity_listview, orgEventsList);

        final ArrayAdapter adaptorGenreEvents = new ArrayAdapter<>(MainActivity.this,
                R.layout.activity_listview, genreEventsList);

        final ListView listView = (ListView) findViewById(R.id.mobile_list);

        //spinner1 stuff
        s1.setOnItemSelectedListener(this);
        List<String> s1categories = new ArrayList<String>();
        s1categories.add("nearby events");
        s1categories.add("all events");
        s1categories.add("building");
        s1categories.add("organization");
        s1categories.add("genre");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, s1categories);
        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        s1.setAdapter(dataAdapter1);

        //spinner2 stuff
        s2.setOnItemSelectedListener(this);
        final List<String> s2categories = new ArrayList<String>();
        s2categories.add("--select--");
        // Creating adapter for spinner2
        final ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, s2categories);
        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter2 to spinner2
        s2.setAdapter(dataAdapter2);

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (s1.getSelectedItem().toString().equals("nearby events")) {
                    basedOnLocation = true;
                    listView.setAdapter(null);
                    s2categories.clear();
                    s2categories.add("--select--");
                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                } else if (s1.getSelectedItem().toString().equals("all events")) {
                    basedOnLocation = false;
                    listView.setAdapter(null);
                    allEventsList.clear();
                    s2categories.clear();
                    //s2categories.add("--select--");
                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    res.moveToFirst();
                    res2.moveToFirst();
                    res3.moveToFirst();
                    res4.moveToFirst();
                    while (res2.moveToNext()) {
                        allEventsList.add((res2.getString(1)));
                    }
                    Collections.sort(allEventsList);
                    listView.setAdapter(adaptorAllEvents);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent myIntent = new Intent(MainActivity.this, DetailView.class);
                            String eventName = allEventsList.get(position);
                            myIntent.putExtra("EventName", eventName);
                            startActivity(myIntent);
                        }
                    });
                }
                else if (s1.getSelectedItem().toString().equals("building")) {
                    basedOnLocation = false;
                    listView.setAdapter(null);
                    s2categories.clear();
                    buildingEventsList.clear();
                    //s2categories.add("--select--");
                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    res.moveToFirst();
                    res2.moveToFirst();
                    res3.moveToFirst();
                    res4.moveToFirst();
                    while(res.moveToNext()){
                        s2categories.add(res.getString(0));
                    }
                    s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            buildingEventsList.clear();
                            res2.moveToFirst();
                            while (res2.moveToNext()) {
                                if(res2.getString(2).equals(s2.getSelectedItem())) {
                                    buildingEventsList.add(res2.getString(1));
                                }
                            }
                            Collections.sort(buildingEventsList);
                            listView.setAdapter(adaptorBuildingEvents);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent myIntent = new Intent(MainActivity.this, DetailView.class);
                                    String eventName = buildingEventsList.get(position);
                                    myIntent.putExtra("EventName", eventName);
                                    startActivity(myIntent);
                                }
                            });
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
                else if (s1.getSelectedItem().toString().equals("organization"))
                {
                    basedOnLocation = false;
                    listView.setAdapter(null);
                    s2categories.clear();
                    orgEventsList.clear();
                    //s2categories.add("--select--");
                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    res.moveToFirst();
                    res2.moveToFirst();
                    res3.moveToFirst();
                    res4.moveToFirst();
                    while(res3.moveToNext()){
                        s2categories.add(res3.getString(0));
                        Log.d("test", res3.getString(0));
                    }
                    s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            orgEventsList.clear();
                            res2.moveToFirst();
                            while (res2.moveToNext()) {
                                if(res2.getString(8).equals(s2.getSelectedItem())) {
                                    orgEventsList.add(res2.getString(1));
                                }
                            }
                            Collections.sort(orgEventsList);
                            listView.setAdapter(adaptorOrgEvents);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent myIntent = new Intent(MainActivity.this, DetailView.class);
                                    String eventName = orgEventsList.get(position);
                                    myIntent.putExtra("EventName", eventName);
                                    startActivity(myIntent);
                                }
                            });
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
                else if (s1.getSelectedItem().toString().equals("genre"))
                {
                    basedOnLocation = false;
                    listView.setAdapter(null);
                    s2categories.clear();
                    orgEventsList.clear();
                    //s2categories.add("--select--");
                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    res.moveToFirst();
                    res2.moveToFirst();
                    res3.moveToFirst();
                    res4.moveToFirst();
                    while(res4.moveToNext()){
                        s2categories.add(res4.getString(0));
                        //Log.d("test", res3.getString(0));
                    }
                    s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            orgEventsList.clear();
                            res2.moveToFirst();
                            while (res2.moveToNext()) {
                                if(res2.getString(9).equals(s2.getSelectedItem())) {
                                    orgEventsList.add(res2.getString(1));
                                }
                            }
                            Collections.sort(orgEventsList);
                            listView.setAdapter(adaptorOrgEvents);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent myIntent = new Intent(MainActivity.this, DetailView.class);
                                    String eventName = orgEventsList.get(position);
                                    myIntent.putExtra("EventName", eventName);
                                    startActivity(myIntent);
                                }
                            });
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(basedOnLocation) {
                    eventList.clear();
                    //eventIDListAlpha.clear();
                    //eventIDList.clear();
                    res.moveToFirst();
                    //check for nearby location
                    while (res.moveToNext()) {
                        if ((res.getInt(1) < ((location.getLatitude() * 100000) + 90)) &&   // upper bound
                                (res.getInt(1) > ((location.getLatitude() * 100000) - 90)) &&  //lower bound
                                (res.getInt(2) < ((location.getLongitude() * 100000) + 90)) && // right bound
                                (res.getInt(2) > ((location.getLongitude() * 100000) - 90))    //left bound
                                ) {
                            //check for events in nearby locations
                            String eventBuilding = res.getString(0);
                            res2.moveToFirst();
                            while (res2.moveToNext()) {
                                if (res2.getString(2).equals(eventBuilding)) {
                                    eventList.add(res2.getString(1));
                                    //eventIDList.add(res2.getInt(0));
                                }
                            }
                        }
                    }
                    Collections.sort(eventList);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent myIntent = new Intent(MainActivity.this, DetailView.class);
                            String eventName = eventList.get(position);
                            myIntent.putExtra("EventName", eventName);
                            startActivity(myIntent);
                        }
                    });
                }

                Log.d("test", "end of locationChanged");
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
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
