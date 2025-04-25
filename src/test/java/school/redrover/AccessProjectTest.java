package school.redrover;


import org.testng.Assert;
import school.redrover.common.BaseTest;
import org.testng.annotations.Test;
import school.redrover.page.folder.FolderProjectPage;
import school.redrover.page.HomePage;

public class  AccessProjectTest extends BaseTest {

    @Test
    public void testLinkProject () {
        final String projectName = "first project folder";
        final String descriptionName = "folder";

        FolderProjectPage folderProjectPage = new HomePage(getDriver())
                .createJob()
                .sendItemName(projectName)
                .selectFolderAndClickOk()
                .sendDescription(descriptionName)
                .clickSave();

        Assert.assertEquals(folderProjectPage.getProjectName(),projectName);
        Assert.assertEquals(folderProjectPage.getDescription(),descriptionName);
    }
}
