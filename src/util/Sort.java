package util;
import clinic.Appointment;
import clinic.Provider;
import clinic.Person;
import clinic.Patient;
import clinic.Technician;
import util.List;
import java.util.Iterator;
/**
 *
 * @author Dhawal
 */


public class Sort {
    public static int LESSTHAN = -1;
    public static int MORETHAN = 1;
    public static int EQUAL = 0;
    /**
     * Sorts the list of providers to sort by their profile
     * @param list the list of providers to sort
     */
    public static void providers(List<Provider> list) {
        Iterator<Provider> iterator = list.iterator();
        while (iterator.hasNext()) {
            Provider current = iterator.next();
            int j = list.indexOf(current);
            while (j > 0 && list.get(j - 1).getProfile().compareTo(current.getProfile()) > EQUAL) { // while the previous element is greater than the current
                list.set(j, list.get(j - 1)); // swap them so our current element j is in the correct position
                j--; // move back one to our element we are on that we just swapped backwards and check again
            }
            list.set(j, current); // make sure element is in correct position after swaps
        }
    }
    /**
     * Sorts the list of patients by their profile
     * @param list the list of patients to sort
     */
    public static void patients(List<Patient> list) {
        Iterator<Patient> iterator = list.iterator();
        while (iterator.hasNext()) {
            Patient current = iterator.next();
            int j = list.indexOf(current);
            while (j > 0 && list.get(j - 1).getProfile().compareTo(current.getProfile()) > EQUAL) { // while the previous element is greater than the current
                list.set(j, list.get(j - 1)); // swap them so our current element j is in the correct position
                j--; // move back one to our element we are on that we just swapped backwards and check again
            }
            list.set(j, current); // make sure element is in correct position after swaps
        }
    }
    /**
     * "rotate" technician list by moving the first element to the end of the list and shifting everything else
     * @param list
     */
    public static void rotateTechnicians(List<Technician> list) {
        if (list.isEmpty() || list.size() == 1) {return;}
        Technician firstElement = list.get(0); // Store the first element (index 0) temporarily
        for (int i = 1; i < list.size(); i++) { // Shift all elements one position to the left
            list.set(i - 1, list.get(i));
        }
        list.set(list.size() - 1, firstElement); // Place the first element at the end of the list
    }
    /**
     * reverse the list of technicians-- needed because we want index 0 to represent the next person in "line"
     * @param list of technicians to reverse
     */
    public static void reverseTechnicians(List<Technician> list) {
        int i = 0;
        int j = list.size() - 1;
        while (i < j) {
            Technician temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
            i++;
            j--;
        }
    }
    /**
     * generalized selection sort for appointments based on key given
     * @param list to sort of appointments
     * @param keys sortKey enum(s) to sort by
     */
    public static void appointment(List<Appointment> list, List<SortKey> keys) {
        for (int i = 0; i < list.size() - 1; i++) {
            int min = i;
            for (int j = i + 1; j < list.size(); j++) {
                Appointment current = list.get(j);
                Appointment minElement = list.get(min);
                if (compareAppointments(current, minElement, keys) < 0) {
                    min = j;
                }
            }
            Appointment temp = list.get(min);
            list.set(min, list.get(i));
            list.set(i, temp);
        }
    }
    /**
     *  HELPER METHOD - Compare two appointments based on keys given and swap them if needed back
     *  in the main appointment selection sort above
     * @param a1 compare this appointment
     * @param a2 to this appointment
     * @param keys by these/this key(s)
     * @return -1 if a1 is less than a2, 0 if equal, 1 if a1 is greater than a2
     */
    private static int compareAppointments(Appointment a1, Appointment a2, List<SortKey> keys) {
        for (SortKey key : keys) {
            int comparison = 0;
            switch (key) {
                case DATE:
                    comparison = a1.getDate().compareTo(a2.getDate());
                    comparison = checkComparisonResult(comparison);
                    break;
                case TIMESLOT:
                    comparison = a1.getTimeslot().compareTo(a2.getTimeslot());
                    comparison = checkComparisonResult(comparison);
                    break;
                case PROVIDER:
                    comparison = a1.getProvider().getProfile().getLname().compareTo(a2.getProvider().getProfile().getLname());
                    comparison = checkComparisonResult(comparison);
                    break;
                case PROVIDER_FNAME:
                    comparison = a1.getProvider().getProfile().getFname().compareTo(a2.getProvider().getProfile().getFname());
                    comparison = checkComparisonResult(comparison);
                    break;
                case COUNTY:
                    comparison = ((Provider) a1.getProvider()).getLocation().getCounty().compareTo(((Provider) a2.getProvider()).getLocation().getCounty());
                    comparison = checkComparisonResult(comparison);
                    break;
                case PATIENT:
                    comparison = a1.getPatient().getProfile().compareTo(a2.getPatient().getProfile()); // compare by patient profile (lname, fname, then dob)
                    comparison = checkComparisonResult(comparison);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid key for comparison");
            }
            if (comparison != EQUAL) {
                return comparison;
            }
        }
        return EQUAL;
    }
    /**
     * HELPER METHOD - in some cases when comparing strings like last names, we want to make sure that if the comparison
     * resulted in say -5, that we set it to LESSTHAN instead, and same for >0 for MORETHAN etc.
     * @param comparison the comparison result
     * @return the comparison result but set to LESSTHAN, MORETHAN, or EQUAL if it was not already
     */
    private static int checkComparisonResult(int comparison) {
        if (comparison < 0) {
            return LESSTHAN;
        } else if (comparison > 0) {
            return MORETHAN;
        } else {
            return EQUAL;
        }
    }
}