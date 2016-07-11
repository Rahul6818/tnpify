package tnpify.tnp_login;

import java.util.Date;
import java.util.Random;

/**
 * Created by Tushar on 7/7/2016.
 */
public class TnpEvent {
    public static long lastID = -1;
    public long id;
    public EventType type;
    public int companyID;
    public Date date;
//    public Time time;
    public String venue;

    public static enum EventType {
        PPT, Test, GD, Interview;
    }

    public TnpEvent(int companyID, Date date, EventType type, Random r) {
        id = lastID + 1;
        lastID++;
        this.companyID = companyID;
        this.date = date;
        this.type = type;
        switch (type) {
            case PPT:
            case GD:
                venue = (r.nextInt(2) == 1 ? "Seminar Hall" : "Dogra Hall");
                break;
            case Test:
                venue = (new String[]{"CSC", "LH121", "V LT 1", "IV LT 3"})[r.nextInt(4)];
                break;
            case Interview:
                venue = (new String[]{"VI 325", "VI 113", "VI 214", "V 204", "V 323"})[r.nextInt(5)];
                break;
            default:
                venue = "CSC";
                break;
        }
    }

    public TnpEvent(Random r) {
        id = lastID + 1;
        lastID++;
        companyID = r.nextInt(10);
        date = new Date();
        date.setTime(Math.abs(r.nextLong()));
        date.setYear(2016);
        venue = "CSC";
        type = EventType.values()[r.nextInt(EventType.values().length)];
    }

    public TnpEvent(Date edate, Random r) {
        date = (Date) edate.clone();
        id = lastID + 1;
        lastID++;
        companyID = r.nextInt(10);
        date.setHours(r.nextInt(24));
        date.setMinutes(r.nextInt(60));
        venue = "CSC";
        type = EventType.values()[r.nextInt(EventType.values().length)];
    }

    @Override
    public String toString() {
        return "company" + companyID + "\n" + type.toString() + "\n" + date.toString() + "\n" + venue;
    }

    public static boolean sameDate(Date date1, Date date2) {
        return date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDate() == date2.getDate();
    }
}
