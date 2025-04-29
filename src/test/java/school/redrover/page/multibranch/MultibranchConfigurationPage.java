package school.redrover.page.multibranch;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import school.redrover.common.BasePage;

public class MultibranchConfigurationPage extends BasePage {

    public MultibranchConfigurationPage(WebDriver driver) { super(driver); }

    public MultibranchConfigurationPage hoverOnEnabledDisabledToggle() {
        Actions action = new Actions(getDriver());
        action.moveToElement(getDriver().findElement(By.id("toggle-switch-enable-disable-project"))).perform();

        return this;
    }

    public MultibranchConfigurationPage clickEnabledDisabledToggle() {
        getDriver().findElement(By.className("jenkins-toggle-switch__label")).click();

        return this;
    }

    public MultibranchProjectPage clickSaveButton() {
        getDriver().findElement(By.className("jenkins-submit-button")).click();

        return new MultibranchProjectPage(getDriver());
    }

    public String getDisableToggleText() {
        return getDriver().findElement(By.className("jenkins-toggle-switch__label__unchecked-title")).getText();
    }

    public String getEnableToggleText() {
        return getDriver().findElement(By.className("jenkins-toggle-switch__label__checked-title")).getText();
    }

    public String getEnabledDisabledToggleShownAttribute() {
        return getDriver().findElement(By.id("toggle-switch-enable-disable-project")).getDomAttribute("aria-describedby");
    }
}
