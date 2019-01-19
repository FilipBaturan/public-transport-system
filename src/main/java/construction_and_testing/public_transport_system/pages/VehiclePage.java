package construction_and_testing.public_transport_system.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class VehiclePage {

    private WebDriver driver;

    @FindBy(css = "div.card-body:last-child")
    private WebElement buttonAdd;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[2]/form/div[1]/div/input")
    private WebElement inputName;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[2]/form/div[2]/div[1]/select")
    private WebElement inputType;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[2]/form/div[2]/div[2]/select")
    private WebElement inputCurrentLine;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[3]/button")
    private WebElement buttonSave;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[2]/form/div[2]/div[1]/select/option[1]")
    private WebElement optionBus;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[2]/form/div[2]/div[1]/select/option[2]")
    private WebElement optionMetro;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[2]/form/div[2]/div[1]/select/option[3]")
    private WebElement optionTram;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[2]/form/div[1]/div/div/span")
    private WebElement spanNameError;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[2]/form/div[2]/div[1]/div/span")
    private WebElement spanVehicleTypeError;

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

    public WebElement selectTransportLine(int option) {
        return driver.findElement(By
                .xpath("/html/body/ngb-modal-window/div/div/div[2]/form/div[2]/div[2]/select/option[" + option + "]"));
    }

    public List<WebElement> getVehicleNames() {
        return driver.findElements(By.cssSelector("h4.card-title.text-center"));
    }

    public List<WebElement> getVehicleCurrentLines() {
        return driver.findElements(By.cssSelector("div.card-body p.text-center"));
    }

    public WebElement getEditButton() {
        return driver.findElements(By.cssSelector("button.btn.btn-success.w-50")).get(0);
    }

    public List<WebElement> getDeleteButtons() {
        return driver.findElements(By.cssSelector("button.btn.btn-danger.w-50"));
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

    public void ensureIsEdited(String editedName) {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.textToBe(By
                        .xpath("//*[@id=\"content\"]/div/div[1]/div/div[1]/h4"), editedName));
    }

    public void ensureIsRemoved(int previousNumberOfVehicles) {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.numberOfElementsToBe(
                        By.cssSelector("h4.card-title.text-center"), previousNumberOfVehicles - 1));
    }

    public void ensureIsDisplayedFirstError() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(spanNameError));
    }

    public void ensureIsDisplayedSecondError() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(spanVehicleTypeError));
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

    public WebElement getSpanNameError() {
        return spanNameError;
    }

    public WebElement getSpanVehicleTypeError() {
        return spanVehicleTypeError;
    }

    public WebElement getSpanExitModalForm() {
        return spanExitModalForm;
    }
}