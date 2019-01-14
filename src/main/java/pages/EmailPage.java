package pages;

import model.Email;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import static org.testng.Assert.assertEquals;


public class EmailPage {

    @FindBy(xpath = "//div[contains(@class, 'Subject_message')]")
    private WebElement subjectField;

    @FindBy(xpath = "//div[contains(@class, 'mail-Message-Body-Content')]")
    private WebElement textField;

    public EmailPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void verifyEmailDetails(Email email) {
        String actualSubject = subjectField.getText();
        String actualText = textField.getText();
        assertEquals(actualSubject, email.getSubject());
        assertEquals(actualText, email.getText());
        Reporter.log("Verification of email's subject and text is passed.");
    }
}
