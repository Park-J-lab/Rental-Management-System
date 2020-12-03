package ReportUtils;

/*
* This class is used to register a new uncovered rent, which is then added to the UNCOVERED_RENT table in the database
* */

public class UncoveredRentDetail {
    private final String complexID; // complex ID
    private final String aptID; // apartment ID
    private final String year;
    private final String quarter;
    private final double amount; // the amount of the rent

    public UncoveredRentDetail(String complexID, String aptID,
                               String year, String quarter, double amount) {
        this.complexID = complexID;
        this.aptID = aptID;
        this.year = year;
        this.quarter = quarter;
        this.amount = amount;
    }

    // return complex ID
    public String getComplexID() { return complexID; }

    // return apartment ID
    public String getAptID() { return aptID; }

    public String getYear() { return year; }

    public String getQuarter() { return quarter; }

    // return the amount of the rent
    public double getAmount() { return amount; }
}
