package construction_and_testing.public_transport_system.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.ThreadLocalRandom;

import static construction_and_testing.public_transport_system.pages.util.SeleniumProperties.CHROME_DRIVER_PATH;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class OperatorPageTest{

        private WebDriver browser;

        private WelcomePage welcomePage;

        private NavigationBarPage navigationBarPage;

        private OperatorPage operatorsPage;

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
            operatorsPage = PageFactory.initElements(browser, OperatorPage.class);

            welcomePage.login("null", "null");
            navigationBarPage.getUsersField().click();
            navigationBarPage.getOperatorsLink().click();

        }

        @Test
        public void testAddingOperator() {

            assertEquals("http://localhost:4200/operators", browser.getCurrentUrl());
            operatorsPage.ensureTableIsDisplayed();
            int sizeBeforeAdding = operatorsPage.getTableSize();
            operatorsPage.ensureButtonIsDisplayed();
            operatorsPage.getAddButton().click();
            assertThat(operatorsPage.getForm().isDisplayed());

            int mNum = ThreadLocalRandom.current().nextInt(0, 1000 + 1);
            String testUsername = "Test username" + mNum;
            operatorsPage.setUsernameInput(testUsername);
            operatorsPage.getFormAddButton().click();
            operatorsPage.ensureIsChanged(sizeBeforeAdding, 1);

            int sizeAfter = operatorsPage.getTableSize();
            assertEquals(sizeBeforeAdding + 1, sizeAfter);

            assertEquals(operatorsPage.getLastTdUsername().getText(), testUsername);

        }

        @Test
        public void testAddingOperatorInvalid() {

            assertEquals("http://localhost:4200/operators", browser.getCurrentUrl());
            operatorsPage.ensureTableIsDisplayed();
            int sizeBeforeAdding = operatorsPage.getTableSize();
            operatorsPage.ensureButtonIsDisplayed();
            operatorsPage.getAddButton().click();
            assertTrue(operatorsPage.getForm().isDisplayed());

            String testUsername = "l";
            operatorsPage.setUsernameInput(testUsername);
            operatorsPage.getFormAddButton().click();

            int sizeAfter = operatorsPage.getTableSize();
            assertEquals(sizeBeforeAdding, sizeAfter);

        }

        @Test
        public void blockOperator() {
            assertEquals("http://localhost:4200/operators", browser.getCurrentUrl());
            operatorsPage.ensureTableIsDisplayed();
            int sizeBeforeAdding = operatorsPage.getTableSize();
            operatorsPage.getBlockButton().click();
            operatorsPage.ensureIsChanged(sizeBeforeAdding, -1);

            int sizeAfter = operatorsPage.getTableSize();
            assertEquals(sizeBeforeAdding, sizeAfter + 1);

        }

        @Test
        public void changeOperator() throws InterruptedException {
            assertEquals("http://localhost:4200/operators", browser.getCurrentUrl());
            operatorsPage.ensureTableIsDisplayed();
            int sizeBeforeAdding = operatorsPage.getTableSize();
            operatorsPage.getChangeButton().click();

            int mNum = ThreadLocalRandom.current().nextInt(0, 1000 + 1);
            String newFirstName = "New firstname" + mNum;
            operatorsPage.setFirstNameInput(newFirstName);
            operatorsPage.getFormAddButton().click();

            //browser.manage().timeouts().implicitlyWait(10, TimeUnit.MINUTES);
            Thread.sleep(2000);

            int sizeAfter = operatorsPage.getTableSize();
            assertEquals(sizeBeforeAdding, sizeAfter);

            assertEquals(operatorsPage.getLastTdFirstName().getText(), newFirstName);

        }

        @AfterMethod
        public void closeSelenium() {
            browser.quit();
        }
}
