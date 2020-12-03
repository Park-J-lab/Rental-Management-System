package ReportUtils;

/*
* This class is used to register a new lease, which will be added to the LEASE table in the database.
* */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LeaseDetail {
    private final String complexID; // the id of the complex
    private final String aptID; // the id of the apartment
    private final String leaseID; // the id of the lease
    private final String lesseeName; // the name of the lessee
    private final String startDate; // the start date of the lease
    private String endDate; // the end data of the lease
    private final double rentAmount; // the monthly rent
    private final double deposit; // the deposit
    private final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

    public LeaseDetail(String complexID, String aptID, String lesseeName, String startDate, String period,
                       double rentAmount, double deposit) {
        this.complexID = complexID;
        this.aptID = aptID;
        this.lesseeName = lesseeName;
        this.startDate = startDate;
        this.rentAmount = rentAmount;
        this.deposit = deposit;

        setEndDate(startDate, period);
        leaseID = complexID + "-" + aptID + "-" + Timer.getTimer();
    }

    // set the end date according the start date and the lease period
    public void setEndDate(String startDate, String period) {
        Date dt= null;
        try {
            dt = sdf.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar startD = Calendar.getInstance();
        assert dt != null;
        startD.setTime(dt);
        if (period.equals("6 month")) {
            startD.add(Calendar.MONTH,6);
        } else {
            startD.add(Calendar.MONTH,12);
        }

        Date dt1= startD.getTime();
        endDate = sdf.format(dt1);
    }

    // return lease ID
    public String getLeaseID() { return leaseID; }

    // return complex ID
    public String getComplexID() { return complexID; }

    // return apartment ID
    public String getAptID() { return aptID; }

    // return lessee name
    public String getLesseeName() { return lesseeName; }

    // return start date
    public String getStartDate() { return startDate; }

    // return end date
    public String getEndDate() { return endDate; }

    // return rent amount
    public double getRentAmount() { return rentAmount; }

    // return deposit
    public double getDeposit() { return deposit; }
}
