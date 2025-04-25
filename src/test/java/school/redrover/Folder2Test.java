package school.redrover;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;
import school.redrover.page.organizationfolder.OrganizationFolderPage;

public class Folder2Test extends BaseTest {

    private static final String FOLDER_NAME_A = "Folder A";
    private static final String FOLDER_NAME_B = "Folder B";
    private static final String JOB_NAME = "New Job";

    @Test
    public void testNewFolderIsEmptyByDefault() {
        String folderStatus = new HomePage(getDriver())
                .clickNewItemOnLeftSidePanel()
                .sendItemName(FOLDER_NAME_A)
                .selectFolderAndClickOkWithJS()
                .clickSave()
                .getFolderStatus();

        Assert.assertEquals(folderStatus, "This folder is empty");
    }

    @Test(dependsOnMethods = "testNewFolderIsEmptyByDefault")
    public void testCannotCreateItemsWithTheSameNameInFolder() {
        WebElement errorMessage = new HomePage(getDriver())
                .clickOnOrganizationFolderInListOfItems(FOLDER_NAME_A)
                .clickOnNewItemWithinFolder()
                .sendItemName(JOB_NAME)
                .selectFreestyleClickOkAndReturnToHomePage()
                .clickOnOrganizationFolderInListOfItems(FOLDER_NAME_A)
                .clickOnNewItemWithinFolder()
                .sendItemName(JOB_NAME)
                .getInvalidItemNameError();

        Assert.assertTrue(errorMessage.isDisplayed());
        Assert.assertEquals(errorMessage.getText(),
                String.format("» A job already exists with the name ‘%s’", JOB_NAME));
    }

    @Ignore
    @Test
    public void testSameNameItemsInDifferentFolders() {
        String firstItemName = new HomePage(getDriver())
                .clickOnOrganizationFolderInListOfItems(FOLDER_NAME_A)
                .clickOnNewItemWithinFolder()
                .sendItemName(JOB_NAME)
                .selectFreestyleClickOkAndReturnToHomePage()
                .clickOnOrganizationFolderInListOfItems(FOLDER_NAME_A)
                .getJobWithinFolderName(JOB_NAME);

        String secondItemName = new OrganizationFolderPage(getDriver())
                .goToHomePage()
                .clickNewItemOnLeftSidePanel()
                .sendItemName(FOLDER_NAME_B)
                .selectFolderAndClickOkWithJS()
                .saveAndReturnToHomePage()
                .clickOnOrganizationFolderInListOfItems(FOLDER_NAME_B)
                .clickOnNewItemWithinFolder()
                .sendItemName(JOB_NAME)
                .selectFreestyleClickOkAndReturnToHomePage()
                .clickOnOrganizationFolderInListOfItems(FOLDER_NAME_B)
                .getJobWithinFolderName(JOB_NAME);

        Assert.assertEquals(firstItemName, JOB_NAME);
        Assert.assertEquals(secondItemName, JOB_NAME);
    }

    @Test(dependsOnMethods = "testNewFolderIsEmptyByDefault")
    public void testAddItem() {
        String jobName = new HomePage(getDriver())
                .clickOnOrganizationFolderInListOfItems(FOLDER_NAME_A)
                .clickOnNewItemWithinFolder()
                .sendItemName(JOB_NAME)
                .selectFreestyleClickOkAndReturnToHomePage()
                .clickOnOrganizationFolderInListOfItems(FOLDER_NAME_A)
                .getJobWithinFolderName(JOB_NAME);

        Assert.assertEquals(jobName, JOB_NAME);
    }
}
