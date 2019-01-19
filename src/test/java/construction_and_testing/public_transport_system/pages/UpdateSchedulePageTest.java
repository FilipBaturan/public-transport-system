package construction_and_testing.public_transport_system.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static construction_and_testing.public_transport_system.pages.util.SeleniumProperties.CHROME_DRIVER_PATH;
import static org.testng.AssertJUnit.assertEquals;

public class UpdateSchedulePageTest {

    private WebDriver browser;

    private WelcomePage welcomePage;

    private NavigationBarPage navBarPage;

    private UpdateSchedulePage schedulePage;

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
        navBarPage = PageFactory.initElements(browser, NavigationBarPage.class);
        schedulePage = PageFactory.initElements(browser, UpdateSchedulePage.class);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        welcomePage.ensureIsDisplayed();
        welcomePage.login("null", "null");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        navBarPage.ensureIsDisplayed();
        navBarPage.getUpdateScheduleLink().click();
        assertEquals("http://localhost:4200/updateSchedules", browser.getCurrentUrl());

    }

    @Test
    public void testUpdateSchedule() {
        schedulePage.ensureIsDisplayedTransportLineComboCheckBox();
        schedulePage.ensureIsDisplayedScheduleTable();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        schedulePage.getTransportLineComboCheckBox().click();
        schedulePage.getCheckTransportLine().click();
        /*schedulePage.getTransportLineComboCheckBox().click();
        schedulePage.getCheckTransportLine1().click();
        schedulePage.getTransportLineComboCheckBox().click();
        schedulePage.getCheckTransportLine2().click();
        schedulePage.getTransportLineComboCheckBox().click();
        schedulePage.getCheckTransportLine().click();*/
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //schedulePage.ensureIsDisplayedCell1();

        int numRows = schedulePage.getNumOfRows();
        int numColumns = schedulePage.getNumOfColumns();

        assertEquals(7, numRows);
        assertEquals(3, numColumns);

        assertEquals("R1-WORKDAY", schedulePage.getHeader1().getText());

        assertEquals("07:55", schedulePage.getCell1().getAttribute("ng-reflect-model"));
        assertEquals("18:00", schedulePage.getCell2().getAttribute("ng-reflect-model"));
        assertEquals("22:00", schedulePage.getCell3().getAttribute("ng-reflect-model"));

        schedulePage.getCell1().clear();
        schedulePage.getCell1().sendKeys("07:50");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void closeSelenium() {
        browser.quit();
    }
}