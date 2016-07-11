package tnpify.tnp_login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class EventsActivity extends NavigationActivity {
    long selectedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        CalendarView ecv = (CalendarView) findViewById(R.id.eventsCalendarView);
        ecv.setShowWeekNumber(false);
        ecv.setFirstDayOfWeek(Calendar.MONDAY);
        selectedDate = ecv.getDate();
        ecv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                if(view.getDate() == selectedDate)
                    return;
                selectedDate = view.getDate();
                month = month + 1;
                String dateExtra = year + "-" + (month < 10 ? "0" : "") + month + "-" + (dayOfMonth < 10 ? "0" : "") + dayOfMonth;
                Intent dateEvents = new Intent(EventsActivity.this, EventDateActivity.class);
                dateEvents.putExtra("date", dateExtra);
                EventsActivity.this.startActivity(dateEvents);
            }
        });

    }
}
