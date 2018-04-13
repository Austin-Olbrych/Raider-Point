package c.scrummies.detailview;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import java.util.Calendar;

public class DetailView extends AppCompatActivity {

    int year,month,date,hour,minute; //Variables for start and end time
    String name,location; //Variables for name and location
    long startMillis = 0;
    long endMillis = 0;

    /* For Hailey

    //What is needed
    public void openDetailView (View view) {
        Intent intent = new Intent(this, DetailView.class);
        intent.putExtra("eventID", variable);
        startActivity(intent);
    }
    //Example 1 starting intent
    public void openDetailView (View view) {
        Intent intent = new Intent(this, DetailView.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    Example 2 variable through intent
    Intent intent = new Intent(MainActivity.this, DetailView.class);
    intent.putExtra("eventID", variable);
    startActivity(intent);

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity_detail_view);

        //Read in variable from MainActivity
        //Intent activityIntent = getIntent();
        //int eventID = activityIntent.getDoubleExtra("eventID", 0);

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

        intent.putExtra("title", "Name of Event"); //Event title
        intent.putExtra("eventLocation", "KHIC"); //Event location

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2018, 0, 22, 17, 0);
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2018, 0, 22, 18, 45);
        endMillis = endTime.getTimeInMillis();

        intent.putExtra("beginTime", startMillis);
        intent.putExtra("endTime", endMillis);

        startActivity(intent);
    }
}
