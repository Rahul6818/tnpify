package tnpify.tnp_login;

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
}
