package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.common.TestUtils;
import school.redrover.page.HomePage;


import java.util.List;
import static org.testng.Assert.*;


public class FreestyleProject4Test extends BaseTest {
    private static final String JOB_NAME = "Test item";

    @Test
    public void testCreateFreestyleProjectWithNoneSCM() {
        TestUtils.createFreestyleProject(getDriver(), JOB_NAME);
        new Actions(getDriver()).moveToElement(getDriver().findElement(
                By.xpath("//label[text()='None']"))).perform();
        getDriver().findElement(By.name("Submit")).click();
        WebElement result = getDriver().findElement(
                By.cssSelector("#main-panel > div.jenkins-app-bar > div.jenkins-app-bar__content.jenkins-build-caption > h1"));
        Assert.assertEquals(result.getText(), JOB_NAME);
    }

    @Test
    public void testCheckDiscardOldBuilds() {
        int expectedClicks = 8;
        int logLimit = 5;

        TestUtils.createFreestyleProject(getDriver(), JOB_NAME);
        getDriver().findElement(By.xpath("//label[contains(text(),'Discard old builds')]")).click();
        getDriver().findElement(By.name("_.numToKeepStr")).sendKeys(String.valueOf(logLimit));
        getDriver().findElement(By.name("Submit")).click();

        WebElement buildButton = getDriver().findElement(
                By.xpath("//a[@href='/job/Test%20item/build?delay=0sec']"));

        for (int i = 0; i < expectedClicks; i++) {
            buildButton.click();
            getWait10().until(ExpectedConditions.numberOfElementsToBe(
                    By.className("app-builds-container__item"),
                    Math.min(i + 1, logLimit)));
        }

        getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.className("app-builds-container")));
        List<WebElement> entries = getDriver().findElements(By.className("app-builds-container__item"));

        assertEquals(entries.size(), logLimit);
    }
}
