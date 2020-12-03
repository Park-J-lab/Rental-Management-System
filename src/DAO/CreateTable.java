package DAO;

/*
* This class is used to create tables in the database if there does not exist such a table.
* */


import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {

    // create tables if there is no such table

    private static final String CREATE_MANAGER = "CREATE TABLE IF NOT EXISTS MANAGER"
            + "("
            + " ManagerID char(5),"
            + " ComplexID char(5) NOT NULL,"
            + " Password text NOT NULL,"
            + " Name char(30) NOT NULL,"
            + " Phone_number varchar(20) NOT NULL,"
            + " Wage int DEFAULT 16000,"
            + " PRIMARY KEY (ManagerID)"
            + ")";

    private static final String CREATE_COMPLEX = "CREATE TABLE IF NOT EXISTS COMPLEX"
            + "("
            + " ComplexID char(5),"
            + " Address text NOT NULL,"
            + " Total_Apartment_Num int NOT NULL,"
            + " Leased_Apartment_Num int DEFAULT 0,"
            + " PRIMARY KEY (ComplexID)"
            + ")";

    private static final String CREATE_APT = "CREATE TABLE IF NOT EXISTS APARTMENT"
            + "("
            + " ComplexID char(5),"
            + " AptID char(6),"
            + " Area real NOT NULL,"
            + " Rent real NOT NULL,"
            + " Fine real NOT NULL,"
            + " Leased_Or_Not int DEFAULT 0,"
            + " PRIMARY KEY (ComplexID, AptID)"
            + ")";

    private static final String CREATE_TENANT_CHANGES = "CREATE TABLE IF NOT EXISTS TENANT_CHANGES"
            + "("
            + " ComplexID char(5),"
            + " Year text,"
            + " Quarter text,"
            + " Changing_Tenant_Num int DEFAULT 0,"
            + " PRIMARY KEY (ComplexID, Year, Quarter)"
            + ")";

    private static final String CREATE_UNCOVERED_RENT = "CREATE TABLE IF NOT EXISTS UNCOVERED_RENT"
            + "("
            + " ComplexID char(5),"
            + " AptID char(6),"
            + " Amount real NOT NULL,"
            + " Year text,"
            + " Quarter text,"
            + " PRIMARY KEY (ComplexID, AptID, Year, Quarter)"
            + ")";

    private static final String CREATE_EXPENSE = "CREATE TABLE IF NOT EXISTS EXPENSE"
            + "("
            + " ComplexID char(5),"
            + " Expense_Type text NOT NULL,"
            + " Expense real NOT NULL,"
            + " Year text,"
            + " Quarter text,"
            + " PRIMARY KEY (ComplexID, Expense_Type, Year, Quarter)"
            + ")";

    private static final String CREATE_LEASE = "CREATE TABLE IF NOT EXISTS LEASE"
            + "("
            + " LeaseID Text,"
            + " ComplexID char(5) NOT NULL,"
            + " AptID char(6) NOT NULL,"
            + " Lessee_Name Text NOT NULL,"
            + " Start_Date timestamp with time zone NOT NULL,"
            + " End_Date timestamp with time zone NOT NULL,"
            + " Rent real NOT NULL,"
            + " Deposit real DEFAULT 3000,"
            + " PRIMARY KEY (LeaseID)"
            + ")";

    private static final String CREATE_PAYMENT = "CREATE TABLE IF NOT EXISTS PAYMENT"
            + "("
            + " PaymentID Text,"
            + " ComplexID char(5) NOT NULL,"
            + " AptID char(6) NOT NULL,"
            + " LeaseID Text NOT NULL,"
            + " Name Text NOT NULL,"
            + " Date timestamp with time zone NOT NULL,"
            + " Amount_Paid real NOT NULL,"
            + " Late_Or_Not Text,"
            + " PRIMARY KEY (PaymentID)"
            + ")";

    private static final String CREATE_MAINTENANCE = "CREATE TABLE IF NOT EXISTS MAINTENANCE"
            + "("
            + " MaintenanceID Text,"
            + " ComplexID char(5) NOT NULL,"
            + " AptID char(6) NOT NULL,"
            + " Occurrence_Date timestamp with time zone NOT NULL,"
            + " Problem text NOT NULL,"
            + " Type text NOT NULL,"
            + " Resolution text,"
            + " Resolution_Date timestamp with time zone,"
            + " Expense real,"
            + " PRIMARY KEY (MaintenanceID)"
            + ")";

    // create the table
    public static void createTable(Statement statement) throws SQLException {
        statement.execute(CREATE_MANAGER);
        statement.execute(CREATE_COMPLEX);
        statement.execute(CREATE_APT);
//        statement.execute(CREATE_TENANT_CHANGES);
        statement.execute(CREATE_UNCOVERED_RENT);
        statement.execute(CREATE_EXPENSE);
        statement.execute(CREATE_LEASE);
        statement.execute(CREATE_PAYMENT);
        statement.execute(CREATE_MAINTENANCE);
    }
}
