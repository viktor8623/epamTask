package mailApiHelpers;

import model.Email;
import org.testng.Reporter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class EmailSender {

    public static void sendEmail(Email email, String password) {

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src\\main\\resources\\mail.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
            Reporter.log("Error occurred while trying to read email properties.");
        }

        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email.getFrom(), password);
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(email.getFrom()));
            InternetAddress[] addressTo = {new InternetAddress(email.getTo())};
            InternetAddress[] addressCc = {new InternetAddress(email.getCc())};
            InternetAddress[] addressBcc = {new InternetAddress(email.getBcc()), new InternetAddress(email.getFrom())};
            msg.setRecipients(Message.RecipientType.TO, addressTo);
            msg.setRecipients(Message.RecipientType.CC, addressCc);
            msg.setRecipients(Message.RecipientType.BCC, addressBcc);
            msg.setSubject(email.getSubject());
            msg.setSentDate(new Date());
            msg.setText(email.getText());
            Transport.send(msg);
            Reporter.log("Email has been sent. " + email);
        } catch (MessagingException ex) {
            ex.printStackTrace();
            Reporter.log("Error occurred while trying to send email.");
        }
    }
}