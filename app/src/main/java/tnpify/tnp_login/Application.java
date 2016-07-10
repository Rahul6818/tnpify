package tnpify.tnp_login;

import java.util.Random;

/**
 * Created by Tushar on 7/10/2016.
 */
public class Application implements Comparable<Application>{
    /**
     * Compares this object to the specified object to determine their relative
     * order.
     *
     * @param another the object to compare to this instance.
     * @return a negative integer if this instance is less than {@code another};
     * a positive integer if this instance is greater than
     * {@code another}; 0 if this instance has the same order as
     * {@code another}.
     * @throws ClassCastException if {@code another} cannot be converted into something
     *                            comparable to {@code this} instance.
     */
    @Override
    public int compareTo(Application another) {
        return status.compareTo(another.status);
    }

    public enum ApplicationStatus{
        Selected, Shortlisted, Applied, NotShortlisted, NotSelected;

        public String[] statuses() {
            return new String[] {"Selected", "ShortListed", "Applied", "Not Shortlisted", "Not Selected"};
        }
    }
    private static long lastAppID = -1;
    long appID;
    String username;
    int compID;
    String resumeType;
    ApplicationStatus status;
    public Application(Random r, String username, int compID) {
        this.compID = compID;
        this.username = username;
        resumeType = DummyData.getCompanyFromID(compID).type.toString();
        appID = lastAppID + 1;
        lastAppID++;
        status = ApplicationStatus.values()[r.nextInt(ApplicationStatus.values().length)];
    }
    public Application(String username, int compID, String resumeType) {
        this.compID = compID;
        this.username = username;
        this.resumeType = resumeType;
        appID = lastAppID + 1;
        lastAppID++;
        status = ApplicationStatus.Applied;
    }
}
