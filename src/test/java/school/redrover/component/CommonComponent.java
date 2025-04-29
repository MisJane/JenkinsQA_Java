package school.redrover.component;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BaseComponent;

public class CommonComponent extends BaseComponent {

    public CommonComponent(WebDriver driver) {
        super(driver);
    }

    public boolean doesUrlContainCreateItemEndpoint() {
        return getWait5().until(ExpectedConditions.urlContains("/createItem"));
    }

    public boolean isHeadingDisplayed() {
        return getWait5().until(ExpectedConditions.elementToBeClickable(By.id("general"))).isDisplayed();
    }

    public String getHeadingText() {
        return getDriver().findElement(By.tagName("h1")).getText();
    }
}
