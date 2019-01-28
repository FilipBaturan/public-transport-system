package construction_and_testing.public_transport_system.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UpdateSchedulePage {
    private WebDriver driver;

    private NavigationBarPage navBarPage;

    //@FindBy(xpath = "/html/body/app-root/app-schedule/div/div/angular2-multiselect[2]")
    @FindBy(css = ".c-btn")
    private WebElement transportLineComboCheckBox;

    @FindBy(xpath = "//*[@id=\"content\"]/angular2-multiselect/div/div[2]/div[3]/div[3]/ul/li[2]")
    private WebElement checkTransportLine;

    @FindBy(xpath = "//*[@id=\"content\"]/angular2-multiselect/div/div[2]/div[3]/div[3]/ul/li[3]")
    private WebElement checkTransportLine1;

    @FindBy(xpath = "//*[@id=\"content\"]/angular2-multiselect/div/div[2]/div[3]/div[3]/ul/li[5]")
    private WebElement checkTransportLine2;

    @FindBy(css = "#content > table")
    private WebElement scheduleTable;

    @FindBy(xpath = "//*[@id=\"content\"]/table/thead/mat-header-row/mat-header-cell[1]/")
    private WebElement header1;

    ////*[@id="mat-input-63"]
    @FindBy(xpath = "//*[@id=\"mat-input-0\"]")
    private WebElement cell1;

    @FindBy(xpath = "//*[@id=\"mat-input-1\"]")
    private WebElement cell2;

    @FindBy(xpath = "//*[@id=\"mat-input-2\"]")
    private WebElement cell3;

    @FindBy(xpath = "//*[@id=\"mat-input-10\"]")
    private WebElement cell4;

    @FindBy(css = "mat-header-cell.mat-header-cell:nth-child(2) > button:nth-child(1)")
    private WebElement addRemoveScheduleButton;

    @FindBy(css = "button.schedule-btns-lower:nth-child(1)")
    private WebElement addRowButton;

    @FindBy(css = "button.schedule-btns-lower:nth-child(2)")
    private WebElement removeRowButton;

    @FindBy(css = "button.schedule-btns-lower:nth-child(3)")
    private WebElement saveSchedule;

    public UpdateSchedulePage() {
    }

    public UpdateSchedulePage(WebDriver driver) {
        this.driver = driver;
    }

    public UpdateSchedulePage(WebDriver driver, NavigationBarPage navBarPage, WebElement transportLineComboCheckBox, WebElement checkTransportLine, WebElement scheduleTable) {
        this.driver = driver;
        this.navBarPage = navBarPage;
        this.transportLineComboCheckBox = transportLineComboCheckBox;
        this.checkTransportLine = checkTransportLine;
        this.scheduleTable = scheduleTable;
    }

    public void ensureIsDisplayedTransportLineComboCheckBox() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(transportLineComboCheckBox));
    }

    public void ensureIsDisplayedScheduleTable() {
        (new WebDriverWait(driver, 30))
                .until(ExpectedConditions.visibilityOf(scheduleTable));
    }

    public void ensureIsDisplayedAllHeaderCells() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("mat-header-cell")));
    }

    public void ensureIsDisplayedAllRows() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("mat-row")));
    }

    public void ensureHasCorrectSizeHeaderCells(int numHeaders) {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("mat-header-cell"),numHeaders));
    }

    public void ensureHasCorrectSizeRows(int numRows) {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("mat-row"),numRows));
    }

    public void ensureIsDisplayedAddRemoveScheduleBtn() {
        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.elementToBeClickable(addRemoveScheduleButton));
    }

    public void ensureIsSaveScheduleBtn() {
        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.elementToBeClickable(saveSchedule));
    }

    public void ensureIsAddRowButton() {
        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.elementToBeClickable(addRowButton));
    }

    public void ensureIsRemoveRowBtn() {
        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.elementToBeClickable(removeRowButton));
    }

    public void ensureIsDisplayedCheckBox1() {
        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.elementToBeClickable(checkTransportLine));
    }

    public void ensureIsDisplayedCheckBox2() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(checkTransportLine1));
    }

    public void ensureIsDisplayedCheckBox3() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(checkTransportLine2));
    }

    public void ensureIsDisplayedCell1() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.textToBe(By.xpath("//*[@id=\"mat-input-0\"]"), "07:55"));
    }

    public void ensureIsDisplayedCell4() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.textToBe(By.xpath("//*[@id=\"mat-input-10\"]"), ""));
    }

    public int getNumOfRows() {
        return driver.findElements(By.cssSelector("mat-row")).size();
    }

    public int getNumOfColumns() {
        return driver.findElements(By.cssSelector("mat-header-cell")).size();
    }

    public WebElement getTransportLineComboCheckBox() {
        return transportLineComboCheckBox;
    }

    public void setTransportLineComboCheckBox(WebElement transportLineComboCheckBox) {
        this.transportLineComboCheckBox = transportLineComboCheckBox;
    }

    public WebElement getCheckTransportLine() {
        return checkTransportLine;
    }

    public void setCheckTransportLine(WebElement checkTransportLine) {
        this.checkTransportLine = checkTransportLine;
    }

    public WebElement getCheckTransportLine1() {
        return checkTransportLine1;
    }

    public void setCheckTransportLine1(WebElement checkTransportLine1) {
        this.checkTransportLine1 = checkTransportLine1;
    }

    public WebElement getCheckTransportLine2() {
        return checkTransportLine2;
    }

    public void setCheckTransportLine2(WebElement checkTransportLine2) {
        this.checkTransportLine2 = checkTransportLine2;
    }

    public WebElement getHeader1() {
        return header1;
    }

    public WebElement getCell1() {
        return cell1;
    }

    public void setCell1(WebElement cell1) {
        this.cell1 = cell1;
    }

    public WebElement getCell2() {
        return cell2;
    }

    public void setCell2(WebElement cell2) {
        this.cell2 = cell2;
    }

    public WebElement getCell3() {
        return cell3;
    }

    public void setCell3(WebElement cell3) {
        this.cell3 = cell3;
    }

    public WebElement getCell4() {
        return cell4;
    }

    public WebElement getAddRemoveScheduleButton() {
        return addRemoveScheduleButton;
    }

    public WebElement getAddRowButton() {
        return addRowButton;
    }

    public WebElement getRemoveRowButton() {
        return removeRowButton;
    }

    public WebElement getSaveSchedule() {
        return saveSchedule;
    }

    public WebElement getScheduleTable() {
        return scheduleTable;
    }

    public void setScheduleTable(WebElement scheduleTable) {
        this.scheduleTable = scheduleTable;
    }
}
