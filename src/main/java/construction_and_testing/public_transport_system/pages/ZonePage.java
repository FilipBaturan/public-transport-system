package construction_and_testing.public_transport_system.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ZonePage {

    private WebDriver driver;

    @FindBy(css = "div.card-body:last-child")
    private WebElement buttonAdd;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[2]/form/div/input")
    private WebElement inputName;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[3]/button")
    private WebElement buttonSave;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[2]/form/div/div[1]/span")
    private WebElement spanNameError;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[1]/button/span")
    private WebElement spanExitModalForm;

    public ZonePage() {
    }

    public ZonePage(WebDriver driver) {
        this.driver = driver;
    }

    public int numberOfZones() {
        return driver.findElements(By.cssSelector("h4.card-title.text-center")).size();
    }

    public List<WebElement> getZoneNames() {
        return driver.findElements(By.cssSelector("h4.card-title.text-center"));
    }

    public List<WebElement> getZonesTransportLines() {
        return driver.findElements(By.cssSelector("div.card-body p.text-center"));
    }

    public WebElement getEditButton() {
        return driver.findElements(By.cssSelector("button.btn.btn-success.w-50")).get(1);
    }

    public List<WebElement> getRemoveButton() {
        return driver.findElements(By.cssSelector("button.btn.btn-danger.w-50"));
    }

    public List<WebElement> getFormAddButtons() {
        return driver.findElements(By.cssSelector("button.btn.btn-success.ml-2"));
    }

    public List<WebElement> getFormRemoveButtons() {
        return driver.findElements(By.cssSelector("button.btn.btn-danger.ml-2"));
    }

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(buttonAdd));
    }

    public void ensureIsDisplayedButtonSave() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(buttonSave));
    }

    public void ensureIsAdded(int previousNumberOfZones) {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.numberOfElementsToBe(
                        By.cssSelector("h4.card-title.text-center"), previousNumberOfZones + 1));
    }

    public void ensureIsEdited(String editedName) {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.textToBe(By
                        .xpath("//*[@id=\"content\"]/div/div[2]/div/div[1]/h4"), editedName));
    }

    public void ensureIsRemoved(int previousNumberOfZones) {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.numberOfElementsToBe(
                        By.cssSelector("h4.card-title.text-center"), previousNumberOfZones - 1));
    }

    public void ensureIsDisplayedFirstError() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(spanNameError));
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

    public WebElement getButtonSave() {
        return buttonSave;
    }

    public WebElement getSpanNameError() {
        return spanNameError;
    }

    public WebElement getSpanExitModalForm() {
        return spanExitModalForm;
    }
}
