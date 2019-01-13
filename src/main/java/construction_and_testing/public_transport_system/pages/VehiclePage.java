package construction_and_testing.public_transport_system.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VehiclePage {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div[7]/div/div")
    private WebElement buttonAdd;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[2]/form/div/input")
    private WebElement inputName;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[2]/form/div/select[1]")
    private WebElement inputType;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[2]/form/div/select[2]")
    private WebElement inputCurrentLine;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[3]/button")
    private WebElement buttonSave;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[2]/form/div/select[1]/option[1]")
    private WebElement optionBus;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[2]/form/div/select[1]/option[2]")
    private WebElement optionMetro;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[2]/form/div/select[1]/option[3]")
    private WebElement optionTram;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div[2]/div/div[2]/button[1]")
    private WebElement buttonEditVehicle;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div[2]/div/div[1]/h4")
    private WebElement editVehicleName;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div[2]/div/div[2]/button[2]")
    private WebElement buttonDeleteVehicle;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div[2]/div/div[1]/h4")
    private WebElement deleteNameVehicle;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[2]/form/div/div[1]/span")
    private WebElement spanFirstError;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[2]/form/div/div[2]/span")
    private WebElement spanSecondError;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[1]/button/span")
    private WebElement spanExitModalForm;

    public VehiclePage() {
    }

    public VehiclePage(WebDriver driver) {
        this.driver = driver;
    }

    public int numberOfVehicles() {
        return driver.findElements(By.cssSelector("h4.card-title.text-center")).size();
    }

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(buttonAdd));
    }

    public void ensureIsDisplayedButtonSave() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(buttonSave));
    }

    public void ensureIsAdded(int previousNumberOfVehicles) {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.numberOfElementsToBe(
                        By.cssSelector("h4.card-title.text-center"), previousNumberOfVehicles + 1));
    }

    public void ensureIsEdited(String previousVehicleName) {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions
                        .not(ExpectedConditions.textToBePresentInElementValue(editVehicleName, previousVehicleName)));
    }

    public void ensureIsRemoved(int previousNumberOfVehicles) {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.numberOfElementsToBe(
                        By.cssSelector("h4.card-title.text-center"), previousNumberOfVehicles - 1));
    }

    public void ensureIsDisplayedFirstError() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(spanFirstError));
    }

    public void ensureIsDisplayedSecondError() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(spanSecondError));
    }

    public WebElement getButtonAdd() {
        return buttonAdd;
    }

    public WebElement getInputName() {
        return inputName;
    }

    public void setInputName(String value) {
        inputName.clear();
        inputName.sendKeys(value);
    }

    public WebElement getInputType() {
        return inputType;
    }

    public WebElement getInputCurrentLine() {
        return inputCurrentLine;
    }

    public WebElement getButtonSave() {
        return buttonSave;
    }

    public WebElement getOptionBus() {
        return optionBus;
    }

    public WebElement getOptionMetro() {
        return optionMetro;
    }

    public WebElement getOptionTram() {
        return optionTram;
    }

    public WebElement getButtonEditVehicle() {
        return buttonEditVehicle;
    }

    public WebElement getButtonDeleteVehicle() {
        return buttonDeleteVehicle;
    }

    public WebElement getDeleteNameVehicle() {
        return deleteNameVehicle;
    }

    public WebElement getSpanFirstError() {
        return spanFirstError;
    }

    public WebElement getSpanSecondError() {
        return spanSecondError;
    }

    public WebElement getSpanExitModalForm() {
        return spanExitModalForm;
    }
}