package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;
import school.redrover.page.buildhistory.BuildHistoryPage;
import school.redrover.page.freestyle.FreestyleConfigurationPage;
import school.redrover.page.newitem.NewItemPage;

public class AssemblyTest extends BaseTest {

    @Ignore
    @Test
    public void testBuildHistory() {
        final String text = "Freestyle";
        final String text2 ="No builds";

        new HomePage(getDriver())
                .clickNewItem();

        new NewItemPage(getDriver())
                .sendItemName(text)
                .selectFreestyleAndClickOk();

        new FreestyleConfigurationPage(getDriver())
                .clickFreestyleText();

        String buildHistory = new BuildHistoryPage(getDriver())
                .clickCollectNow()
                .clickDeleteBuild()
                .getTextNoBuilds();

        Assert.assertEquals(buildHistory, text2);
    }
}
