package school.redrover;

import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

import static org.testng.Assert.assertEquals;


public class DashboardViewTest extends BaseTest {

    private static final String JOB_NAME = "Test item";
    private final String VIEW_NAME = "TestViewName";

    @Test(dependsOnMethods = "testCreateFreestyleProjectForView")
    public void testCreateMyView() {
        new HomePage(getDriver())
                .clickNewView()
                .addName(VIEW_NAME)
                .clickMyView()
                .clickCreateButton();

        String newViewName = new HomePage(getDriver()).getNameOfView(VIEW_NAME);
        assertEquals(newViewName, VIEW_NAME);
    }

    @Test
    public void testCreateFreestyleProjectForView() {
        String projectName = new HomePage(getDriver())
                .clickNewItem()
                .sendItemName(JOB_NAME)
                .selectFreestyleClickOkAndWaitCreateItemFormIsClose()
                .waitUntilTextConfigureToBePresentInH1()
                .clickSaveButton()
                .waitUntilTextNameProjectToBePresentInH1(JOB_NAME)
                .getProjectName();

        assertEquals(projectName, JOB_NAME);
    }
}
