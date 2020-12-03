package ReportUtils;

/*
* This class is used to help generate the Payment Report.
* */

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class PaymentReportDetail {
    private SimpleStringProperty paymentID; // payment ID
    private SimpleStringProperty complexID; // complex ID
    private SimpleStringProperty aptID; // apartment ID
    private SimpleStringProperty leaseID; // lease ID
    private SimpleStringProperty lesseeName; // lessee name
    private SimpleStringProperty date; // date of payment
    private SimpleDoubleProperty amount; // amount of payment
    private SimpleStringProperty lateOrNot; // whether it is a late payment

    // set payment ID
    public void setPaymentID(String paymentID) { this.paymentID = new SimpleStringProperty(paymentID); }

    // set complex ID
    public void setComplexID(String complexID) {
        this.complexID = new SimpleStringProperty(complexID);
    }

    // set apartment ID
    public void setAptID(String aptID) {
        this.aptID = new SimpleStringProperty(aptID);
    }

    // set lease ID
    public void setLeaseID(String leaseID) {
        this.leaseID = new SimpleStringProperty(leaseID);
    }

    // set lessee name
    public void setLesseeName(String lesseeName) {
        this.lesseeName = new SimpleStringProperty(lesseeName);
    }

    // set date of payment
    public void setDate(String date) { this.date = new SimpleStringProperty(date); }

    // set amount of payment
    public void setAmount(double amount) { this.amount = new SimpleDoubleProperty(amount); }

    // set whether it is a late payment
    public void setLateOrNot(String status) { lateOrNot = new SimpleStringProperty(status); }

    // return payment ID
    public String getPaymentID() {
        return paymentID.get();
    }

    // return complex ID
    public String getComplexID() {
        return complexID.get();
    }

    // return apartment ID
    public String getAptID() {
        return aptID.get();
    }

    // return lease ID
    public String getLeaseID() {
        return leaseID.get();
    }

    // return lessee name
    public String getLesseeName() {
        return lesseeName.get();
    }

    // return date of payment
    public String getDate() {
        return date.get();
    }

    // return amount of payment
    public double getAmount() {
        return amount.get();
    }

    // return whether it is a late payment or not
    public String getLateOrNot() {
        return lateOrNot.get();
    }

    @Override
    public String toString() {
        return String.format("%-25s", paymentID.get()) +
                String.format("%-12s",complexID.get()) +
                String.format("%-8s", aptID.get()) +
                String.format("%-25s", leaseID.get()) +
                String.format("%-20s", lesseeName.get()) +
                String.format("%-15s", date.get()) +
                String.format("%-15s", amount.get()) +
                String.format("%-10s", lateOrNot.get());
    }
}
