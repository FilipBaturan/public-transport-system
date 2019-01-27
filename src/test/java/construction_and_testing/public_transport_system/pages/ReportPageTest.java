package construction_and_testing.public_transport_system.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static construction_and_testing.public_transport_system.pages.util.SeleniumProperties.CHROME_DRIVER_PATH;
import static org.testng.Assert.assertFalse;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class ReportPageTest {
    private WebDriver browser;

    WelcomePage welcomePage;

    private NavigationBarPage navigationBarPage;

    private ReportPage reportPage;


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
        reportPage = PageFactory.initElements(browser, ReportPage.class);

        welcomePage.ensureIsDisplayed();
        welcomePage.login("admin", "admin");
        navigationBarPage.getUsersField().click();
        navigationBarPage.getReportLink().click();
        reportPage.ensureIsDisplayed();
    }

    @Test
    public void gettingReport() {

        assertEquals("http://localhost:4200/reports", browser.getCurrentUrl());

        reportPage.getShowPriceButton().click();

        reportPage.ensureChartIsDisplayed();

        assertTrue(reportPage.getWeeklyChart().isDisplayed());
        assertTrue(reportPage.getMontlyChart().isDisplayed());
        assertTrue(reportPage.getNumOfTicketsSold().isDisplayed());
    }


    @Test
    public void settingInvalidDates() {

        assertEquals("http://localhost:4200/reports", browser.getCurrentUrl());
        reportPage.setInputStartDate("8798706");

        reportPage.getShowPriceButton().click();

        assertFalse(reportPage.getWeeklyChart().isDisplayed());
        assertFalse(reportPage.getMontlyChart().isDisplayed());
        assertFalse(reportPage.getNumOfTicketsSold().isDisplayed());
    }

    @AfterMethod
    public void closeSelenium() {
        browser.quit();
    }
}
