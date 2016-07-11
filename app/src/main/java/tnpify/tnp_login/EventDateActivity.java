package tnpify.tnp_login;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
        String inputDate = getIntent().getStringExtra("date");
        date = parseDate(inputDate);
        getSupportActionBar().setTitle(inputDate);
        Toast.makeText(this, date.toString(), Toast.LENGTH_SHORT).show();
        ArrayAdapter<TnpEvent> adap = new ArrayAdapter<TnpEvent>(this, android.R.layout.simple_list_item_1, getEvents()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                TnpEvent ev = getItem(position);
                if(DummyData.getCompanyFromID(ev.companyID).isApplied(LoginActivity.getUsername(getApplicationContext()))) {
                    v.setBackgroundColor(Color.GREEN);
                } else {
                    v.setBackgroundColor(Color.WHITE);
                }
                return v;
            }
        };
        elv.setAdapter(adap);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public List<TnpEvent> getEvents() {
        List<TnpEvent> list = DummyData.getEventsOn(date);
        return list;
    }
}
