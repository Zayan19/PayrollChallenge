package se.challenge.payroll;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUpgrader {
    String databaseName;
    String user;
    String password;

    public DatabaseUpgrader(String databaseName) throws ClassNotFoundException, SQLException {
        user = "postgres";
        password = "password";
        Class.forName("org.postgresql.Driver");
        this.databaseName = databaseName;
    }

    public void CreateAndUpgradeDatabase() throws SQLException, ClassNotFoundException {


        Connection initialConnection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", user, password);
        Statement statement = initialConnection.createStatement();
        ResultSet databaseCheck = statement.executeQuery(String.format("SELECT datname FROM pg_database where datname='%s'", databaseName));
        if (!databaseCheck.next()) {
            statement.executeUpdate(String.format("CREATE DATABASE %s", databaseName));
        }
        initialConnection.close();

        String url = String.format("jdbc:postgresql://localhost:5432/%s", databaseName);
        Connection connection = DriverManager.getConnection(url, user, password);
        CreatePayrollEntryTable(connection);
        CreateTimeReportEntryTable(connection);

        connection.close();
    }

    private void CreatePayrollEntryTable(Connection connection) throws SQLException {
        String createPayRollEntryTable = "CREATE TABLE IF NOT EXISTS payrollentry ( id SERIAL, employeeid INT NOT NULL, hoursworked FLOAT NOT NULL, date TIMESTAMP NOT NULL, jobgroup CHARACTER(1) )";
        Statement statement = connection.createStatement();
        statement.executeUpdate(createPayRollEntryTable);
    }

    private void CreateTimeReportEntryTable(Connection connection) throws SQLException {
        String timeReportEntryTable = "CREATE TABLE IF NOT EXISTS timereportentry ( id INTEGER UNIQUE NOT NULL)";
        Statement statement = connection.createStatement();
        statement.executeUpdate(timeReportEntryTable);
    }

    public void DropDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", user, password);
        Statement statement = connection.createStatement();
        ResultSet databaseCheck = statement.executeQuery(String.format("SELECT datname FROM pg_database where datname='%s'", databaseName));
        statement.executeUpdate(String.format("DROP DATABASE %s", databaseName));
        connection.close();
    }
}