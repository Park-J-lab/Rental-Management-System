package ReportUtils;

/*
 * This class is used to help generate the Maintenance Report
 * */

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class MaintenanceReportDetail {

    private SimpleStringProperty complexID; // complex ID
    private SimpleStringProperty aptID; // apartment ID
    private SimpleStringProperty problem; // problem description
    private SimpleStringProperty type; // problem type
    private SimpleStringProperty resolution; // resolution
    private SimpleStringProperty occurDate; // date of occurrence
    private SimpleStringProperty resDate; // date of resolution
    private SimpleDoubleProperty expense; // expense

    // set complex ID
    public void setComplexID(String complexID) {
        this.complexID = new SimpleStringProperty(complexID);
    }

    // set apartment ID
    public void setAptID(String aptID) {
        this.aptID = new SimpleStringProperty(aptID);
    }

    // set problem description
    public void setProblem(String problem) {
        this.problem = new SimpleStringProperty(problem);
    }

    // set problem type
    public void setType(String type) {
        this.type = new SimpleStringProperty(type);
    }

    // set date of occurrence
    public void setOccurDate(String occurDate) {
        this.occurDate = new SimpleStringProperty(occurDate);
    }

    // set date of resolution
    public void setResDate(String resDate) {
        this.resDate = new SimpleStringProperty(resDate);
    }

    // set resolution
    public void setResolution(String resolution) { this.resolution = new SimpleStringProperty(resolution); }

    // set expense
    public void setExpense(double expense) {
        this.expense = new SimpleDoubleProperty(expense);
    }

    // return complex ID
    public String getComplexID() { return complexID.get(); }

    // return apartment ID
    public String getAptID() { return aptID.get(); }

    // return problem description
    public String getProblem() { return problem.get(); }

    // return problem type
    public String getType() { return type.get(); }

    // return date of occurrence
    public String getOccurDate() { return occurDate.get(); }

    // return date of resolution
    public String getResDate() {
        return resDate.get();
    }

    // return resolution
    public String getResolution() { return resolution.get(); }

    // return expense
    public double getExpense() {
        return expense.get();
    }

    @Override
    public String toString() {
        return String.format("%-12s",complexID.get()) +
                String.format("%-8s", aptID.get()) +
                String.format("%-15s", occurDate.get()) +
                String.format("%-35s", problem.get()) +
                String.format("%-12s", type.get()) +
                String.format("%-25s", resolution.get()) +
                String.format("%-15s", resDate.get()) +
                String.format("%-15s", expense.get());
    }
}
