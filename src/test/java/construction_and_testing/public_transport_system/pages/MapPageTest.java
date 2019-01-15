package construction_and_testing.public_transport_system.pages;

import construction_and_testing.public_transport_system.pages.MapPage;
import construction_and_testing.public_transport_system.pages.NavigationBarPage;
import construction_and_testing.public_transport_system.pages.WelcomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static construction_and_testing.public_transport_system.pages.util.SeleniumProperties.CHROME_DRIVER_PATH;
import static org.assertj.core.api.Assertions.assertThat;

public class MapPageTest {

    private WebDriver browser;

    private WelcomePage welcomePage;

    private NavigationBarPage navigationBarPage;

    private MapPage mapPage;

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
        mapPage = PageFactory.initElements(browser, MapPage.class);

        welcomePage.ensureIsDisplayed();
        welcomePage.login("null", "null");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        navigationBarPage.ensureIsDisplayed();
        navigationBarPage.getMapDropDown().click();
        navigationBarPage.getRouteLink().click();

        assertThat("http://localhost:4200/map").isEqualTo(browser.getCurrentUrl());

    }


    /**
     * Test valid transport line adding
     */
    @Test
    public void testAddTransportLine() {

        mapPage.ensureIsDisplayed();

        mapPage.ensureIsDisplayedButtonEdit();

        int beforeCount = mapPage.numberOfTransportLines();

        mapPage.getButtonEdit().click();
        mapPage.ensureIsDisplayedEditor();

        mapPage.getButtonDraw().click();

        new Actions(browser).moveByOffset(300, 120).click().build().perform();
        new Actions(browser).moveByOffset(30, 170).click().build().perform();
        new Actions(browser).moveByOffset(110, -20).click().build().perform();
        new Actions(browser).moveByOffset(0, 0).click().build().perform();

        mapPage.getButtonApply().click();

        mapPage.ensureIsAddedLine(beforeCount);
        mapPage.ensureIsDisplayedButtonEdit();

        assertThat(mapPage.numberOfTransportLines()).isEqualTo(beforeCount + 1);
    }

    @AfterMethod
    public void closeSelenium() {
        browser.quit();
    }
}
