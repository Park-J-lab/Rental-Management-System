package DAO;

/*
* This class implements the methods declared in the ReportDao interface.
* */

import ReportUtils.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportDaoImpl implements ReportDao {

    // add an apartment to the complex
    @Override
    public void addApt(Apartment apt) {
        Connection conn = null;
        PreparedStatement ptmt = null;

        try {
            conn = DbUtil.getConnection();

            // set the statement structure to insert a new record
            String insertSql = "INSERT INTO APARTMENT " +
                    "(ComplexID, AptID, Area, Rent, Fine, Leased_Or_Not) " +
                    "VALUES (" +
                    "?,?,?,?,?,?)";
            ptmt = conn.prepareStatement(insertSql);

            // complete the statement
            ptmt.setString(1, apt.getComplexID());
            ptmt.setString(2, apt.getAptID());
            ptmt.setDouble(3, apt.getArea());
            ptmt.setDouble(4, apt.getRent());
            ptmt.setDouble(5, apt.getFine());
            ptmt.setString(6, apt.getLeasedOrNot());

            ptmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                DbUtil.closeAll(conn, ptmt, null);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    // get the IDs of the apartments that have not been leased yet
    @Override
    public List<String> getAvailableApt(String complexID) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            conn = DbUtil.getConnection();
            String selectSql = "SELECT AptID FROM APARTMENT WHERE ComplexID = '" + complexID +
                    "' AND Leased_Or_Not = 'No'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(selectSql);
            List<String> aptIDList = new ArrayList<>();
            while (rs.next()) { aptIDList.add(rs.getString("AptID")); }
            return aptIDList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                DbUtil.closeAll(conn, stmt, rs);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    // get the rent of a particular apartment
    @Override
    public double getAptRent(String complexID, String aptID) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            conn = DbUtil.getConnection();
            String selectSql = "SELECT Rent FROM APARTMENT WHERE ComplexID = '" + complexID +
                    "' AND AptID = '" + aptID + "'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(selectSql);
            if (rs.next()) { return rs.getDouble("Rent"); }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                DbUtil.closeAll(conn, stmt, rs);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return 2500;
    }

    // get the area of a particular apartment
    @Override
    public double getAptArea(String complexID, String aptID) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            conn = DbUtil.getConnection();
            String selectSql = "SELECT Area FROM APARTMENT WHERE ComplexID = '" + complexID +
                    "' AND AptID = '" + aptID + "'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(selectSql);
            if (rs.next()) { return rs.getDouble("Area"); }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                DbUtil.closeAll(conn, stmt, rs);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return 90;
    }

    // set the leased-status of the apartment to 'yes' once it is leased
    @Override
    public void setLeasedStatusToYes(String complexID, String aptID) {
        Connection conn = null;
        Statement stmt = null;
        try{
            conn = DbUtil.getConnection();
            String updateSql = "UPDATE APARTMENT SET Leased_Or_Not = 'Yes' WHERE ComplexID = '" + complexID +
                    "' AND AptID = '" + aptID + "'";
            stmt = conn.createStatement();
            stmt.executeUpdate(updateSql);
            updateOccupiedNum(complexID);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                DbUtil.closeAll(conn, stmt, null);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    // update the number of occupied apartments in the complex once there is a new lease
    @Override
    public void updateOccupiedNum(String complexID) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int preNum = 0;

        try{
            conn = DbUtil.getConnection();
            String selectSql = "SELECT Leased_Apartment_Num FROM COMPLEX WHERE ComplexID = '" + complexID + "'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(selectSql);

            if (rs.next()) { preNum = rs.getInt("Leased_Apartment_Num"); }

            preNum++;
            String updateSql = "UPDATE COMPLEX SET Leased_Apartment_Num = " + preNum +
                    " WHERE ComplexID = '" + complexID + "'";
            stmt.executeUpdate(updateSql);

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                DbUtil.closeAll(conn, stmt, rs);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    // add a lease to the table
    @Override
    public void addLease(LeaseDetail detail) {
        Connection conn = null;
        PreparedStatement ptmt = null;

        try {
            conn = DbUtil.getConnection();

            // delete the previous record if there exists one
            deleteLease(detail);

            // set the statement structure to insert a new record
            String insertSql = "INSERT INTO LEASE " +
                    "(LeaseID, ComplexID, AptID, Lessee_Name, Start_Date, End_Date, Rent, Deposit) " +
                    "VALUES (" +
                    "?,?,?,?,?,?,?,?)";
            ptmt = conn.prepareStatement(insertSql);

            // complete the statement
            ptmt.setString(1, detail.getLeaseID());
            ptmt.setString(2, detail.getComplexID());
            ptmt.setString(3, detail.getAptID());
            ptmt.setString(4, detail.getLesseeName());
            ptmt.setString(5, detail.getStartDate());
            ptmt.setString(6, detail.getEndDate());
            ptmt.setDouble(7, detail.getRentAmount());
            ptmt.setDouble(8, detail.getDeposit());

            ptmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                DbUtil.closeAll(conn, ptmt, null);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }

    }

    // delete a lease
    @Override
    public void deleteLease(LeaseDetail detail) {
        Connection conn = null;
        Statement stmt = null;
        try{
            conn = DbUtil.getConnection();
            String deleteSql = "DELETE FROM LEASE WHERE ComplexID = '" + detail.getComplexID() +
                    "' AND AptID = '" + detail.getAptID() + "'";
            stmt = conn.createStatement();
            stmt.executeUpdate(deleteSql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                DbUtil.closeAll(conn, stmt, null);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    // get the IDs of the apartments that have been leased
    @Override
    public List<String> getLeasedAptIDs(String complexID) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            conn = DbUtil.getConnection();
            String selectSql = "SELECT AptID FROM LEASE WHERE ComplexID = '" + complexID + "'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(selectSql);
            List<String> aptIDList = new ArrayList<>();
            while (rs.next()) { aptIDList.add(rs.getString("AptID")); }
            Collections.sort(aptIDList);
            return aptIDList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                DbUtil.closeAll(conn, stmt, rs);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    // get the ID of a particular lease
    @Override
    public String getLeaseIDForPayment(String complexID, String aptID) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            conn = DbUtil.getConnection();
            String selectSql = "SELECT LeaseID FROM LEASE WHERE ComplexID = '" + complexID +
                    "' AND AptID = '" + aptID + "'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(selectSql);
            if (rs.next()) { return rs.getString("LeaseID"); }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                DbUtil.closeAll(conn, stmt, rs);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return "";
    }

    // add a new payment
    @Override
    public void addPayment(PaymentDetail detail) {
        Connection conn = null;
        PreparedStatement ptmt = null;

        try {
            conn = DbUtil.getConnection();

            // set the statement structure to insert a new record
            String insertSql = "INSERT INTO PAYMENT " +
                    "(PaymentID, ComplexID, AptID, LeaseID, Name, Date, Amount_Paid, Late_Or_Not) " +
                    "VALUES (" +
                    "?,?,?,?,?,date('now','start of day'),?,?)";
            ptmt = conn.prepareStatement(insertSql);

            // complete the statement
            ptmt.setString(1, detail.getPaymentID());
            ptmt.setString(2, detail.getComplexID());
            ptmt.setString(3, detail.getAptID());
            ptmt.setString(4, detail.getLeaseID());
            ptmt.setString(5, detail.getLesseeName());
            ptmt.setDouble(6, detail.getAmountPaid());
            ptmt.setString(7,detail.getLateOrNot());

            ptmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                DbUtil.closeAll(conn, ptmt, null);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    // add a new uncovered rent
    @Override
    public void addUncoveredRent(UncoveredRentDetail detail) {
        Connection conn = null;
        PreparedStatement ptmt = null;

        try {
            conn = DbUtil.getConnection();

            // set the statement structure to insert a new record
            String insertSql = "INSERT INTO UNCOVERED_RENT " +
                    "(ComplexID, AptID, Amount, Year, Quarter) " +
                    "VALUES (" +
                    "?,?,?,?,?)";
            ptmt = conn.prepareStatement(insertSql);

            // complete the statement
            ptmt.setString(1, detail.getComplexID());
            ptmt.setString(2, detail.getAptID());
            ptmt.setDouble(3, detail.getAmount());
            ptmt.setString(4, detail.getYear());
            ptmt.setString(5, detail.getQuarter());

            ptmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                DbUtil.closeAll(conn, ptmt, null);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    // add a new maintenance request
    @Override
    public void addMaintenance(MaintenanceDetail detail) {
        Connection conn = null;
        PreparedStatement ptmt = null;

        try {
            conn = DbUtil.getConnection();

            // set the statement structure to insert a new record
            String insertSql = "INSERT INTO MAINTENANCE " +
                    "(MaintenanceID, ComplexID, AptID, Occurrence_Date, Problem, Type, " +
                    "Resolution, Resolution_Date, Expense) " +
                    "VALUES (" +
                    "?,?,?,?,?,?,?,?,?)";
            ptmt = conn.prepareStatement(insertSql);

            // complete the statement
            ptmt.setString(1, detail.getMaintenanceID());
            ptmt.setString(2, detail.getComplexID());
            ptmt.setString(3, detail.getAptID());
            ptmt.setString(4, detail.getOccurDate());
            ptmt.setString(5, detail.getProblem());
            ptmt.setString(6, detail.getType());
            ptmt.setString(7, detail.getResolution());
            ptmt.setString(8, detail.getResDate());
            ptmt.setDouble(9, detail.getExpense());

            ptmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                DbUtil.closeAll(conn, ptmt, null);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    // update the expense (utilities, repairs, insurance, new tenant cleaning)
    @Override
    public void updateExpense(ExpenseDetail detail) {
        Connection conn = null;
        PreparedStatement ptmt = null;
        ResultSet rs;

        try {
            conn = DbUtil.getConnection();

            // find if there is already a record
            String selectSql = "SELECT Expense FROM EXPENSE WHERE ComplexID = ? AND Expense_Type = ? AND" +
                    " Year = ? AND Quarter = ?";
            ptmt = conn.prepareStatement(selectSql);

            // complete the statement
            ptmt.setString(1, detail.getComplexID());
            ptmt.setString(2, detail.getType());
            ptmt.setString(3, detail.getYear());
            ptmt.setString(4, detail.getQuarter());
            rs = ptmt.executeQuery();
            if (rs.next()) {
                double curVal = rs.getDouble("EXPENSE") + detail.getAmount();
                String updateSql = "UPDATE EXPENSE SET Expense = ? WHERE ComplexID = ? AND Expense_Type = ? AND" +
                        " Year = ? AND Quarter = ?";
                ptmt = conn.prepareStatement(updateSql);

                // complete the statement
                ptmt.setDouble(1, curVal);
                ptmt.setString(2, detail.getComplexID());
                ptmt.setString(3, detail.getType());
            } else {
                // set the statement structure to update the new record
                String insertSql = "INSERT INTO EXPENSE " +
                        "(ComplexID, Expense_Type, Expense, Year, Quarter) " +
                        "VALUES (" +
                        "?,?,?,?,?)";
                ptmt = conn.prepareStatement(insertSql);

                // complete the statement
                ptmt.setString(1, detail.getComplexID());
                ptmt.setString(2, detail.getType());
                ptmt.setDouble(3, detail.getAmount());

            }
            ptmt.setString(4, detail.getYear());
            ptmt.setString(5, detail.getQuarter());
            ptmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                DbUtil.closeAll(conn, ptmt, null);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    // get the details needed to generate the lease report
    @Override
    public List<LeaseReportDetail> getLeaseReportDetail(String complexID, String[] dates) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            conn = DbUtil.getConnection();
            String selectSql = "SELECT * FROM LEASE WHERE ComplexID = '" + complexID +
                    "' AND Start_Date >= '" + dates[0] +
                    "' AND Start_Date <= '" + dates[1] + "'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(selectSql);
            List<LeaseReportDetail> detailList = new ArrayList<>();

            while (rs.next()) {
                LeaseReportDetail detail = new LeaseReportDetail();
                detail.setLeaseID(rs.getString("LeaseID"));
                detail.setComplexID(rs.getString("ComplexID"));
                detail.setAptID(rs.getString("AptID"));
                detail.setLesseeName(rs.getString("Lessee_Name"));
                detail.setStartDate(rs.getString("Start_Date"));
                detail.setEndDate(rs.getString("End_Date"));
                detail.setRent(rs.getDouble("Rent"));
                detail.setDeposit(rs.getDouble("Deposit"));

                detailList.add(detail);
            }
            return detailList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                DbUtil.closeAll(conn, stmt, rs);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    // get the details needed to generate the payment report
    @Override
    public List<PaymentReportDetail> getPaymentReportDetail(String complexID, String[] dates) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            conn = DbUtil.getConnection();
            String selectSql = "SELECT * FROM PAYMENT WHERE ComplexID = '" + complexID +
                    "' AND Date >= '" + dates[0] +
                    "' AND Date <= '" + dates[1] + "'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(selectSql);
            List<PaymentReportDetail> detailList = new ArrayList<>();

            while (rs.next()) {
                PaymentReportDetail detail = new PaymentReportDetail();
                detail.setPaymentID(rs.getString("PaymentID"));
                detail.setComplexID(rs.getString("ComplexID"));
                detail.setAptID(rs.getString("AptID"));
                detail.setLeaseID(rs.getString("LeaseID"));
                detail.setLesseeName(rs.getString("Name"));
                detail.setDate(rs.getString("Date"));
                detail.setAmount(rs.getDouble("Amount_Paid"));
                detail.setLateOrNot(rs.getString("Late_Or_Not"));

                detailList.add(detail);
            }
            return detailList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                DbUtil.closeAll(conn, stmt, rs);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    // get the details needed to generate the maintenance report
    @Override
    public List<MaintenanceReportDetail> getMaintenanceReportDetail(String complexID, String[] dates) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            conn = DbUtil.getConnection();
            String selectSql = "SELECT * FROM MAINTENANCE WHERE ComplexID = '" + complexID +
                    "' AND Occurrence_Date >= '" + dates[0] +
                    "' AND Occurrence_Date <= '" + dates[1] + "'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(selectSql);
            List<MaintenanceReportDetail> detailList = new ArrayList<>();

            while (rs.next()) {
                MaintenanceReportDetail detail = new MaintenanceReportDetail();
                detail.setComplexID(rs.getString("ComplexID"));
                detail.setAptID(rs.getString("AptID"));
                detail.setOccurDate(rs.getString("Occurrence_Date"));
                detail.setProblem(rs.getString("Problem"));
                detail.setType(rs.getString("Type"));
                detail.setResolution(rs.getString("Resolution"));
                detail.setResDate(rs.getString("Resolution_Date"));
                detail.setExpense(rs.getDouble("Expense"));

                detailList.add(detail);
            }
            return detailList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                DbUtil.closeAll(conn, stmt, rs);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    // get the total uncovered rent in a particular quarter
    @Override
    public int getTotalUncoveredRent(String complexID, String quarter) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int total = 0;

        try{
            conn = DbUtil.getConnection();
            String selectSql = "SELECT Amount FROM UNCOVERED_RENT WHERE ComplexID = '" + complexID +
                    "' AND Quarter = '" + quarter + "'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(selectSql);

            while (rs.next()) { total += (int) rs.getDouble("Amount"); }

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                DbUtil.closeAll(conn, stmt, rs);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return total;
    }

    // get the expense information of a particular quarter
    @Override
    public int[] getExpenseInfo(String complexID, String quarter) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int[] info = new int[4];

        try{
            conn = DbUtil.getConnection();
            stmt = conn.createStatement();

            String selectUtilitiesSql = "SELECT Expense FROM EXPENSE WHERE ComplexID = '" + complexID +
                    "' AND Expense_Type = 'Utilities'" +
                    " AND Quarter = '" + quarter + "'";
            String selectRepairSql = "SELECT Expense FROM EXPENSE WHERE ComplexID = '" + complexID +
                    "' AND Expense_Type = 'Repairs'" +
                    " AND Quarter = '" + quarter + "'";
            String selectInsuranceSql = "SELECT Expense FROM EXPENSE WHERE ComplexID = '" + complexID +
                    "' AND Expense_Type = 'Insurance'" +
                    " AND Quarter = '" + quarter + "'";
            String selectCleaningSql = "SELECT Expense FROM EXPENSE WHERE ComplexID = '" + complexID +
                    "' AND Expense_Type = 'New tenant cleaning'" +
                    " AND Quarter = '" + quarter + "'";

            rs = stmt.executeQuery(selectUtilitiesSql);
            if (rs.next()) { info[0] += (int) rs.getDouble("Expense"); }

            rs = stmt.executeQuery(selectRepairSql);
            if (rs.next()) { info[1] += (int) rs.getDouble("Expense"); }

            rs = stmt.executeQuery(selectInsuranceSql);
            if (rs.next()) { info[2] += (int) rs.getDouble("Expense"); }

            rs = stmt.executeQuery(selectCleaningSql);
            if (rs.next()) { info[3] += (int) rs.getDouble("Expense"); }

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                DbUtil.closeAll(conn, stmt, rs);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return info;
    }

    // get the total maintenance information of a particular quarter
    @Override
    public int getTotalMaintenanceExpense(String complexID, String[] dates) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int total = 0;

        try{
            conn = DbUtil.getConnection();
            String selectSql = "SELECT Expense FROM MAINTENANCE WHERE ComplexID = '" + complexID +
                    "' AND Occurrence_Date >= '" + dates[0] +
                    "' AND Occurrence_Date <= '" + dates[1] + "'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(selectSql);

            while (rs.next()) { total += (int) rs.getDouble("Expense"); }

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                DbUtil.closeAll(conn, stmt, rs);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return total;
    }

    // get the wage of the manager in a particular complex
    @Override
    public int getWage(String complexID) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int wage = 0;

        try{
            conn = DbUtil.getConnection();
            String selectSql = "SELECT Wage FROM MANAGER WHERE ComplexID = '" + complexID + "'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(selectSql);

            while (rs.next()) { wage += (int) rs.getDouble("Wage"); }

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                DbUtil.closeAll(conn, stmt, rs);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return wage;
    }

    // get the information of a particular complex, such as occupancy rate
    @Override
    public String[] getComplexInfo(String complexID) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String[] info = new String[3];
        try{
            conn = DbUtil.getConnection();
            String selectSql = "SELECT Address, Total_Apartment_Num, Leased_Apartment_Num FROM COMPLEX " +
                    "WHERE ComplexID = '" + complexID + "'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(selectSql);
            if (rs.next()) {
                info[0] = rs.getString("Address");
                info[1] = rs.getString("Total_Apartment_Num");
                info[2] = rs.getString("Leased_Apartment_Num");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                DbUtil.closeAll(conn, stmt, rs);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return info;
    }

    // add an manager to a complex if there is no manager
    @Override
    public void addManager(Manager manager) {
        Connection conn = null;
        PreparedStatement ptmt = null;
        try {
            conn = DbUtil.getConnection();

            // set the statement structure to insert a new record
            String sql = "INSERT INTO MANAGER " +
                    "(ManagerID, ComplexID, Password, Name, Phone_number, Wage) " +
                    "VALUES (" +
                    "?,?,?,?,?,?)";
            ptmt = conn.prepareStatement(sql);

            // complete the statement
            ptmt.setString(1, manager.getManagerID());
            ptmt.setString(2, manager.getComplexID());
            ptmt.setString(3, manager.getPassword());
            ptmt.setString(4, manager.getManagerName());
            ptmt.setString(5, manager.getPhoneNum());
            ptmt.setDouble(6, manager.getWage());

            ptmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                DbUtil.closeAll(conn, ptmt, null);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    // verify the manager id and password
    @Override
    public boolean verifyStatus(String id, String providedPwd) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String pwd;

        try{
            conn = DbUtil.getConnection();
            String selectSql = "SELECT Password FROM MANAGER WHERE ManagerID = '" + id + "'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(selectSql);

            if (!rs.next()) { return false; }
            pwd =  rs.getString("Password");
            if (!pwd.equals(providedPwd)) { return false; }

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                DbUtil.closeAll(conn, stmt, rs);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return true;
    }

    // get the ID of the complex which corresponding to a particular manager
    @Override
    public String getComplexID(String managerID) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String complexID = "";
        try {
            conn = DbUtil.getConnection();

            // set the statement to retrieve a new record
            String sql = "SELECT ComplexID FROM MANAGER WHERE ManagerID = '" + managerID + "'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) { complexID = rs.getString("ComplexID"); }

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                DbUtil.closeAll(conn, stmt, rs);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return complexID;
    }
}
