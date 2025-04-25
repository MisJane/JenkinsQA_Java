package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.common.TestUtils;
import school.redrover.page.freestyle.FreestyleConfigurationPage;
import school.redrover.page.HomePage;

import java.util.List;

public class FreestyleProjectConfigurationBuildTriggersTest extends BaseTest {

    private static final String PROJECT_NAME = "New project";
    private static final String AUTH_TOKEN = "sometoken8ad01cf431d742977b8cc82";
    private static final String expectedTriggerInfoText = """
        Use the following URL to trigger build remotely: JENKINS_URL/job/New%20project/build?token=TOKEN_NAME or /buildWithParameters?token=TOKEN_NAME
        Optionally append &cause=Cause+Text to provide text that will be included in the recorded build cause.
        """.trim();
    private static final String SCHEDULE = "H 14 * * 1-5";

    @Test
    public void testCheckBuildTriggersSection() {

        //Actions
        FreestyleConfigurationPage freestyleConfigurationPage = new HomePage(getDriver())
                .createJob()
                .sendItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .scrollToTriggersItem();

        //Assertions - Common
        Assert.assertEquals(freestyleConfigurationPage.getSectionNameTriggers(), "Triggers");
        Assert.assertEquals(freestyleConfigurationPage.getSubtitleTextTriggers(),
                "Set up automated actions that start your build based on specific events, like code changes or scheduled times.");
        Assert.assertEquals(freestyleConfigurationPage.countHelperIconsTriggersSection(), 8);

        //Assertions - Trigger builds remotely (e.g., from scripts)
        Assert.assertTrue(freestyleConfigurationPage.isTriggerBuildsRemotelyCheckboxDisplayed());
        Assert.assertTrue(freestyleConfigurationPage.isTriggerBuildsRemotelyCheckboxEnabled());
        Assert.assertEquals(freestyleConfigurationPage
                .getTriggerBuildsRemotelyLabelText(), "Trigger builds remotely (e.g., from scripts)");
        Assert.assertEquals(freestyleConfigurationPage
                .getTriggerBuildsRemotelyHelpIconTitle(), "Help for feature: Trigger builds remotely (e.g., from scripts)");

        //Assertions - Build after other projects are built
        Assert.assertTrue(freestyleConfigurationPage.isBuildAfterProjectsCheckboxDisplayed());
        Assert.assertTrue(freestyleConfigurationPage.isBuildAfterProjectsCheckboxEnabled());
        Assert.assertEquals(freestyleConfigurationPage.getBuildAfterProjectsLabelText(), "Build after other projects are built");
        Assert.assertEquals(freestyleConfigurationPage
                .getBuildAfterProjectsHelpIconTitle(), "Help for feature: Build after other projects are built");

        //Assertions - Build periodically
        Assert.assertTrue(freestyleConfigurationPage.isBuildPeriodicallyCheckboxDisplayed());
        Assert.assertTrue(freestyleConfigurationPage.isBuildPeriodicallyCheckboxEnabled());
        Assert.assertEquals(freestyleConfigurationPage
                .getBuildPeriodicallyLabelText(), "Build periodically");
        Assert.assertEquals(freestyleConfigurationPage.getBuildPeriodicallyHelpIconTitle(), "Help for feature: Build periodically");

        //Assertions - GitHub hook trigger for GITScm polling
        Assert.assertTrue(freestyleConfigurationPage.isGithubHookTriggerCheckboxDisplayed());
        Assert.assertTrue(freestyleConfigurationPage.isGithubHookTriggerCheckboxEnabled());
        Assert.assertEquals(freestyleConfigurationPage.getGithubHookTriggerLabelText(), "GitHub hook trigger for GITScm polling");
        Assert.assertEquals(freestyleConfigurationPage
                .getGithubHookTriggerHelpIconTitle(), "Help for feature: GitHub hook trigger for GITScm polling");
    }

    @Test()
    public void testRemoteTriggerOptionDisplaysTokenField() {

        //Actions
        FreestyleConfigurationPage freestyleConfigurationPage = new HomePage(getDriver())
                .createJob()
                .sendItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .scrollToTriggersItem()
                .clickTriggerBuildsRemotely()
                .enterAuthToken(AUTH_TOKEN)
                .clickSaveButton()
                .clickConfigure()
                .scrollToTriggersItem();

        //Assertions
        Assert.assertEquals(freestyleConfigurationPage.getAuthenticationTokenLabelText(), "Authentication Token");
        Assert.assertEquals(freestyleConfigurationPage.getAuthTokenDomValue(), AUTH_TOKEN);
        Assert.assertEquals(freestyleConfigurationPage.getTriggerInfoText(), expectedTriggerInfoText);
    }

    @Test
    public void testBuildAfterOtherProjectsAreBuiltOptionDisplaysField() {
        WebDriver driver = getDriver();

        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        TestUtils.scrollToItemWithJS(driver,
                driver.findElement(By.id("source-code-management")));

        // Enable “Build after other projects are built”
        driver.findElement(By.xpath("//label[contains(text(), 'Build after other projects are built')]")).click();

        //Wait for the "Projects to watch" field to be displayed and ent
        driver.findElement(By.xpath("//input[@name='_.upstreamProjects']")).sendKeys("New Project");

        //Wait for the "Projects to watch" field to be displayed and enter the project name
        getWait5().until(ExpectedConditions.attributeToBe(By.xpath("//input[@name='_.upstreamProjects']"), "aria-expanded", "true"));
        driver.findElement(By.xpath("//input[@name='_.upstreamProjects']")).sendKeys(Keys.ARROW_DOWN, Keys.ENTER);

        //Click on the "Trigger only if build is stable" checkbox and others
        List<WebElement> labels = driver.findElements(By.xpath("//input[@name='ReverseBuildTrigger.threshold']/following-sibling::label"));
        for (WebElement label : labels) {
            label.click();
        }

        //Click Submit and save the project
        driver.findElement(By.xpath("//button[@name='Submit']")).click();

        //Configure
        driver.findElement(By.xpath("//*[@id=\"tasks\"]/div[5]/span/a")).click();
        TestUtils.scrollToItemWithJS(driver,
                driver.findElement(By.id("source-code-management")));

        //Assertions
        Assert.assertEquals(
                driver.findElement(By.xpath("//input[@name='_.upstreamProjects']"))
                        .getDomAttribute("value"),
                "New project, ",
                "Value attribute doesn't match expected text"
        );

        List<WebElement> radios = driver.findElements(By.name("ReverseBuildTrigger.threshold"));
        Assert.assertEquals(
                radios.get(radios.size() - 1).getDomAttribute("checked"),
                "true",
                "The last radio button should be selected"
        );
    }

    @Test
    public void testBuildPeriodicallyScheduleFieldIsDisplayed() {

        //Actions
        String actualSchedule = new HomePage(getDriver())
                .createJob()
                .sendItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .scrollToTriggersItem()
                .checkBuildPeriodicallyCheckbox()
                .sendScheduleText(SCHEDULE)
                .clickSaveButton()
                .clickConfigure()
                .scrollToTriggersItem()
                .sendScheduleActualText();

        //Assertions
        Assert.assertEquals(actualSchedule, SCHEDULE);
    }
}
