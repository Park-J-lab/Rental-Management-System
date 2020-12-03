package DAO;

/*
* This class is the interface of DAO, all methods regarding sql operation are defined here.
* */

import ReportUtils.*;

import java.util.List;

public interface ReportDao {

    // add an apartment to the complex
    void addApt(Apartment apt) throws Exception;

    // get the IDs of the apartments that have not been leased yet
    List<String> getAvailableApt(String complexID) throws Exception;

    // get the rent of a particular apartment
    double getAptRent(String complexID, String aptID) throws Exception;

    // get the area of a particular apartment
    double getAptArea(String complexID, String aptID) throws Exception;

    // set the leased-status of the apartment to 'yes' once it is leased
    void setLeasedStatusToYes(String complexID, String aptID) throws Exception;

    // update the number of occupied apartments in the complex once there is a new lease
    void updateOccupiedNum(String complexID) throws Exception;

    // add a lease to the table
    void addLease(LeaseDetail detail) throws Exception;

    // delete a lease from the table
    void deleteLease(LeaseDetail detail) throws Exception;

    // get the IDs of the apartments that have been leased
    List<String> getLeasedAptIDs(String complexID) throws Exception;

    // get the ID of a particular lease
    String getLeaseIDForPayment(String complexID, String aptID) throws Exception;

    // add a new payment
    void addPayment(PaymentDetail detail) throws Exception;

    // add a new uncovered rent
    void addUncoveredRent(UncoveredRentDetail detail) throws Exception;

    // add a new maintenance request
    void addMaintenance(MaintenanceDetail detail) throws Exception;

    // update the expense (utilities, repairs, insurance, new tenant cleaning)
    void updateExpense(ExpenseDetail detail) throws Exception;

    // get the details needed to generate the lease report
    List<LeaseReportDetail> getLeaseReportDetail(String complexID, String[] dates) throws Exception;

    // get the details needed to generate the payment report
    List<PaymentReportDetail> getPaymentReportDetail(String complexID, String[] dates) throws Exception;

    // get the details needed to generate the maintenance report
    List<MaintenanceReportDetail> getMaintenanceReportDetail(String complexID, String[] dates) throws Exception;

    // get the total uncovered rent in a particular quarter
    int getTotalUncoveredRent(String complexID, String quarter) throws Exception;

    // get the expense information of a particular quarter
    int[] getExpenseInfo(String complexID, String quarter) throws Exception;

    // get the total maintenance information of a particular quarter
    int getTotalMaintenanceExpense(String complexID, String[] dates) throws Exception;

    // get the wage of the manager in a particular complex
    int getWage(String complexID) throws Exception;

    // get the information of a particular complex, such as occupancy rate
    String[] getComplexInfo(String complexID) throws Exception;

    // add an manager to a complex if there is no manager
    void addManager(Manager manager) throws Exception;

    // verify the manager id and password
    boolean verifyStatus(String id, String providedPwd) throws Exception;

    // get the ID of the complex which corresponding to a particular manager
    String getComplexID(String managerID) throws Exception;
}
