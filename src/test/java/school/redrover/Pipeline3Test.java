package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.util.List;


public class Pipeline3Test extends BaseTest {

    @Test
    public void testCreatePipelineWithValidName() {
        final String projectName = "TestPipeline";

        WebDriver driver = getDriver();

        createPipeline(driver, projectName);
        goHomeUsingJS(driver);

        Assert.assertEquals(
                driver.findElement(By.cssSelector(".jenkins-table__link > span:nth-child(1)")).getText(),
                projectName);
    }

    @Test
    public void testCreatePipelineWithUnderscores() {
        final String projectName = "_Test_Pipeline_";

        WebDriver driver = getDriver();

        createPipeline(driver, projectName);
        goHomeUsingJS(driver);

        Assert.assertEquals(
                driver.findElement(By.cssSelector(".jenkins-table__link > span:nth-child(1)")).getText(),
                projectName);
    }

    @Test
    public void testCreatePipelineWithEmptyName() {
        WebDriver driver = getDriver();

        driver.findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        driver.findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();

        WebElement errorMessage = getWait5().
                until(ExpectedConditions.visibilityOfElementLocated(By.id("itemname-required")));
        WebElement button = driver.findElement(By.id("ok-button"));

        Assert.assertEquals(
                errorMessage.getText(), "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(
                button.isEnabled(), "Button should be disabled");
    }

    @Ignore
    @Test
    public void testCreatePipelineWithExistingName() {
        final String projectName = "TestPipeline";

        WebDriver driver = getDriver();

        createPipeline(driver, projectName);
        goHomeUsingJS(driver);

        driver.findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        driver.findElement(By.id("name")).sendKeys(projectName);
        driver.findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();

        WebElement errorMessage = getWait5().
                until(ExpectedConditions.visibilityOfElementLocated(By.id("itemname-invalid")));
        WebElement button = driver.findElement(By.id("ok-button"));

        Assert.assertEquals(
                errorMessage.getText(), "» A job already exists with the name ‘" + projectName + "’");
        Assert.assertFalse(
                button.isEnabled(), "Button should be disabled");


    }

    @Test
    public void testCreatePipelineWithNameContainingInvalidCharacters() {
        WebDriver driver = getDriver();

        List<String> invalidNames = List.of(
                "/Test Pipeline",
                "Test/Pipeline",
                "TestPipeline/",
                "#TestPipeline",
                "Test%Pipeline",
                "?TestPipeline",
                "Test*Pipeline"
        );

        driver.findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        for(String invalidName : invalidNames) {
            WebElement nameField = driver.findElement(By.id("name"));
            nameField.clear();
            nameField.sendKeys(invalidName);

            driver.findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();

            String invalidChar = invalidName
                    .replaceAll("[a-zA-Z0-9 _-]", "")
                    .replaceAll("\\s+", "");

            WebElement errorMessage = getWait5().until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("itemname-invalid")));
            Assert.assertEquals(
                    errorMessage.getText(),
                    "» ‘" + invalidChar + "’ is an unsafe character");

            try {
                getWait5().until(ExpectedConditions.attributeToBe(By.id("ok-button"), "disabled", "true"));
            } catch (TimeoutException e) {
                Assert.fail("Button did not become disabled after entering invalid name: '" +
                        invalidName + "'. Invalid character: '" + invalidChar + "'");
            }

