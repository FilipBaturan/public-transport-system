package construction_and_testing.public_transport_system.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MapPage {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"original\"]/div/div[1]/div[2]/div[2]/svg")
    private WebElement svgMapViewer;

    @FindBy(xpath = "//*[@id=\"original\"]/div/div[2]/div[1]/div/a[1]")
    private WebElement buttonZoom;

    @FindBy(xpath = "//*[@id=\"content\"]/div[3]/div/button")
    private WebElement buttonEdit;

    @FindBy(xpath = "//*[@id=\"edit\"]/div/div[2]/div[1]/div[3]/div/div/a")
    private WebElement buttonDraw;

    @FindBy(xpath = "//*[@id=\"edit\"]/div/div[2]/div[1]/div[4]/a")
    private WebElement buttonApply;

    @FindBy(xpath = "//*[@id=\"edit\"]/div/div[1]/div[2]/div[4]/div/div[1]/div/div/div[2]/div[4]/div")
    private WebElement buttonWidth10;

    @FindBy(xpath = "//*[@id=\"edit\"]/div/div[1]/div[2]/div[4]/div/div[1]/div/div/div[1]/div[1]")
    private WebElement buttonColorBlack;

    @FindBy(xpath = "//*[@id=\"edit\"]/div/div[1]/div[2]/div[4]/div/div[1]/div/input[1]")
    private WebElement inputStationName;

    @FindBy(xpath = "//*[@id=\"edit\"]/div/div[1]/div[2]/div[4]/div/div[1]/div/input[2]")
    private WebElement buttonTypeBus;

    @FindBy(xpath = "//*[@id=\"edit\"]/div/div[1]/div[2]/div[4]/div/div[1]/div/input[3]")
    private WebElement buttonTypeMetro;

    @FindBy(xpath = "//*[@id=\"edit\"]/div/div[1]/div[2]/div[4]/div/div[1]/div/input[4]")
    private WebElement buttonTypeTram;

    @FindBy(css = "div.leaflet-popup-content > input:first-of-type")
    private WebElement inputEditStationName;

    @FindBy(xpath = "//*[@id=\"edit\"]/div/div[1]/div[2]/div[4]/div/div[1]/div/input[2]")
    private WebElement buttonEditDeleteStation;

    @FindBy(xpath = "//*[@id=\"edit\"]/div/div[1]/div[2]/div[4]/div/div[1]/div/input[3]")
    private WebElement buttonEditRenameStation;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[2]/form/div/input")
    private WebElement inputEditTransportLineName;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[2]/form/div/select/option[1]")
    private WebElement selectOptionBus;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[2]/form/div/select/option[2]")
    private WebElement selectOptionMetro;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[2]/form/div/select/option[3]")
    private WebElement selectOptionTram;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[3]/button")
    private WebElement buttonSave;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[2]/form/div/div/span")
    private WebElement spanFirstError;

    @FindBy(xpath = "//*[@id=\"edit\"]/div/div[1]/div[2]/div[4]/div/div[1]/div/div/div[4]/input[2]")
    private WebElement buttonRemoveTransportLine;

    public MapPage() {
    }

    public MapPage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(buttonZoom));
    }

    public void ensureIsDisplayedEditor() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(buttonDraw));
    }

    public void ensureIsDisplayedButtonEdit() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(buttonEdit));
    }

    public void ensureIsDisplayedTransportLinePopUp() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(buttonWidth10));
    }

    public void ensureIsDisplayedStationPopUp() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(inputStationName));
    }

    public void ensureIsDisplayedStationEditPopUp() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(buttonEditDeleteStation));
    }

    public void ensureIsAddedStations(int previousNumberOfStations, int numberOfNewStations) {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.numberOfElementsToBe(
                        By.cssSelector("img.leaflet-marker-icon.leaflet-zoom-animated.leaflet-clickable"),
                        previousNumberOfStations + numberOfNewStations));
    }

    public void ensureIsDeletedStations(int previousNumberOfStations, int numberOfDeletedStations) {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.numberOfElementsToBe(
                        By.cssSelector("img.leaflet-marker-icon.leaflet-zoom-animated.leaflet-clickable"),
                        previousNumberOfStations - numberOfDeletedStations));
    }

    public void ensureIsDisplayedButtonSave() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(buttonSave));
    }

    public void ensureIsAddedTransportLine(int previousNumberOfTransportLines) {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.numberOfElementsToBe(
                        By.cssSelector("button.view"), previousNumberOfTransportLines + 1));
    }

    public void ensureIsEditedTransportLine(String editedName) {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.textToBe(By
                        .xpath("//*[@id=\"content\"]/div[3]/div/div[1]/button[1]"), editedName));
    }

    public void ensureIsRemovedTransportLine(int previousNumberOfTransportLines) {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.numberOfElementsToBe(
                        By.cssSelector("button.view"), previousNumberOfTransportLines - 1));
    }

    public void ensureIsDisplayedFirstError() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(spanFirstError));
    }

    public int numberOfTransportLines() {
        return driver.findElements(By.cssSelector("button.view")).size();
    }

    public int numberOfStations() {
        return driver.findElements(By
                .cssSelector("img.leaflet-marker-icon.leaflet-zoom-animated.leaflet-clickable")).size();
    }

    public List<WebElement> getTransportLines() {
        return driver.findElements(By.cssSelector("button.view"));
    }

    public List<WebElement> getStationsOnEditMap() {
        return driver.findElements(By.cssSelector("img.leaflet-marker-icon.leaflet-zoom-animated.leaflet-clickable." +
                "leaflet-marker-draggable"));
    }

    public WebElement getEditButtonTransportLine() {
        return driver.findElements(By.cssSelector("button.btn.btn-primary.edit.ng-star-inserted")).get(0);
    }

    public WebElement getSvgMapViewer() {
        return svgMapViewer;
    }

    public WebElement getButtonZoom() {
        return buttonZoom;
    }

    public WebElement getButtonEdit() {
        return buttonEdit;
    }

    public WebElement getButtonDraw() {
        return buttonDraw;
    }

    public WebElement getButtonApply() {
        return buttonApply;
    }

    public WebElement getButtonWidth10() {
        return buttonWidth10;
    }

    public WebElement getButtonColorBlack() {
        return buttonColorBlack;
    }

    public WebElement getInputStationName() {
        return inputStationName;
    }

    public WebElement getButtonTypeBus() {
        return buttonTypeBus;
    }

    public WebElement getButtonTypeMetro() {
        return buttonTypeMetro;
    }

    public WebElement getButtonTypeTram() {
        return buttonTypeTram;
    }

    public WebElement getInputEditStationName() {
        return inputEditStationName;
    }

    public void setInputEditStationName(String value) {
        inputEditStationName.clear();
        inputEditStationName.sendKeys(value);
    }

    public WebElement getButtonEditDeleteStation() {
        return buttonEditDeleteStation;
    }

    public WebElement getButtonEditRenameStation() {
        return buttonEditRenameStation;
    }

    public WebElement getInputEditTransportLineName() {
        return inputEditTransportLineName;
    }

    public void setInputEditTransportLineName(String value) {
        inputEditTransportLineName.clear();
        inputEditTransportLineName.sendKeys(value);
    }

    public WebElement getSelectOptionBus() {
        return selectOptionBus;
    }

    public WebElement getSelectOptionMetro() {
        return selectOptionMetro;
    }

    public WebElement getSelectOptionTram() {
        return selectOptionTram;
    }

    public WebElement getButtonSave() {
        return buttonSave;
    }

    public WebElement getSpanFirstError() {
        return spanFirstError;
    }

    public WebElement getButtonRemoveTransportLine() {
        return buttonRemoveTransportLine;
    }
}
