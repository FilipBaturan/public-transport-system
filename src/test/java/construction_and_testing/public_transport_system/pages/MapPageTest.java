package construction_and_testing.public_transport_system.pages;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static construction_and_testing.public_transport_system.pages.util.SeleniumProperties.CHROME_DRIVER_PATH;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MapPageTest {

    private WebDriver browser;

    private WelcomePage welcomePage;

    private NavigationBarPage navigationBarPage;

    private MapPage mapPage;

    @Before
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

        navigationBarPage.ensureIsDisplayedLogout();
        navigationBarPage.getMapDropDown().click();
        navigationBarPage.ensureIsDisplayedMapDropItems();
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
        new Actions(browser).moveByOffset(-5, 0).click().build().perform();

        mapPage.ensureIsDisplayedTransportLinePopUp();
        mapPage.getButtonWidth10().click();
        mapPage.getButtonColorBlack().click();

        mapPage.getButtonApply().click();

        mapPage.ensureIsAddedTransportLine(beforeCount);
        mapPage.ensureIsDisplayedButtonEdit();

        assertThat(mapPage.numberOfTransportLines()).isEqualTo(beforeCount + 1);
        assertThat(mapPage.getTransportLines()
                .get(mapPage.getTransportLines().size() - 1)
                .getText()).contains("gener@ted");
    }

    /**
     * Test valid transport line deletion
     */
    @Test
    public void testRemoveTransportLine() {

        mapPage.ensureIsDisplayed();

        mapPage.ensureIsDisplayedButtonEdit();

        int beforeCount = mapPage.numberOfTransportLines();

        mapPage.getButtonEdit().click();
        mapPage.ensureIsDisplayedEditor();

        new Actions(browser).moveToElement(mapPage.getButtonDraw()).build().perform();

        new Actions(browser).moveByOffset(260, 110).click().build().perform();
        mapPage.getButtonRemoveTransportLine().click();
        mapPage.getButtonApply().click();

        mapPage.ensureIsRemovedTransportLine(beforeCount);
        assertThat(mapPage.numberOfTransportLines()).isEqualTo(beforeCount - 1);

    }

    /**
     * Test valid transport line adding
     */
    @Test
    public void testEditTransportLine() {

        mapPage.ensureIsDisplayed();

        mapPage.ensureIsDisplayedButtonEdit();

        int beforeCount = mapPage.numberOfTransportLines();

        mapPage.getEditButtonTransportLine().click();
        mapPage.ensureIsDisplayedButtonSave();

        mapPage.setInputEditTransportLineName("M5");
        mapPage.getSelectOptionMetro().click();
        mapPage.getButtonSave().click();

        mapPage.ensureIsEditedTransportLine("M5");

        assertThat(mapPage.numberOfTransportLines()).isEqualTo(beforeCount);
        assertThat(mapPage.getTransportLines().get(0).getText()).contains("M5");
    }

    /**
     * Test saving transport line with min length firstName
     */
    @Test
    public void testSaveTransportLineWithMinLengthName() {

        mapPage.ensureIsDisplayed();

        mapPage.ensureIsDisplayedButtonEdit();

        int beforeCount = mapPage.numberOfTransportLines();

        mapPage.getEditButtonTransportLine().click();
        mapPage.ensureIsDisplayedButtonSave();

        mapPage.setInputEditTransportLineName("a");
        mapPage.getSelectOptionMetro().click();
        mapPage.getButtonSave().click();

        assertThat(mapPage.numberOfTransportLines()).isEqualTo(beforeCount);
        assertThat(mapPage.getTransportLines().get(0).getText()).contains("a");
    }

    /**
     * Test saving transport line with not unique firstName
     */
    @Test
    public void testSaveTransportLineNotUniqueName() {

        mapPage.ensureIsDisplayed();

        mapPage.ensureIsDisplayedButtonEdit();

        int beforeCount = mapPage.numberOfTransportLines();

        mapPage.getEditButtonTransportLine().click();
        mapPage.ensureIsDisplayedButtonSave();

        mapPage.setInputEditTransportLineName(mapPage.getTransportLines().get(1).getText());
        mapPage.getSelectOptionMetro().click();
        mapPage.getButtonSave().click();

        mapPage.ensureIsDisplayedButtonEdit();

        assertThat(mapPage.numberOfTransportLines()).isEqualTo(beforeCount);
    }

    /**
     * Test saving transport line with long firstName
     */
    @Test
    public void testSaveTransportLineWithLongName() {

        mapPage.ensureIsDisplayed();

        mapPage.ensureIsDisplayedButtonEdit();

        int beforeCount = mapPage.numberOfTransportLines();

        mapPage.getEditButtonTransportLine().click();
        mapPage.ensureIsDisplayedButtonSave();

        mapPage.setInputEditTransportLineName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        mapPage.getSelectOptionMetro().click();
        mapPage.getButtonSave().click();

        mapPage.ensureIsDisplayedFirstError();

        assertThat(mapPage.getSpanFirstError().getText())
                .isEqualTo("Transport line firstName must be maximum 30 characters long!");
        assertThat(mapPage.numberOfTransportLines()).isEqualTo(beforeCount);
    }

    /**
     * Test valid station adding
     */
    @Test
    public void testAddStation() {

        mapPage.ensureIsDisplayed();

        mapPage.ensureIsDisplayedButtonEdit();

        int beforeCount = mapPage.numberOfStations();

        mapPage.getButtonEdit().click();
        mapPage.ensureIsDisplayedEditor();

        mapPage.getButtonDraw().getLocation();
        new Actions(browser).moveToElement(mapPage.getButtonDraw()).moveByOffset(300, 0)
                .contextClick().build().perform();

        mapPage.ensureIsDisplayedStationPopUp();
        mapPage.getInputStationName().sendKeys("SB33");
        mapPage.getButtonTypeBus().click();

        mapPage.getButtonApply().click();

        mapPage.ensureIsAddedStations(beforeCount, 1);
        mapPage.ensureIsDisplayedButtonEdit();

        assertThat(mapPage.numberOfStations()).isEqualTo(beforeCount + 1);
    }

    /**
     * Test valid station editing
     */
    @Test
    public void testEditStation() {

        mapPage.ensureIsDisplayed();

        mapPage.ensureIsDisplayedButtonEdit();

        int beforeCount = mapPage.numberOfStations();

        mapPage.getButtonEdit().click();
        mapPage.ensureIsDisplayedEditor();

        new Actions(browser).click(mapPage.getStationsOnEditMap().get(0)).build().perform();
        mapPage.ensureIsDisplayedStationEditPopUp();

        mapPage.setInputEditStationName("SB55");
        mapPage.getButtonEditRenameStation().click();

        mapPage.getButtonApply().click();

        mapPage.ensureIsDisplayedButtonEdit();
        mapPage.ensureIsDeletedStations(beforeCount, 0);

        assertThat(mapPage.numberOfStations()).isEqualTo(beforeCount);
    }

    /**
     * Test saving station with min length firstName
     */
    @Test
    public void testSaveStationWithMinLengthName() {

        mapPage.ensureIsDisplayed();

        mapPage.ensureIsDisplayedButtonEdit();

        int beforeCount = mapPage.numberOfStations();

        mapPage.getButtonEdit().click();
        mapPage.ensureIsDisplayedEditor();

        new Actions(browser).click(mapPage.getStationsOnEditMap().get(0)).build().perform();
        mapPage.ensureIsDisplayedStationEditPopUp();

        mapPage.setInputEditStationName("aaa");
        mapPage.getButtonEditRenameStation().click();

        mapPage.getButtonApply().click();

        mapPage.ensureIsDisplayedButtonEdit();
        mapPage.ensureIsDeletedStations(beforeCount, 0);

        assertThat(mapPage.numberOfStations()).isEqualTo(beforeCount);
    }

    /**
     * Test saving station with max length firstName
     */
    @Test
    public void testSaveStationWithMaxLengthName() {

        mapPage.ensureIsDisplayed();

        mapPage.ensureIsDisplayedButtonEdit();

        int beforeCount = mapPage.numberOfStations();

        mapPage.getButtonEdit().click();
        mapPage.ensureIsDisplayedEditor();

        new Actions(browser).click(mapPage.getStationsOnEditMap().get(0)).build().perform();
        mapPage.ensureIsDisplayedStationEditPopUp();

        mapPage.setInputEditStationName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        mapPage.getButtonEditRenameStation().click();

        mapPage.getButtonApply().click();

        mapPage.ensureIsDisplayedButtonEdit();
        mapPage.ensureIsDeletedStations(beforeCount, 0);

        assertThat(mapPage.numberOfStations()).isEqualTo(beforeCount);
    }

    /**
     * Test saving station with short firstName
     */
    @Test
    public void testSaveStationWithShortName() {

        mapPage.ensureIsDisplayed();

        mapPage.ensureIsDisplayedButtonEdit();

        int beforeCount = mapPage.numberOfStations();

        mapPage.getButtonEdit().click();
        mapPage.ensureIsDisplayedEditor();

        new Actions(browser).click(mapPage.getStationsOnEditMap().get(0)).build().perform();
        mapPage.ensureIsDisplayedStationEditPopUp();

        mapPage.setInputEditStationName("S");
        mapPage.getButtonEditRenameStation().click();

        mapPage.getButtonApply().click();

        mapPage.ensureIsDisplayedButtonEdit();
        mapPage.ensureIsDeletedStations(beforeCount, 0);

        assertThat(mapPage.numberOfStations()).isEqualTo(beforeCount);
    }

    /**
     * Test saving station with long firstName
     */
    @Test
    public void testSaveStationWithLongName() {

        mapPage.ensureIsDisplayed();

        mapPage.ensureIsDisplayedButtonEdit();

        int beforeCount = mapPage.numberOfStations();

        mapPage.getButtonEdit().click();
        mapPage.ensureIsDisplayedEditor();

        new Actions(browser).click(mapPage.getStationsOnEditMap().get(0)).build().perform();
        mapPage.ensureIsDisplayedStationEditPopUp();

        mapPage.setInputEditStationName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        mapPage.getButtonEditRenameStation().click();

        mapPage.getButtonApply().click();

        mapPage.ensureIsDisplayedButtonEdit();
        mapPage.ensureIsDeletedStations(beforeCount, 0);

        assertThat(mapPage.numberOfStations()).isEqualTo(beforeCount);
    }

    /**
     * Test valid station deletion
     */
    @Test
    public void testRemoveStation() {

        mapPage.ensureIsDisplayed();

        mapPage.ensureIsDisplayedButtonEdit();

        int beforeCount = mapPage.numberOfStations();

        mapPage.getButtonEdit().click();
        mapPage.ensureIsDisplayedEditor();

        new Actions(browser).click(mapPage.getStationsOnEditMap().get(0)).build().perform();
        mapPage.ensureIsDisplayedStationEditPopUp();

        mapPage.getButtonEditDeleteStation().click();

        mapPage.getButtonApply().click();

        mapPage.ensureIsDeletedStations(beforeCount, 1);

        assertThat(mapPage.numberOfStations()).isEqualTo(beforeCount - 1);
    }

    @After
    public void closeSelenium() {
        browser.quit();
    }
}
