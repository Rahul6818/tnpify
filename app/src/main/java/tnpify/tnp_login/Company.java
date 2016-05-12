package tnpify.tnp_login;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Tushar on 12-05-2016.
 */
public class Company {
    public int id;
    public String name;
    public String[] locations;
    public Company(int id, String name, String[] locations) {
        this.id = id;
        this.name = name;
        this.locations = locations;
    }
    @Override
    public String toString() {
        return name;
    }

    public static Company getCompanyFromID(int ID) {
        return new Company(ID, "company" + ID, new String[]{"loc" + 2*ID, "loc" + 2*ID+1});
    }

    public static ArrayList<Company> getCompaniesFromIDs(int[] IDs) {
        ArrayList<Company> list = new ArrayList<Company>(IDs.length);
        for(int i = 0; i < IDs.length; i++) {
            list.add(getCompanyFromID(IDs[i]));
        }
        return list;
    }

}
