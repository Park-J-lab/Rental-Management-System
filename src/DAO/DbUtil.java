package DAO;

/*
* This class is used to deal with some frequently-used functions regarding database connection
* */

import java.sql.*;

public class DbUtil {
    private final static String driver = "org.sqlite.JDBC";
    private final static String url = "jdbc:sqlite:src/database/Safehome_Student_Residences.db";

    // initialize the driver, construct a connection to the database, and create a table
    static {
        System.out.println("Connecting...");
        try {
            Class.forName(driver);
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            CreateTable.createTable(stmt);
            System.out.println("Successfully Connected!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection failed :(");
            e.printStackTrace();
        }
    }

    // get connection to Loan.db
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    // close the resources
    public static void closeAll(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
        if (rs!=null) { rs.close(); }
        if (stmt!=null) { stmt.close(); }
        if (conn!=null) { conn.close(); }
    }
}
