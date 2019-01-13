package construction_and_testing.public_transport_system.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.transaction.Transactional;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static construction_and_testing.public_transport_system.selenium.util.SeleniumProperties.CHROME_DRIVER_PATH;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class ValidatorsPageTest {

    private WebDriver browser;

    WelcomePage welcomePage;
    ValidatorsPage validatorsPage;


    public void logIn()
    {
        welcomePage.setUsername("a");
        welcomePage.setPassword("a");
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
        validatorsPage = PageFactory.initElements(browser, ValidatorsPage.class);
        logIn();
        navigateToValidators();

    }

    private void navigateToValidators() {

        welcomePage.getUsersField().click();
        welcomePage.getValidatorsLink().click();
    }

    @Test
    public void testAddingValidAtor() {

        assertEquals("http://localhost:4200/validators", browser.getCurrentUrl());
        validatorsPage.ensureTableIsDisplayed();
        int sizeBeforeAdding =  validatorsPage.getTableSize();
        validatorsPage.ensureButtonIsDisplayed();
        validatorsPage.getAddButton().click();
        assertThat(validatorsPage.getForm().isDisplayed());

        int mNum = ThreadLocalRandom.current().nextInt(0, 1000 + 1);
        String testUsername = "Test username" + mNum;
        validatorsPage.setUsernameInput(testUsername);
        validatorsPage.getFormAddButton().click();
        validatorsPage.ensureIsChanged(sizeBeforeAdding, 1);

        int sizeAfter = validatorsPage.getTableSize();
        assertEquals(sizeBeforeAdding + 1, sizeAfter);

        assertEquals(validatorsPage.getLastTdUsername().getText(), testUsername);

    }

    @Test
    public void testAddingInvalidator() {

        assertEquals("http://localhost:4200/validators", browser.getCurrentUrl());
        validatorsPage.ensureTableIsDisplayed();
        int sizeBeforeAdding =  validatorsPage.getTableSize();
        validatorsPage.ensureButtonIsDisplayed();
        validatorsPage.getAddButton().click();
        assertTrue(validatorsPage.getForm().isDisplayed());

        String testUsername = "l";
        validatorsPage.setUsernameInput(testUsername);
        validatorsPage.getFormAddButton().click();

        int sizeAfter = validatorsPage.getTableSize();
        assertEquals(sizeBeforeAdding, sizeAfter);

    }

    @Test
    public void blockValidator()
    {
        assertEquals("http://localhost:4200/validators", browser.getCurrentUrl());
        validatorsPage.ensureTableIsDisplayed();
        int sizeBeforeAdding =  validatorsPage.getTableSize();
        validatorsPage.getBlockButton().click();
        validatorsPage.ensureIsChanged(sizeBeforeAdding, -1);

        int sizeAfter = validatorsPage.getTableSize();
        assertEquals(sizeBeforeAdding, sizeAfter + 1);

    }

    @Test
    public void changeValidator() throws InterruptedException {
        assertEquals("http://localhost:4200/validators", browser.getCurrentUrl());
        validatorsPage.ensureTableIsDisplayed();
        int sizeBeforeAdding =  validatorsPage.getTableSize();
        validatorsPage.getChangeButton().click();

        int mNum = ThreadLocalRandom.current().nextInt(0, 1000 + 1);
        String newFirstName = "New firstname" + mNum;
        validatorsPage.setFirstNameInput(newFirstName);
        validatorsPage.getFormAddButton().click();

        //browser.manage().timeouts().implicitlyWait(10, TimeUnit.MINUTES);
        Thread.sleep(2000);

        int sizeAfter = validatorsPage.getTableSize();
        assertEquals(sizeBeforeAdding, sizeAfter);

        assertEquals(validatorsPage.getLastTdFirstName().getText(), newFirstName);

    }


    @AfterMethod
    public void closeSelenium() {
        browser.quit();
    }
}
