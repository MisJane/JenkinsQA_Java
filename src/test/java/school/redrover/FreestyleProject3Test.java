package school.redrover;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

import java.util.List;

public class FreestyleProject3Test extends BaseTest {
    private static final String PROJECT_NAME = "Freestyle Project";
    private static final String GITHUB_PROJECT_URL = "https://github.com/RedRoverSchool/JenkinsQA_Java_2025_spring";
    private static final String MENU_GITHUB_OPTION = "GitHub";

    @Test
    public void testFreestyleProjectAddGitHubURL() {
        List<String> freestyleProjectPage = new HomePage(getDriver())
                .createJob()
                .sendItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .checkGitHubProjectCheckbox()
                .sentGitHubProjectURL(GITHUB_PROJECT_URL)
                .clickSaveButton()
                .getLeftSideMenuNameList();

        Assert.assertTrue(freestyleProjectPage.contains(MENU_GITHUB_OPTION));
    }

}
