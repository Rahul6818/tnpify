package tnpify.tnp_login;

import java.util.ArrayList;
import java.util.Arrays;
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
    public static final int KEY_COMPANIES = 1;
    private static Random creator = new Random();
    private static final boolean[] dummyDataCreated = new boolean[2];
    static final List<Company> origList = new ArrayList<Company>();
    public static Company getCompanyFromID(int id) {
        for(Company c : origList) {
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
                for (int i = 0; i < 20; ++i) {
                    origList.add(new Company(i, (i < 10 ? "company0" : "company") + i, new String[]{"loc" + (2 * i), "loc" + (2 * i + 1)}, creator));
                }
                break;
        }
        dummyDataCreated[key] = true;
    }
}
