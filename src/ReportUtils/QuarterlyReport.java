package ReportUtils;

/*
* This class is used to help generate the Quarterly Report.
* */

import DAO.ReportDaoImpl;

import java.util.List;

public class QuarterlyReport {
    private final String complexID; // complex ID
    private final String[] complexInfo; // basic information of the complex, such as the number of apartments
    private final String quarter; // current quarter
    private final String[] dates; // the start date and the end date of the current quarter
    private final int[] revenue; // paid rent and uncovered rent
    private int[] expenseInfo; // expense amount, including utilities, repairs, insurance, and cleaning
    private int maintenanceExpense; // maintenance expense
    private int wage; // wage of the manager
    private int totalExpense; // total expense of the current quarter
    private final ReportDaoImpl dao;

    public QuarterlyReport(String complexID, String quarter, String[] dates) {
        this.complexID = complexID;
        this.quarter = quarter;
        this.dates = dates;
        dao = new ReportDaoImpl();
        complexInfo = new String[4];
        setComplexInfo();
        revenue = new int[2];
        setTotalRent();
        setUncoveredRent();
        expenseInfo = new int[4];
        setExpenseInfo();
        setMaintenanceExpense();
        setWage();
        setTotalExpense();
    }

    // get basic information of the complex from the COMPLEX table in the database
    public void setComplexInfo() {
        String[] info = dao.getComplexInfo(complexID);
        double totalAptNum = Double.parseDouble(info[1]);
        double occupiedAptNum = Double.parseDouble(info[2]);
        int occupancyRate = (int) (occupiedAptNum / totalAptNum * 100);
        System.arraycopy(info, 0, complexInfo, 0, 3);
        complexInfo[3] = occupancyRate + "%";
    }

    // get the total paid rent amount of the complex from the PAYMENT table int the database
    public void setTotalRent() {
        List<PaymentReportDetail> detailList = dao.getPaymentReportDetail(complexID, dates);
        for (PaymentReportDetail detail : detailList) { revenue[0] += (int) detail.getAmount(); }
    }

    // get the uncovered rent of the complex from the UNCOVERED_RENT table in the database
    public void setUncoveredRent() { revenue[1] = dao.getTotalUncoveredRent(complexID, quarter); }

    // get the expense amount of the complex from the EXPENSE table in the database
    public void setExpenseInfo() { expenseInfo = dao.getExpenseInfo(complexID, quarter); }

    // get the maintenance expense from the MAINTENANCE table in the database
    public void setMaintenanceExpense() { maintenanceExpense = dao.getTotalMaintenanceExpense(complexID, dates); }

    // get the monthly wage of the manager from the MANAGER table in the database
    public void setWage() { wage = dao.getWage(complexID) * 3; }

    // calculate the total expense
    public void setTotalExpense() {
        for (int expense : expenseInfo) { totalExpense += expense; }
        totalExpense += maintenanceExpense;
        totalExpense += wage;
    }

    // return the information of the complex
    public String[] getComplexInfo() { return complexInfo; }

    // return the rents of the complex
    public int[] getRevenue() { return revenue; }

    // return the expenses (utilities, repairs, insurance, cleaning) of the complex
    public int[] getExpenseInfo() { return expenseInfo; }

    // return the maintenance expense of the complex
    public int getMaintenanceExpense() { return maintenanceExpense; }

    // return the monthly wage of the manager
    public int getWage() { return wage; }

    // return the total expense of the current quarter in the complex
    public int getTotalExpense() { return totalExpense; }
}
