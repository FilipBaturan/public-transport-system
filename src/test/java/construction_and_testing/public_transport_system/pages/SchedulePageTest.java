package construction_and_testing.public_transport_system.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static construction_and_testing.public_transport_system.pages.util.SeleniumProperties.CHROME_DRIVER_PATH;
import static org.testng.AssertJUnit.assertEquals;


public class SchedulePageTest {
    private WebDriver browser;

    private WelcomePage welcomePage;

    private NavigationBarPage navBarPage;

    private SchedulePage schedulePage;

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
        schedulePage = PageFactory.initElements(browser, SchedulePage.class);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        welcomePage.ensureIsDisplayed();
        welcomePage.login("null", "null");

        navBarPage.ensureIsDisplayed();
        navBarPage.getScheduleLink().click();
        assertEquals("http://localhost:4200/schedule", browser.getCurrentUrl());

    }

    @Test
    public void testDisplaySchedule() {
        schedulePage.ensureIsDisplayedDayOfWeekComboCheckBox();
        schedulePage.ensureIsDisplayedTransportLineComboCheckBox();
        schedulePage.ensureIsDisplayedScheduleTable();

        schedulePage.getDayOfWeekComboCheckBox().click();
        schedulePage.getCheckDayOfWeek().click();

        schedulePage.getTransportLineComboCheckBox().click();
        schedulePage.getCheckTransportLine().click();
        schedulePage.getCheckTransportLine1().click();
        schedulePage.getCheckTransportLine2().click();
        schedulePage.getTransportLineComboCheckBox().click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int numRows = schedulePage.getNumOfRows();
        int numColumns = schedulePage.getNumOfColumns();

        assertEquals(7, numRows);
        assertEquals(3, numColumns);

        assertEquals("R1-WORKDAY", schedulePage.getHeader1().getText());

        assertEquals("07:55", schedulePage.getCell1().getText());
        assertEquals("10:00", schedulePage.getCell2().getText());
    }

    @AfterMethod
    public void closeSelenium() {
        browser.quit();
    }
}
