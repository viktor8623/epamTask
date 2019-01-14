package pages;

import model.Email;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import java.util.List;


public class MailBoxPage {
    private WebDriver driver;

    @FindBy(xpath = "//a[contains(@class, 'mail-FolderList-Item_inbox')]")
    private WebElement inboxFolder;

    @FindBy(xpath = "//a[@href='#sent']")
    private WebElement sentFolder;

    @FindBy(xpath = "//a[contains(@class, 'mail-MessageSnippet')]")
    private List<WebElement> messagesList;

    @FindBy(xpath = "//div[@class='mail-User-Name']")
    private WebElement userName;

    @FindBy(xpath = "//a[text()='Выйти из сервисов Яндекса']")
    private WebElement logOutOption;

    public MailBoxPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    private void goToSentFolder() {
        WebElement firstMessage = messagesList.get(0);
        sentFolder.click();
        waitForDataUpdating(firstMessage);
    }

    private void waitForDataUpdating(WebElement firstMessage) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.stalenessOf(firstMessage));
    }

    private String getSubject(WebElement message) {
        return message.findElement(By.xpath(".//span[contains(@class, 'js-message-snippet-subject')]/span[1]"))
                .getText();
    }

    public void verifySending(Email email) {
        goToSentFolder();
        checkFirstEmailSubject(email);
        Reporter.log("Verification of sending is passed.");
    }

    public void verifyReceiving(Email email) {
        checkFirstEmailSubject(email);
        Reporter.log("Verification of receiving is passed.");
    }

    private void checkFirstEmailSubject(Email email) {
        String topEmailSubject = getSubject(messagesList.get(0));
        Assert.assertEquals(topEmailSubject, email.getSubject());
    }

    public void openEmail(int index) {
        messagesList.get(index).click();
        Reporter.log("Email with index " + index + " has been opened.");
    }

    public void logOut() {
        userName.click();
        logOutOption.click();
        Reporter.log("User has been logged out.");
    }
}
