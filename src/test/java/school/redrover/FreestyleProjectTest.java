package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.component.HeaderComponent;
import school.redrover.page.freestyle.FreestyleConfigurationPage;
import school.redrover.page.freestyle.FreestyleProjectPage;
import school.redrover.page.HomePage;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class FreestyleProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "Freestyle Project";
    private static final String UPDATED_PROJECT_NAME = "NEW Freestyle NAME";
    private static final String PROJECT_DESCRIPTION = "This is a NEW freestyleProject description";
    private static final String SECOND_PROJECT_NAME = "Second Freestyle Project";

    @Test
    public void testCreateFreestyleProject() {
        FreestyleProjectPage freestyleProjectPage = new HomePage(getDriver())
                .createJob()
                .sendItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton();

        Assert.assertEquals(freestyleProjectPage.getProjectName(), PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testCreateFreestyleProject")
    public void testDisableProject() {
        String warningProjectIsDisabled = new HomePage(getDriver())
                .clickOnJobInListOfItems(PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .waitUntilTextNameProjectToBePresentInH1(PROJECT_NAME)
                .clickConfigure()
                .clickEnableDisableToggle()
                .clickSaveButton()
                .getDisabledWarningMessageText();

        Assert.assertEquals(warningProjectIsDisabled, "This project is currently disabled");
    }

    @Test(dependsOnMethods = "testDisableProject")
    public void testEnableProject() {
        String projectIsEnabled = new HomePage(getDriver())
                .clickOnJobInListOfItems(PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .waitUntilTextNameProjectToBePresentInH1(PROJECT_NAME)
                .clickEnableButton()
                .clickConfigure()
                .getProjectStatus();

        Assert.assertEquals(projectIsEnabled, "Enabled");
    }

    @Test(dependsOnMethods = "testEnableProject")
    public void testCreateDuplicate() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItemOnLeftSidePanel()
                .sendItemName(PROJECT_NAME)
                .selectFreestyle()
                .getItemNameInvalidMessage();

        Assert.assertEquals(errorMessage, "» A job already exists with the name ‘%s’".formatted(PROJECT_NAME));
    }

    @Test(dependsOnMethods = "testCreateDuplicate")
    public void testEditDescription() {
        String freestyleProjectDescriptionText = new HomePage(getDriver())
                .clickOnJobInListOfItems(PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickEditDescriptionButton()
                .sendDescription(PROJECT_DESCRIPTION)
                .clickSave()
                .getDescription();

        Assert.assertEquals(freestyleProjectDescriptionText, PROJECT_DESCRIPTION);
    }

    @Test(dependsOnMethods = "testEditDescription")
    public void testRenameFreestyleProject() {
        FreestyleProjectPage freestyleProjectPage = new HomePage(getDriver())
                .clickOnJobInListOfItems(PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickLeftSideMenuRename()
                .sendName(UPDATED_PROJECT_NAME)
                .clickRename();

        Assert.assertEquals(freestyleProjectPage.getProjectName(), UPDATED_PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testRenameFreestyleProject")
    public void testDeleteFreestyleProject() {
        List<String> projectNameList = new HomePage(getDriver())
                .clickOnJobInListOfItems(UPDATED_PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickLeftSideMenuDelete()
                .clickPopUpYesDeleteProject()
                .getProjectNameList();

        Assert.assertEquals(projectNameList.size(), 0);
    }

    @Test
    public void testAddBuildSteps() {
        List<String> projectNameList = new HomePage(getDriver())
                .clickNewItem()
                .sendItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .addBuildSteps(7)
                .addBuildSteps(2)
                .addBuildSteps(3)
                .addBuildSteps(4)
                .addBuildSteps(5)
                .addBuildSteps(6)
                .addBuildSteps(1)
                .getChunkHeaderList();

        assertEquals(projectNameList.size(), 7);
    }

    @Test
    public void testCreateWithDescription() {
        String freestyleProjectDescriptionText = new HomePage(getDriver())
                .clickNewItemOnLeftSidePanel()
                .sendItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .addDescription(PROJECT_DESCRIPTION)
                .clickSaveButton()
                .getDescription();

        Assert.assertEquals(freestyleProjectDescriptionText, PROJECT_DESCRIPTION);
    }

    @Test
    public void testEmptyItemNameField() {
        final String message = "» This field cannot be empty, please enter a valid name";

        String actualMessage = new HomePage(getDriver())
                .createJob()
                .selectFreestyle()
                .getErrorMessageOnEmptyField();

        Assert.assertEquals(actualMessage, message);
    }

    @Test
    public void testTriggerBuildAfterOtherProjects() {
        new HomePage(getDriver())
                .clickNewItem()
                .sendItemName(PROJECT_NAME)
                .selectFreestyleClickOkAndWaitCreateItemFormIsClose();

        new HeaderComponent(getDriver())
                .goToHomePage()
                .clickNewItem()
                .sendItemName(SECOND_PROJECT_NAME)
                .selectFreestyleClickOkAndWaitCreateItemFormIsClose()
                .clickToReverseBuildTriggerAndSetUpStreamProject(PROJECT_NAME)
                .clickSaveButton()
                .waitUntilTextNameProjectToBePresentInH1(SECOND_PROJECT_NAME);

        final List<String> builds = new HeaderComponent(getDriver())
                .goToHomePage()
                .clickJobLink(PROJECT_NAME)
                .clickBuildNow()
                .waitJobStarted(SECOND_PROJECT_NAME)
                .getBuildList();

        final String buildStatusText = new FreestyleConfigurationPage(getDriver()).getBuildStatusText();

        Assert.assertEquals(builds.size(), 1);
        Assert.assertTrue(buildStatusText.contains("Today"));
        Assert.assertTrue(buildStatusText.contains("#1"));
    }

    @Test
    public void testAddPostBuildActions () {
        List<String> postBuildNameList = new HomePage(getDriver())
                .clickNewItem()
                .sendItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .addPostBuildActions(1)
                .addPostBuildActions(5)
                .addPostBuildActions(1)
                .addPostBuildActions(11)
                .addPostBuildActions(2)
                .addPostBuildActions(8)
                .addPostBuildActions(10)
                .getChunkHeaderList();

        assertEquals(postBuildNameList.size(), 6);
    }

    @Test
    public void testAddBuildStepsANDPostBuildActions () {
        List<String> postBuildNameList = new HomePage(getDriver())
                .clickNewItem()
                .sendItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .addPostBuildActions(1)
                .addPostBuildActions(5)
                .addBuildSteps(5)
                .addPostBuildActions(11)
                .addBuildSteps(7)
                .addPostBuildActions(8)
                .getChunkHeaderList();

        assertEquals(postBuildNameList.size(), 6);
    }
}