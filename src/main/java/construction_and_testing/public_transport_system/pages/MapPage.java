package construction_and_testing.public_transport_system.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MapPage {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"original\"]/div/div[1]/div[2]/div[2]/svg")
    private WebElement svgMapViewer;

    @FindBy(xpath = "//*[@id=\"original\"]/div/div[2]/div[1]/div/a[1]")
    private WebElement buttonZoom;

    @FindBy(xpath = "//*[@id=\"content\"]/button")
    private WebElement buttonEdit;

    @FindBy(xpath = "//*[@id=\"edit\"]/div/div[2]/div[1]/div[3]/div/div/a")
    private WebElement buttonDraw;

    @FindBy(xpath = "//*[@id=\"edit\"]/div/div[2]/div[1]/div[4]/a")
    private WebElement buttonApply;

    public MapPage() {
    }

    public MapPage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(buttonZoom));
    }

    public void ensureIsDisplayedEditor() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(buttonDraw));
    }

    public void ensureIsDisplayedButtonEdit() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(buttonEdit));
    }

    public void ensureIsAddedLine(int previousNumberOfTransportLines) {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.numberOfElementsToBe(
                        By.cssSelector("button.view"), previousNumberOfTransportLines + 1));
    }

    public int numberOfTransportLines(){
        return driver.findElements(By.cssSelector("button.view")).size();
    }

    public WebElement getSvgMapViewer() {
        return svgMapViewer;
    }

    public WebElement getButtonZoom() {
        return buttonZoom;
    }

    public WebElement getButtonEdit() {
        return buttonEdit;
    }

    public WebElement getButtonDraw() {
        return buttonDraw;
    }

    public WebElement getButtonApply() {
        return buttonApply;
    }
}
