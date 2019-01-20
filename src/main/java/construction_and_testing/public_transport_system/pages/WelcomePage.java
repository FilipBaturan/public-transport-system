package construction_and_testing.public_transport_system.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WelcomePage {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div[1]/div/div[3]/app-auth/form")
    private WebElement formLogin;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div[1]/div/div[3]/app-auth/form/input[1]")
    private WebElement inputUsername;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div[1]/div/div[3]/app-auth/form/input[2]")
    private WebElement inputPassword;

    @FindBy(xpath = "//*[@id=\"signinButton\"]")
    private WebElement logInButton;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div[2]/div/div[2]/button")
    private WebElement buttonSignUp;

    @FindBy(xpath = "//*[@id=\"sidebar\"]/ul[2]/li/a")
    private WebElement logoutButton;

    public WelcomePage() {
    }

    public WelcomePage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getLoginButton() {
        return logInButton;
    }

    private WebElement getInputUsername() {
        return inputUsername;
    }

    public WebElement getInputPassword() {
        return inputPassword;
    }

    public WebElement getLogoutButton() {
        return logoutButton;
    }

    public void ensureLogoutDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(logoutButton));

    }

    public void login(String username, String password) {
        setInputUsername(username);
        setInputPassword(password);
        getButtonSignIn().click();
    }


    public void setInputUsername(String value) {
        inputUsername.clear();
        inputUsername.sendKeys(value);
    }


    public void setInputPassword(String value) {
        inputPassword.clear();
        inputPassword.sendKeys(value);
    }


    public WebElement getButtonSignIn() {
        return logInButton;
    }

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(formLogin));
    }

    public WebElement getButtonSignUp() {
        return buttonSignUp;
    }

    public void setButtonSignUp(WebElement buttonSignUp) {
        this.buttonSignUp = buttonSignUp;
    }
}
