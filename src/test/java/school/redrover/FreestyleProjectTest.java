package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.freestyle.FreestyleProjectPage;
import school.redrover.page.HomePage;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class FreestyleProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "Freestyle Project";
    private static final String UPDATED_PROJECT_NAME = "NEW Freestyle NAME";

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
        final String newProjectDescription = "This is a NEW freestyleProject description";

        FreestyleProjectPage freestyleProjectPage = new HomePage(getDriver())
                .clickOnJobInListOfItems(PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickEditDescriptionButton()
                .sendDescription(newProjectDescription)
                .clickSave();

        Assert.assertEquals(freestyleProjectPage.getDescription(), newProjectDescription);
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
                .getBuildStepsList();

        assertEquals(projectNameList.size(), 7);
    }
}