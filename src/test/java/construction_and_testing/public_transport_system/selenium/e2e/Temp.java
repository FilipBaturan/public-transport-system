package construction_and_testing.public_transport_system.selenium.e2e;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static construction_and_testing.public_transport_system.selenium.util.SeleniumProperties.CHROME_DRIVER_PATH;
import static org.testng.AssertJUnit.assertEquals;

public class Temp {

    private WebDriver browser;

//    MenuPage menuPage;
//    StudentListPage studentListPage;
//    StudentEditPage studentEditPage;

    @BeforeMethod
    public void setupSelenium() {
        //instantiate browser
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        browser = new ChromeDriver();
        //maximize window
        browser.manage().window().maximize();
        //navigate
        browser.navigate().to("http://localhost:4200");

//        menuPage = PageFactory.initElements(browser, MenuPage.class);
//        studentListPage = PageFactory.initElements(browser, StudentListPage.class);
//        studentEditPage = PageFactory.initElements(browser, StudentEditPage.class);
    }

    @Test
    public void testAddStudent() {


        assertEquals("http://google.com", browser.getCurrentUrl());


    }
}
