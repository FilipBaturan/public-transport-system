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

    @FindBy(xpath = "//*[@id=\"homeSubmenu\"]/li[3]/a")
    private WebElement vehicleLink;

    @FindBy(xpath = "//*[@id=\"homeSubmenu\"]/li[2]/a")
    private WebElement zoneLink;

    @FindBy(xpath = "//*[@id=\"homeSubmenu\"]/li[1]/a")
    private WebElement routeLink;

    @FindBy(xpath = "//*[@id=\"sidebar\"]/ul[2]/li/a")
    private WebElement buttonLogout;

    public NavigationBarPage() {
    }

    public NavigationBarPage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(mapDropDown));
    }

    public void ensureIsDisplayedLogout() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(buttonLogout));
    }


    public WebElement getMapDropDown() {
        return mapDropDown;
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
}
