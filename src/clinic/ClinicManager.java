package clinic;

import java.util.Calendar;
import java.util.Scanner;
import util.Date;
import java.io.File;
import util.List;

public class ClinicManager {
    private static boolean programRunning = true;

    private static final int INDEX_COMMAND = 0;
    private static final int INDEX_APPOINTMENT_DATE = 1;
    private static final int INDEX_TIMESLOT = 2;
    private static final int INDEX_FIRST_NAME = 3;
    private static final int INDEX_LAST_NAME = 4;
    private static final int INDEX_DATE_OF_BIRTH = 5;
    private static final int INDEX_NPI_OR_IMAGING_TYPE = 6;
    private static final int INDEX_NEWTIMESLOT = 6;
    private static final int D_OR_T_COMMAND_LENGTH = 7;
    private static final int VALID_C_COMMAND_LENGTH = 6;

    private static final int DOCTOR_COMMAND_LENGTH = 7;
    private static final int TECHNICIAN_COMMAND_LENGTH = 6;
    private static final int PROVIDER_FIRST_NAME_INDEX = 1;
    private static final int PROVIDER_LAST_NAME_INDEX = 2;
    private static final int PROVIDER_DOB_INDEX = 3;
    private static final int PROVIDER_LOCATION_INDEX = 4;
    private static final int DOCTOR_SPECIALTY_INDEX = 5;
    private static final int TECHNICIAN_RATE_INDEX = 5;
    private static final int DOCTOR_NPI_INDEX = 6;

    private static final int MIN_TIMESLOT_INDEX = 1;
    private static final int MAX_TIMESLOT_INDEX = 6;

    private static final String[] OUTPUT_HEADER_ARRAY = {"** Appointments ordered by date/time/provider **", "** Appointments ordered by patient/date/time **", "** Appointments ordered by county/date/time **"};
    private static final int PRINT_APPOINTMENT_VALUE = 0;
    private static final int PRINT_PATIENT_VALUE = 1;
    private static final int PRINT_LOCATION_VALUE = 2;

    private static final int DATE_IS_TODAY = 0;
    private static final int DATE_BEFORE_TODAY = -1;
    private static final int DATE_IS_VALID = 1;
    private static final int DATE_NOT_WITHIN_SIX_MONTHS = 2;

    private static final int DATE_INDEX_MONTH = 0;
    private static final int DATE_INDEX_DAY = 1;
    private static final int DATE_INDEX_YEAR = 2;

    private static final int CALENDAR_OFFSET = 1;
    private static final int NO_OFFSET_VALUE = 0;
    private static final int SIX_MONTH_OFFSET = 6;
    private static final int MONTH_INDEX_OFFSET = 1;

    private static final int BOOKED_VALUE = 1;
    private static final int RESCHEDULE_VALUE = 2;

    private static List<Provider> providerList = new List<Provider>();
    private static List<Technician> technicianList = new List<Technician>();
    private static final boolean PROVIDERS_LOADED = false;


    /**
     *   Reads input(s) from command line and calls readCommand function for each line
     *   Prints when scheduler starts and stops running
     */
    public void run(){
        Scanner commandScanner = new Scanner(System.in);


        System.out.println("Clinic Manager is running.");

        readProviderFile();

        while(programRunning){
            readCommand(commandScanner.nextLine());
        }

        commandScanner.close();
        System.out.println("Clinic Manager terminated.");
    }

    /**
     *  Takes a command line as input and reads the command, and calls correct method
     * Prints invalid command if command is not recognized
     * @param inputLine : The current command line being read
     */
    private void readCommand(String inputLine){
        if(inputLine.isEmpty()){
            return;
        }
        String[] inputList = inputLine.split(",");

        switch (inputList[INDEX_COMMAND]) {
            case "Q": //Quit Program
                programRunning = false;
                break;
            case "D": //Schedule new office appointment
                // Appointment NewAppointment = createAppointment(inputList, D_COMMAND_VALUE);
//                if(NewAppointment != null){
//                    // addAppointmentToList(NewAppointment, BOOKED_VALUE);
//                }
                break;
            case "T": //Schedule new imaging appointment

                break;
            case "C": //Cancel appointment

                break;
            case "R": //Reschedule appointment

                break;
            case "PA": // Print appointment list sorted by appointment date, time, then provider’s name.

                break;
            case "PP":// Print appointment list sorted by the patient (by last name, first name, date of birth, then appointment date and time).

                break;
            case "PL": // Print appointment list sorted by the county name, then the appointment date and time.

                break;
            case "PS": // Print billing statements of all patients

                break;
            case "PO": // Print the list of office appointments, sorted by the county name, then date and time.

                break;
            case "PI": // Print the list of imaging appointments, sorted by the county name, then date and time.

                break;
            case "PC": // Print the expected credit amounts for the providers, sorted by provider profile

                break;
            default: // Command not recognized
                System.out.println("Invalid command!");
                break;
        }
    }

