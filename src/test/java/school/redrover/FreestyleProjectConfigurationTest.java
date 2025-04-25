package school.redrover;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.common.TestUtils;
import school.redrover.page.freestyle.FreestyleConfigurationPage;
import school.redrover.page.HomePage;

import java.util.List;

public class FreestyleProjectConfigurationTest extends BaseTest {

    private static final String PROJECT_NAME = "Freestyle";

    @Test
    public void testDisableProject() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        String warningMessage = new FreestyleConfigurationPage(getDriver())
                .clickEnableDisableToggle()
                .clickSaveButton()
                .getDisabledWarningMessageText();

        Assert.assertEquals(warningMessage, "This project is currently disabled");
    }

    @Test(dependsOnMethods = "testDisableProject")
    public void testWarningMessageIsDisplayedAfterDisableProject() {
        List<WebElement> warningMessageList = new HomePage(getDriver())
                .clickOnJobInListOfItems(PROJECT_NAME)
                .getWarningMessageList();

        Assert.assertEquals(warningMessageList.size(), 1);
    }

    @Test(dependsOnMethods = "testWarningMessageIsDisplayedAfterDisableProject")
    public void testWarningMessageDisappearsAfterEnableProject() {
        List<WebElement> warningMessageList = new HomePage(getDriver())
                .clickOnJobInListOfItems(PROJECT_NAME)
                .clickEnableButton()
                .getWarningMessageList();

        Assert.assertEquals(warningMessageList.size(), 0);
    }

    @Test(dependsOnMethods = "testWarningMessageDisappearsAfterEnableProject")
    public void testEnableProject() {
        String toggleStatus = new HomePage(getDriver())
                .clickOnJobInListOfItems(PROJECT_NAME)
                .clickConfigure()
                .getToggleStatus();

        Assert.assertEquals(toggleStatus, "Enabled");
    }
}
