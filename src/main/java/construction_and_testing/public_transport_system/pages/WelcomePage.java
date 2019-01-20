package construction_and_testing.public_transport_system.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WelcomePage {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div[1]/div/div[3]/app-auth/form/input[1]")
    private WebElement inputUsername;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div[1]/div/div[3]/app-auth/form/input[2]")
    private WebElement inputPassword;

    @FindBy(xpath = "//*[@id=\"signinButton\"]")
    private WebElement buttonSignIn;

    @FindBy(xpath = "//*[@id=\"content\"]/div/div[2]/div/div[2]/button")
    private WebElement buttonSignUp;

    public WelcomePage() {
    }

    public WelcomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[@id=\"content\"]/div/div[1]/div/div[3]/app-auth/form/input[1]")));
    }

    public void login(String username, String password){
        setInputUsername(username);
        setInputPassword(password);
        getButtonSignIn().click();
    }

    public WebElement getInputUsername() {
        return inputUsername;
    }

    public void setInputUsername(String value) {
        inputUsername.clear();
        inputUsername.sendKeys(value);
    }

    public WebElement getInputPassword() {
        return inputPassword;
    }

    public void setInputPassword(String value) {
        inputPassword.clear();
        inputPassword.sendKeys(value);
    }

    public WebElement getButtonSignIn() {
        return buttonSignIn;
    }

    public WebElement getButtonSignUp() {
        return buttonSignUp;
    }

    public void setButtonSignUp(WebElement buttonSignUp) {
        this.buttonSignUp = buttonSignUp;
    }
}
