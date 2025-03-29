package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GroupCodeCraftTest extends BaseTest {

    @Test
    public void testCreatePipeline() {
        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='tasks']/div[1]/span/a"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("jenkins-input"))).sendKeys("NewPipeline");

        driver.findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ok-button"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Submit"))).click();

        String title = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("job-index-headline"))).getText();

        Assert.assertEquals(title, "NewPipeline", "Pipeline title is not correct");
    }
}
