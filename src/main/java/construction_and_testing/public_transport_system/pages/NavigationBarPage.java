package construction_and_testing.public_transport_system.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NavigationBarPage {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"sidebar\"]/ul[1]/li[2]/a")
    private WebElement mapDropDown;

    @FindBy(css = "#sidebar > ul.list-unstyled.components > li:nth-child(8) > a")
    private WebElement updateScheduleLink;

    @FindBy(css = "#sidebar > ul.list-unstyled.components > li:nth-child(7) > a")
    private WebElement scheduleLink;

    @FindBy(xpath = "//*[@id=\"homeSubmenu\"]/li[3]/a")
    private WebElement vehicleLink;

    @FindBy(xpath = "//*[@id=\"homeSubmenu\"]/li[2]/a")
    private WebElement zoneLink;

    @FindBy(xpath = "//*[@id=\"homeSubmenu\"]/li[1]/a")
    private WebElement routeLink;

    @FindBy(xpath = "//*[@id=\"sidebar\"]/ul[2]/li/a")
    private WebElement buttonLogout;

    @FindBy(id = "operatorsLink")
    private WebElement operatorsLink;

    @FindBy(id = "validatorsLink")
    private WebElement validatorsLink;

    @FindBy(id = "unconfirmedUsersLink")
    private WebElement unconfirmedUsersLink;

    @FindBy(id = "regUsersLink")
    private WebElement regUsersLink;

    @FindBy(id = "reportLink")
    private WebElement reportLink;

    @FindBy(id = "usersField")
    private WebElement usersField;


    public NavigationBarPage() {
    }

    public NavigationBarPage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(mapDropDown));
    }

    public void ensureIsDisplayedMapDropItems() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(vehicleLink));
    }

    public void ensureIsDisplayedScheduleLink() {
        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.elementToBeClickable(scheduleLink));
    }

    public void ensureIsDisplayedUpdateScheduleLink() {
        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.elementToBeClickable(updateScheduleLink));
    }

    public void ensureIsDisplayedLogout() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(buttonLogout));
    }


    public WebElement getMapDropDown() {
        return mapDropDown;
    }

    public WebElement getUpdateScheduleLink() {
        return updateScheduleLink;
    }

    public WebElement getScheduleLink() {
        return scheduleLink;
    }

    public WebElement getVehicleLink() {
        return vehicleLink;
    }

    public WebElement getZoneLink() {
        return zoneLink;
    }

    public WebElement getRouteLink() {
        return routeLink;
    }

    public WebElement getButtonLogout() {
        return buttonLogout;
    }

    public WebElement getOperatorsLink() {
        return operatorsLink;
    }

    public WebElement getValidatorsLink() {
        return validatorsLink;
    }

    public WebElement getUsersField() {
        return usersField;
    }

    public WebElement getUnconfirmedUsersLink() {
        return unconfirmedUsersLink;
    }

    public WebElement getRegUsersLink() {
        return regUsersLink;
    }

    public WebElement getReportLink() {
        return reportLink;
    }
}
