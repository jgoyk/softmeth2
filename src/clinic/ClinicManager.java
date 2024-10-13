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
    private static final int INDEX_NPI = 6;
    private static final int INDEX_IMAGING_TYPE = 6;
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
    private static List<Doctor> doctorList = new List<Doctor>();
    private static List<Appointment> appointmentList = new List<Appointment>();


    /**
     *   Reads input(s) from command line and calls readCommand function for each line
     *   Prints when scheduler starts and stops running
     */
    public void run(){
        Scanner commandScanner = new Scanner(System.in);


        System.out.println("Clinic Manager is running.");

        readProviderFile();
        //need to display provider list sorted by provider profile
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
                Appointment NewAppointment = createOfficeAppointment(inputList);
                if(NewAppointment != null){
                    addAppointmentToList(NewAppointment, BOOKED_VALUE);
                }
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
        System.out.println("Providers loaded to the list.");
        for(int i = providerList.size()-1; i >= 0 ; i--){
            System.out.println(providerList.get(i).toString());
        }
        System.out.println("Rotation list for the technicians.");
        for(int i = technicianList.size()-1; i >= 0 ; i--){
            System.out.print(technicianList.get(i).toString());
            if (i != 0){
                System.out.print(" --> ");
            }
        }
        System.out.println();

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
            if(location.name().equals(locationString.trim())){
                providerLocation = location;
            }
        }
        for (Specialty specialty : Specialty.values()) {
            if(specialty.name().equals(specialtyString.trim())){
                doctorSpecialty = specialty;
            }
        }
        Doctor doctor = new Doctor(profile, providerLocation, npi, doctorSpecialty);
        providerList.add(doctor);
        doctorList.add(doctor);
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
            if(location.name().equals(locationString.trim())){
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
            System.out.println("Missing data tokens.");
            return null;
        }
        Date date = null;
        Timeslot slot = null;
        Person patient = null;
        Person provider = null;

        try{
            date = new Date(commandArray[INDEX_APPOINTMENT_DATE]);
        }catch(Exception e){
            System.out.println("Appointment date: " + commandArray[INDEX_APPOINTMENT_DATE] + " is not a valid date.");
            return null;
        }

        try{
            patient = profileCreator(commandArray[INDEX_FIRST_NAME], commandArray[INDEX_LAST_NAME], commandArray[INDEX_DATE_OF_BIRTH]);
            provider = doctorFinder(commandArray[INDEX_NPI]);
            slot = timeslotCreator(commandArray[INDEX_TIMESLOT]);
        }catch(Exception e){
            return null;
        }

        if(!date.isValid()){
            System.out.println("Appointment date: " + commandArray[INDEX_APPOINTMENT_DATE] + " is not a valid date.");
        }
        if( slot == null || patient == null || provider == null){
            return null;
        }

        return new Appointment(date, slot, patient, provider);
    }

    /**
     * Creates an appointment based off commandLine input array
     * Returns the new appointment object if successful, null otherwise
     * @param commandArray an array from command line input with data to create appointment
     @return an appointment or null object based on provided commandLine input array
     */
    private Appointment createTechnicianAppointment(String[] commandArray){

        if(commandArray.length != D_OR_T_COMMAND_LENGTH){
            System.out.println("Missing data tokens.");
            return null;
        }
        Date date = null;
        Timeslot slot = null;
        Person patient = null;
        Radiology service = null;
        Person provider = null;

        try{
            date = new Date(commandArray[INDEX_APPOINTMENT_DATE]);
        }catch(Exception e){
            System.out.println("Appointment date: " + commandArray[INDEX_APPOINTMENT_DATE] + " is not a valid date.");
            return null;
        }

        try{
            patient = profileCreator(commandArray[INDEX_FIRST_NAME], commandArray[INDEX_LAST_NAME], commandArray[INDEX_DATE_OF_BIRTH]);

            slot = timeslotCreator(commandArray[INDEX_TIMESLOT]);
        }catch(Exception e){
            return null;
        }

        if(!date.isValid()){
            System.out.println("Appointment date: " + commandArray[INDEX_APPOINTMENT_DATE] + " is not a valid date.");
        }
        if( slot == null || patient == null){
            return null;
        }

        return new Appointment(date, slot, patient, provider);
    }
    /**
     Creates a profile object based off inputs
     If any errors occur they are printed and null is return
     @param serviceString a string representing patient first name
     @return Profile object based off input data
     */
    private Person technicianAssigner(String serviceString){
        Radiology room = null;
        for (Radiology service : Radiology.values()) {
            if(service.name().toLowerCase().equals(serviceString.toLowerCase().trim())){
                room = service;
            }
        }
        if(room == null){
            System.out.println(serviceString + " - imaging service not provided.");
            return null;
        }

        Person technician = null;


        return technician;
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
        date = new Date(dateOfBirthString);

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
     Returns a timeslot object based off string input, returns null if the string is invalid

     @param timeslotString a string representing the timeslot object to be created
     @return A timeslot object created from input string
     */
    private Timeslot timeslotCreator(String timeslotString){
        int timeslot = 0;
        try {
            timeslot = Integer.parseInt(timeslotString);
        } catch(Exception e){
            System.out.println(timeslotString + " is not a valid time slot.");
            return null;
        }
        if (timeslot > 0 && timeslot < 13){
            return new Timeslot(timeslot);
        } else {
            System.out.println(timeslotString + " is not a valid time slot.");
            return null;
        }
    }
    /**
     Returns a provider object based off input string,
     If provider doesn't exist, it prints the error and returns null
     @param npi a string representing the doctors NPI
     @return A provider object based off input string
     */
    private Person doctorFinder(String npi){

        for(int i = 0; i < doctorList.size(); i++){
            if(doctorList.get(i).getNpi().equals(npi)){
                return doctorList.get(i);
            }
        }
        System.out.println(npi + " - provider doesn't exist.");
        return null;
    }
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

    /**
     * Adds an appointment to list and prints a status based on statusValue
     * Returns true or false based on success of appointment list addition
     * @param appointment The new appointment to be added to the list
     * @param statusValue An integer value to differentiate which status to print after adding new appointment
     * @return a true or false boolean value depending on if the appointment was successfully added to list
     */
    boolean addAppointmentToList(Appointment appointment, int statusValue){

        if(appointmentValidator(appointment)){
            appointmentList.add(appointment);
            if (statusValue == BOOKED_VALUE){
                System.out.println(appointment.toString() + " booked.");
            } else {
                System.out.println( "Rescheduled to " + appointment.toString());
            }
            return true; //Appointment was successfully added to list
        }
        return false; //Appointment was not added to list

    }

    /**
     * Checks for timeslot conflicts for appointment input and checks if appointment date is a valid date
     * Prints error if there is a conflict or validity issue, and returns true or false based on success
     * @param appointment An appointment object to be validated
     * @return a boolean value based on if the appointment is valid or not
     */
    private boolean appointmentValidator(Appointment appointment){
        // Check if patient is available
        if(appointmentList.contains(appointment)){
            System.out.println(appointment.getPatient().toString()+" has an existing appointment at the same time slot.");
            return false;
        }
        // Check if timeslot is available
//        if(appointmentList.getAppointmentByProvider(appointment.getDate(), appointment.getTimeslot(), appointment.getProvider()) != null){
//            System.out.println(appointment.getProvider().getString_ProviderInfo() + " is not available at slot " + appointment.getTimeslot().toString().replaceAll("\\D+", "")+".");
//            return false;
//        }
        // Check if appointment date is valid
        return appointmentDateValidator(appointment);
    }

    /**
     * Checks if appointment object date is valid, not today or before today, is within six months, and is not a weekend
     * Prints any errors and returns true if successful, false otherwise
     * @param appointment An appointment object to be checked for date validity
     * @return a boolean value based on if the appointment date is a valid appointment date
     */
    private boolean appointmentDateValidator(Appointment appointment){
        //Check appointment date is valid calendar date
        if(!appointment.getDate().isValid()){
            System.out.println("Appointment date: " + appointment.getDate().toString() + " is not a valid calendar date.");
            return false;
        }
        //Check if appointment date is today
        if(checkDateValid(appointment.getDate()) <= DATE_IS_TODAY){
            System.out.println("Appointment date: " + appointment.getDate().toString() + " is today or a date before today.");
            return false;
        }
        //Check if appointment date is within 6 months
        if(checkDateValid(appointment.getDate()) == DATE_NOT_WITHIN_SIX_MONTHS){
            System.out.println("Appointment date: " + appointment.getDate().toString() + " is not within six months.");
            return false;
        }
        //Check if appointment date is a weekend
        Calendar appointmentCalendar = Calendar.getInstance();
        try{
            appointmentCalendar.set(appointment.getDate().getYear(), appointment.getDate().getMonth() - CALENDAR_OFFSET, appointment.getDate().getDay());
        }catch(Exception e){
            System.out.println("Date can not be convert to Calendar object");
            return false;
        }
        int day = appointmentCalendar.get(Calendar.DAY_OF_WEEK);
        if(day == Calendar.SATURDAY || day == Calendar.SUNDAY){
            System.out.println("Appointment date: " + appointment.getDate().toString() + " is Saturday or Sunday.");
            return false;
        }
        return true;
    }
}
