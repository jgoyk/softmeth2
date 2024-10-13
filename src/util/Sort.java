package util;
import clinic.*;
import util.List;
import java.util.Iterator;
/**
 *
 * @author Dhawal
 */


public class Sort {
    public static final int EQUAL = 0;

    public static final char DATE_TIME_PROVIDER_NAME = 'A'; //Used for PA Command
    public static final char PATIENT_DATE_TIME = 'P'; // Used for PP and PS commands
    public static final char COUNTY_DATE_TIME = 'L'; // Used for PL, PO, and PI commands

    /**
     * generalized selection sort for appointments based on key given
     * @param list to sort of appointments
     * @param key sortKey enum(s) to sort by
     */
    public static void appointment(List<Appointment> list, char key) {
        for (int i = 0; i < list.size() - 1; i++) {
            int min = i;
            for (int j = i + 1; j < list.size(); j++) {
                Appointment current = list.get(j);
                Appointment minElement = list.get(min);
                if (compareAppointments(current, minElement, key) < 0) {
                    min = j;
                }
            }
            Appointment temp = list.get(min);
            list.set(min, list.get(i));
            list.set(i, temp);
        }
    }
    public static void provider(List<Provider> list) { // Used for Providers.txt import and PC command
        for (int i = 0; i < list.size() - 1; i++) {
            int min = i;
            for (int j = i + 1; j < list.size(); j++) {
                Provider current = list.get(j);
                Provider minElement = list.get(min);
                if (current.getProfile().compareTo(minElement.getProfile()) < 0) {
                    min = j;
                }
            }
            Provider temp = list.get(min);
            list.set(min, list.get(i));
            list.set(i, temp);
        }
    }


    /**
     *  HELPER METHOD - Compare two appointments based on keys given and swap them if needed back
     *  in the main appointment selection sort above
     * @param a1 compare this appointment
     * @param a2 to this appointment
     * @param key by these/this key(s)
     * @return -1 if a1 is less than a2, 0 if equal, 1 if a1 is greater than a2
     */
    private static int compareAppointments(Appointment a1, Appointment a2, char key) {
        int dateTimeComparison = a1.compareTo(a2);
        int patientComparison = a1.getPatient().getProfile().compareTo(a2.getPatient().getProfile());
        Provider provider1 = (Provider)(a1.getProvider());
        Provider provider2 = (Provider)(a2.getProvider());
        int countyComparison = provider1.getLocation().getCounty().compareTo(provider2.getLocation().getCounty());

        if (key == DATE_TIME_PROVIDER_NAME){
            if (dateTimeComparison != EQUAL){
                return dateTimeComparison;
            } else{
                return a1.getProvider().getProfile().compareTo(a2.getProvider().getProfile()); //This compares by provider last name, first name, then DOB. Might need to change
            }
        } else if (key == PATIENT_DATE_TIME){
            if (patientComparison != EQUAL){
                return patientComparison;
            } else{
                return dateTimeComparison;
            }
        } else if (key == COUNTY_DATE_TIME){
            if (countyComparison != EQUAL){
                return countyComparison;
            } else{
                return dateTimeComparison;
            }
        }
        return EQUAL;
    }


}