    private void readProviderFile(){
        try {
            File providerFile = new File("./src/providers.txt");
            Scanner fileScanner = new Scanner(providerFile);
            while(fileScanner.hasNextLine()){
                String commandArray = fileScanner.nextLine();
                providerCreator(commandArray);
            }
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
        System.out.println("Rotation list for the technicians.");
        for(int i = technicianList.size()-1; i >= 0 ; i--){
            System.out.print(technicianList.get(i).toString());
            if (i != 0){
                System.out.print(" --> ");
            }
        }

    }

    private void providerCreator(String inputLine){
        if(inputLine.isEmpty()){
            return;
        }
        String[] inputList = inputLine.split("  ");
        switch (inputList[INDEX_COMMAND]) {
            case "D": //Create new doctor
                doctorCreator(inputList);
                break;
            case "T": //Create new technician
                technicianCreator(inputList);
                break;
            default: // Command not recognized
                System.out.println("Invalid command!");
                break;
        }

    }

    private void doctorCreator(String[] commandArray){
        if (commandArray.length != DOCTOR_COMMAND_LENGTH){
            return;
        }

        Date dateOfBirth = new Date(commandArray[PROVIDER_DOB_INDEX]);
        Profile profile = new Profile(commandArray[PROVIDER_FIRST_NAME_INDEX], commandArray[PROVIDER_LAST_NAME_INDEX], dateOfBirth);
        String npi = commandArray[DOCTOR_NPI_INDEX];
        String locationString = commandArray[PROVIDER_LOCATION_INDEX];
        String specialtyString = commandArray[DOCTOR_SPECIALTY_INDEX];
        Location providerLocation = null;
        Specialty doctorSpecialty = null;

        for (Location location : Location.values()) {
            if(location.name().equals(locationString.toLowerCase().trim())){
                providerLocation = location;
            }
        }
        for (Specialty specialty : Specialty.values()) {
            if(specialty.name().equals(specialtyString.toLowerCase().trim())){
                doctorSpecialty = specialty;
            }
        }

        Doctor doctor = new Doctor(profile, providerLocation, npi, doctorSpecialty);
        providerList.add(doctor);
    }

    private void technicianCreator(String[] commandArray){
        if (commandArray.length != TECHNICIAN_COMMAND_LENGTH){
            return;
        }

        Date dateOfBirth = new Date(commandArray[PROVIDER_DOB_INDEX]);
        Profile profile = new Profile(commandArray[PROVIDER_FIRST_NAME_INDEX], commandArray[PROVIDER_LAST_NAME_INDEX], dateOfBirth);
        int rate = Integer.parseInt(commandArray[TECHNICIAN_RATE_INDEX]);
        String locationString = commandArray[PROVIDER_LOCATION_INDEX];
        Location providerLocation = null;


        for (Location location : Location.values()) {
            if(location.name().equals(locationString.toLowerCase().trim())){
                providerLocation = location;
            }
        }


        Technician technician = new Technician(profile, providerLocation, rate);
        providerList.add(technician);
        technicianList.add(technician);
    }

    /**
     * Creates an appointment based off commandLine input array
     * Returns the new appointment object if successful, null otherwise
     * @param commandArray an array from command line input with data to create appointment
     @return an appointment or null object based on provided commandLine input array
     */
    private Appointment createOfficeAppointment(String[] commandArray){

        if(commandArray.length != D_OR_T_COMMAND_LENGTH){
            System.out.println("Invalid command!");
            return null;
        }
        Date date = null;
        Timeslot slot = null;
        Person patient = null;
        Person provider = null;

        try{
            date = dateCreator(commandArray[INDEX_APPOINTMENT_DATE].split("/")[DATE_INDEX_MONTH], commandArray[INDEX_APPOINTMENT_DATE].split("/")[DATE_INDEX_DAY], commandArray[INDEX_APPOINTMENT_DATE].split("/")[DATE_INDEX_YEAR]);
        }catch(Exception e){
            System.out.println("Appointment date: " + commandArray[INDEX_APPOINTMENT_DATE] + " is not a valid date.");
            return null;
        }

        try{
            //slot = timeslotCreator(commandArray[INDEX_TIMESLOT]);
            patient = profileCreator(commandArray[INDEX_FIRST_NAME], commandArray[INDEX_LAST_NAME], commandArray[INDEX_DATE_OF_BIRTH]);
            //provider = providerCreator(commandArray[INDEX_PROVIDER]);
        }catch(Exception e){
            return null;
        }

        if(date == null){
            System.out.println("Appointment date: " + commandArray[INDEX_APPOINTMENT_DATE] + " is not a valid date.");
        }
        else if(slot == null){
            System.out.println(commandArray[INDEX_TIMESLOT].trim() + " is not a valid time slot.");
        }
        if(date == null || slot == null || patient == null || provider == null){
            return null;
        }

        return new Appointment(date, slot, patient, provider);
    }
    /**
     Creates a profile object based off inputs
     If any errors occur they are printed and null is return
     @param firstName a string representing patient first name
     @param lastName a string representing patient last name
     @param dateOfBirthString a string representing patient date of birth
     @return Profile object based off input data
     */
    private Person profileCreator(String firstName, String lastName, String dateOfBirthString){
        Date date = null;
        date = dateCreator(dateOfBirthString.split("/")[DATE_INDEX_MONTH], dateOfBirthString.split("/")[DATE_INDEX_DAY], dateOfBirthString.split("/")[DATE_INDEX_YEAR]);

        if(date == null || !date.isValid()){
            System.out.println("Patient dob: " + dateOfBirthString + " is not a valid calendar date.");
            return null;
        }
        if(checkDateValid(date) > 0){
            System.out.println("Patient dob: " + dateOfBirthString + " is today or a date after today.");
            return null;
        }

        Profile profile;
        Person person;
        try{
            profile = new Profile(firstName, lastName, date);
        }catch(Exception e){
            return null;
        }
        try{
            person = new Person(profile);
        }catch(Exception e){
            return null;
        }

        return person;
    }

    /**
     Returns a date object based off input strings, or returns null if they are invalid
     @param monthString A string representing the date month
     @param dayString A string representing the date day
     @param yearString A string representing the date year
     @return A date object based off the inputted strings, null if they are invalid
     */
    private Date dateCreator(String monthString, String dayString, String yearString){
        Date date;
        try{
            date = new Date(Integer.parseInt(monthString), Integer.parseInt(dayString), Integer.parseInt(yearString));
        }catch(Exception e){
            return null;
        }
        return date;
    }

    /**
     Returns a timeslot object based off string input, returns null if the string is invalid

     @param timeslotString a string representing the timeslot object to be created
     @return A timeslot object created from input string
     */
//    private Timeslot timeslotCreator(String timeslotString){
//        int timeslotIndex;
//        try{
//            timeslotIndex = Integer.parseInt(timeslotString);
//        }catch(Exception e){
//            return null;
//        }
//
//        if(timeslotIndex < MIN_TIMESLOT_INDEX || timeslotIndex > MAX_TIMESLOT_INDEX){
//            return null;
//        }
//
//        try{
//            return Timeslot.values()[Integer.parseInt(timeslotString)-1];
//        }catch(Exception e){
//            return null;
//        }
//    }
    /**
     Returns a provider object based off input string,
     If provider doesn't exist, it prints the error and returns null
     @param providerString a string representing the provider object to be created
     @return A provider object based off input string
     */
//    private Provider providerCreator(String providerString){
//        for (Provider provider : Provider.values()) {
//            if(provider.name().toLowerCase().equals(providerString.toLowerCase().trim())){
//                return provider;
//            }
//        }
//        System.out.println(providerString + " - provider doesn't exist.");
//        return null;
//    }
    /**
     Checks if given date is not today or before, and is within 6 months
     Returns an integer value based on the validity of the date
     @param targetDate the date object to be checked
     @return an integer based on if the date is valid, or what the issue is
     */
    private int checkDateValid(Date targetDate){
        //Check if date is today or before
        if(targetDate.compareTo(getSystemDate(NO_OFFSET_VALUE)) == DATE_IS_TODAY){
            return DATE_IS_TODAY;
        }
        //Check if date is before today
        if(targetDate.compareTo(getSystemDate(NO_OFFSET_VALUE)) == DATE_BEFORE_TODAY){
            return DATE_BEFORE_TODAY;
        }
        //Check if date is within six months
        if(targetDate.compareTo(getSystemDate(SIX_MONTH_OFFSET)) != 1){
            return DATE_IS_VALID;
        }else{
            return DATE_NOT_WITHIN_SIX_MONTHS;
        }

    }
    /**
     Gets current system date and returns a Date object of the current date with the month offset added
     @param monthOffset An integer value based on how much the
     @return A date object created from the system date and month offset
     */
    public Date getSystemDate(int monthOffset){

        Calendar appointmentCalendar = Calendar.getInstance();
        appointmentCalendar.add(Calendar.DATE, NO_OFFSET_VALUE);
        appointmentCalendar.add(Calendar.YEAR, NO_OFFSET_VALUE);
        appointmentCalendar.add(Calendar.MONTH, monthOffset + MONTH_INDEX_OFFSET);


        return new Date(appointmentCalendar.get(Calendar.MONTH), appointmentCalendar.get(Calendar.DAY_OF_MONTH), appointmentCalendar.get(Calendar.YEAR));
    }
}
