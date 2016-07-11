package tnpify.tnp_login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class EventsActivity extends NavigationActivity {
    long selectedDate;
    public static List<TnpEvent> events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        tnpify.tnp_login.CalendarView ecv = (tnpify.tnp_login.CalendarView) findViewById(R.id.eventsCalendarView);
//        ecv.setShowWeekNumber(false);
        ecv.setFirstDayOfWeek(Calendar.MONDAY);
        selectedDate = ecv.getDate();
        ecv.setEventHandler(new CalendarView.EventHandler() {
            @Override
            public void onDayLongPress(Date date) {

            }

            @Override
            public void onDayTap(Date date) {
                selectedDate = date.getTime();
                int year = date.getYear() + 1900;
                int month = date.getMonth() + 1;
                int dayOfMonth = date.getDate();
                String dateExtra = year + "-" + (month < 10 ? "0" : "") + month + "-" + (dayOfMonth < 10 ? "0" : "") + dayOfMonth;
                Intent dateEvents = new Intent(EventsActivity.this, EventDateActivity.class);
                dateEvents.putExtra("date", dateExtra);
                EventsActivity.this.startActivity(dateEvents);
            }

            @Override
            public HashSet<Date> onUpdateCalendar(Date firstDateShown, Date firstDateNotShown) {
                List<TnpEvent> events = DummyData.getPersonalEventsBetween(firstDateShown, firstDateNotShown);
                HashSet<Date> eventDates = new HashSet<>();
                for(TnpEvent ev : events) {
                    eventDates.add(ev.date);
                }
                return eventDates;
            }


        });

        ecv.updateCalendar();
    }
}
