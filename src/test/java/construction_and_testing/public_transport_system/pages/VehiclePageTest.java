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
public class VehiclePageTest {

    private WebDriver browser;

    private VehiclePage vehiclePage;

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
        vehiclePage = PageFactory.initElements(browser, VehiclePage.class);

        welcomePage.ensureIsDisplayed();
        welcomePage.login("null", "null");

        navigationBarPage.ensureIsDisplayedLogout();
        navigationBarPage.getMapDropDown().click();
        navigationBarPage.ensureIsDisplayedMapDropItems();
        navigationBarPage.getVehicleLink().click();

        assertThat("http://localhost:4200/vehicles").isEqualTo(browser.getCurrentUrl());
    }

    /**
     * Test valid vehicle adding
     */
    @Test
    public void testAddVehicle() {

        vehiclePage.ensureIsDisplayed();

        int beforeCount = vehiclePage.numberOfVehicles();

        vehiclePage.getButtonAdd().click();

        vehiclePage.ensureIsDisplayedButtonSave();
        assertThat(vehiclePage.getInputName().isDisplayed()).isTrue();
        assertThat(vehiclePage.getInputType().isDisplayed()).isTrue();
        assertThat(vehiclePage.getInputCurrentLine().isDisplayed()).isTrue();

        vehiclePage.setInputName("NS-B-023-R3");
        vehiclePage.getOptionBus().click();

        WebElement selectedTransportLine = vehiclePage.selectTransportLine(2);
        String selectedTransportLineName = selectedTransportLine.getText();
        selectedTransportLine.click();
        vehiclePage.getButtonSave().click();

        vehiclePage.ensureIsAdded(beforeCount);

        List<WebElement> allCardNames = vehiclePage.getVehicleNames();
        List<WebElement> allCardCurrentLines = vehiclePage.getVehicleCurrentLines();
        WebElement newVehicleName = allCardNames.get(allCardNames.size() - 2);
        WebElement newVehicleCurrentLine = allCardCurrentLines.get(allCardCurrentLines.size() - 1);

        assertThat(vehiclePage.numberOfVehicles()).isEqualTo(beforeCount + 1);
        assertThat(newVehicleName.getText()).isEqualTo("NS-B-023-R3");
        assertThat(newVehicleCurrentLine.getText()).contains(selectedTransportLineName);
    }


    /**
     * Test valid vehicle editing
     */
    @Test
    public void testEditVehicle() {

        vehiclePage.ensureIsDisplayed();

        vehiclePage.getEditButton().click();

        vehiclePage.ensureIsDisplayedButtonSave();
        assertThat(vehiclePage.getInputName().isDisplayed()).isTrue();
        assertThat(vehiclePage.getInputType().isDisplayed()).isTrue();
        assertThat(vehiclePage.getInputCurrentLine().isDisplayed()).isTrue();

        vehiclePage.setInputName("NS-T-824-P7");
        vehiclePage.getOptionBus().click();

        WebElement selectedTransportLine = vehiclePage.selectTransportLine(2);
        String selectedTransportLineName = selectedTransportLine.getText();
        selectedTransportLine.click();
        vehiclePage.getButtonSave().click();

        vehiclePage.ensureIsEdited("NS-T-824-P7");

        List<WebElement> allCardNames = vehiclePage.getVehicleNames();
        List<WebElement> allCardCurrentLines = vehiclePage.getVehicleCurrentLines();
        WebElement editedVehicleName = allCardNames.get(0);
        WebElement editedVehicleCurrentLine = allCardCurrentLines.get(0);

        assertThat(editedVehicleName.getText()).isEqualTo("NS-T-824-P7");
        assertThat(editedVehicleCurrentLine.getText()).contains(selectedTransportLineName);
    }

    /**
     * Test valid vehicle deletion
     */
    @Test
    public void testRemoveVehicle() {

        vehiclePage.ensureIsDisplayed();

        int beforeCount = vehiclePage.numberOfVehicles();
        String beforeName = vehiclePage.getVehicleNames().get(vehiclePage.getDeleteButtons().size() - 1).getText();

        vehiclePage.getDeleteButtons().get(vehiclePage.getDeleteButtons().size() - 1).click();

        vehiclePage.ensureIsRemoved(beforeCount);

        assertThat(vehiclePage.numberOfVehicles()).isEqualTo(beforeCount - 1);
        assertThat(vehiclePage.getVehicleNames().get(vehiclePage.getVehicleNames().size() - 1).getText())
                .isNotEqualTo(beforeName);
    }

    /**
     * Test valid vehicle saving with minimum firstName length
     */
    @Test
    public void testSaveVehicleWithMinimumNameLength() {

        vehiclePage.ensureIsDisplayed();

        int beforeCount = vehiclePage.numberOfVehicles();

        vehiclePage.getButtonAdd().click();

        vehiclePage.ensureIsDisplayedButtonSave();
        assertThat(vehiclePage.getInputName().isDisplayed()).isTrue();
        assertThat(vehiclePage.getInputType().isDisplayed()).isTrue();
        assertThat(vehiclePage.getInputCurrentLine().isDisplayed()).isTrue();

        vehiclePage.setInputName("aaa");
        vehiclePage.getOptionBus().click();

        WebElement selectedTransportLine = vehiclePage.selectTransportLine(2);
        String selectedTransportLineName = selectedTransportLine.getText();
        selectedTransportLine.click();
        vehiclePage.getButtonSave().click();

        vehiclePage.ensureIsAdded(beforeCount);

        List<WebElement> allCardNames = vehiclePage.getVehicleNames();
        List<WebElement> allCardCurrentLines = vehiclePage.getVehicleCurrentLines();
        WebElement newVehicleName = allCardNames.get(allCardNames.size() - 2);
        WebElement newVehicleCurrentLine = allCardCurrentLines.get(allCardCurrentLines.size() - 1);

        assertThat(vehiclePage.numberOfVehicles()).isEqualTo(beforeCount + 1);
        assertThat(newVehicleName.getText()).isEqualTo("aaa");
        assertThat(newVehicleCurrentLine.getText()).contains(selectedTransportLineName);
    }

    /**
     * Test valid vehicle saving with maximum firstName length
     */
    @Test
    public void testSaveVehicleWithMaximumNameLength() {

        vehiclePage.ensureIsDisplayed();

        int beforeCount = vehiclePage.numberOfVehicles();

        vehiclePage.getButtonAdd().click();

        vehiclePage.ensureIsDisplayedButtonSave();
        assertThat(vehiclePage.getInputName().isDisplayed()).isTrue();
        assertThat(vehiclePage.getInputType().isDisplayed()).isTrue();
        assertThat(vehiclePage.getInputCurrentLine().isDisplayed()).isTrue();

        vehiclePage.setInputName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        vehiclePage.getOptionBus().click();

        WebElement selectedTransportLine = vehiclePage.selectTransportLine(3);
        String selectedTransportLineName = selectedTransportLine.getText();
        selectedTransportLine.click();
        vehiclePage.getButtonSave().click();

        vehiclePage.ensureIsAdded(beforeCount);

        // new vehicle card is placed at add card position
        List<WebElement> allCardNames = vehiclePage.getVehicleNames();
        List<WebElement> allCardCurrentLines = vehiclePage.getVehicleCurrentLines();
        WebElement newVehicleName = allCardNames.get(allCardNames.size() - 2);
        WebElement newVehicleCurrentLine = allCardCurrentLines.get(allCardCurrentLines.size() - 1);

        assertThat(vehiclePage.numberOfVehicles()).isEqualTo(beforeCount + 1);
        assertThat(newVehicleName.getText()).isEqualTo("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertThat(newVehicleCurrentLine.getText()).contains(selectedTransportLineName);
    }

    /**
     * Test invalid vehicle saving with null firstName and type
     */
    @Test
    public void testSaveVehicleWithNullNameAndType() {

        vehiclePage.ensureIsDisplayed();

        int beforeCount = vehiclePage.numberOfVehicles();

        vehiclePage.getButtonAdd().click();

        vehiclePage.ensureIsDisplayedButtonSave();
        assertThat(vehiclePage.getInputName().isDisplayed()).isTrue();
        assertThat(vehiclePage.getInputType().isDisplayed()).isTrue();
        assertThat(vehiclePage.getInputCurrentLine().isDisplayed()).isTrue();


        vehiclePage.getButtonSave().click();
        vehiclePage.ensureIsDisplayedFirstError();
        vehiclePage.ensureIsDisplayedSecondError();

        assertThat(vehiclePage.getSpanNameError().getText()).isEqualTo("Vehicle firstName is required!");
        assertThat(vehiclePage.getSpanVehicleTypeError().getText()).isEqualTo("Vehicle type is required!");
        vehiclePage.getSpanExitModalForm().click();
        assertThat(vehiclePage.numberOfVehicles()).isEqualTo(beforeCount);
    }

    /**
     * Test invalid vehicle saving with null firstName
     */
    @Test
    public void testSaveVehicleWithNullName() {

        vehiclePage.ensureIsDisplayed();

        int beforeCount = vehiclePage.numberOfVehicles();

        vehiclePage.getButtonAdd().click();

        vehiclePage.ensureIsDisplayedButtonSave();
        assertThat(vehiclePage.getInputName().isDisplayed()).isTrue();
        assertThat(vehiclePage.getInputType().isDisplayed()).isTrue();
        assertThat(vehiclePage.getInputCurrentLine().isDisplayed()).isTrue();


        vehiclePage.getOptionBus().click();

        vehiclePage.getButtonSave().click();
        vehiclePage.ensureIsDisplayedFirstError();

        assertThat(vehiclePage.getSpanNameError().getText()).isEqualTo("Vehicle firstName is required!");
        vehiclePage.getSpanExitModalForm().click();
        assertThat(vehiclePage.numberOfVehicles()).isEqualTo(beforeCount);
    }

    /**
     * Test invalid vehicle saving with short firstName
     */
    @Test
    public void testSaveVehicleWithShortName() {

        vehiclePage.ensureIsDisplayed();

        int beforeCount = vehiclePage.numberOfVehicles();

        vehiclePage.getButtonAdd().click();

        vehiclePage.ensureIsDisplayedButtonSave();
        assertThat(vehiclePage.getInputName().isDisplayed()).isTrue();
        assertThat(vehiclePage.getInputType().isDisplayed()).isTrue();
        assertThat(vehiclePage.getInputCurrentLine().isDisplayed()).isTrue();

        vehiclePage.getInputName().sendKeys("a");
        vehiclePage.getOptionBus().click();

        vehiclePage.getButtonSave().click();
        vehiclePage.ensureIsDisplayedFirstError();

        assertThat(vehiclePage.getSpanNameError().getText())
                .isEqualTo("Vehicle firstName must be at least 3 characters long!");
        vehiclePage.getSpanExitModalForm().click();
        assertThat(vehiclePage.numberOfVehicles()).isEqualTo(beforeCount);
    }

    /**
     * Test invalid vehicle saving with long firstName
     */
    @Test
    public void testSaveVehicleWithLongName() {

        vehiclePage.ensureIsDisplayed();

        int beforeCount = vehiclePage.numberOfVehicles();

        vehiclePage.getButtonAdd().click();

        vehiclePage.ensureIsDisplayedButtonSave();
        assertThat(vehiclePage.getInputName().isDisplayed()).isTrue();
        assertThat(vehiclePage.getInputType().isDisplayed()).isTrue();
        assertThat(vehiclePage.getInputCurrentLine().isDisplayed()).isTrue();

        vehiclePage.getInputName().sendKeys("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        vehiclePage.getOptionBus().click();

        vehiclePage.getButtonSave().click();
        vehiclePage.ensureIsDisplayedFirstError();

        assertThat(vehiclePage.getSpanNameError().getText())
                .isEqualTo("Vehicle firstName must be maximum 30 characters long!");
        vehiclePage.getSpanExitModalForm().click();
        assertThat(vehiclePage.numberOfVehicles()).isEqualTo(beforeCount);
    }

    /**
     * Test invalid vehicle saving with null type
     */
    @Test
    public void testSaveVehicleWithNullType() {

        vehiclePage.ensureIsDisplayed();

        int beforeCount = vehiclePage.numberOfVehicles();

        vehiclePage.getButtonAdd().click();

        vehiclePage.ensureIsDisplayedButtonSave();
        assertThat(vehiclePage.getInputName().isDisplayed()).isTrue();
        assertThat(vehiclePage.getInputType().isDisplayed()).isTrue();
        assertThat(vehiclePage.getInputCurrentLine().isDisplayed()).isTrue();

        vehiclePage.getInputName().sendKeys("NS-B-023-R3");

        vehiclePage.getButtonSave().click();
        vehiclePage.ensureIsDisplayedSecondError();

        assertThat(vehiclePage.getSpanVehicleTypeError().getText()).isEqualTo("Vehicle type is required!");
        vehiclePage.getSpanExitModalForm().click();
        assertThat(vehiclePage.numberOfVehicles()).isEqualTo(beforeCount);
    }

    @After
    public void closeSelenium() {
        browser.quit();
    }
}
