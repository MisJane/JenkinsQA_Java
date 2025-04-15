package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.common.TestUtils;

public class FreestyleProjectConfigurationTest extends BaseTest {

    @Test
    public void testDisableProject() {
        TestUtils.createFreestyleProject(getDriver(), "Freestyle");

        getWait5().until(ExpectedConditions.elementToBeClickable(By.cssSelector("label[for='enable-disable-project']"))).click();
        getWait5().until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(By.name("Submit")))).click();
        String projectIsDisabledText = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("enable-project"))).getText();

        Assert.assertTrue(projectIsDisabledText.contains("This project is currently disabled"));
    }

    @Test
    public void testEnableProject() {
        TestUtils.createFreestyleProject(getDriver(), "Freestyle");

        getWait5().until(ExpectedConditions.elementToBeClickable(By.cssSelector("label[for='enable-disable-project']"))).click();
        getWait5().until(ExpectedConditions.elementToBeClickable((By.name("Submit")))).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Enable')]"))).click();
        getWait5().until(ExpectedConditions.refreshed(
                ExpectedConditions.elementToBeClickable(By.cssSelector("[href$='configure']")))).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//span[text()='Enabled']")).getText(),
                "Enabled");
    }

    @Test
    public void testWarningMessageDisappears() {
        final By warningMessage = By.id("enable-project");
        TestUtils.createFreestyleProject(getDriver(), "Freestyle");

        getWait5().until(ExpectedConditions.elementToBeClickable(By.cssSelector("label[for='enable-disable-project']"))).click();
        getWait5().until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(By.name("Submit")))).click();

        String projectIsDisabledText = getWait5().until(ExpectedConditions.visibilityOfElementLocated(warningMessage)).getText();
        Assert.assertTrue(projectIsDisabledText.contains("This project is currently disabled"));

        getDriver().findElement(By.xpath("//button[contains(text(),'Enable')]")).click();

        Assert.assertTrue(getWait5().until(ExpectedConditions.invisibilityOfElementLocated(warningMessage)),
                "The warning message is still present" );
    }
}
