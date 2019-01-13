package construction_and_testing.public_transport_system.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegUsersPage {

    private WebDriver driver;

    @FindBy(id = "regUsersTable")
    private WebElement regUsersTable;

    @FindBy(id = "usersTickets")
    private WebElement usersTicketsTable;

    @FindBy(xpath = "//*[@id=\"regUsersTable\"]/tbody/tr[1]/td[5]")
    private WebElement ticketsLink;

    @FindBy(xpath = "//*[@id=\"usersTickets\"]/tbody/tr[1]/td[3]")
    private WebElement denyButton;

    @FindBy(xpath = "//*[@id=\"content\"]/h1")
    private WebElement title;

    public RegUsersPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getRegUsersTable() {
        return regUsersTable;
    }

    public WebElement getUsersTicketsTable() {
        return usersTicketsTable;
    }

    public int getRegUsersTableSize() {
        try {
            return getRegUsersTable().findElements(By.tagName("tr")).size();
        } catch (Exception e) {
            // header
            return 1;
        }
    }

    public int getUsersTicketsTableSize() {
        try {
            return getUsersTicketsTable().findElements(By.tagName("tr")).size();
        } catch (Exception e) {
            // header
            return 1;
        }
    }

    public void ensureRegUsersTableIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(regUsersTable));
    }

    public void ensureUsersTicketsTableIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(usersTicketsTable));
    }

    public WebElement getTicketsLink() {
        return ticketsLink;
    }

    public WebElement getDenyButton() {
        return denyButton;
    }

    public WebElement getTitle() { return title; }

    public void ensureTitleIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(title));
    }
}
