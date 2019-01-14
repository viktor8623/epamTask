package dataHelpers;

import model.Email;
import org.testng.Reporter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DBHelper extends DataManager {
    private static final String URL = "jdbc:mysql://localhost:3306/tutby?";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";

    private ResultSet getResultSet(String query) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public String getPassword(String login) {
        String query = "SELECT password FROM credentials WHERE login = \"" + login + "\"";
        String password = "";
        try {
            ResultSet resultSet = getResultSet(query);
            resultSet.next();
            password = resultSet.getString("password");
            resultSet.close();
            Reporter.log("Password has been read from DB.");
        } catch (SQLException e) {
            e.printStackTrace();
            Reporter.log("Enable to read password from DB.");
        }
        return password;
    }

    public List<Email> getEmails() {
        String query = "SELECT * FROM emails";
        ArrayList<Email> emails = new ArrayList<>();
        try {
            ResultSet resultSet = getResultSet(query);
            while (resultSet.next()) {
                Email email = Email.newEntity()
                        .withId(resultSet.getString("id"))
                        .withFrom(resultSet.getString("from"))
                        .withTo(resultSet.getString("to"))
                        .withCc(resultSet.getString("cc"))
                        .withBcc(resultSet.getString("bcc"))
                        .withSubject(resultSet.getString("subject"))
                        .withText(resultSet.getString("text"))
                        .build();
                emails.add(email);
            }
            resultSet.close();
            Reporter.log("EmailList has been read from DB.");
        } catch (SQLException e) {
            e.printStackTrace();
            Reporter.log("Enable to read emailList from DB.");
        }
        return emails;
    }
}
