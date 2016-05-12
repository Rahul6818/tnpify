package tnpify.tnp_login;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tushar on 12-05-2016.
 */
public class Company {
    public int id;
    public String name;
    public String[] locations;
    public String ctc; //Cost to company
    public String cgpa; //minimum CGPA
    public String deadline; //Application deadline.
    public Company(int id, String name, String[] locations) {
        this.id = id;
        this.name = name;
        this.locations = locations;
        this.cgpa = Double.toString(Math.random() * 10);
        this.ctc = Integer.toString((int)((Math.random() * 10 + 20) * 1000));
        String date = Integer.toString((int) ((Math.random() * 30) + 1));
        String[] months = new String[]{"July", "August", "September", "October", "November"};
        String month = months[(int)(Math.random() * 4.9)];
        this.deadline = date + " " + month;
    }
    @Override
    public String toString() {
        return name;
    }

    public static Company getCompanyFromID(int ID) {
        return new Company(ID, "company" + ID, new String[]{"loc" + 2*ID, "loc" + 2*ID+1});
    }

    public static List<Company> filterName(Company[] companies, String name){
        List<Company> ret = new ArrayList<Company>();
        for(int i = 0; i < companies.length; i++){
            try {
                if (companies[i].name.equalsIgnoreCase(name)) {
                    ret.add(companies[i]);
                }
            }

            catch (Exception e){

            }
        }
        return ret;
    }

    public static List<Company> filterLocation(Company[] companies, String location){
        List<Company> ret = new ArrayList<Company>();
        for(int i = 0; i < companies.length; i++){
            try {
                if (Arrays.asList(companies[i].locations).contains(location)) {
                    ret.add(companies[i]);
                }
            }

            catch (Exception e){

            }
        }
        return ret;
    }

    public static List<Company> filterCTC(Company[] companies, String ctc){
        List<Company> ret = new ArrayList<Company>();
        for(int i = 0; i < companies.length; i++){
            try {
                if (Integer.parseInt(companies[i].ctc) >= Integer.parseInt(ctc)) {
                    ret.add(companies[i]);
                }
            }

            catch (Exception e){

            }
        }
        return ret;
    }

    public static List<Company> filterCGPA(Company[] companies, String cgpa){
        List<Company> ret = new ArrayList<Company>();
        for(int i = 0; i < companies.length; i++){
            try {
                if (Float.parseFloat(companies[i].cgpa) <= Float.parseFloat(cgpa)) {
                    ret.add(companies[i]);
                }
            }

            catch (Exception e){
                ret.add(companies[i]);
            }
        }
        return ret;
    }


    public static ArrayList<Company> getCompaniesFromIDs(int[] IDs) {
        ArrayList<Company> list = new ArrayList<Company>(IDs.length);
        for(int i = 0; i < IDs.length; i++) {
            list.add(getCompanyFromID(IDs[i]));
        }
        return list;
    }

}
