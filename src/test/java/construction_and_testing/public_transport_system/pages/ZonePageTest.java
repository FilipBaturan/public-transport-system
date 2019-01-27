package construction_and_testing.public_transport_system.pages;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static construction_and_testing.public_transport_system.pages.util.SeleniumProperties.CHROME_DRIVER_PATH;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ZonePageTest {

    private WebDriver browser;

    private ZonePage zonePage;

    @Before
    public void setupSelenium() {
        //instantiate browser
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        browser = new ChromeDriver();
        //maximize window
        browser.manage().window().maximize();
        //navigate
        browser.navigate().to("http://localhost:4200");

        WelcomePage welcomePage = PageFactory.initElements(browser, WelcomePage.class);
        NavigationBarPage navigationBarPage = PageFactory.initElements(browser, NavigationBarPage.class);
        zonePage = PageFactory.initElements(browser, ZonePage.class);

        welcomePage.ensureIsDisplayed();
        welcomePage.login("null", "null");

        navigationBarPage.ensureIsDisplayedLogout();
        navigationBarPage.getMapDropDown().click();
        navigationBarPage.ensureIsDisplayedMapDropItems();
        navigationBarPage.getZoneLink().click();

        assertThat("http://localhost:4200/zones").isEqualTo(browser.getCurrentUrl());
    }

    /**
     * Test valid zone adding
     */
    @Test
    public void testAddZone() {
        zonePage.ensureIsDisplayed();

        int beforeCount = zonePage.numberOfZones();

        zonePage.getButtonAdd().click();

        zonePage.ensureIsDisplayedButtonSave();
        assertThat(zonePage.getInputName().isDisplayed()).isTrue();

        zonePage.setInputName("Kamenica");

        zonePage.getButtonSave().click();

        zonePage.ensureIsAdded(beforeCount);

        List<WebElement> allCardNames = zonePage.getZoneNames();
        List<WebElement> allCardCurrentLines = zonePage.getZonesTransportLines();
        WebElement newZoneName = allCardNames.get(allCardNames.size() - 2);
        WebElement newZoneTransportLine = allCardCurrentLines.get(allCardCurrentLines.size() - 1);

        assertThat(zonePage.numberOfZones()).isEqualTo(beforeCount + 1);
        assertThat(newZoneName.getText()).isEqualTo("Kamenica");
        assertThat(newZoneTransportLine.getText()).isEqualTo("Number of routes: 0");
    }

    /**
     * Test valid zone editing
     */
    @Test
    public void testEditZone() {
        zonePage.ensureIsDisplayed();

        int beforeCount = zonePage.numberOfZones();

        zonePage.getEditButton().click();

        zonePage.ensureIsDisplayedButtonSave();
        assertThat(zonePage.getInputName().isDisplayed()).isTrue();

        zonePage.setInputName("Veternik");
        zonePage.getFormRemoveButtons().get(0).click();

        zonePage.getButtonSave().click();

        zonePage.ensureIsEdited("Veternik");

        List<WebElement> allCardNames = zonePage.getZoneNames();
        List<WebElement> allCardCurrentLines = zonePage.getZonesTransportLines();
        WebElement editedZoneName = allCardNames.get(1);
        WebElement editedZoneTransportLine = allCardCurrentLines.get(1);

        assertThat(zonePage.numberOfZones()).isEqualTo(beforeCount);
        assertThat(editedZoneName.getText()).isEqualTo("Veternik");
        assertThat(editedZoneTransportLine.getText()).isEqualTo("Number of routes: 3");
    }

    /**
     * Test valid zone deletion
     */
    @Test
    public void testRemoveZone() {
        zonePage.ensureIsDisplayed();

        int beforeCount = zonePage.numberOfZones();
        String nameBefore = zonePage.getZoneNames().get(1).getText();

        zonePage.getRemoveButton().get(1).click();

        zonePage.ensureIsRemoved(beforeCount);

        String zoneName = zonePage.getZoneNames().get(1).getText();
        String zonesTransportLines = zonePage.getZonesTransportLines().get(1).getText();
        String defaultZoneTransportLines = zonePage.getZonesTransportLines().get(0).getText();

        assertThat(zonePage.numberOfZones()).isEqualTo(beforeCount - 1);
        assertThat(zoneName).isNotEqualTo(nameBefore);
        assertThat(zonesTransportLines).isEqualTo("Number of routes: 0");
        assertThat(defaultZoneTransportLines).isEqualTo("Number of routes: 4");
    }

    /**
     * Test saving with min length name
     */
    @Test
    public void testSaveWithMinLengthName() {
        zonePage.ensureIsDisplayed();

        int beforeCount = zonePage.numberOfZones();

        zonePage.getButtonAdd().click();

        zonePage.ensureIsDisplayedButtonSave();
        assertThat(zonePage.getInputName().isDisplayed()).isTrue();

        zonePage.setInputName("a");

        zonePage.getButtonSave().click();

        zonePage.ensureIsAdded(beforeCount);

        List<WebElement> allCardNames = zonePage.getZoneNames();
        List<WebElement> allCardCurrentLines = zonePage.getZonesTransportLines();
        WebElement newZoneName = allCardNames.get(allCardNames.size() - 2);
        WebElement newZoneTransportLine = allCardCurrentLines.get(allCardCurrentLines.size() - 1);

        assertThat(zonePage.numberOfZones()).isEqualTo(beforeCount + 1);
        assertThat(newZoneName.getText()).isEqualTo("a");
        assertThat(newZoneTransportLine.getText()).isEqualTo("Number of routes: 0");
    }

    /**
     * Test saving with max length name
     */
    @Test
    public void testSaveWithMaxLengthName() {
        zonePage.ensureIsDisplayed();

        int beforeCount = zonePage.numberOfZones();

        zonePage.getButtonAdd().click();

        zonePage.ensureIsDisplayedButtonSave();
        assertThat(zonePage.getInputName().isDisplayed()).isTrue();

        zonePage.setInputName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        zonePage.getButtonSave().click();

        zonePage.ensureIsAdded(beforeCount);

        List<WebElement> allCardNames = zonePage.getZoneNames();
        List<WebElement> allCardCurrentLines = zonePage.getZonesTransportLines();
        WebElement newZoneName = allCardNames.get(allCardNames.size() - 2);
        WebElement newZoneTransportLine = allCardCurrentLines.get(allCardCurrentLines.size() - 1);

        assertThat(zonePage.numberOfZones()).isEqualTo(beforeCount + 1);
        assertThat(newZoneName.getText()).isEqualTo("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertThat(newZoneTransportLine.getText()).isEqualTo("Number of routes: 0");
    }

    /**
     * Test saving with not unique name
     */
    @Test
    public void testSaveWithNotUniqueName() {
        zonePage.ensureIsDisplayed();

        int beforeCount = zonePage.numberOfZones();

        zonePage.getButtonAdd().click();

        zonePage.ensureIsDisplayedButtonSave();
        assertThat(zonePage.getInputName().isDisplayed()).isTrue();

        zonePage.setInputName(zonePage.getZoneNames().get(1).getText());

        zonePage.getButtonSave().click();

        assertThat(zonePage.numberOfZones()).isEqualTo(beforeCount);
    }

    /**
     * Test saving with short name
     */
    @Test
    public void testSaveWithShortName() {
        zonePage.ensureIsDisplayed();

        int beforeCount = zonePage.numberOfZones();

        zonePage.getButtonAdd().click();

        zonePage.ensureIsDisplayedButtonSave();
        assertThat(zonePage.getInputName().isDisplayed()).isTrue();

        zonePage.setInputName("");

        zonePage.getButtonSave().click();

        zonePage.ensureIsDisplayedFirstError();

        assertThat(zonePage.getSpanNameError().getText()).isEqualTo("Zone name is required!");
        assertThat(zonePage.numberOfZones()).isEqualTo(beforeCount);
    }

    /**
     * Test saving with short name
     */
    @Test
    public void testSaveWithLongName() {
        zonePage.ensureIsDisplayed();

        int beforeCount = zonePage.numberOfZones();

        zonePage.getButtonAdd().click();

        zonePage.ensureIsDisplayedButtonSave();
        assertThat(zonePage.getInputName().isDisplayed()).isTrue();

        zonePage.setInputName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        zonePage.getButtonSave().click();

        zonePage.ensureIsDisplayedFirstError();

        assertThat(zonePage.getSpanNameError().getText()).isEqualTo("Zone name must be maximum 30 characters long!");
        assertThat(zonePage.numberOfZones()).isEqualTo(beforeCount);
    }

    @After
    public void closeSelenium() {
        browser.quit();
    }
}
