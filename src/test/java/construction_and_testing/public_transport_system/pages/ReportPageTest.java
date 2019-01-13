package construction_and_testing.public_transport_system.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static construction_and_testing.public_transport_system.selenium.util.SeleniumProperties.CHROME_DRIVER_PATH;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class ReportPageTest {
    private WebDriver browser;

    WelcomePage welcomePage;
    ReportPage reportPage;


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
        reportPage = PageFactory.initElements(browser, ReportPage.class);
        logIn();
        navigateRegUsers();

    }

    private void navigateRegUsers() {

        welcomePage.getUsersField().click();
        welcomePage.getReportLink().click();
    }

    @Test
    public void gettingReport(){

        assertEquals("http://localhost:4200/reports", browser.getCurrentUrl());

        reportPage.getShowPriceButton().click();

        //browser.manage().timeouts().implicitlyWait(10, TimeUnit.MINUTES);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(reportPage.getWeeklyChart().isDisplayed());
        assertTrue(reportPage.getMontlyChart().isDisplayed());
        assertTrue(reportPage.getNumOfTicketsSold().isDisplayed());
        assertTrue(reportPage.getNumOfTicketsSold().getText().endsWith("2"));
    }

    @AfterMethod
    public void closeSelenium() {
        browser.quit();
    }
}
