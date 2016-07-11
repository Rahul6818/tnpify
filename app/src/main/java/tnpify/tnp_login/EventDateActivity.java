package tnpify.tnp_login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class EventDateActivity extends AppCompatActivity {
    public ListView elv;
    public Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_date);
        elv = (ListView) findViewById(R.id.eventsListView);
        date = parseDate(getIntent().getStringExtra("date"));
        Toast.makeText(this, date.toString(), Toast.LENGTH_SHORT).show();
        ArrayAdapter<TnpEvent> adap = new ArrayAdapter<TnpEvent>(this, android.R.layout.simple_list_item_1, getEvents());
        elv.setAdapter(adap);
    }
    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
    public List<TnpEvent> getEvents() {
//        ArrayList<TnpEvent> list = new ArrayList<>(5);
//        Random r = new Random();
//        list.add(new TnpEvent(date, r));
//        list.add(new TnpEvent(date, r));
//        list.add(new TnpEvent(date, r));
//        list.add(new TnpEvent(date, r));
//        list.add(new TnpEvent(date, r));
        List<TnpEvent> list = DummyData.getEventsOn(date);
        return list;
    }
}
