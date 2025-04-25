package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;
import school.redrover.page.pipeline.PipelineConfigurationPage;

public class PipelineConfigurePage3Test extends BaseTest {
    final String projectName = "Pipeline1";

    @Test
    public void testDisableToggleInPipeline() {

        PipelineConfigurationPage pipelineConfigurationPage = new HomePage(getDriver())
                .createNewPipeline(projectName)
                .switchToggle();

        Assert.assertTrue(pipelineConfigurationPage.isToggleDisabled(), "Toggle is not in Disabled state");
    }

    @Test
    public void testEnableToggleInPipeline() {

        PipelineConfigurationPage pipelineConfigurationPage = new HomePage(getDriver())
                .createNewPipeline(projectName)
                .switchToggle()
                .clickSave()
                .clickConfigure()
                .switchToggle();

        Assert.assertTrue(pipelineConfigurationPage.isToggleEnabled(), "Toggle is not in Enabled state");
    }

    @Test
    public void testTooltipForToggle() {

        PipelineConfigurationPage pipelineConfigurationPage = new HomePage(getDriver())
                .createNewPipeline(projectName);

        Assert.assertEquals(pipelineConfigurationPage.checkTooltipText(), "Enable or disable the current project", "Tooltip text is incorrect");
    }
}
