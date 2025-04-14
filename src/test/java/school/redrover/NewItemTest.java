package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import school.redrover.testdata.TestDataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class NewItemTest extends BaseTest {
    private Actions actions;

    @Test
    public void testCheckNewItemPageHeader() {
        WebDriver driver = getDriver();
        driver.findElement(By.linkText("New Item")).click();
        String header = driver.findElement(By.tagName("h1")).getText();

        Assert.assertEquals(header, "New Item");
    }

    @Ignore
    @Test
    public void testCreateNewItemFreestyleProject() {
        String headerNewItem = "New Item1";
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        WebDriver driver = getDriver();
        driver.findElement(By.linkText("New Item")).click();
        driver.findElement(By.id("name")).sendKeys(headerNewItem);
        driver.findElement(By.xpath("//span[text()='Freestyle project']")).click();
        driver.findElement(By.id("ok-button")).click();
        driver.findElement(By.name("Submit")).click();

        String nameOfCreatedItem = wait.until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath("//h1[contains(text(), '" + headerNewItem + "')]")))
                .getText();
        Assert.assertEquals(nameOfCreatedItem, "New Item1");
    }

    @Ignore
    @Test
    public void testCreateNewItemOrganizationFolder() {
        String headerNewItem = "New Item2";
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        WebDriver driver = getDriver();
        driver.findElement(By.linkText("New Item")).click();
        driver.findElement(By.id("name")).sendKeys(headerNewItem);
        WebElement organizationFolder = driver.findElement(By.xpath("//span[text()='Organization Folder']"));
        WebElement pageFooterLink = driver.findElement(By.className("page-footer__links"));
        actions = new Actions(driver);
        actions.moveToElement(pageFooterLink).perform();
        organizationFolder.click();

        driver.findElement(By.id("ok-button")).click();
        driver.findElement(By.name("Submit")).click();

        String nameOfCreatedItem = wait.until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath("//h1[contains(text(), '" + headerNewItem + "')]")))
                .getText();
        Assert.assertEquals(nameOfCreatedItem, "New Item2");
    }

    @Test
    public void verifyErrorMessageForEmptyItemName() {
        WebDriver driver = getDriver();
        driver.findElement(By.linkText("New Item")).click();
        driver.findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        WebElement itemNameError = driver.findElement(By.id("itemname-required"));
        Assert.assertEquals(itemNameError.getText(), "» This field cannot be empty, please enter a valid name");
    }

    @Test(dataProvider = "provideInvalidCharacters", dataProviderClass = TestDataProvider.class)
    public void testNameWithInvalidCharactersError(String invalidCharacter) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys("ItemName".concat(invalidCharacter));
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();

        String errorMessage = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("itemname-invalid"))).getText();
        boolean isSubmitButtonClickable = getDriver().findElement(By.id("ok-button")).isEnabled();

        Assert.assertEquals(errorMessage, String.format("» ‘%s’ is an unsafe character", invalidCharacter));
        Assert.assertFalse(isSubmitButtonClickable);
    }

    @Test()
    public void testCheckErrorMessageForSpecialCharacters() {
        String[] specialCharacters = {"!","@","#","$","%","^","&","*","/","\\","|","[","]",";",":","?","<",">"};

        WebDriver driver = getDriver();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='tasks']/div[1]/span/a"))).click();

        for(String ch: specialCharacters) {
            getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.className("jenkins-input"))).sendKeys(ch);
            String alertMessage = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("itemname-invalid"))).getText();

            Assert.assertEquals(alertMessage,  String.format("» ‘%s’ is an unsafe character", ch));

            driver.findElement(By.className("jenkins-input")).sendKeys("\b");
        }
    }

    @Test
    public void testCreatePipeline() {
        final String pipelineName = "NewPipeline";

        WebDriver driver = getDriver();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='tasks']/div[1]/span/a"))).click();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.className("jenkins-input"))).sendKeys(pipelineName);

        driver.findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("ok-button"))).click();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.name("Submit"))).click();

        String title = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.className("job-index-headline"))).getText();

        Assert.assertEquals(title, pipelineName, "Pipeline title is not correct");
    }

    @DataProvider(name = "projectTypes")
    public Object[][] projectTypes() {
        return new Object[][]{
                {"Freestyle project"},
                {"Pipeline"},
                {"Multi-configuration project"},
                {"Folder"},
                {"Multibranch Pipeline"},
                {"Organization Folder"}
        };
    }

    @Test(dataProvider = "projectTypes")
    public void testOKButtonIsDisabledIfCreatingProjectWithEmptyName(String projectType) {
        getDriver().findElement(By.linkText("New Item")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[text()='" + projectType + "']"))).click();

        WebElement okButton = getDriver().findElement(By.id("ok-button"));

        Assert.assertFalse(okButton.isEnabled(), "Expected OK button to be disabled.");
    }

    @Test
    public void testCheckItemsTypes() {
        final List<String> expectedItemsTypes = new ArrayList<>(List.of(
                "Freestyle project",
                "Pipeline",
                "Multi-configuration project",
                "Folder",
                "Multibranch Pipeline",
                "Organization Folder"));

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/view/all/newJob']"))).click();

        List<WebElement> itemsTypes = getDriver().findElements(By.xpath("//span[@class='label']"));
        List<String> actualItemsTypes = new ArrayList<>();
        for (WebElement element : itemsTypes) {
            actualItemsTypes.add(element.getDomProperty("innerText"));
        }

        Assert.assertEquals(actualItemsTypes, expectedItemsTypes, "Error!");
    }

    @Test (dataProvider = "itemTypes", dataProviderClass = TestDataProvider.class)
    public void testCheckItemsTypes2(String expectedItemType) {

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/view/all/newJob']"))).click();
        String actualItemType = getDriver().findElement(By.xpath("//span[text()='" + expectedItemType + "']")).getText();

        Assert.assertEquals(actualItemType, expectedItemType, "Error!");
    }
}
