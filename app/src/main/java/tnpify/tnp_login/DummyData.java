package tnpify.tnp_login;

import android.widget.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Tushar on 7/8/2016.
 */
public class DummyData {
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    static final List<String> DUMMY_CREDENTIALS = new ArrayList<String>();
    public static final int KEY_LOGIN  = 0;
    public static final int KEY_COMPANIES = KEY_LOGIN + 1;
    public static final int KEY_APPLICATIONS = KEY_COMPANIES + 1;
    private static Random creator = new Random();
    private static final boolean[] dummyDataCreated = new boolean[3];
    private static String username = null;
    private static final List<Company> origList = new ArrayList<Company>();
    private static final List<Company> appliedCompanies = new ArrayList<Company>();
    private static final List<Application> applications = new ArrayList<Application>();
    private static final List<TnpEvent> events = new ArrayList<TnpEvent>();

    public static Company getCompanyFromID(int id) {
        for(Company c : getAllCompanies()) {
            if(c.id == id)
                return c;
        }
        return null;
    }

    public static ArrayList<Company> getCompaniesFromIDs(int[] IDs) {
        ArrayList<Company> compList = new ArrayList<Company>(IDs.length);
        for(int i = 0; i < IDs.length; i++) {
            Company c = getCompanyFromID(IDs[i]);
            if(c != null)
                compList.add(c);
        }
        return compList;
    }

    public static List<Company> getAllCompanies() {
        createDummyData(KEY_COMPANIES);
        return origList;
    }

    public static void createDummyData(int key) {
        if(dummyDataCreated[key])
            return;
        switch(key) {
            case KEY_LOGIN:
                DUMMY_CREDENTIALS.addAll(Arrays.asList(new String[]{
                        "foo@example.com:hello", "bar@example.com:world",
                        "patidarnet@gmail.com:12345","1:1", "2012cs10259:tushar123", "2012cs10244:rahul123", "2012cs10238:nikhil123", "2012cs10228:kaushal123"
                }));
                break;
            case KEY_COMPANIES:
                Company c;
                for (int i = 0; i < 99; ++i) {
                    c = new Company(i, (i < 10 ? "company0" : "company") + i, new String[]{"loc" + (2 * i), "loc" + (2 * i + 1)}, creator);
                    origList.add(c);
                    events.add(new TnpEvent(c.id, c.pptDate, TnpEvent.EventType.PPT, creator));
                    events.add(new TnpEvent(c.id, c.testDate, TnpEvent.EventType.Test, creator));
                    if(c.gdDate != null) {
                        events.add(new TnpEvent(c.id, c.gdDate, TnpEvent.EventType.GD, creator));
                    }
                    events.add(new TnpEvent(c.id, c.interviewDate, TnpEvent.EventType.Interview, creator));
                }
                break;
            case KEY_APPLICATIONS:
                if(username == null) {
                    return;
                }
                List<Company> opC = new ArrayList<Company>();
                applications.clear();

                for(int i = 0; i < origList.size(); i++) {
                    if(origList.get(i).deadline.getTime() <= Company.ONE_MONTH_TIME + System.currentTimeMillis()) {
                        opC.add(origList.get(i));
                    }
                }
                int numApps = creator.nextInt(opC.size() / 4);
                for(int i = 0; i < numApps; i++) {
                    Company comp = opC.remove(creator.nextInt(opC.size()));
                    createApplication(comp);
                }
        }
        dummyDataCreated[key] = true;
    }

    public static List<Application> getAppList() {
        createDummyData(KEY_APPLICATIONS);
        return applications;
    }

    private static boolean createApplication(Company company) {
        if(company.deadline.getTime() > Company.ONE_MONTH_TIME + System.currentTimeMillis()) {
            return false;
        }
        company.applied = username;
        Application app = new Application(creator, username, company.id);
        applications.add(app);
        appliedCompanies.add(company);
        return true;
    }

    public static List<Company> openCompanies() {
        createDummyData(KEY_APPLICATIONS);
        return Company.filterOpen(getAllCompanies(), username);
    }

    public static boolean logIn(String mEmail, String mPassword) {
        for (String credential : DummyData.DUMMY_CREDENTIALS) {
            String[] pieces = credential.split(":");
            if (pieces[0].equals(mEmail)) {
                // Account exists, return true if the password matches.

                if(pieces[1].equals(mPassword)) {
                    username = mEmail;
                    return true;
                }
            }
        }
        return false;
    }

    public static void loggedIn(String mEmail) {
        username = mEmail;
    }

    public static void logOut() {
        username = null;
        dummyDataCreated[KEY_APPLICATIONS] = false;
    }

    public static List<Company> getAppliedCompanies() {
        return appliedCompanies;
    }

    public static boolean apply(Company company, String ResumeType) {
        if(company == null)
            return false;
        boolean success = company.apply(username);
        if(success) {
            Application app = new Application(username, company.id, ResumeType);
            applications.add(app);
            appliedCompanies.add(company);
            return true;
        }
        return false;
    }

    public static boolean withdraw(Company company) {
        if(company == null)
            return false;
        boolean success = company.withdraw(username);
        if(success) {
            appliedCompanies.remove(company);
            for(Application app : applications) {
                if(app.compID == company.id) {
                    applications.remove(app);
                    break;
                }
            }
            return true;
        }
        return false;
    }

    public static String[] getResumes() {
        return new String[]{"Analytics", "Finance", "Core_Technical", "Consultation", "Management"};
    }

    public static List<TnpEvent> getEventsOn(Date date) {
        List<TnpEvent> dateEvents = new ArrayList<TnpEvent>();
        for(TnpEvent ev : events) {
            if(CalendarView.sameDate(date, ev.date)) {
                dateEvents.add(ev);
            }
        }
        return dateEvents;
    }

    public static List<TnpEvent> upcomingEvents() {
        List<TnpEvent> upcomingEvents = new ArrayList<TnpEvent>();
        long today = System.currentTimeMillis();
        for(TnpEvent ev : events) {
            if(ev.date.getTime() > today && ev.date.getTime() < today + (Company.ONE_MONTH_TIME / 4L)) {
                upcomingEvents.add(ev);
            }
        }
        Collections.sort(upcomingEvents);
        return upcomingEvents;
    }

    public static List<TnpEvent> upcomingPersonalEvents() {
        List<TnpEvent> upcomingPersonalEvents = new ArrayList<TnpEvent>();
        long today = System.currentTimeMillis();
        for(TnpEvent ev : events) {
            if(ev.date.getTime() > today && ev.date.getTime() < today + (Company.ONE_MONTH_TIME / 4L) &&
                    getCompanyFromID(ev.companyID).isApplied(username)) {
                upcomingPersonalEvents.add(ev);
            }
        }
        Collections.sort(upcomingPersonalEvents);
        return upcomingPersonalEvents;
    }

    public static List<TnpEvent> getPersonalEventsBetween(Date date1, Date date2) {
        List<TnpEvent> ret = new ArrayList<>();
        for(TnpEvent ev : events) {
            if(ev.date.getTime() >= date1.getTime() && ev.date.getTime() < date2.getTime() &&
                    getCompanyFromID(ev.companyID).isApplied(username)) {
                ret.add(ev);
            }
        }
        return ret;
    }
}
