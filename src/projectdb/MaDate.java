package projectdb;

import java.time.LocalDate;

public class MaDate {

    private int JJ; // Day
    private int MM; // Month
    private int AA; // Year (4 digits)

    // Constructor
    public MaDate(int JJ, int MM, int AA) {
        setDate(JJ, MM, AA);
    }

    // Method to set the complete date
    public void setDate(int JJ, int MM, int AA) {
        if (MM < 1 || MM > 12) {
            throw new IllegalArgumentException("Month (MM) must be between 1 and 12");
        }
        if (!isValidDay(JJ, MM, AA)) {
            throw new IllegalArgumentException("Invalid day (JJ) for the given month (MM) and year (AA)");
        }
        if (!isFutureDate(JJ, MM, AA)) {
            throw new IllegalArgumentException("The date must be in the future");
        }
        this.JJ = JJ;
        this.MM = MM;
        this.AA = AA;
    }

    // Getters
    public int getJJ() {
        return JJ;
    }

    public int getMM() {
        return MM;
    }

    public int getAA() {
        return AA;
    }

    // Individual setters without validation
    private void setJJ(int JJ) {
        this.JJ = JJ;
    }

    private void setMM(int MM) {
        this.MM = MM;
    }

    private void setAA(int AA) {
        this.AA = AA;
    }

    // Helper method to validate the day based on the month and year
    private boolean isValidDay(int JJ, int MM, int AA) {
        if (JJ < 1 || JJ > 31) {
            return false;
        }
        if (MM == 2) { // February
            if (isLeapYear(AA)) {
                return JJ <= 29;
            } else {
                return JJ <= 28;
            }
        }
        if (MM == 4 || MM == 6 || MM == 9 || MM == 11) { // April, June, September, November
            return JJ <= 30;
        }
        return true;
    }

    // Helper method to check if a year is a leap year
    private boolean isLeapYear(int year) {
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                return year % 400 == 0;
            } else {
                return true;
            }
        }
        return false;
    }

    // Helper method to check if the date is in the future
    private boolean isFutureDate(int JJ, int MM, int AA) {
        LocalDate currentDate = LocalDate.now();
        LocalDate inputDate = LocalDate.of(AA, MM, JJ);
        return inputDate.isAfter(currentDate) || inputDate.equals(currentDate);
    }

    @Override
    public String toString() {
        return String.format("MaDate{JJ=%02d, MM=%02d, AA=%d}", JJ, MM, AA);
    }
}
