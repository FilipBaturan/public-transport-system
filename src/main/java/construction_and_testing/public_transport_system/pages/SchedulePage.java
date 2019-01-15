package construction_and_testing.public_transport_system.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SchedulePage {
    private WebDriver driver;

    private NavigationBarPage navBarPage;

    //@FindBy(xpath = "/html/body/app-root/app-schedule/div/div/angular2-multiselect[1]")
    @FindBy(xpath = "/html/body/app-root/app-schedule/div/div/angular2-multiselect[1]/div/div[1]/div")
    private WebElement dayOfWeekComboCheckBox;

    @FindBy(xpath = "/html/body/app-root/app-schedule/div/div/angular2-multiselect[1]/div/div[2]/div[3]/div[2]/ul/li[1]")
    private WebElement checkDayOfWeek;

    //@FindBy(xpath = "/html/body/app-root/app-schedule/div/div/angular2-multiselect[2]")
    @FindBy(xpath = "/html/body/app-root/app-schedule/div/div/angular2-multiselect[2]/div/div[1]/div")
    private WebElement transportLineComboCheckBox;

    @FindBy(xpath = "/html/body/app-root/app-schedule/div/div/angular2-multiselect[2]/div/div[2]/div[3]/div[3]/ul/li[1]")
    private WebElement checkTransportLine;

    @FindBy(xpath = "/html/body/app-root/app-schedule/div/div/angular2-multiselect[2]/div/div[2]/div[3]/div[3]/ul/li[2]")
    private WebElement checkTransportLine1;

    @FindBy(xpath = "/html/body/app-root/app-schedule/div/div/angular2-multiselect[2]/div/div[2]/div[3]/div[3]/ul/li[3]")
    private WebElement checkTransportLine2;

    @FindBy(xpath = "/html/body/app-root/app-schedule/div/div/table")
    private WebElement scheduleTable;

    @FindBy(xpath = "/html/body/app-root/app-schedule/div/div/table/thead/mat-header-row/mat-header-cell[1]/div/button")
    private WebElement header1;

    @FindBy(xpath = "html/body/app-root/app-schedule/div/div/table/tbody/mat-row[1]/mat-cell[1]")
    private WebElement cell1;

    @FindBy(xpath = "html/body/app-root/app-schedule/div/div/table/tbody/mat-row[1]/mat-cell[2]")
    private WebElement cell2;

    public SchedulePage() {
    }

    public SchedulePage(WebDriver driver) {
        this.driver = driver;
    }

    public SchedulePage(WebDriver driver, NavigationBarPage navBarPage, WebElement dayOfWeekComboCheckBox, WebElement checkDayOfWeek, WebElement transportLineComboCheckBox, WebElement checkTransportLine, WebElement scheduleTable) {
        this.driver = driver;
        this.navBarPage = navBarPage;
        this.dayOfWeekComboCheckBox = dayOfWeekComboCheckBox;
        this.checkDayOfWeek = checkDayOfWeek;
        this.transportLineComboCheckBox = transportLineComboCheckBox;
        this.checkTransportLine = checkTransportLine;
        this.scheduleTable = scheduleTable;
    }

    public void ensureIsDisplayedDayOfWeekComboCheckBox() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(dayOfWeekComboCheckBox));
    }

    public void ensureIsDisplayedTransportLineComboCheckBox() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(transportLineComboCheckBox));
    }

    public void ensureIsDisplayedCheckDayOfWeek() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(checkDayOfWeek));
    }

    public void ensureIsDisplayedScheduleTable() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(scheduleTable));
    }

    public int getNumOfRows(){
        return driver.findElements(By.cssSelector("mat-row")).size();
    }

    public int getNumOfColumns(){
        return driver.findElements(By.cssSelector("mat-header-cell")).size();
    }

    public WebElement getDayOfWeekComboCheckBox() {
        return dayOfWeekComboCheckBox;
    }

    public void setDayOfWeekComboCheckBox(WebElement dayOfWeekComboCheckBox) {
        this.dayOfWeekComboCheckBox = dayOfWeekComboCheckBox;
    }

    public WebElement getTransportLineComboCheckBox() {
        return transportLineComboCheckBox;
    }

    public void setTransportLineComboCheckBox(WebElement transportLineComboCheckBox) {
        this.transportLineComboCheckBox = transportLineComboCheckBox;
    }

    public WebElement getCheckDayOfWeek() {
        return checkDayOfWeek;
    }

    public void setCheckDayOfWeek(WebElement checkDayOfWeek) {
        this.checkDayOfWeek = checkDayOfWeek;
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

    public WebElement getScheduleTable() {
        return scheduleTable;
    }

    public void setScheduleTable(WebElement scheduleTable) {
        this.scheduleTable = scheduleTable;
    }
}
