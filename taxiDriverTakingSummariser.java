import java.util.Scanner;

class TaxiDriver {
    final int DAYS = 28;
    final int PERIODS = 3; // Morning, Afternoon, Evening

    String[] stations = new String[DAYS];
    int[][] takings = new int[DAYS][PERIODS];
    boolean[][] workingPeriods = new boolean[DAYS][PERIODS];
    int dayCounter = 0;
} // END class TaxiDriver

class TaxiDriverTakingMSummariser {

    public static void main(String[] args) {
        TaxiDriver newDriver = new TaxiDriver();
        menu(newDriver);
        return;
    } // END main

    // Getter for DAYS in Main class
    public static int getDays(TaxiDriver newDriver) {
        return newDriver.DAYS;
    }

    // Getter for PERIODS in Main class
    public static int getPeriods(TaxiDriver newDriver) {
        return newDriver.PERIODS;
    }

    public static String getStation(TaxiDriver newDriver, int day) {
        return newDriver.stations[day];
    } // END getStation

    public static int getDayCounter(TaxiDriver newDriver) {
        return newDriver.dayCounter;
    } // END getDayCounter

    public static int getTakings(TaxiDriver newDriver, int day, int period) {
        return newDriver.takings[day][period];
    } // END getTakings


    public static boolean getWorkingPeriod(TaxiDriver newDriver, int day, int period) {
        return newDriver.workingPeriods[day][period];
    } // END getWorkingPeriod

    public static void setWorkingPeriod(TaxiDriver newDriver, int day, int period, boolean isWorking) {
        newDriver.workingPeriods[day][period] = isWorking;
        return;
    } // END setWorkingPeriod

    public static void setStation(TaxiDriver newDriver, int day, String station) {
        newDriver.stations[day] = station;
        return;
    } // END setStation


    public static void setDayCounter(TaxiDriver newDriver, int counter) {
        newDriver.dayCounter = counter;
    } // END setPatientCounter


    public static void addTakings(TaxiDriver newDriver, int day, int period, int amount) {
        newDriver.takings[day][period] += amount;
        return;
    } // END addTakings

    // Main menu
    public static void menu(TaxiDriver newDriver) {
        int counter = getDayCounter(newDriver);

        while (counter < getDays(newDriver)) {

            inputDayDetails(newDriver, counter);
            processPeriods(newDriver, counter);
            printDaySummary(newDriver, counter);
            setDayCounter(newDriver, counter+1);
        }
        printFourWeekSummary(newDriver);
    } // END menu

    // Input day details
    public static void inputDayDetails(TaxiDriver newDriver, int day) {
        String station = inputs("Enter the station for day " + (day + 1) + " (Kings Cross, Liverpool Street, Paddington, Euston): ");
        String periodName;
        String response;

        while (!isValidStation(station)) {
            station = inputs("Invalid station. Please enter a valid station name: ");
        }
        setStation(newDriver, day, station);

        for (int period = 0; period < getPeriods(newDriver); period++) {
            periodName = getPeriodName(period);
            response = inputs("Are you working in the " + periodName + " (Y/N)? ").toUpperCase();

            while (!isYesOrNo(response)) {
                response = inputs("Invalid input. Please enter 'Y' or 'N': ").toUpperCase();
            }
            setWorkingPeriod(newDriver, day, period, response.equals("Y"));
        }
        return;
    } // END inputDayDetails

    // Process fares for each period
    public static void processPeriods(TaxiDriver newDriver, int day) {
        for (int period = 0; period < getPeriods(newDriver); period++) {
            if (getWorkingPeriod(newDriver, day, period)) {
                processFares(newDriver, day, period);
            }
        }
        return;
    } // END processPeriods


    // Process fares for a single period
    public static void processFares(TaxiDriver newDriver, int day, int period) {
        String periodName = getPeriodName(period);
        boolean exit = false;
        String fareInput;

        print("Entering fares for " + periodName + ": ");

        while (!exit) {
            fareInput = inputs("Enter fare amount (or type 'E' to quit): ").toUpperCase();

            if (fareInput.equals("E")) {
                exit = true;
            } else if (isPositiveInteger(fareInput)) {
                addTakings(newDriver, day, period, Integer.parseInt(fareInput));
            } else {
                print("Invalid input. Please enter a positive integer or 'E' to quit.");
            }
        }
        return;
    } // END processFares




    // Print daily summary
    public static void printDaySummary(TaxiDriver newDriver, int day) {
        String periodName;
        int takings;

        print("Summary for Day " + (day + 1) + " at " + getStation(newDriver, day) + ":");
        for (int period = 0; period < getPeriods(newDriver); period++) { 
            periodName = getPeriodName(period);
            takings = getTakings(newDriver, day, period);
            print(periodName + " takings: £" + takings);
        }
        print("\n");
        return;
    } // END printDaySummary

    // Print four-week summary
    public static void printFourWeekSummary(TaxiDriver newDriver) {
        print("Four-week summary:");
        for (int day = 0; day < getDayCounter(newDriver); day++) {
            printDaySummary(newDriver, day);
        }
        return;
    } // END printFourWeekSummary

    // Utility methods
    public static String getPeriodName(int period) {
        if (period == 0) {
            return "Morning";
        } else if (period == 1) {
            return "Afternoon";
        } else if (period == 2) {
            return "Evening";
        } else {
            return "Unknown";
        }
    } // END getPeriodName

    public static boolean isValidStation(String station) {
        return station.equalsIgnoreCase("Kings Cross") || station.equalsIgnoreCase("Liverpool Street") ||
                station.equalsIgnoreCase("Paddington") || station.equalsIgnoreCase("Euston");
    } // END isValidStation

    public static boolean isYesOrNo(String input) {
        return input.equals("Y") || input.equals("N");
    } // END isYesOrNo

    public static boolean isPositiveInteger(String input) {
        return input.matches("\\d+") && Integer.parseInt(input) > 0;
    } // END isPositiveInteger

    public static String inputs(String message) {
        Scanner scanner = new Scanner(System.in);
        print(message);
        return scanner.nextLine();
    } // END inputs

    public static void print(String message) {
        System.out.println(message);
        return;
    } // END print

} // END class TaxiDriverTakingMSummariser
