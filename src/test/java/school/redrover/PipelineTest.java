package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;
import school.redrover.page.pipeline.PipelineConfigurationPage;
import school.redrover.page.pipeline.PipelineProjectPage;

public class PipelineTest extends BaseTest {

    private static final String PROJECT_NAME = "New Pipeline";
    private static final String PIPELINE_DESCRIPTION = "Pipeline Project Name";

    @Test
    public void testCreatePipeline() {

        PipelineProjectPage pipelineProjectPage = new HomePage(getDriver())
                .createJob()
                .sendItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .sendDescription(PIPELINE_DESCRIPTION)
                .clickSave();

        Assert.assertEquals(pipelineProjectPage.getProjectName(), PROJECT_NAME);
        Assert.assertEquals(pipelineProjectPage.getDescription(), PIPELINE_DESCRIPTION);
    }

    @Test
    public void testDisableProject() {

        PipelineConfigurationPage pipelineConfigurationPage = new HomePage(getDriver())
                .clickNewItemOnLeftSidePanel()
                .sendItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .switchToggle();

        Assert.assertTrue(pipelineConfigurationPage.isToggleDisabled(), "The switch is not in an active state");
    }
}

