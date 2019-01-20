package construction_and_testing.public_transport_system.pages;

import construction_and_testing.public_transport_system.pages.NavigationBarPage;
import construction_and_testing.public_transport_system.pages.VehiclePage;
import construction_and_testing.public_transport_system.pages.WelcomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static construction_and_testing.public_transport_system.pages.util.SeleniumProperties.CHROME_DRIVER_PATH;
import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class VehiclePageTest {

    private WebDriver browser;

    private WelcomePage welcomePage;

    private NavigationBarPage navigationBarPage;

    private VehiclePage vehiclePage;

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
        vehiclePage = PageFactory.initElements(browser, VehiclePage.class);

        welcomePage.ensureIsDisplayed();
        welcomePage.login("null", "null");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        navigationBarPage.ensureIsDisplayed();
        navigationBarPage.getMapDropDown().click();
        navigationBarPage.getVehicleLink().click();

        assertThat("http://localhost:4200/vehicles").isEqualTo(browser.getCurrentUrl());

    }

    /**
     * Test valid vehicle adding
     */
    @Test
    @Rollback
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

        String selectedTransportLineXPath = "/html/body/ngb-modal-window/div/div/div[2]/form/div/select[2]/option[3]";
        String selectedTransportLineName = browser.findElement(By.xpath(selectedTransportLineXPath)).getText();

        browser.findElement(By.xpath(selectedTransportLineXPath)).click();
        vehiclePage.getButtonSave().click();

        vehiclePage.ensureIsAdded(beforeCount);

        // new vehicle card is placed at add card position
        List<WebElement> allCardNames = browser.findElements(By.cssSelector("h4.card-title.text-center"));
        List<WebElement> allCardCurrentLines = browser.findElements(By.cssSelector("div.card-body p.text-center"));
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
    @Rollback
    public void testEditVehicle() {

        vehiclePage.ensureIsDisplayed();

        vehiclePage.getButtonEditVehicle().click();

        vehiclePage.ensureIsDisplayedButtonSave();
        assertThat(vehiclePage.getInputName().isDisplayed()).isTrue();
        assertThat(vehiclePage.getInputType().isDisplayed()).isTrue();
        assertThat(vehiclePage.getInputCurrentLine().isDisplayed()).isTrue();

        String beforeName = vehiclePage.getInputName().getText();

        vehiclePage.setInputName("NS-T-824-P7");
        vehiclePage.getOptionTram().click();

        String selectedTransportLineXPath = "/html/body/ngb-modal-window/div/div/div[2]/form/div/select[2]/option[2]";
        String selectedTransportLineName = browser.findElement(By.xpath(selectedTransportLineXPath)).getText();

        browser.findElement(By.xpath(selectedTransportLineXPath)).click();
        vehiclePage.getButtonSave().click();

        vehiclePage.ensureIsEdited(beforeName);

        List<WebElement> allCardNames = browser.findElements(By.cssSelector("h4.card-title.text-center"));
        List<WebElement> allCardCurrentLines = browser.findElements(By.cssSelector("div.card-body p.text-center"));
        WebElement newVehicleName = allCardNames.get(1);
        WebElement newVehicleCurrentLine = allCardCurrentLines.get(1);

        assertThat(newVehicleName.getText()).isEqualTo("NS-T-824-P7");
        assertThat(newVehicleCurrentLine.getText()).contains(selectedTransportLineName);
    }

    /**
     * Test valid vehicle deletion
     */
    @Test
    @Rollback
    public void testRemoveVehicle() {

        vehiclePage.ensureIsDisplayed();

        int beforeCount = vehiclePage.numberOfVehicles();

        vehiclePage.getButtonDeleteVehicle().click();

        vehiclePage.ensureIsRemoved(beforeCount);

        assertThat(vehiclePage.numberOfVehicles()).isEqualTo(beforeCount - 1);
        assertThat(vehiclePage.getDeleteNameVehicle().getText()).isNotEqualTo("NS-T-824-P7");
    }

    /**
     * Test valid vehicle saving with minimum name length
     */
    @Test
    @Rollback
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

        String selectedTransportLineXPath = "/html/body/ngb-modal-window/div/div/div[2]/form/div/select[2]/option[3]";
        String selectedTransportLineName = browser.findElement(By.xpath(selectedTransportLineXPath)).getText();

        browser.findElement(By.xpath(selectedTransportLineXPath)).click();
        vehiclePage.getButtonSave().click();

        vehiclePage.ensureIsAdded(beforeCount);

        // new vehicle card is placed at add card position
        List<WebElement> allCardNames = browser.findElements(By.cssSelector("h4.card-title.text-center"));
        List<WebElement> allCardCurrentLines = browser.findElements(By.cssSelector("div.card-body p.text-center"));
        WebElement newVehicleName = allCardNames.get(allCardNames.size() - 2);
        WebElement newVehicleCurrentLine = allCardCurrentLines.get(allCardCurrentLines.size() - 1);

        assertThat(vehiclePage.numberOfVehicles()).isEqualTo(beforeCount + 1);
        assertThat(newVehicleName.getText()).isEqualTo("aaa");
        assertThat(newVehicleCurrentLine.getText()).contains(selectedTransportLineName);
    }

    /**
     * Test valid vehicle saving with maximum name length
     */
    @Test
    @Rollback
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

        String selectedTransportLineXPath = "/html/body/ngb-modal-window/div/div/div[2]/form/div/select[2]/option[3]";
        String selectedTransportLineName = browser.findElement(By.xpath(selectedTransportLineXPath)).getText();

        browser.findElement(By.xpath(selectedTransportLineXPath)).click();
        vehiclePage.getButtonSave().click();

        vehiclePage.ensureIsAdded(beforeCount);

        // new vehicle card is placed at add card position
        List<WebElement> allCardNames = browser.findElements(By.cssSelector("h4.card-title.text-center"));
        List<WebElement> allCardCurrentLines = browser.findElements(By.cssSelector("div.card-body p.text-center"));
        WebElement newVehicleName = allCardNames.get(allCardNames.size() - 2);
        WebElement newVehicleCurrentLine = allCardCurrentLines.get(allCardCurrentLines.size() - 1);

        assertThat(vehiclePage.numberOfVehicles()).isEqualTo(beforeCount + 1);
        assertThat(newVehicleName.getText()).isEqualTo("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertThat(newVehicleCurrentLine.getText()).contains(selectedTransportLineName);
    }

    /**
     * Test invalid vehicle saving with null name and type
     */
    @Test
    @Rollback
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

        assertThat(vehiclePage.getSpanFirstError().getText()).isEqualTo("Vehicle name is required!");
        assertThat(vehiclePage.getSpanSecondError().getText()).isEqualTo("Vehicle type is required!");
        vehiclePage.getSpanExitModalForm().click();
        assertThat(vehiclePage.numberOfVehicles()).isEqualTo(beforeCount);
    }

    /**
     * Test invalid vehicle saving with null name
     */
    @Test
    @Rollback
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

        assertThat(vehiclePage.getSpanFirstError().getText()).isEqualTo("Vehicle name is required!");
        vehiclePage.getSpanExitModalForm().click();
        assertThat(vehiclePage.numberOfVehicles()).isEqualTo(beforeCount);
    }

    /**
     * Test invalid vehicle saving with short name
     */
    @Test
    @Rollback
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

        assertThat(vehiclePage.getSpanFirstError().getText())
                .isEqualTo("Vehicle name must be at least 3 characters long!");
        vehiclePage.getSpanExitModalForm().click();
        assertThat(vehiclePage.numberOfVehicles()).isEqualTo(beforeCount);
    }

    /**
     * Test invalid vehicle saving with long name
     */
    @Test
    @Rollback
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

        assertThat(vehiclePage.getSpanFirstError().getText())
                .isEqualTo("Vehicle name must be maximum 30 characters long!");
        vehiclePage.getSpanExitModalForm().click();
        assertThat(vehiclePage.numberOfVehicles()).isEqualTo(beforeCount);
    }

    /**
     * Test invalid vehicle saving with null type
     */
    @Test
    @Rollback
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
        vehiclePage.ensureIsDisplayedFirstError();

        assertThat(vehiclePage.getSpanFirstError().getText()).isEqualTo("Vehicle type is required!");
        vehiclePage.getSpanExitModalForm().click();
        assertThat(vehiclePage.numberOfVehicles()).isEqualTo(beforeCount);
    }

    @AfterMethod
    public void closeSelenium() {
        browser.quit();
    }
}