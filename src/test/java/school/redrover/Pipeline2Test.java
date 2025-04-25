package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.common.TestUtils;
import school.redrover.page.HomePage;
import school.redrover.page.pipeline.PipelineProjectPage;

public class Pipeline2Test extends BaseTest {

    @Test
    public void testCreateNewPipeline() {
        final String projectName = "PipelineProjectNameTest";

        PipelineProjectPage pipelineProjectPage = new HomePage(getDriver())
                .createJob()
                .sendItemName(projectName)
                .selectPipelineAndClickOk()
                .clickSave();

        Assert.assertEquals(pipelineProjectPage.getProjectName(), projectName);
    }

    @Test
    public void testCopyFromError() {
        final String freestyleProjectA = "FirstProject";
        final String freestyleProjectB = "SecondProject";
        final String copyFrom = "No such item";

        WebDriver driver = getDriver();
        TestUtils.newItemCreate(this, freestyleProjectA, 2);

        driver.findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a")).click();
        driver.findElement(By.className("jenkins-input")).sendKeys(freestyleProjectB);
        driver.findElement(By.id("from")).sendKeys(copyFrom);
        driver.findElement(By.id("ok-button")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"main-panel\"]/h1")).getText(),"Error");
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"main-panel\"]/p")).getText(), "No such job: " + copyFrom);
    }
}
