package ReportUtils;

/*
 * This class is used to register a maintenance request, which will be added to the MAINTENANCE table in the database
 * */

public class MaintenanceDetail {
    private final String maintenanceID; // maintenance ID
    private final String complexID; // complex ID
    private final String aptID; // apartment ID
    private final String occurDate; // date of occurrence
    private String resDate; // date of resolution
    private final String problem; // problem description
    private final String type; // problem type
    private String resolution; // resolution
    private double expense; // expense

    public MaintenanceDetail(String complexID, String aptID,
                             String occurDate, String resDate,
                             String problem, String type, String resolution,
                             double expense) {
        this.complexID = complexID;
        this.aptID = aptID;
        this.occurDate = occurDate;
        this.resDate = resDate;
        this.problem = problem;
        this.type = type;
        this.resolution = resolution;
        this.expense = expense;
        maintenanceID = complexID + "-" + aptID + "-" + Timer.getTimer();
    }

    // set resolution
    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    // set expense
    public void setExpense(double expense) {
        this.expense = expense;
    }

    // set date of resolution
    public void setResDate() {
        this.resDate = Timer.getDate();
    }

    // return maintenance ID
    public String getMaintenanceID() { return maintenanceID; }

    // return complex ID
    public String getComplexID() { return complexID; }

    // return apartment ID
    public String getAptID() { return aptID; }

    // return problem type
    public String getType() { return type; }

    // return date of occurrence
    public String getOccurDate() { return occurDate; }

    // return date of resolution
    public String getResDate() { return resDate; }

    // return problem description
    public String getProblem() { return problem; }

    // return resolution
    public String getResolution() { return resolution; }

    // return expense
    public double getExpense() { return expense; }
}
