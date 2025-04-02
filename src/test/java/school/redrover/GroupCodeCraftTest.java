package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

    @Test
    public void testAboutJenkins() {
        WebDriver driver = getDriver();

        driver.findElement(By.xpath("//*[@id=\"tasks\"]/div[3]/span/a/span[1]")).click();
        driver.findElement(By.xpath("//*[@id=\"main-panel\"]/section[4]/div/div[4]/a/dl/dt")).click();
        String appName = driver.findElement(By.className("app-about-heading")).getText();
        String appVersion = driver.findElement(By.className("app-about-version")).getText();

        Assert.assertEquals(appName, "Jenkins");
        Assert.assertEquals(appVersion, "Version 2.492.2");
    }

    @Test
    public void testNameBuildHistory() {
        WebDriver driver = getDriver();

        driver.findElement(By.cssSelector("a.task-link[href='/view/all/builds']")).click();
        String buildText = driver.findElement(By.cssSelector("h1")).getText();

        Assert.assertEquals(buildText, "Build History of Jenkins");
    }

    @Test
    public void testShowProperties() {
        WebDriver driver = getDriver();

        driver.findElement(By.xpath("//*[@id=\"tasks\"]/div[3]")).click();
        driver.findElement(By.xpath("//*[@id=\"main-panel\"]/section[4]/div/div[1]")).click();
        driver.findElement(By.xpath("//*[@id=\"main-panel\"]/div[3]/div/button[1]")).click();

        WebElement vendor = driver.findElement(By.xpath("//*[.='java.specification.vendor']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", vendor);
        String javaVendor = driver.findElement(By.xpath("//*[.='java.specification.vendor']/following-sibling::td/div[2]")).getText();

        Assert.assertEquals(javaVendor, "Oracle Corporation");
    }

    @Test
    public void newItemOrgFolderTest() throws InterruptedException {
        String nameOrgFolder = "Folder archive 01";

        getDriver().findElement(By.xpath("//a[span[contains(@class, 'task-link-text') and text()='New Item']]")).click();
        getDriver().findElement(By.id("name")).sendKeys(nameOrgFolder);

        WebElement newItemOrgFolder =
                getDriver().findElement(By.xpath("//li[.//label/span[text()='Organization Folder']]"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", newItemOrgFolder);
        newItemOrgFolder.click();
        getDriver().findElement(By.xpath("//*[@id=\"ok-button\"]")).click();

        getDriver().findElement(By.xpath("//label[@for='enable-disable-project']")).click();

        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath(
                "//*[@id=\"main-panel\"]/h1")).getText(), nameOrgFolder);
        Assert.assertEquals(getDriver().findElement(By.xpath(
                        "//*[@id='disabled-message']")).getText(),
                "This Organization Folder is currently disabled");
    }

    @Test
    public void testBuildHistoryMoreActionsButton() {
        WebDriver driver = getDriver();

        driver.findElement(By.cssSelector("a.task-link[href='/view/all/builds']")).click();
        driver.findElement(By.xpath("//button[@tooltip='More actions']")).click();

        String buttonIcon = driver.findElement(By.id("button-icon-legend")).getText();
        String buttonAtom = driver.findElement(By.xpath("//*[@id='tippy-1']/div/div/div/button[2]")).getText();

        Assert.assertEquals(buttonIcon, "Icon legend");
        Assert.assertEquals(buttonAtom, "Atom feed");
    }

    @Test
    public void testCreateMultibranch() throws InterruptedException {
        String nameOfMultibranch = "Test";
        String nameOfDisplay = "Name of test";

        getDriver().findElement(By.xpath("//a[span[contains(@class, 'task-link-text') and text()='New Item']]")).click();
        getDriver().findElement(By.id("name")).sendKeys(nameOfMultibranch);

        WebElement buttonMultibranchPipe =
                getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[2]"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", buttonMultibranchPipe);
        buttonMultibranchPipe.click();

        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();

        WebElement sendText = getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/div[2]/div/div[2]/input"));
        sendText.sendKeys(nameOfDisplay);

        WebElement buttonSave =
                getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", buttonSave);
        buttonSave.click();

        Assert.assertEquals(getDriver().findElement(By.xpath(
                "//*[@id='main-panel']/h1")).getText(), nameOfDisplay);
        Assert.assertEquals(getDriver().findElement(By.xpath(
                        "//*[@id='main-panel']/div[3]/div/section/h2")).getText(),
                "This folder is empty");
    }
}
