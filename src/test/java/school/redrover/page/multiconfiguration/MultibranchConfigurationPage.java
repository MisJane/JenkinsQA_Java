package school.redrover.page.multiconfiguration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.common.BasePage;

public class MultibranchConfigurationPage extends BasePage {

    public MultibranchConfigurationPage(WebDriver driver) { super(driver); }

    public MultibranchConfigurationPage clickEnableToggle() {
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
}
