package school.redrover.page.multiconfiguration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import school.redrover.common.BasePage;

public class MultiConfigurationConfigurePage extends BasePage {

    public MultiConfigurationConfigurePage(WebDriver driver) {
        super(driver);
    }

    public MultiConfigurationConfigurePage clickEnableToggle() {
        getDriver().findElement(By.className("jenkins-toggle-switch__label")).click();

        return this;
    }

    public MultiConfigurationStatusPage clickSaveButton() {
        getDriver().findElement(By.className("jenkins-submit-button")).click();

        return new MultiConfigurationStatusPage(getDriver());
    }

    public String checkTooltipVisibility() {
        Actions actions = new Actions(getDriver());
        WebElement element = getDriver().findElement(By.id("toggle-switch-enable-disable-project"));
        actions.moveToElement(element).perform();

        return element.getAttribute("aria-describedby");
    }
}