package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class CreateAndDeletePipelineItemTest extends BaseTest {
    @Ignore
    @Test
    public void testCreateAndDeletePipelineItem() {
        WebDriver driver = getDriver();

        //Click on New Item
        driver.findElement(By.xpath("//a[@href='newJob']")).click();

        //input text, choose radio and click ОК
        driver.findElement(By.id("name")).sendKeys("somePipeline");
        driver.findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        driver.findElement(By.id("ok-button")).click();
        driver.findElement(By.name("Submit")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("#main-panel > div.jenkins-app-bar > div.jenkins-app-bar__content.jenkins-build-caption > h1")).getText(), "somePipeline");

        //delete Item
        driver.findElement(new By.ByXPath("//*[@id=\"tasks\"]/div[5]/span/a")).click();
        driver.findElement(new By.ByXPath("//*[@id=\"jenkins\"]/dialog[2]/div[3]/button[1]")).click();
        Assert.assertNotEquals(driver.findElement(By.id("jenkins")).getText(), "somePipeline");
        //soft.assertNotEquals(driver.findElement(By.id("jenkins")).getText(), "somePipeline");
    }

    @Test
    public void getTextOnCreateEmptyNamePipelineItem() {
        WebDriver driver = getDriver();

        driver.findElement(By.xpath("//a[@href='newJob']")).click();

        driver.findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        Assert.assertEquals(driver.findElement(By.id("itemname-required")).getText(), "» This field cannot be empty, please enter a valid name");

    }

    @Test
    public void isDisabledButtonOKOnCreateNamePipelineItem() {
        WebDriver driver = getDriver();

        driver.findElement(By.xpath("//a[@href='newJob']")).click();

        driver.findElement(By.id("name")).sendKeys("somePipeline");
        Assert.assertTrue(driver.findElement(By.id("ok-button")).isDisplayed());

    }

    @Test
    public void isEnabledProjectOnCreatePipelineItem() {
        WebDriver driver = getDriver();

        driver.findElement(By.xpath("//a[@href='newJob']")).click();

        driver.findElement(By.id("name")).sendKeys("somePipeline");
        driver.findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        driver.findElement(By.id("ok-button")).click();
        Assert.assertEquals(driver.findElement(By.className("jenkins-toggle-switch__label__checked-title")).getText(), "Enabled");

    }

    @Ignore
    @Test
    public void isShownDialogOnDeletePipelineItem() {
        WebDriver driver = getDriver();
        final String itemName = "somePipeline";

        driver.findElement(By.xpath("//a[@href='newJob']")).click();
        driver.findElement(By.id("name")).sendKeys(itemName);
        driver.findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        driver.findElement(By.id("ok-button")).click();
        driver.findElement(By.name("Submit")).click();
        driver.findElement(new By.ByXPath("//*[@id=\"tasks\"]/div[5]/span/a")).click();
       /* try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/

        Assert.assertTrue(driver.findElement(By.xpath("//body[@id='jenkins']/dialog[@class='jenkins-dialog']")).isDisplayed());
    }

}