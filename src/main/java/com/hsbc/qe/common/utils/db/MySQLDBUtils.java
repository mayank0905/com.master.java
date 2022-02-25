package com.hsbc.qe.common.utils.db;

import org.jooq.impl.DSL;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import static com.hsbc.qe.ui.config.ConfigurationManager.getConfiguration;


public class MySQLDBUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(MySQLDBUtils.class);
    private static final String dbHost = getConfiguration().dbHost();
    private static final String dbName = getConfiguration().dbName();
    private static final String url = "jdbc:mysql://" + dbHost + ":3306/" + dbName;
    private static final String dbUser = getConfiguration().dbUser();
    private static final String dbPassword = getConfiguration().dbPassword();
    private static Connection con = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    public static Connection connectToDB() {
        LOGGER.info("Connect to DB " + url + " by user " + dbUser);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, dbUser, dbPassword);
            LOGGER.info("Connection to DB successful!");
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        } catch (SQLException sqlEx) {
            LOGGER.error("Connection to DB failed!\n" + sqlEx.getMessage());
        }

        return con;
    }

    public static void closeConnection() {
        if (con != null) {
            try {
                con.close();
                LOGGER.info("Connection to DB closed successfully");
            } catch (SQLException se) {
                LOGGER.error("Connection to DB was not closed. Reason:\n" + se.getMessage());
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
                LOGGER.info("Statement closed successfully");
            } catch (SQLException se) {
                LOGGER.error("Statement was not closed. Reason:\n" + se.getMessage());
            }
        }

        if (rs != null) {
            try {
                rs.close();
                LOGGER.info("ResultSet closed successfully");
            } catch (SQLException se) {
                LOGGER.error("ResultSet was not closed. Reason:\n" + se.getMessage());
            }
        }
    }

    public static void createTable(String query) {
        try {
            stmt = connectToDB().prepareStatement(query);
            LOGGER.info("Send request to DB: " + query);
            stmt.executeUpdate(query);
            LOGGER.info("Table was created successfully");
        } catch (SQLException se) {
            LOGGER.error("Table was not created. Reason:\n" + se.getMessage());
        }
    }

    public static void dropTable(String tableName) {
        String query = "DROP TABLE " + tableName;
        try {
            stmt = connectToDB().prepareStatement(query);
            LOGGER.info("Send request to DB: " + query);
            stmt.executeUpdate(query);
            LOGGER.info("Table was deleted successfully");
        } catch (SQLException se) {
            LOGGER.error("Table was not deleted. Reason:\n" + se.getMessage());
        }
    }

    public static ResultSet selectFromTable(String query) {
        try {
            stmt = connectToDB().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            LOGGER.info("Send request to DB: " + query);
            rs = stmt.executeQuery(query);
            rs.next();
            addSQLRequestResultsToLog(query);
        } catch (SQLException se) {
            LOGGER.error(se.getMessage());
        }
        return rs;
    }

    private static void addSQLRequestResultsToLog(String query) {
        StringBuilder builder = new StringBuilder();
        DSL.using(connectToDB()).fetchStream(query)
                .forEach(r -> builder.append(r.format()));
        LOGGER.info("Request results:\n" + builder);
    }

    public static void insertIntoTable(String query) {
        try {
            stmt = connectToDB().createStatement();
            LOGGER.info("Send request to DB: " + query);
            stmt.executeUpdate(query);
            LOGGER.info("New data was inserted into table successfully");
        } catch (SQLException se) {
            LOGGER.error("New data was not inserted into table. Reason:\n" + se.getMessage());
        }
    }

    public static void updateInTable(String query) {
        try {
            stmt = connectToDB().createStatement();
            LOGGER.info("Send request to DB: " + query);
            stmt.executeUpdate(query);
            LOGGER.info("Data in table was updated successfully");
        } catch (SQLException se) {
            LOGGER.error("Data in table was not updated. Reason:\n" + se.getMessage());
        }
    }

    public static void deleteFromTable(String query) {
        try {
            LOGGER.info("Send request to DB: " + query);
            stmt = connectToDB().createStatement();
            stmt.executeUpdate(query);
            LOGGER.info("Data from table was deleted successfully");
        } catch (SQLException se) {
            LOGGER.error("Data from table was not deleted. Reason:\n" + se.getMessage());
        }
    }
}
