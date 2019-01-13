package construction_and_testing.public_transport_system.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ValidatorsPage {

    private WebDriver driver;

    @FindBy(id = "addButton")
    private WebElement addButton;

    @FindBy(id = "addForm")
    private WebElement form;

    @FindBy(name = "fname")
    private WebElement firstNameInput;

    @FindBy(name = "username")
    private WebElement usernameInput;


    @FindBy(id = "formAddButton")
    private WebElement formAddButton;

    @FindBy(id = "validatorsTable")
    private WebElement table;

    @FindBy(xpath = "//*[@id=\"validatorsTable\"]/tbody/tr[last()]/td[1]")
    private WebElement lastTdFirstName;

    @FindBy(xpath = "//*[@id=\"validatorsTable\"]/tbody/tr[last()]/td[2]")
    private WebElement lastTdLastName;

    @FindBy(xpath = "//*[@id=\"validatorsTable\"]/tbody/tr[last()]/td[3]")
    private WebElement lastTdUsername;

    @FindBy(xpath = "//*[@id=\"validatorsTable\"]/tbody/tr[last()]/td[4]")
    private WebElement lastTdEmail;

    @FindBy(xpath = "//*[@id=\"validatorsTable\"]/tbody/tr[last()]/td[6]")
    private WebElement blockButton;

    @FindBy(xpath = "//*[@id=\"validatorsTable\"]/tbody/tr[last()]/td[5]")
    private WebElement changeButton;

    public ValidatorsPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getChangeButton() { return changeButton; }

    public WebElement getBlockButton() { return blockButton; }

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

    public WebElement getTable()
    {
        return table;
    }


    public int getTableSize(){
        try {
            return getTable().findElements(By.tagName("tr")).size();
        }
        catch (Exception e)
        {
            // header
            return 1;
        }
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
