package tnpify.tnp_login;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by Tushar on 7/7/2016.
 */
public class TnpEvent {
    public int id;
    public eventTypes type;
    public int companyID;
    public Date date;
//    public Time time;
    public String venue;

    public enum eventTypes {
        PPT, Test, GD, Interview
    }

    public static TnpEvent randomEventGen(int id, Random r) {
        TnpEvent ev = new TnpEvent();
        ev.id = id;
        ev.companyID = r.nextInt(10);
        Date d = new Date();
        d.setDate(r.nextInt(27) + 1);
        d.setMonth(r.nextInt(12) + 1);
        d.setYear(2016);
        d.setHours(r.nextInt(24));
        d.setMinutes(r.nextInt(60));
        ev.date = d;
        ev.venue = "CSC";
        ev.type = eventTypes.values()[r.nextInt(eventTypes.values().length)];
        return ev;
    }
    public static TnpEvent randomEventGen(int id, Date edate, Random r) {
        Date d = (Date) edate.clone();
        TnpEvent ev = new TnpEvent();
        ev.id = id;
        ev.companyID = r.nextInt(10);
        d.setHours(r.nextInt(24));
        d.setMinutes(r.nextInt(60));
        ev.date = d;
        ev.venue = "CSC";
        ev.type = eventTypes.values()[r.nextInt(eventTypes.values().length)];

        return ev;
    }
    @Override
    public String toString() {
        return "company" + companyID + "\n" + type.toString() + "\n" + date.toString() + "\n" + venue;
    }
}
