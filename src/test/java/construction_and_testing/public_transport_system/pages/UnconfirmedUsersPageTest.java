package construction_and_testing.public_transport_system.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static construction_and_testing.public_transport_system.pages.util.SeleniumProperties.CHROME_DRIVER_PATH;
import static org.testng.AssertJUnit.assertEquals;

public class UnconfirmedUsersPageTest {

    private WebDriver browser;

    private WelcomePage welcomePage;

    private NavigationBarPage navigationBarPage;

    private UnconfirmedUsersPage unconfirmedUsersPage;

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
        unconfirmedUsersPage = PageFactory.initElements(browser, UnconfirmedUsersPage.class);

        welcomePage.ensureIsDisplayed();
        welcomePage.login("b", "b");

        // navigate To Unchecked Users
        navigationBarPage.ensureIsDisplayed();
        navigationBarPage.getUsersField().click();
        navigationBarPage.getUnconfirmedUsersLink().click();

    }

    @Test
    public void checkUsersDocuments() {
        unconfirmedUsersPage.ensureTableIsDisplayed();
        assertEquals("http://localhost:4200/unconfirmedUsers", browser.getCurrentUrl());
        unconfirmedUsersPage.getCheckLink().click();


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("http://localhost:4200/userProfile", browser.getCurrentUrl());

    }

    @Test
    public void acceptUser() throws InterruptedException {

        unconfirmedUsersPage.ensureTableIsDisplayed();
        assertEquals("http://localhost:4200/unconfirmedUsers", browser.getCurrentUrl());

        int sizeBeforeAdding = unconfirmedUsersPage.getTableSize();
        unconfirmedUsersPage.getAcceptButton().click();

        unconfirmedUsersPage.ensureIsChanged(sizeBeforeAdding, -1);

        int sizeAfter = unconfirmedUsersPage.getTableSize();
        assertEquals(sizeBeforeAdding, sizeAfter + 1);
    }

    @Test
    public void denyUser() throws InterruptedException {
        assertEquals("http://localhost:4200/unconfirmedUsers", browser.getCurrentUrl());
        unconfirmedUsersPage.ensureTableIsDisplayed();

        int sizeBeforeAdding = unconfirmedUsersPage.getTableSize();
        unconfirmedUsersPage.getDenyButton().click();

        unconfirmedUsersPage.ensureIsChanged(sizeBeforeAdding, -1);

        int sizeAfter = unconfirmedUsersPage.getTableSize();
        assertEquals(sizeBeforeAdding, sizeAfter + 1);
    }

    @AfterMethod
    public void closeSelenium() {
        browser.quit();
    }

}
