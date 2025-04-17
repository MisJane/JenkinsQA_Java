package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class MultibranchPipelineConfigurationTest extends BaseTest {
    @Test
    public void testEnableDisablePipeline() {
        getDriver().findElement(By.xpath("//a[@data-task-success='Done.']")).click();
        getDriver().findElement(By.id("name")).sendKeys("TEST MULTIBRANCH PIPELINE");
        getDriver().findElement(By.xpath("//span[text()='Multibranch Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        Assert.assertEquals(
                getDriver().findElement(By.className("jenkins-toggle-switch__label__checked-title")).getText(),
                "Enabled");
    }

    @Test
    public void testEnableDisableToggleTooltip() {
        getDriver().findElement(By.xpath("//a[@data-task-success='Done.']")).click();
        getDriver().findElement(By.id("name")).sendKeys("TEST MULTIBRANCH PIPELINE");
        getDriver().findElement(By.xpath("//span[text()='Multibranch Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        Actions action = new Actions(getDriver());
        action.moveToElement(getDriver()
                        .findElement(By.xpath("//span[@title='(No new builds within this Multibranch Pipeline will be executed until it is re-enabled)']")))
                .perform();

        Assert.assertEquals(
                getDriver().findElement(By.id("toggle-switch-enable-disable-project")).getDomAttribute("aria-describedby"),
                "tippy-10");
    }
}
