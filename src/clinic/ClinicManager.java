package clinic;

import java.util.Scanner;

public class ClinicManager {
    public static boolean programRunning = true;

    public static final int INDEX_COMMAND = 0;


    /**
     *   Reads input(s) from command line and calls readCommand function for each line
     *   Prints when scheduler starts and stops running
     */
    public void run(){
        Scanner CommandScanner = new Scanner(System.in);
        System.out.println("Clinic Manager is running.");
        while(programRunning){
            readCommand(CommandScanner.nextLine());
        }

        CommandScanner.close();
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
}
