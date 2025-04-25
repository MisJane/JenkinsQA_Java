package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;
import school.redrover.page.pipeline.PipelineProjectPage;

import java.time.Duration;

public class Pipeline1Test extends BaseTest {

    @Test
    public void testCreate() {
        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.findElement(By.xpath("//span[text()='Create a job']")).click();
        driver.findElement(By.id("name")).sendKeys("MyPipeline");
        driver.findElement(By.xpath("//span[text()='Pipeline']")).click();
        driver.findElement(By.id("ok-button")).click();
        driver.findElement(By.xpath("//textarea[@name='description']")).sendKeys("This is a test pipeline");
        driver.findElement(By.xpath("//label[contains(text(),'Do not allow the pipeline to resume if the control')]")).click();
        driver.findElement(By.cssSelector("button[name='Submit']")).click();
        WebElement pipelineName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='MyPipeline']")));
        Assert.assertEquals(pipelineName.getText(), "MyPipeline");


    }

    @Test
    public void testCreatePOM() {
        final String projectName = "MyPipeline";
        final String projectDescription = "This is a test pipeline";

        PipelineProjectPage pipelineProjectPage = new HomePage(getDriver())
                .createJob()
                .sendItemName(projectName)
                .selectPipelineAndClickOk()
                .sendDescription(projectDescription)
                .clickSave();

        Assert.assertEquals(pipelineProjectPage.getProjectName(), projectName);
        Assert.assertEquals(pipelineProjectPage.getDescription(), projectDescription);
    }
}
