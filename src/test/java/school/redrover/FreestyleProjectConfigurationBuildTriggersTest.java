package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.freestyle.FreestyleConfigurationPage;
import school.redrover.page.HomePage;

public class FreestyleProjectConfigurationBuildTriggersTest extends BaseTest {

    private static final String PROJECT_NAME = "New project";
    private static final String AUTH_TOKEN = "sometoken8ad01cf431d742977b8cc82";
    private static final String EXPECTED_TRIGGER_INFO_TEXT = """
        Use the following URL to trigger build remotely: JENKINS_URL/job/New%20project/build?token=TOKEN_NAME or /buildWithParameters?token=TOKEN_NAME
        Optionally append &cause=Cause+Text to provide text that will be included in the recorded build cause.
        """.trim();
    private static final String EXPECTED_SCHEDULE = "H 14 * * 1-5";

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
        Assert.assertEquals(freestyleConfigurationPage.getTriggerInfoText(), EXPECTED_TRIGGER_INFO_TEXT);
    }

    @Ignore//FreestyleProjectConfigurationBuildTriggersTest.testBuildAfterOtherProjectsAreBuiltOptionDisplaysField:104 » NullPointer Cannot invoke "String.equals(Object)" because the https://github.com/RedRoverSchool/JenkinsQA_Java_2025_spring/actions/runs/14732685957/job/41350713407?pr=1586
    @Test
    public void testBuildAfterOtherProjectsAreBuiltOptionDisplaysField() {

        //Actions
        FreestyleConfigurationPage freestyleConfigurationPage = new HomePage(getDriver())
                .createJob()
                .sendItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .scrollToTriggersItem()
                .clickBuildAfterProjects()
                .setProjectToWatch(PROJECT_NAME)
                .clickAllReverseBuildTriggerLabels()
                .clickSaveButton()
                .clickConfigure()
                .scrollToTriggersItem();

        //Assertions
        Assert.assertEquals(freestyleConfigurationPage.getCurrentProjectName(), PROJECT_NAME+", ");
        Assert.assertTrue(freestyleConfigurationPage.isLastRadioButtonSelected());
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
                .sendScheduleText(EXPECTED_SCHEDULE)
                .clickSaveButton()
                .clickConfigure()
                .scrollToTriggersItem()
                .sendScheduleActualText();

        //Assertions
        Assert.assertEquals(actualSchedule, EXPECTED_SCHEDULE);
    }
}
