package tnpify.tnp_login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;

public class EventsActivity extends NavigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        CalendarView ecv = (CalendarView) findViewById(R.id.eventsCalendarView);
        ecv.setShowWeekNumber(false);
        ecv.setFirstDayOfWeek(Calendar.MONDAY);
        ecv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String dateExtra = year + "-" + (month < 10 ? "0" : "") + month + "-" + (dayOfMonth < 10 ? "0" : "") + dayOfMonth;
                Intent dateEvents = new Intent(EventsActivity.this, EventDateActivity.class);
                dateEvents.putExtra("date", dateExtra);
                EventsActivity.this.startActivity(dateEvents);
            }
        });

    }
}
