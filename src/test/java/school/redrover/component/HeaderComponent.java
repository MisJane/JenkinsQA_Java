package school.redrover.component;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BaseComponent;
import school.redrover.page.HomePage;
import school.redrover.page.search.SearchPage;

public class HeaderComponent extends BaseComponent {

    public HeaderComponent(WebDriver driver) {
        super(driver);
    }

    public HomePage clickLogoIcon() {
        //This code make this method stable, otherwise it often fails with StaleElementReferenceException
        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement logo = getWait10().until(ExpectedConditions.elementToBeClickable(By.id("jenkins-home-link")));
                logo.click();
                return new HomePage(getDriver());
            } catch (StaleElementReferenceException e) {
                attempts++;
                if (attempts == 3) throw e;
            }
        }

        return new HomePage(getDriver());
    }

    public HomePage goToHomePage() {
        WebElement logo = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("jenkins-home-link")));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", logo);
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("main-panel")));
        return new HomePage(getDriver());
    }

    public SearchPage clickSearchButton() {
        getWait10().until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("button-open-command-palette")))
                .click();

        return new SearchPage(getDriver());
    }
}
