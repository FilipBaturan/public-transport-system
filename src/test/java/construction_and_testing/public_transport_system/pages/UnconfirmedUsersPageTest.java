package construction_and_testing.public_transport_system.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.ThreadLocalRandom;

import static construction_and_testing.public_transport_system.selenium.util.SeleniumProperties.CHROME_DRIVER_PATH;
import static org.testng.AssertJUnit.assertEquals;

public class UnconfirmedUsersPageTest {

    private WebDriver browser;

    WelcomePage welcomePage;
    UnconfirmedUsersPage unconfirmedUsersPage;


    public void logIn()
    {
        welcomePage.setUsername("b");
        welcomePage.setPassword("b");
        welcomePage.getLoginButton().click();
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
        unconfirmedUsersPage = PageFactory.initElements(browser, UnconfirmedUsersPage.class);
        logIn();
        navigateToUncheckedUsers();

    }

    private void navigateToUncheckedUsers() {

        welcomePage.getUsersField().click();
        welcomePage.getUnconfirmedUsersLink().click();

    }

    @Test
    public void checkUsersDocuments()
    {
        assertEquals("http://localhost:4200/unconfirmedUsers", browser.getCurrentUrl());
        unconfirmedUsersPage.ensureTableIsDisplayed();
        unconfirmedUsersPage.getCheckLink().click();

        //browser.manage().timeouts().implicitlyWait(10, TimeUnit.MINUTES);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("http://localhost:4200/userProfile", browser.getCurrentUrl());

    }

    @Test
    public void acceptUser() throws InterruptedException {
        assertEquals("http://localhost:4200/unconfirmedUsers", browser.getCurrentUrl());
        unconfirmedUsersPage.ensureTableIsDisplayed();

        int sizeBeforeAdding =  unconfirmedUsersPage.getTableSize();
        unconfirmedUsersPage.getAcceptButton().click();

        Thread.sleep(3000);

        int sizeAfter = unconfirmedUsersPage.getTableSize();
        assertEquals(sizeBeforeAdding, sizeAfter + 1);
    }

    @Test
    public void denyUser() throws InterruptedException {
        assertEquals("http://localhost:4200/unconfirmedUsers", browser.getCurrentUrl());
        unconfirmedUsersPage.ensureTableIsDisplayed();

        int sizeBeforeAdding =  unconfirmedUsersPage.getTableSize();
        unconfirmedUsersPage.getDenyButton().click();

        Thread.sleep(2000);

        int sizeAfter = unconfirmedUsersPage.getTableSize();
        assertEquals(sizeBeforeAdding, sizeAfter + 1);
    }

    @AfterMethod
    public void closeSelenium() {
        browser.quit();
    }

}
