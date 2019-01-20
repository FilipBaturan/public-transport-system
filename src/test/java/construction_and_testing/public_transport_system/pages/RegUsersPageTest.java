package construction_and_testing.public_transport_system.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static construction_and_testing.public_transport_system.pages.util.SeleniumProperties.CHROME_DRIVER_PATH;
import static org.testng.AssertJUnit.assertEquals;

public class RegUsersPageTest {

    private WebDriver browser;

    private WelcomePage welcomePage;

    private NavigationBarPage navigationBarPage;

    private RegUsersPage regUsersPage;


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
        navigationBarPage = PageFactory.initElements(browser, NavigationBarPage.class);
        regUsersPage = PageFactory.initElements(browser, RegUsersPage.class);

        welcomePage.ensureIsDisplayed();
        welcomePage.login("b", "b");

        navigationBarPage.getUsersField().click();
        navigationBarPage.getRegUsersLink().click();
        regUsersPage.ensureRegUsersTableIsDisplayed();
    }

    @Test
    public void checkUsersTickets() {
        assertEquals("http://localhost:4200/registeredUsers", browser.getCurrentUrl());
        regUsersPage.getTicketsLink().click();

        regUsersPage.ensureTitleIsDisplayed();

        assertEquals("http://localhost:4200/userTickets/1", browser.getCurrentUrl());
    }

    @Test
    public void denyTicket() {
        assertEquals("http://localhost:4200/registeredUsers", browser.getCurrentUrl());
        regUsersPage.getTicketsLink().click();

        regUsersPage.ensureTitleIsDisplayed();

        assertEquals("http://localhost:4200/userTickets/1", browser.getCurrentUrl());

        regUsersPage.ensureUsersTicketsTableIsDisplayed();

        int sizeBeforeAdding = regUsersPage.getUsersTicketsTableSize();

        regUsersPage.getDenyButton().click();

        regUsersPage.ensureUsersTicketsTableIsDisplayed();

        int sizeAfter = regUsersPage.getUsersTicketsTableSize();
        assertEquals(sizeBeforeAdding, sizeAfter + 1);
    }

    @AfterMethod
    public void closeSelenium() {
        browser.quit();
    }

}
