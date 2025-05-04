package school.redrover;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;
import school.redrover.page.view.EditViewPage;
import school.redrover.page.view.NewViewPage;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class DashboardViewTest extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(DashboardViewTest.class);
    private final String JOB_NAME = "Test item";

    @Test(dependsOnMethods = "testCreateFreestyleProjectForView")
    public void testCreateMyView() {
        String view_name = "TestViewName";

        new HomePage(getDriver())
                .clickNewView()
                .addName(view_name)
                .clickMyView()
                .clickCreateButton();

        String newViewName = new HomePage(getDriver()).getNameOfView(view_name);
        assertEquals(newViewName, view_name);
    }

    @Ignore
    @Test(dependsOnMethods = "testCreateFreestyleProjectForView")
    public void testCreateListView() {
        String List_VIEW_NAME = "TestListViewName";
        String TEST_ITEM_JOB = "Test item";
        NewViewPage initialPage = new HomePage(getDriver()).clickNewView().addName(List_VIEW_NAME);

        EditViewPage editViewPage = (EditViewPage) initialPage
                .clickListView()
                .clickCreateButton();

        List<String> projectNameList = editViewPage
                .fillDescription("Description for Test List View")
                .JobsCheckTestItem(TEST_ITEM_JOB)
                .clickAddJobFilter()
                .clickStatusFilterOfJobFilter()
                .clickAddColumn()
                .checkProjectDescriptionColumn()
                .clickSaveButton()
                .getProjectNameList();

        String projectName = projectNameList.get(0);

        assertEquals(projectName, TEST_ITEM_JOB);
        assertTrue(new HomePage(getDriver()).isJobDisplayed(JOB_NAME));
        assertEquals(new HomePage(getDriver()).getNameOfView(List_VIEW_NAME), List_VIEW_NAME);
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

    private static String genetateRandomName() {
        return "Test" + System.currentTimeMillis();
    }
}