            WebElement button = driver.findElement(By.id("ok-button"));
            Assert.assertFalse(button.isEnabled(), "Button should be disabled for invalid name: '" +
                    invalidName + "' with character '" + invalidChar + "'");

        }
    }

    @Ignore //Error:    Pipeline3Test.testCreatePipelineBasedOnExistingItemViaCopyFrom:152 » WebDriver unknown error: unhandled inspector error: {"code":-32000,"message":"Node with given id does not belong to the document"}
    @Test
    public void testCreatePipelineBasedOnExistingItemViaCopyFrom() {
        final String existingProjectName = "ExistingPipeline";
        final String newProjectName = "NewPipeline";

        WebDriver driver = getDriver();

        //Creating first project
        createPipeline(driver, existingProjectName);

        openPipelineConfiguration();
        openPipelineSection("pipeline");

        WebElement selectElement = getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//textarea/following-sibling::div[@class='samples']/select")));
        new Select(selectElement).selectByValue("hello");

        WebElement submitButton = driver.findElement(By.name("Submit"));
        submitButton.click();

        getWait5().until(ExpectedConditions.stalenessOf(submitButton));
        goHomeUsingJS(driver);

        //Creating second project
        driver.findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        driver.findElement(By.id("name")).sendKeys(newProjectName);
        driver.findElement(By.id("from")).sendKeys(existingProjectName);
        driver.findElement(By.id("ok-button")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();
        goHomeUsingJS(driver);

        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a/span[text() = 'NewPipeline']"))).click();
        openPipelineConfiguration();
        openPipelineSection("pipeline");

        List<WebElement> lines = driver.findElements(By.cssSelector(".ace_line"));
        StringBuilder scriptText = new StringBuilder();
        for (WebElement line : lines) {
            scriptText.append(line.getText()).append("\n");
        }
        String fullScript = scriptText.toString();

        Assert.assertTrue(
                fullScript.contains("pipeline {"), "Script does not contain expected 'pipeline {' block");
        Assert.assertTrue(
                fullScript.contains("echo 'Hello World'"), "Missing echo statement");
    }

    @Test
    public void testCreatePipelineWithNonExistentCopyFromName() {
        final String existingProjectName = "ExistingPipeline";
        final String newProjectName = "NewPipeline";
        final String nonExistentProject = "NonExistentProject";

        WebDriver driver = getDriver();

        createPipeline(driver, existingProjectName);
        goHomeUsingJS(driver);

        driver.findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        driver.findElement(By.id("name")).sendKeys(newProjectName);
        driver.findElement(By.id("name")).sendKeys(newProjectName);
        driver.findElement(By.id("from")).sendKeys(nonExistentProject);
        driver.findElement(By.id("ok-button")).click();

        getWait5().until(ExpectedConditions.urlContains("/createItem"));

        Assert.assertEquals(driver.findElement(By.tagName("h1")).getText(), "Error");
        Assert.assertEquals(driver.findElement(By.tagName("p")).getText(), "No such job: NonExistentProject");
    }

    @Test
    public void testCreateNoItemAfterAbandon() {
        final String projectName = "TestPipelineAbandon";

        WebDriver driver = getDriver();

        driver.findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        driver.findElement(By.id("name")).sendKeys(projectName);
        driver.findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        goHomeUsingJS(driver);

        Assert.assertEquals(driver.findElement(
                By.cssSelector(".empty-state-block h1")).getText(), "Welcome to Jenkins!");
    }

    @Test
    public void testCreatePipelineWithDescription() {
        final String projectName = "NewPipelineV";
        final String descriptionText = "New Created Description\nTRALALALALALALALA";

        WebDriver driver = getDriver();

        driver.findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        driver.findElement(By.id("name")).sendKeys(projectName);
        driver.findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        driver.findElement(By.id("ok-button")).click();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.name("description")))
                .sendKeys(descriptionText);
        driver.findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@id='description']/div"))).getText(), descriptionText);
    }

    private void goHomeUsingJS(WebDriver driver) {
        ((JavascriptExecutor)driver).executeScript("window.location.href='/';");
    }

    private void createPipeline(WebDriver driver, String projectName) {
        driver.findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        driver.findElement(By.id("name")).sendKeys(projectName);
        driver.findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        driver.findElement(By.id("ok-button")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();
    }

    private void openPipelineConfiguration() {
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[span[@class='task-link-text' and text()='Configure']]"))).click();
    }
    private void openPipelineSection(String sectionId) {
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@data-section-id ='" + sectionId + "']"))).click();
    }
}
