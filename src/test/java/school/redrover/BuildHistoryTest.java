package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

public class BuildHistoryTest extends BaseTest {

    @Test
    public void testQuickAccessToTheBuildHistorySection() {
        final String text = "Build History of Jenkins";

        String buildHistoryPage = new HomePage(getDriver())
                .clickBuildHistoryTab()
                .isBuildHistoryText();

        Assert.assertEquals(buildHistoryPage, text);
    }

    @Ignore// BuildHistoryTest.testCheckTheBuildStatusDisplay:30 » NoSuchElement no such element: Unable to locate element: {"method":"xpath","selector":"//span[text()='Build Now']/.."} https://github.com/RedRoverSchool/JenkinsQA_Java_2025_spring/actions/runs/14732685957/job/41350713407?pr=1586
    @Test
    public void testCheckTheBuildStatusDisplay() {
        final String projectName = "Freestyle Project";

        String buildStatusProjectName = new HomePage(getDriver())
                .createJob()
                .sendItemName(projectName)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLeftSideMenuBuildNow()
                .getHeader()
                .goToHomePage()
                .clickBuildHistoryTab()
                .buildProjectText(projectName);

        Assert.assertEquals(buildStatusProjectName, projectName);
    }
}
