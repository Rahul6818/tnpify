package tnpify.tnp_login;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Tushar on 12-05-2016.
 */

public class Company implements Comparable<Company>{

    public static enum CompanyType {
        Analytics, Core_Technical, Consulting, Finance, Management;

        public static String[] types() {
            return new String[] {"Analytics", "Core_Technical", "Consulting", "Finance", "Management"};
        }

        public static boolean isValidType(String type) {
            for(String t : types()) {
                if(t.equalsIgnoreCase(type))
                    return true;
            }
            return false;
        }
    }
    public static final int NAME_SORT = 0;
    public static final int CTC_SORT = 1;
    public static final int CGPA_SORT = 2;
    public static final int DEADLINE_SORT = 3;
    static final long ONE_MONTH_TIME = 2419200000L;
    public int id;
    public String name;
    public CompanyType type;
    public String[] locations;
    public String applied = null;
    public int ctc; //Cost to company
    public float cgpa; //minimum CGPA
    public Date deadline; //Application deadline.


    public Company(int id, String name, String[] locations, Random r) {
        this.id = id;
        this.name = name;
        this.locations = locations;
        applied = null;
        if(r != null) {
            type = CompanyType.values()[r.nextInt(CompanyType.values().length)];
            cgpa = (float) (r.nextInt(100) / 10.0);
            ctc = (r.nextInt(11) + 20) * 1000;
            deadline = new Date();
            deadline.setTime(Math.abs(r.nextLong()));
            if(deadline.getMonth() < 6) {
                deadline.setMonth(deadline.getMonth() + 6);
            }
            deadline.setYear(116); // Sets year since 1900
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isOpen(String username) {
        long today = System.currentTimeMillis();
        long lastTime = deadline.getTime();
        return today <= lastTime && (today + ONE_MONTH_TIME) > lastTime && username != null && username.length() != 0 && !username.equalsIgnoreCase(applied);
    }

    public boolean apply(String username) {
        if(isOpen(username)) {
            applied = username;
            return true;
        }
        return false;
    }

    public boolean canWithdraw(String username) {
        long today = System.currentTimeMillis();
        long lastTime = deadline.getTime();
        return today <= lastTime && username != null && username.equalsIgnoreCase(applied);
    }

    public boolean withdraw(String username) {
        if(canWithdraw(username)) {
            applied = null;
            return true;
        }
        return false;
    }

    public static List<Company> filterType(List<Company> companies, String type) {
        if(type == null || type.length() == 0 || !CompanyType.isValidType(type)) {
            return companies;
        }
        List<Company> ret = new ArrayList<Company>();
        for(Company company : companies) {
            if(company.type.toString().equalsIgnoreCase(type)) {
                ret.add(company);
            }
        }
        return ret;
    }

    public static List<Company> filterName(List<Company> companies, String name){
        if(name == null || name.length() == 0) {
            return companies;
        }
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
        if(location == null || location.length() == 0) {
            return companies;
        }
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
        if(ctc == null || ctc.length() == 0) {
            return companies;
        }
        List<Company> ret = new ArrayList<Company>();
        for(int i = 0; i < companies.size(); i++){
            try {
                if (companies.get(i).ctc >= Integer.parseInt(ctc)) {
                    ret.add(companies.get(i));
                }
            }

            catch (Exception e){

            }
        }
        return ret;
    }

    public static List<Company> filterCGPA(List<Company> companies, String cgpa){
        if(cgpa == null || cgpa.length() == 0) {
            return companies;
        }
        List<Company> ret = new ArrayList<Company>();
        for(int i = 0; i < companies.size(); i++){
            try {
                if (companies.get(i).cgpa <= Float.parseFloat(cgpa)) {
                    ret.add(companies.get(i));
                }
            }

            catch (Exception e){
                ret.add(companies.get(i));
            }
        }
        return ret;
    }

    public static List<Company> filterOpen(List<Company> companies, String username) {
        if(username == null || username.length() == 0) {
            return new ArrayList<Company>();
        }
        if(username.equalsIgnoreCase("All")) {
            return companies;
        }
        List<Company> ret = new ArrayList<Company>();
        for(Company company : companies) {
            if(company.isOpen(username)) {
                ret.add(company);
            }
        }
        return ret;
    }

    public static List<Company> filtered(List<Company> companies, List<String> filters) {
        return filtered(companies, filters, NAME_SORT, true);
    }

    public static List<Company> filtered(List<Company> companies, List<String> filters, int sortBy, boolean ascendingSort) {
        List<Company> ret = companies;
        if(filters != null) {
            for (String filter : filters) {
                if (filter.startsWith("Name")) {
                    ret = filterName(ret, filter.substring(4));
                } else if (filter.startsWith("Location")) {
                    ret = filterLocation(ret, filter.substring(8));
                } else if (filter.startsWith("CTC")) {
                    ret = filterCTC(ret, filter.substring(3));
                } else if (filter.startsWith("CGPA")) {
                    ret = filterCGPA(ret, filter.substring(4));
                } else if (filter.startsWith("Type")) {
                    ret = filterType(ret, filter.substring(4));
                } else if (filter.startsWith("Open")) {
                    ret = filterOpen(ret, filter.substring(4));
                }
            }
        }
        Comparator<Company> c;
        switch(sortBy) {
            case CGPA_SORT:
                c = new CGPACompare();
                break;
            case CTC_SORT:
                c = new CTCCompare();
                break;
            case DEADLINE_SORT:
                c = new DeadlineCompare();
                break;
            default:
                c = new NameCompare();
                break;
        }
        Collections.sort(companies, c);
        if(!ascendingSort) {
            Collections.reverse(companies);
        }
        return ret;
    }

    /**
     * Compares this object to the specified object to determine their relative
     * order.
     *
     * @param company the object to compare to this instance.
     * @return a negative integer if this instance is less than {@code company};
     * a positive integer if this instance is greater than
     * {@code company}; 0 if this instance has the same order as
     * {@code company}.
     * @throws ClassCastException if {@code company} cannot be converted into something
     *                            comparable to {@code this} instance.
     */
    @Override
    public int compareTo(Company company) {
        return name.compareToIgnoreCase(company.name);
    }

}

class CTCCompare implements Comparator<Company> {

    /**
     * Compares the two specified objects to determine their relative ordering. The ordering
     * implied by the return value of this method for all possible pairs of
     * {@code (company1, company2)} should form an <i>equivalence relation</i>.
     * This means that
     * <ul>
     * <li>{@code compare(a,a)} returns zero for all {@code a}</li>
     * <li>the sign of {@code compare(a,b)} must be the opposite of the sign of {@code
     * compare(b,a)} for all pairs of (a,b)</li>
     * <li>From {@code compare(a,b) > 0} and {@code compare(b,c) > 0} it must
     * follow {@code compare(a,c) > 0} for all possible combinations of {@code
     * (a,b,c)}</li>
     * </ul>
     *
     * @param company1 an {@code Object}.
     * @param company2 a second {@code Object} to compare with {@code company1}.
     * @return an integer < 0 if {@code company1} is less than {@code company2}, 0 if they are
     * equal, and > 0 if {@code company1} is greater than {@code company2}.
     * @throws ClassCastException if objects are not of the correct type.
     */
    @Override
    public int compare(Company company1, Company company2) {
        return ((Integer)company1.ctc).compareTo((Integer)company2.ctc);
    }
}

class CGPACompare implements Comparator<Company> {

    /**
     * Compares the two specified objects to determine their relative ordering. The ordering
     * implied by the return value of this method for all possible pairs of
     * {@code (company1, company2)} should form an <i>equivalence relation</i>.
     * This means that
     * <ul>
     * <li>{@code compare(a,a)} returns zero for all {@code a}</li>
     * <li>the sign of {@code compare(a,b)} must be the opposite of the sign of {@code
     * compare(b,a)} for all pairs of (a,b)</li>
     * <li>From {@code compare(a,b) > 0} and {@code compare(b,c) > 0} it must
     * follow {@code compare(a,c) > 0} for all possible combinations of {@code
     * (a,b,c)}</li>
     * </ul>
     *
     * @param company1 an {@code Object}.
     * @param company2 a second {@code Object} to compare with {@code company1}.
     * @return an integer < 0 if {@code company1} is less than {@code company2}, 0 if they are
     * equal, and > 0 if {@code company1} is greater than {@code company2}.
     * @throws ClassCastException if objects are not of the correct type.
     */
    @Override
    public int compare(Company company1, Company company2) {
        return ((Float)company1.cgpa).compareTo((Float)company2.cgpa);
    }
}

class DeadlineCompare implements Comparator<Company> {

    /**
     * Compares the two specified objects to determine their relative ordering. The ordering
     * implied by the return value of this method for all possible pairs of
     * {@code (company1, company2)} should form an <i>equivalence relation</i>.
     * This means that
     * <ul>
     * <li>{@code compare(a,a)} returns zero for all {@code a}</li>
     * <li>the sign of {@code compare(a,b)} must be the opposite of the sign of {@code
     * compare(b,a)} for all pairs of (a,b)</li>
     * <li>From {@code compare(a,b) > 0} and {@code compare(b,c) > 0} it must
     * follow {@code compare(a,c) > 0} for all possible combinations of {@code
     * (a,b,c)}</li>
     * </ul>
     *
     * @param company1 an {@code Object}.
     * @param company2 a second {@code Object} to compare with {@code company1}.
     * @return an integer < 0 if {@code company1} is less than {@code company2}, 0 if they are
     * equal, and > 0 if {@code company1} is greater than {@code company2}.
     * @throws ClassCastException if objects are not of the correct type.
     */
    @Override
    public int compare(Company company1, Company company2) {
        return ((Long) company1.deadline.getTime()).compareTo((Long) company2.deadline.getTime());
    }
}

class NameCompare implements Comparator<Company> {

    /**
     * Compares the two specified objects to determine their relative ordering. The ordering
     * implied by the return value of this method for all possible pairs of
     * {@code (company1, company2)} should form an <i>equivalence relation</i>.
     * This means that
     * <ul>
     * <li>{@code compare(a,a)} returns zero for all {@code a}</li>
     * <li>the sign of {@code compare(a,b)} must be the opposite of the sign of {@code
     * compare(b,a)} for all pairs of (a,b)</li>
     * <li>From {@code compare(a,b) > 0} and {@code compare(b,c) > 0} it must
     * follow {@code compare(a,c) > 0} for all possible combinations of {@code
     * (a,b,c)}</li>
     * </ul>
     *
     * @param company1 an {@code Object}.
     * @param company2 a second {@code Object} to compare with {@code company1}.
     * @return an integer < 0 if {@code company1} is less than {@code company2}, 0 if they are
     * equal, and > 0 if {@code company1} is greater than {@code company2}.
     * @throws ClassCastException if objects are not of the correct type.
     */
    @Override
    public int compare(Company company1, Company company2) {
        return (company1.name).compareTo(company2.name);
    }
}