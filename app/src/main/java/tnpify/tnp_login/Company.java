package tnpify.tnp_login;

import android.content.Context;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Tushar on 12-05-2016.
 */

public class Company {
    public static enum CompanyType {
        Analytics, Core_Technical, Consulting, Finance, Management;

        public static String[] types() {
            return new String[] {"Analytics", "Core_Technical", "Consulting", "Finance", "Management"};
        }
    }
    public int id;
    public String name;
    public CompanyType type;
    public String[] locations;
    public String ctc; //Cost to company
    public String cgpa; //minimum CGPA
    public String deadline; //Application deadline.
    public Company(int id, String name, String[] locations) {
        this.id = id;
        this.name = name;
        this.locations = locations;
        this.type = CompanyType.values()[new Random().nextInt(CompanyType.values().length)];
        this.cgpa = Double.toString(Math.random() * 10);
        int decimal = cgpa.indexOf('.');
        if(cgpa.length() > decimal + 3) {
            cgpa = cgpa.substring(0, decimal + 3);
        }
        this.ctc = Integer.toString((int)((Math.random() * 10 + 20)) * 1000);
        String date = Integer.toString((int) ((Math.random() * 30) + 1));
        String[] months = new String[]{"July", "August", "September", "October", "November"};
        String month = months[(int)(Math.random() * 4.9)];
        this.deadline = date + " " + month;
    }
    @Override
    public String toString() {
        return name;
    }

    public static List<Company> filterType(List<Company> companies, String type) {
        List<Company> ret = new ArrayList<Company>();
        for(Company company : companies) {
            if(company.type.toString().equalsIgnoreCase(type)) {
                ret.add(company);
            }
        }
        return ret;
    }
    public static List<Company> filterName(List<Company> companies, String name){
        List<Company> ret = new ArrayList<Company>();
        for(int i = 0; i < companies.size(); i++){
            try {
                if (companies.get(i).name.equalsIgnoreCase(name)) {
                    ret.add(companies.get(i));
                }
            }

            catch (Exception e){

            }
        }
        return ret;
    }

    public static List<Company> filterLocation(List<Company> companies, String location){
        List<Company> ret = new ArrayList<Company>();
        for(int i = 0; i < companies.size(); i++){
            try {
                if (Arrays.asList(companies.get(i).locations).contains(location)) {
                    ret.add(companies.get(i));
                }
            }

            catch (Exception e){

            }
        }
        return ret;
    }

    public static List<Company> filterCTC(List<Company> companies, String ctc){
        List<Company> ret = new ArrayList<Company>();
        for(int i = 0; i < companies.size(); i++){
            try {
                if (Integer.parseInt(companies.get(i).ctc) >= Integer.parseInt(ctc)) {
                    ret.add(companies.get(i));
                }
            }

            catch (Exception e){

            }
        }
        return ret;
    }

    public static List<Company> filterCGPA(List<Company> companies, String cgpa){
        List<Company> ret = new ArrayList<Company>();
        for(int i = 0; i < companies.size(); i++){
            try {
                if (Float.parseFloat(companies.get(i).cgpa) <= Float.parseFloat(cgpa)) {
                    ret.add(companies.get(i));
                }
            }

            catch (Exception e){
                ret.add(companies.get(i));
            }
        }
        return ret;
    }

    public static List<Company> filtered(Context context, List<Company> companies, List<String> filters) {
        if(filters == null) {
            return companies;
        }
//        Toast.makeText(context, companies.toString(), Toast.LENGTH_SHORT).show();
        List<Company> ret = companies;
        for(String filter : filters) {
            if(filter.startsWith("Name")) {
                ret = filterName(ret, filter.substring(4));
            } else if(filter.startsWith("Location")) {
                ret = filterLocation(ret, filter.substring(8));
            } else if(filter.startsWith("CTC")) {
                ret = filterCTC(ret, filter.substring(3));
            } else if(filter.startsWith("CGPA")){
                ret = filterCGPA(ret, filter.substring(4));
            } else if(filter.startsWith("Type")) {
                ret = filterType(ret, filter.substring(4));
            }
        }
        return ret;
    }



}
