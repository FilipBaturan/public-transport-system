package construction_and_testing.public_transport_system.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignUpPage {

    private WebDriver webDriver;

    @FindBy(xpath = "//*[@id=\"content\"]/div/input[1]")
    private WebElement nameInput;

    @FindBy(xpath = "//*[@id=\"content\"]/div/input[2]")
    private WebElement lastNameInput;

    @FindBy(xpath = "//*[@id=\"content\"]/div/input[3]")
    private WebElement usernameInput;

    @FindBy(xpath = "//*[@id=\"content\"]/div/input[4]")
    private WebElement passwordInput;

    @FindBy(xpath = "//*[@id=\"content\"]/div/input[5]")
    private WebElement emailInput;

    @FindBy(xpath = "//*[@id=\"content\"]/div/input[6]")
    private WebElement telephoneInput;

    @FindBy(xpath = "//*[@id=\"content\"]/div/button")
    private WebElement signUpButton;


    public SignUpPage() {
    }

    public SignUpPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void ensureIsDisplayed(){
        (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.elementToBeClickable(signUpButton));
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement getNameInput() {
        return nameInput;
    }

    public void setNameInput(String nameInput) {
        this.nameInput.clear();
        this.nameInput.sendKeys(nameInput);
    }

    public WebElement getLastNameInput() {
        return lastNameInput;
    }

    public void setLastNameInput(String lastNameInput) {
        this.lastNameInput.clear();
        this.lastNameInput.sendKeys(lastNameInput);
    }

    public WebElement getUsernameInput() {
        return usernameInput;
    }

    public void setUsernameInput(String usernameInput) {
        this.usernameInput.clear();
        this.usernameInput.sendKeys(usernameInput);
    }

    public WebElement getPasswordInput() {
        return passwordInput;
    }

    public void setPasswordInput(String passwordInput) {
        this.passwordInput.clear();
        this.passwordInput.sendKeys(passwordInput);
    }

    public WebElement getEmailInput() {
        return emailInput;
    }

    public void setEmailInput(String emailInput) {
        this.emailInput.clear();
        this.emailInput.sendKeys(emailInput);
    }

    public WebElement getTelephoneInput() {
        return telephoneInput;
    }

    public void setTelephoneInput(String telephoneInput) {
        this.telephoneInput.clear();
        this.telephoneInput.sendKeys(telephoneInput);
    }

    public WebElement getSignUpButton() {
        return signUpButton;
    }

    public void setSignUpButton(WebElement signUpButton) {
        this.signUpButton = signUpButton;
    }
}
