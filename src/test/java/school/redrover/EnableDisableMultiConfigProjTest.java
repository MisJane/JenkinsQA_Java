package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

public class EnableDisableMultiConfigProjTest extends BaseTest {

    @Test
    public void testTooltipIsVisible() {

        String tooltipIsVisible = new HomePage(getDriver())
                .clickNewItemOnLeftSidePanel()
                .sendItemName("MyProject120")
                .selectMultiConfigurationAndClickOk()
                .checkTooltipVisibility();

        Assert.assertTrue(tooltipIsVisible.contains("tippy"));
    }

    @Test (dependsOnMethods = {"testTooltipIsVisible"})
    public void testProjectDisabledMessageIsVisible() {

        boolean projectDisabledMessageIsVisible = new HomePage(getDriver())
                .clickNewItemOnLeftSidePanel()
                .sendItemName("MyProject121")
                .selectMultiConfigurationAndClickOk()
                .clickEnableToggle()
                .clickSaveButton()
                .projectDisabledMessageCheck();

        Assert.assertTrue(projectDisabledMessageIsVisible);
    }

    @Test (dependsOnMethods = {"testProjectDisabledMessageIsVisible"})
    public void testProjectDisabledMessageInvisible() {

        boolean projectDisabledMessageInvisible = new HomePage(getDriver())
                .clickNewItemOnLeftSidePanel()
                .sendItemName("MyProject122")
                .selectMultiConfigurationAndClickOk()
                .clickEnableToggle()
                .clickSaveButton()
                .clickEnableButton()
                .messageNotDisplayedCheck();

        Assert.assertTrue(projectDisabledMessageInvisible);
    }
}