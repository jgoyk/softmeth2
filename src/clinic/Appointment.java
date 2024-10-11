package clinic;
import util.Date;
/**
 * Represents an appointment in a clinic system, storing information such as 
 * the date, timeslot, patient profile, and provider information.
 * 
 * @author YourName
 */
public class Appointment implements Comparable<Appointment> {
    protected Date date; // Date of the appointment
    protected Timeslot timeslot; // Time slot for the appointment
    protected Person patient; // Patient involved in the appointment
    protected Person provider; // Provider (doctor/technician) handling the appointment

    /**
     * Default constructor that initializes the appointment with null values.
     */
    public Appointment() {
        this.date = null;
        this.timeslot = null;
        this.patient = null;
        this.provider = null;
    }

    /**
     * Constructs an appointment with the specified date, timeslot, patient, and provider.
     *
     * @param appointmentDate The date of the appointment.
     * @param appointmentTimeslot The timeslot of the appointment.
     * @param appointmentPatient The patient for the appointment.
     * @param appointmentProvider The provider (doctor/technician) for the appointment.
     */
    public Appointment(Date appointmentDate, Timeslot appointmentTimeslot, Person appointmentPatient, Person appointmentProvider) {
        this.date = new Date(appointmentDate);
        this.timeslot = appointmentTimeslot;
        this.patient = appointmentPatient;
        this.provider = appointmentProvider;
    }

    /**
     * Copy constructor to create a deep copy of another appointment.
     *
     * @param targetAppointment The appointment to copy from.
     */
    public Appointment(Appointment targetAppointment) {
        this.date = new Date(targetAppointment.getDate());
        this.timeslot = targetAppointment.getTimeslot();
        this.patient = targetAppointment.getPatient();
        this.provider = targetAppointment.getProvider();
    }

    /**
     * Gets the date of the appointment.
     *
     * @return The date of the appointment.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets the timeslot of the appointment.
     *
     * @return The timeslot of the appointment.
     */
    public Timeslot getTimeslot() {
        return timeslot;
    }

    /**
     * Gets the patient involved in the appointment.
     *
     * @return The patient for the appointment.
     */
    public Person getPatient() {
        return patient;
    }

    /**
     * Gets the provider handling the appointment.
     *
     * @return The provider for the appointment.
     */
    public Person getProvider() {
        return provider;
    }

    /**
     * Returns a string representation of the appointment, combining the date, timeslot, patient, and provider information.
     *
     * @return A string representing the appointment.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(date.toString()).append(" ");
        result.append(timeslot.toString()).append(" ");
        result.append(patient.toString()).append(" ");
        result.append(provider.toString());
        return result.toString();
    }

    /**
     * Compares this appointment to another based on their string representations.
     *
     * @param targetAppointment The appointment to compare against.
     * @return A negative integer, zero, or a positive integer if this appointment
     *         is less than, equal to, or greater than the specified appointment.
     */
    @Override
    public int compareTo(Appointment targetAppointment) {
        return this.toString().compareTo(targetAppointment.toString());
    }

    /**
     * Checks if this appointment is equal to another based on their properties.
     *
     * @param other The object to compare against.
     * @return true if the appointments are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Appointment that = (Appointment) other;
        return date.equals(that.getDate()) &&
               timeslot.equals(that.getTimeslot()) &&
               patient.equals(that.getPatient()) &&
               provider.equals(that.getProvider());
    }

    /**
     * Generates a hash code for this appointment.
     *
     * @return The hash code of the appointment.
     */
    @Override
    public int hashCode() {
        return 0;
        //return Objects.hash(date, timeslot, patient, provider);
    }
}
