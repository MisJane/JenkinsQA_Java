package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import school.redrover.page.HomePage;
import school.redrover.page.newitem.NewItemPage;
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

        NewItemPage page = new HomePage(getDriver())
                .createJob();

        for(String ch: specialCharacters) {
            page.sendItemName(ch);
            String alertMessage = page.getAlertMessageText();

            Assert.assertEquals(alertMessage,  String.format("» ‘%s’ is an unsafe character", ch));

            page.sendItemName("\b");
        }
    }

    @Test
    public void testCreatePipeline() {
        final String pipelineName = "New Pipeline";

        String actualProjectName = new HomePage(getDriver())
                .createJob()
                .sendItemName(pipelineName)
                .selectPipelineAndClickOk()
                .clickSave()
                .getProjectName();

        Assert.assertEquals(actualProjectName, pipelineName, "Pipeline title is not correct");
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
        final List<String> EXPECTED_ITEM_TYPES_TEXT_LIST = new ArrayList<>(List.of(
                "Freestyle project",
                "Pipeline",
                "Multi-configuration project",
                "Folder",
                "Multibranch Pipeline",
                "Organization Folder"));

        List<String> actualItemsTypesTextList = new HomePage (getDriver())
                .clickNewItemOnLeftSidePanel()
                .getItemTypesTextList();

        Assert.assertEquals(actualItemsTypesTextList, EXPECTED_ITEM_TYPES_TEXT_LIST, "Error!");
    }

    @Test (dataProvider = "itemTypes", dataProviderClass = TestDataProvider.class)
    public void testCheckItemsTypes2(String expectedItemTypeText) {

        String actualItemTypeText = new HomePage(getDriver())
                .clickNewItemOnLeftSidePanel()
                .getItemTypeText(expectedItemTypeText);

        Assert.assertEquals(actualItemTypeText, expectedItemTypeText, "Error!");
    }
}
