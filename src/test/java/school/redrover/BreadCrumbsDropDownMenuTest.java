package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.freestyle.FreestyleProjectPage;
import school.redrover.page.HomePage;

public class BreadCrumbsDropDownMenuTest extends BaseTest {

    @Test
    public void testVerifyDropDownMenu() {
        String[] dropDownMenuItems = new HomePage(getDriver())
                .clickNewItemOnLeftSidePanel()
                .sendItemName("New Freestyle Project")
                .selectFreestyleAndClickOk()
                .addDescription("Freestyle Project Description")
                .clickSaveButton()
                .clickProjectBreadcrumbsDropDownMenu()
                .getDropDownMenuItemsText();

        String[] mainMenuItems = new FreestyleProjectPage(getDriver()).getMainMenuItemsText();

        Assert.assertEquals(mainMenuItems, dropDownMenuItems, "Items don't match.");
    }
}
