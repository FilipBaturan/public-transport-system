package construction_and_testing.public_transport_system.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UnconfirmedUsersPage {

    private WebDriver driver;

    @FindBy(id = "uncUsersTable")
    private WebElement table;

    @FindBy(xpath = "//*[@id=\"uncUsersTable\"]/tbody/tr[1]/td[5]")
    private WebElement checkLink;

    @FindBy(xpath = "//*[@id=\"uncUsersTable\"]/tbody/tr[1]/td[6]")
    private WebElement acceptButton;

    @FindBy(xpath = "//*[@id=\"uncUsersTable\"]/tbody/tr[1]/td[7]")
    private WebElement denyButton;

    public UnconfirmedUsersPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getTable() {
        return table;
    }

    public int getTableSize() {
        try {
            return getTable().findElements(By.tagName("tr")).size();
        } catch (Exception e) {
            // header
            return 1;
        }
    }

    public void ensureTableIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(table));
    }

    public void ensureIsChanged(int numberOfTickets, int change) {
        //wait for add button to be present
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.numberOfElementsToBe(
                        By.cssSelector("tr"), numberOfTickets + change));
    }

    public WebElement getCheckLink() {
        return checkLink;
    }

    public WebElement getAcceptButton() {
        return acceptButton;
    }

    public WebElement getDenyButton() {
        return denyButton;
    }

}
