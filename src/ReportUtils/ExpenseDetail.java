package ReportUtils;

/*
* This class is used to update the EXPENSE table in the database.
* */

public class ExpenseDetail {
    private final String complexID; // the complex ID
    private final String type; // the expense type
    private final String year;
    private final String quarter;
    private final double amount; // the amount of the expense

    public ExpenseDetail(String complexID, String type,
                               String year, String quarter, double amount) {
        this.complexID = complexID;
        this.type = type;
        this.year = year;
        this.quarter = quarter;
        this.amount = amount;
    }

    // return complex ID
    public String getComplexID() { return complexID; }

    // return expense type
    public String getType() { return type; }

    public String getYear() { return year; }

    public String getQuarter() { return quarter; }

    // return expense amount
    public double getAmount() { return amount; }
}
