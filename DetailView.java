package com.example.hails.raiderpointv2;

/**
 * Created by Hails on 4/21/2018.
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class DetailView extends AppCompatActivity {

    DBHelper mDBHelper2;
    int year, month, date, hour, minute; //Variables for start and end time
    String name, location, description; //Variables for name and location
    long startMillis = 0;
    long endMillis = 0;
    Integer ID = 0;

    String eventName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        mDBHelper2 = new DBHelper(this);

        TextView txtName = (TextView)findViewById(R.id.txtName);
        TextView txtDate = (TextView)findViewById(R.id.txtDate);
        TextView txtLocation = (TextView)findViewById(R.id.txtLocation);
        TextView txtTime = (TextView)findViewById(R.id.txtTime);
        TextView txtDescription = (TextView)findViewById(R.id.txtDescription);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            eventName = bundle.getString("EventName");
        }
        //set event variables
        final Cursor res = mDBHelper2.getEvent();
        while (res.moveToNext())
        {
            if(res.getString(1).equals(eventName))
            {
                name = res.getString(1);
                txtName.setText(name);

                month = res.getInt(3);
                date = res.getInt(4);
                year = res.getInt(5);
                txtDate.setText(month + "/" + date + "/" + year);

                location = res.getString(2);
                txtLocation.setText(location);

                hour = res.getInt(6);
                minute = res.getInt(7);

                if(res.getInt(6) > 12 || res.getInt(6) == 12)  //PM
                {
                    int displayHour;
                    if(res.getInt(6) > 12)
                    {
                        displayHour = res.getInt(6) - 12;
                    }
                    else
                    {
                        displayHour = 12;
                    }

                    if(res.getInt(7) == 0)
                    {
                        txtTime.setText("Time: " + displayHour + ":00 PM");
                    }
                    else
                    {
                        txtTime.setText("Time: " + displayHour + ":" + res.getInt(7) + " PM");
                    }
                }
                else    //AM
                {
                    if(res.getInt(7) == 0)
                    {
                        txtTime.setText("Time: " + res.getInt(6) + ":00 AM");
                    }
                    else
                    {
                        txtTime.setText("Time: " + res.getInt(6) + ":" + res.getInt(7) + " AM");
                    }
                }

                description = res.getString(10);
                txtDescription.setText(description);

            }
        }

        Button btnAdd = (Button) findViewById(R.id.btnCalendar);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                insert();
            }
        });
    }

    @SuppressLint("NewApi")
    public void insert() {
        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");

        intent.putExtra("title", name); //Event title
        intent.putExtra("eventLocation", location); //Event location

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(year, month - 1, date, hour, minute);
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(year, month - 1, date, hour + 1, minute);
        endMillis = endTime.getTimeInMillis();

        intent.putExtra("beginTime", startMillis);
        intent.putExtra("endTime", endMillis);

        startActivity(intent);
    }
}

