package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

public class MainPage {
    private static final String URL = "https://mail.tut.by";
    private WebDriver driver;

    @FindBy(xpath = "//input[@id='Username']")
    private WebElement loginInput;

    @FindBy(xpath = "//input[@id='Password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//input[contains(@class, 'loginButton')]")
    private WebElement loginButton;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void navigateTo() {
        driver.get(URL);
    }

    public void logIn(String login, String password) {
        navigateTo();
        loginInput.sendKeys(login);
        passwordInput.sendKeys(password);
        loginButton.click();
        Reporter.log("User is logged in with login " + login + " and password " + password);
    }
}
