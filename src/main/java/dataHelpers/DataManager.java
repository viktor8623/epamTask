package dataHelpers;

import model.Email;

import java.util.List;

public abstract class DataManager {

    public abstract String getPassword(String login);

    public abstract List<Email> getEmails();
}
