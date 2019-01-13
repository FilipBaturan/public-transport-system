package construction_and_testing.public_transport_system.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static construction_and_testing.public_transport_system.selenium.util.SeleniumProperties.CHROME_DRIVER_PATH;
import static org.testng.AssertJUnit.assertEquals;

public class RegUsersPageTest {

    private WebDriver browser;

    WelcomePage welcomePage;
    RegUsersPage regUsersPage;


    public void logIn()
    {
        welcomePage.setUsername("b");
        welcomePage.setPassword("b");
        welcomePage.getLoginButton().click();
        welcomePage.ensureTLogoutDisplayed();;
    }

    @BeforeMethod
    public void setupSelenium() {
        //instantiate browser
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        browser = new ChromeDriver();
        //maximize window
        browser.manage().window().maximize();
        //navigate
        browser.navigate().to("http://localhost:4200");

        welcomePage = PageFactory.initElements(browser, WelcomePage.class);
        regUsersPage = PageFactory.initElements(browser, RegUsersPage.class);
        logIn();
        navigateRegUsers();
        regUsersPage.ensureRegUsersTableIsDisplayed();
    }

    private void navigateRegUsers() {

        welcomePage.getUsersField().click();
        welcomePage.getRegUsersLink().click();
    }

    @Test
    public void checkUsersTickets()
    {
        assertEquals("http://localhost:4200/registeredUsers", browser.getCurrentUrl());
        regUsersPage.getTicketsLink().click();

        regUsersPage.ensureTitleIsDisplayed();

        assertEquals("http://localhost:4200/userTickets/1", browser.getCurrentUrl());
    }

    @Test
    public void denyTicket()
    {
        assertEquals("http://localhost:4200/registeredUsers", browser.getCurrentUrl());
        regUsersPage.getTicketsLink().click();

        regUsersPage.ensureTitleIsDisplayed();

        assertEquals("http://localhost:4200/userTickets/1", browser.getCurrentUrl());

        regUsersPage.ensureUsersTicketsTableIsDisplayed();

        int sizeBeforeAdding =  regUsersPage.getUsersTicketsTableSize();

        regUsersPage.getDenyButton().click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int sizeAfter = regUsersPage.getUsersTicketsTableSize();
        assertEquals(sizeBeforeAdding, sizeAfter + 1);
    }

    @AfterMethod
    public void closeSelenium() {
        browser.quit();
    }

}
