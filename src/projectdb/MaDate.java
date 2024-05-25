package projectdb;

public class MaDate {
    private int JJ; // Day
    private int MM;  // Month
    private int AA;  // Year (4 digits)

    // Constructor
    public MaDate(int JJ, int MM, int AA) {
        
        this.JJ = JJ;
        this.MM = MM;
        this.AA = AA;
    }

    // Getters and Setters with validation
    public int getJJ() {
        return JJ;
    }

    public void setJJ(int JJ) {
        if (!isValidDay(JJ, MM, AA)) {
            throw new IllegalArgumentException("Invalid day (JJ) for the given month (MM) and year (AA)");
        }
        this.JJ = JJ;
    }

    public int getMM() {
        return MM;
    }

    public void setMM(int MM) {
        if (MM < 1 || MM > 12) {
            throw new IllegalArgumentException("Month (MM) must be between 1 and 12");
        }
        this.MM = MM;
    }

    public int getAA() {
        return AA;
    }

    public void setAA(int AA) {
        if (String.valueOf(AA).length() != 4) {
            throw new IllegalArgumentException("Year (AA) must be a 4-digit number");
        }
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

    @Override
    public String toString() {
        return String.format("MaDate{JJ=%02d, MM=%02d, AA=%d}", JJ, MM, AA);
    }
}
