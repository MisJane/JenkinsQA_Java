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
    private static final String JOB_NAME_2 = "Second test item";

    @Test
    public void createNewFreestyleProject() {

        String projectName = new HomePage(getDriver())
                .clickNewItem()
                .sendItemName(JOB_NAME)
                .selectFreestyleAndClickOkNoPageChange()
                .waitInvisibilityCreateItemPage()
                .waitUntilTextConfigureToBePresentInH1()
                .clickSaveButton()
                .waitUntilTextNameProjectToBePresentInH1(JOB_NAME)
                .getProjectName();

        assertEquals(projectName, JOB_NAME);

    }

    @Test
    public void testTriggerBuildAfterOtherProjects() {
        final String pageCreateItem = "createItem";
        final String h1Tag = "h1";

        TestUtils.createFreestyleProject(getDriver(), JOB_NAME);
        getWait10().until(ExpectedConditions.invisibilityOfElementLocated(By.id(pageCreateItem)));
        getWait10().until(ExpectedConditions.textToBePresentInElementLocated(By.tagName(h1Tag), "Configure"));
        TestUtils.gotoHomePage(this);
        TestUtils.createFreestyleProject(getDriver(), JOB_NAME_2);
        getWait10().until(ExpectedConditions.invisibilityOfElementLocated(By.id(pageCreateItem)));
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='triggers']/parent::section")));
        TestUtils.scrollAndClickWithJS(getDriver(), getWait10().until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("input[name = 'jenkins-triggers-ReverseBuildTrigger']"))));
        getDriver().findElement(By.name("_.upstreamProjects")).sendKeys(JOB_NAME);
        getDriver().findElement(By.name("Submit")).click();
        getWait5().until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(h1Tag), JOB_NAME_2));
        TestUtils.gotoHomePage(this);
        TestUtils.moveAndClickWithJS(getDriver(),
                getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//td/a/span[text() = '%s']/../button".formatted(JOB_NAME)))));
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='jenkins-dropdown__item__icon']/parent::*[contains(., '%s')]".formatted("Build Now")))).click();
        getWait10().until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("#buildQueue table td.pane"), JOB_NAME_2));
        getDriver().findElement(By.linkText(JOB_NAME_2)).click();
        getWait5().until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(h1Tag), JOB_NAME_2));
        getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(" #jenkins-build-history a[title='Success']")));

        final String buildStatusText = getDriver().findElement(By.id("jenkins-build-history")).getText();
        final List<WebElement> builds = getDriver().findElements(By.cssSelector("div[page-entry-id]"));

        Assert.assertEquals(builds.size(), 1);
        Assert.assertTrue(buildStatusText.contains("Today"));
        Assert.assertTrue(buildStatusText.contains("#1"));
    }

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

    @Test(dependsOnMethods = "testCheckDiscardOldBuilds")
    public void deleteFreestyleProject() {
        getDriver().findElement(By.linkText(JOB_NAME)).click();
        getWait5().until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), JOB_NAME));
        getDriver().findElement(By.xpath("//a[contains(@data-url, 'doDelete')]")).click();
        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.className("jenkins-dialog")));
        getDriver().findElement(By.cssSelector("button[data-id='ok']")).click();
        getWait5().until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), "Welcome to Jenkins!"));

        assertEquals(getDriver().findElement(By.tagName("h1")).getText(), "Welcome to Jenkins!");
    }
}
