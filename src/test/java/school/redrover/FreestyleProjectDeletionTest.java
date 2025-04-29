package school.redrover;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

public class FreestyleProjectDeletionTest extends BaseTest {

    private static final String PROJECT_NAME = "TestProject";

    @Test
    public void testFreestyleProjectDeletion() {
        HomePage homePage = new HomePage(getDriver());
        homePage.clickNewItemOnLeftSidePanel()
                .sendItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLeftSideMenuDelete()
                .clickPopUpYesDeleteProject()
                .isFreestyleProjectDeleted(PROJECT_NAME);

        boolean projectDeleted = homePage.isFreestyleProjectDeleted(PROJECT_NAME);

        Assert.assertTrue(projectDeleted, "Project '" + PROJECT_NAME + "'is not deleted");
    }
}

