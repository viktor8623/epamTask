package dataHelpers;

import model.Email;
import org.testng.Reporter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVParser extends DataManager {
    private static final String URI_FOR_CREDENTIALS = "src\\main\\resources\\Credentials.csv";
    private static final String URI_FOR_EMAILS = "src\\main\\resources\\Emails.csv";
    private static final String CSV_SPLIT_BY = ",";

    private static ArrayList<String> readFile(String csvFile) {
        ArrayList<String> entries = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line = "";
            br.readLine();
            while((line = br.readLine()) != null) {
                entries.add(line);
            }
            Reporter.log("Test data has been successfully read from CSV file " + csvFile);
        } catch (IOException e) {
            e.printStackTrace();
            Reporter.log("Error occurred while trying to read CSV file with test data.");
        }
        return entries;
    }

    public String getPassword(String login) {
        ArrayList<String> entries = readFile(URI_FOR_CREDENTIALS);
        String password = "";
        for(String entry : entries) {
            String[] credentials = entry.split(CSV_SPLIT_BY);
            if(credentials[0].equals(login)) {
                password = credentials[1];
            }
        }
        return password;
    }

    public List<Email> getEmails() {
        ArrayList<String> entries = readFile(URI_FOR_EMAILS);
        List<Email> emails = new ArrayList<>();
        for(String entry : entries) {
            String[] emailOptions = entry.split(CSV_SPLIT_BY);
            Email email = Email.newEntity()
                    .withId(emailOptions[0])
                    .withFrom(emailOptions[1])
                    .withTo(emailOptions[2])
                    .withCc(emailOptions[3])
                    .withBcc(emailOptions[4])
                    .withSubject(emailOptions[5])
                    .withText(emailOptions[6])
                    .build();
            emails.add(email);
        }
        return emails;
    }
}
