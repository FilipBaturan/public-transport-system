package construction_and_testing.public_transport_system.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReportPage {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div/h1")
    private WebElement title;

    @FindBy(xpath = "/html/body/app-root/app-report/div/div/p/b")
    private WebElement numOfTicketsSold;

    @FindBy(xpath = "//*[@id=\"getReportButton\"]")
    private WebElement showPriceButton;

    @FindBy(xpath = "/html/body/app-root/app-report/div/div/div[3]")
    private WebElement weeklyChart;

    @FindBy(xpath = "/html/body/app-root/app-report/div/div/div[4]")
    private WebElement montlyChart;

    @FindBy(xpath = "//*[@id=\"datePickerDiv\"]/div[1]/input")
    private WebElement inputStartDate;

    

    public ReportPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getNumOfTicketsSold() {
        return numOfTicketsSold;
    }

    public WebElement getShowPriceButton() {
        return showPriceButton;
    }

    public WebElement getWeeklyChart() {
        return weeklyChart;
    }

    public WebElement getMontlyChart() {
        return montlyChart;
    }

    public WebElement getInputStartDate() { return inputStartDate; }

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(title));
    }

    public void ensureChartIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(montlyChart));
    }

    public void setInputStartDate(String value)
    {
        inputStartDate.click();
        inputStartDate.sendKeys(value);
    }
}
