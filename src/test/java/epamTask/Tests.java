package epamTask;

import dataHelpers.DataManager;
import dataHelpers.DataManagerFactory;
import dataHelpers.DataStorageType;
import mailApiHelpers.EmailSender;
import model.Email;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.EmailPage;
import pages.MailBoxPage;
import pages.MainPage;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Tests {
    private WebDriver driver;
    private MainPage mainPage;
    private MailBoxPage mailBoxPage;
    private EmailPage emailPage;
    private DataManager data;

    @BeforeClass
    public void initDriver() {
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        mainPage = new MainPage(driver);
        mailBoxPage = new MailBoxPage(driver);
        emailPage = new EmailPage(driver);
        data = DataManagerFactory.getDataManager(DataStorageType.CSV);
    }

    @Test
    public void emailTest() {
        Email email = data.getEmails().get(0);
        String sendersLogin = email.getFrom();
        String sendersPassword = data.getPassword(sendersLogin);
        String recipientsLogin = email.getTo();
        String recipientsPassword = data.getPassword(recipientsLogin);
        EmailSender.sendEmail(email, sendersPassword);
        mainPage.logIn(sendersLogin, sendersPassword);
        mailBoxPage.verifySending(email);
        mailBoxPage.logOut();
        mainPage.logIn(recipientsLogin, recipientsPassword);
        mailBoxPage.verifyReceiving(email);
        mailBoxPage.openEmail(0);
        emailPage.verifyEmailDetails(email);
        mailBoxPage.logOut();
    }

    @AfterMethod
    public void takeScreenshotOnFailure(ITestResult result) {
        if(result.getStatus() == ITestResult.FAILURE) {
            try {
                TakesScreenshot screenshot = (TakesScreenshot) driver;
                File source = screenshot.getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(source, new File("./Screenshots/"+ "failed_test.png"));
                Reporter.log("Screenshot has been taken.");
            } catch (Exception ex) {
                Reporter.log("Throwing exception while taking screenshot" + ex.getMessage());
            }
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
