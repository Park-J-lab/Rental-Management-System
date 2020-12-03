package ReportUtils;

/*
* This class is used to register a new payment, which will be added to the PAYMENT database
* */

public class PaymentDetail {
    private String paymentID; // payment ID
    private String complexID; // complex ID
    private String leaseID; // lease ID
    private String aptID; // apartment ID
    private String date; // date of payment
    private String lesseeName; // lessee name
    private double amountPaid; // the payment amount
    private String lateOrNot; // whether it is a late payment

    public PaymentDetail() {}

    public PaymentDetail(String complexID, String aptID, String leaseID, String lesseeName,
                         double amountPaid, String lateOrNot) {
        this.complexID = complexID;
        this.aptID = aptID;
        this.leaseID = leaseID;
        this.lesseeName = lesseeName;
        this.amountPaid = amountPaid;
        this.lateOrNot = lateOrNot;
        date = Timer.getDate();
        paymentID = complexID + "-" + aptID + "-" + Timer.getTimer();
    }

    // set apartment ID
    public void setAptID(String aptID) { this.aptID = aptID; }

    // set payment date
    public void setDate(String date) { this.date = date; }

    // set lessee name
    public void setLesseeName(String lesseeName) { this.lesseeName = lesseeName; }

    // set payment amount
    public void setAmountPaid(double amountPaid) { this.amountPaid = amountPaid; }

    // return payment ID
    public String getPaymentID() { return paymentID; }

    // return complex ID
    public String getComplexID() { return complexID; }

    // return apartment ID
    public String getAptID() { return aptID; }

    // return lease ID
    public String getLeaseID() { return leaseID; }

    // return date of payment
    public String getDate() { return date; }

    // return lessee name
    public String getLesseeName() { return lesseeName; }

    // return payment amount
    public double getAmountPaid() { return amountPaid; }

    // return whether it is a late payment or not
    public String getLateOrNot() { return lateOrNot; }
}
