package clinic;

/**
 * Represents a time slot for scheduling appointments with hour and minute fields.
 * Implements Comparable to allow comparison between time slots.
 * 
 * This class provides utility methods for formatting and comparing times.
 * 
 * @author Joshua Goykhman
 */
public class Timeslot implements Comparable<Timeslot> {
    private int hour;   // Hour of the time slot
    private int minute; // Minute of the time slot

    /**
     * Default constructor initializing hour and minute to -1 (invalid state).
     */
    public Timeslot() {
        this.hour = -1;
        this.minute = -1;
    }

    /**
     * Constructor to create a valid Timeslot with specified hour and minute.
     *
     * @param hour The hour for the time slot (must be valid).
     * @param minute The minute for the time slot (must be valid).
     */
    public Timeslot(int hour, int minute) {
        if (!isValidTime(hour, minute)) {
            throw new IllegalArgumentException("Invalid time slot.");
        }
        this.hour = hour;
        this.minute = minute;
    }

    public Timeslot(int timeslotValue) {
        if(timeslotValue > 6){
            this.hour = (timeslotValue-5) / 2 + 13;
            this.minute = (timeslotValue+1) % 2 * 30;
        } else if (timeslotValue > 0) {
            this.hour = (timeslotValue-1) / 2 + 9;
            this.minute = (timeslotValue+1) % 2 * 30;
        } else{
            this.hour = -1;
            this.minute = -1;
        }

    }


    /**
     * Gets the hour of the time slot.
     *
     * @return The hour of the time slot.
     */
    public int getHour() {
        return hour;
    }

    /**
     * Gets the minute of the time slot.
     *
     * @return The minute of the time slot.
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Validates whether the provided hour and minute are valid for a time slot.
     *
     * @param hour The hour to validate (0-23).
     * @param minute The minute to validate (0-59).
     * @return true if the time is valid, false otherwise.
     */
    private boolean isValidTime(int hour, int minute) {
        return (hour >= 0 && hour < 24) && (minute >= 0 && minute < 60);
    }

    /**
     * Returns a formatted string representation of the time slot.
     * 
     * @return A string formatted as "hh:mm AM/PM" for the time slot.
     */
    public String getFormattedTimeSlot() {
        int displayHour = (hour == 0 || hour == 12) ? 12 : hour % 12;
        String period = (hour >= 12) ? "PM" : "AM";

        return String.format("%d:%02d %s", displayHour, minute, period);
    }

    public int getTimeslotInt(){
        int timeslotValue;

        if (hour >= 13) {
            timeslotValue = (hour - 13) * 2 + 5;
        } else if (hour >= 9) {
            timeslotValue = (hour - 9) * 2 + 1;
        } else {
            return -1;
        }
        if (minute >= 30) {
            timeslotValue += 1;
        }
        return timeslotValue;
    }

    /**
     * Compares this time slot with another time slot based on hour and minute.
     *
     * @param other The other time slot to compare with.
     * @return -1 if this time is earlier, 1 if this time is later, 0 if equal.
     */
    @Override
    public int compareTo(Timeslot other) {
        if (this.hour != other.hour) {
            return Integer.compare(this.hour, other.hour);
        }
        return Integer.compare(this.minute, other.minute);
    }

    /**
     * Returns a string representation of the time slot as "HH:MM".
     *
     * @return The string representation of the time slot.
     */
    @Override
    public String toString() {
        return String.format("%02d:%02d", hour, minute);
    }

    /**
     * Checks if this time slot is equal to another object.
     *
     * @param obj The object to compare against.
     * @return true if the time slots have the same hour and minute, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Timeslot other = (Timeslot) obj;
        return this.hour == other.hour && this.minute == other.minute;
    }

    /**
     * Generates a hash code for this Timeslot.
     *
     * @return The hash code of the time slot.
     */
    @Override
    public int hashCode() {
        return 31 * hour + minute;
    }
}
