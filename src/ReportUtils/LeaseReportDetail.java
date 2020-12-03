package ReportUtils;

/*
* This class is used to help generate the Lease Report
* */

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class LeaseReportDetail {
    private SimpleStringProperty leaseID; // lease ID
    private SimpleStringProperty complexID; // complex ID
    private SimpleStringProperty aptID; // apartment ID
    private SimpleStringProperty lesseeName; // lessee name
    private SimpleStringProperty startDate; // start date
    private SimpleStringProperty endDate; // end date
    private SimpleDoubleProperty rent; // monthly rent
    private SimpleDoubleProperty deposit; // deposit

    // set lease ID
    public void setLeaseID(String leaseID) {
        this.leaseID = new SimpleStringProperty(leaseID);
    }

    // set complex ID
    public void setComplexID(String complexID) {
        this.complexID = new SimpleStringProperty(complexID);
    }

    // set apartment ID
    public void setAptID(String aptID) {
        this.aptID = new SimpleStringProperty(aptID);
    }

    // set lessee name
    public void setLesseeName(String lesseeName) {
        this.lesseeName = new SimpleStringProperty(lesseeName);
    }

    // set start date
    public void setStartDate(String startDate) {
        this.startDate = new SimpleStringProperty(startDate);
    }

    // set end date
    public void setEndDate(String endDate) {
        this.endDate = new SimpleStringProperty(endDate);
    }

    // set monthly rent
    public void setRent(double rent) {
        this.rent = new SimpleDoubleProperty(rent);
    }

    // set deposit
    public void setDeposit(double deposit) {
        this.deposit = new SimpleDoubleProperty(deposit);
    }

    // return lease ID
    public String getLeaseID() {
        return leaseID.get();
    }

    // return complex ID
    public String getComplexID() {
        return complexID.get();
    }

    // return apartment ID
    public String getAptID() {
        return aptID.get();
    }

    // return lessee name
    public String getLesseeName() {
        return lesseeName.get();
    }

    // return start date
    public String getStartDate() {
        return startDate.get();
    }

    // return end date
    public String getEndDate() {
        return endDate.get();
    }

    // return rent
    public double getRent() {
        return rent.get();
    }

    // return deposit
    public double getDeposit() {
        return deposit.get();
    }

    @Override
    public String toString() {
        return String.format("%-25s", leaseID.get()) +
                String.format("%-12s",complexID.get()) +
                String.format("%-8s", aptID.get()) +
                String.format("%-20s", lesseeName.get()) +
                String.format("%-15s", startDate.get()) +
                String.format("%-15s", endDate.get()) +
                String.format("%-10s", rent.get()) +
                String.format("%-10s", deposit.get());
    }
}
