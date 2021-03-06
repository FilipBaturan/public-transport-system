package construction_and_testing.public_transport_system.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OperatorPage {

    private WebDriver driver;

    @FindBy(id = "addOperatorButton")
    private WebElement addButton;

    @FindBy(id = "addOperatorForm")
    private WebElement form;

    @FindBy(name = "fname")
    private WebElement firstNameInput;

    @FindBy(name = "username")
    private WebElement usernameInput;


    @FindBy(id = "formAddOperatorButton")
    private WebElement formAddButton;

    @FindBy(id = "operatorsTable")
    private WebElement table;

    @FindBy(xpath = "//*[@id=\"operatorsTable\"]/tbody/tr[last()]/td[1]")
    private WebElement lastTdFirstName;

    @FindBy(xpath = "//*[@id=\"operatorsTable\"]/tbody/tr[last()]/td[2]")
    private WebElement lastTdLastName;

    @FindBy(xpath = "//*[@id=\"operatorsTable\"]/tbody/tr[last()]/td[3]")
    private WebElement lastTdUsername;

    @FindBy(xpath = "//*[@id=\"operatorsTable\"]/tbody/tr[last()]/td[4]")
    private WebElement lastTdEmail;

    @FindBy(xpath = "//*[@id=\"operatorsTable\"]/tbody/tr[last()]/td[5]")
    private WebElement changeButton;

    @FindBy(xpath = "//*[@id=\"operatorsTable\"]/tbody/tr[last()]/td[6]")
    private WebElement blockButton;

    @FindBy(css = "h1.title yt-formatted-string")
    private WebElement naslov;

    @FindBy(xpath = "//*[@id=\"info\"]")
    private WebElement asd;

    public WebElement getAsd() {
        return asd;
    }

    public OperatorPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getChangeButton() {
        return changeButton;
    }

    public WebElement getBlockButton() {
        return blockButton;
    }

    public WebElement getAddButton() {
        return addButton;
    }

    public WebElement getForm() {
        return form;
    }

    public WebElement getFirstNameInput() {
        return firstNameInput;
    }

    public void setFirstNameInput(String value) {
        WebElement el = getFirstNameInput();
        el.clear();
        el.sendKeys(value);
    }

    public WebElement getFormAddButton() {
        return formAddButton;
    }

    public WebElement getTable() {
        return table;
    }


    public int getTableSize() {

        return getTable().findElements(By.tagName("tr")).size();

    }

    public void ensureButtonIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(addButton));
    }

    public void ensureTableIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(table));
    }

    public void ensureIsChanged(int previousNoOfValidators, int change) {
        //wait for add button to be present
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.numberOfElementsToBe(
                        By.cssSelector("tr"), previousNoOfValidators + change));
    }

    public WebElement getLastTdFirstName() {
        return lastTdFirstName;
    }

    public WebElement getLastTdLastName() {
        return lastTdLastName;
    }

    public WebElement getLastTdEmail() {
        return lastTdEmail;
    }

    public WebElement getLastTdUsername() {
        return lastTdUsername;
    }

    public WebElement getUsernameInput() {
        return usernameInput;
    }

    public void setUsernameInput(String value) {
        WebElement el = getUsernameInput();
        el.clear();
        el.sendKeys(value);
    }
}
