package construction_and_testing.public_transport_system.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static construction_and_testing.public_transport_system.pages.util.SeleniumProperties.CHROME_DRIVER_PATH;
import static org.testng.AssertJUnit.assertEquals;

public class SignUpPageTest {

    private WebDriver browser;

    private SignUpPage signUpPage;

    private WelcomePage welcomePage;


    @BeforeMethod
    public void startSelenium() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.navigate().to("http://localhost:4200");

        welcomePage = PageFactory.initElements(browser, WelcomePage.class);
        signUpPage = PageFactory.initElements(browser, SignUpPage.class);

        welcomePage.ensureIsDisplayed();
        welcomePage.getAccountSidebar().click();
        welcomePage.ensureIsDisplayed();
        welcomePage.getButtonSignUp().click();

        assertEquals("http://localhost:4200/signup", browser.getCurrentUrl());
    }

    @Test
    public void successfulRegistration() {
        signUpPage.ensureIsDisplayed();
        signUpPage.setNameInput("Name1");
        signUpPage.setLastNameInput("LastName1");
        signUpPage.setUsernameInput("Username1");
        signUpPage.setPasswordInput("Password1");
        signUpPage.setEmailInput("Email1");
        signUpPage.setTelephoneInput("Telephone1");
        signUpPage.getSignUpButton().click();
        welcomePage.ensureIsDisplayed();
        assertEquals("http://localhost:4200/welcome", browser.getCurrentUrl());
    }

    @AfterMethod
    public void closeSelenium() {
        browser.close();
    }
}
