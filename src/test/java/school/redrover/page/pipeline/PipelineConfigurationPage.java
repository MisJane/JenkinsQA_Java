package school.redrover.page.pipeline;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class PipelineConfigurationPage extends BasePage {

    public PipelineConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public PipelineConfigurationPage sendDescription(String text) {
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(text);

        return this;
    }

    public PipelineConfigurationPage checkByLabel(String label) {
        getDriver().findElement(By.xpath("//label[contains(text(),'%s')]".formatted(label))).click();

        return this;
    }

    public PipelineConfigurationPage switchToggle() {
        By toggle = By.id("toggle-switch-enable-disable-project");

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(toggle));

        getDriver().findElement(toggle).click();

        return this;
    }

    public String checkTooltipText() {

        WebElement toggle = getDriver().findElement(By.id("toggle-switch-enable-disable-project"));

        String tooltipText = toggle.getAttribute("title");

        return tooltipText;
    }

    public boolean isToggleDisabled() {
        By toggleDisabled = By.xpath("//span[@class='jenkins-toggle-switch__label__unchecked-title']");

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(toggleDisabled));

        return getDriver().findElement(toggleDisabled).isDisplayed();
    }

    public boolean isToggleEnabled() {
        By toggleEnabled = By.xpath("//span[@class='jenkins-toggle-switch__label__checked-title']");

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(toggleEnabled));

        return getDriver().findElement(toggleEnabled).isDisplayed();
    }

    public PipelineProjectPage clickSave() {
        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();

        return new PipelineProjectPage(getDriver());
    }





}
