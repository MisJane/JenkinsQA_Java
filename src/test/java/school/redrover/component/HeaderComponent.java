package school.redrover.component;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BaseComponent;
import school.redrover.page.HomePage;
import school.redrover.page.search.SearchPage;

public class HeaderComponent extends BaseComponent {

    public HeaderComponent(WebDriver driver) {
        super(driver);
    }

    public HomePage clickLogoIcon() {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("general")));
        getDriver().findElement(By.id("jenkins-home-link")).click();

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
