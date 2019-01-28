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

//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        welcomePage.ensureIsDisplayed();
        welcomePage.login("null", "null");

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        navBarPage.ensureIsDisplayedUpdateScheduleLink();
        navBarPage.getUpdateScheduleLink().click();
        assertEquals("http://localhost:4200/updateSchedules", browser.getCurrentUrl());

    }

    @Test
    public void testUpdateSchedule() {
        schedulePage.ensureIsDisplayedTransportLineComboCheckBox();
        schedulePage.getTransportLineComboCheckBox().click();
        schedulePage.ensureIsDisplayedCheckBox1();
        schedulePage.getCheckTransportLine().click();

        //schedulePage.ensureIsDisplayedCell1();

        schedulePage.ensureIsDisplayedScheduleTable();

        int numRows = schedulePage.getNumOfRows();
        int numColumns = schedulePage.getNumOfColumns();

        schedulePage.ensureIsDisplayedAllHeaderCells();
        schedulePage.ensureIsDisplayedAllRows();

        schedulePage.ensureHasCorrectSizeHeaderCells(3);


        schedulePage.ensureHasCorrectSizeRows(5);

//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        assertEquals(3, numColumns);
//        assertEquals(5, numRows);
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        //assertEquals("R1-WORKDAY", schedulePage.getHeader1().getText());

        assertEquals("08:00", schedulePage.getCell1().getAttribute("ng-reflect-model"));
        assertEquals("22:00", schedulePage.getCell2().getAttribute("ng-reflect-model"));

        schedulePage.getCell1().clear();
        schedulePage.getCell1().sendKeys("07:50");

        schedulePage.ensureIsDisplayedAddRemoveScheduleBtn();
        schedulePage.getAddRemoveScheduleButton().click();

        schedulePage.ensureIsDisplayedCell4();
        schedulePage.getCell4().sendKeys("14:00");

        schedulePage.ensureIsSaveScheduleBtn();
        schedulePage.getSaveSchedule().click();

        schedulePage.ensureIsDisplayedAddRemoveScheduleBtn();
        schedulePage.getAddRemoveScheduleButton().click();

        schedulePage.getCell1().clear();
        schedulePage.getCell1().sendKeys("08:00");

        schedulePage.ensureIsSaveScheduleBtn();
        schedulePage.getSaveSchedule().click();
        assertEquals("08:00", schedulePage.getCell1().getAttribute("ng-reflect-model"));
        schedulePage.ensureIsSaveScheduleBtn();
        schedulePage.getSaveSchedule().click();
    }

    @AfterMethod
    public void closeSelenium() {
        browser.quit();
    }
}