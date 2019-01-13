package construction_and_testing.public_transport_system.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WelcomePage {

    private WebDriver driver;

    @FindBy(id = "signinButton")
    private WebElement logInButton;

    @FindBy(name = "username")
    private WebElement inputUsername;

    @FindBy(name = "password")
    private WebElement inputPassword;

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

    @FindBy(xpath = "//*[@id=\"sidebar\"]/ul[2]/li/a")
    private WebElement logoutButton;

    public WelcomePage(WebDriver driver) { this.driver = driver; }


    public WebElement getLoginButton() {
        return logInButton;
    }

    public void setUsername(String value) {
        WebElement el = getInputUsername();
        el.clear();
        el.sendKeys(value);
    }

    private WebElement getInputUsername() {
        return inputUsername;
    }

    public WebElement getInputPassword() {
        return inputPassword;
    }

    public void setPassword(String value) {
        WebElement el = getInputPassword();
        el.clear();
        el.sendKeys(value);
    }

    public WebElement getValidatorsLink(){
        return validatorsLink;
    }

    public WebElement getUsersField(){
        return usersField;
    }

    public WebElement getUnconfirmedUsersLink() {
        return unconfirmedUsersLink;
    }

    public WebElement getRegUsersLink() { return regUsersLink; }

    public WebElement getReportLink() { return reportLink; }

    public WebElement getLogoutButton() { return logoutButton; }

    public void ensureTLogoutDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(logoutButton));
    }
}
