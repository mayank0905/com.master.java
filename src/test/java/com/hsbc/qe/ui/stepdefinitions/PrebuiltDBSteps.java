package com.hsbc.qe.ui.stepdefinitions;

import com.hsbc.qe.common.utils.db.MySQLDBUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class PrebuiltDBSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrebuiltBrowserSteps.class);

    @Given("I connect to the database")
    public void connectToDatabase() {
        MySQLDBUtils.connectToDB();
    }

    @When("I execute the UPDATE SQL query to update the database table")
    public void runUPDATESQLQuery(DataTable dataTable) {
        List<List<String>> data = dataTable.asLists();
        String sqlQuery = data.get(1).get(0);
        MySQLDBUtils.updateInTable(sqlQuery);
    }

    @Then("I execute the SELECT SQL query to check that the table is updated")
    public void runSELECTSQLQuery(DataTable dataTable) throws SQLException {
        List<List<String>> data = dataTable.asLists();
        String sqlQuery = data.get(1).get(0);
        String columnName = data.get(2).get(0);
        String expectedValue = data.get(3).get(0);
        ResultSet resultSet = MySQLDBUtils.selectFromTable(sqlQuery);
        String actualValue = resultSet.getString(columnName);
        assertThat("Table column data is not matching", actualValue, is(equalTo(expectedValue)));
    }

    @Given("I close the database connection")
    public void closeTheDatabaseConnection() {
        MySQLDBUtils.closeConnection();
    }
}
