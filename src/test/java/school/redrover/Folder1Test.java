package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

public class Folder1Test extends BaseTest {

    @Test
    public void testAddItemToFolder() {
        final String folderName = "First folder";
        final String itemName = "First item";

        WebDriver driver = getDriver();

        driver.findElement(By.xpath("//*[@id=\"main-panel\"]/div[2]/div/section[1]/ul/li/a")).click();
        driver.findElement(By.id("name")).sendKeys(folderName);
        driver.findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        driver.findElement(By.id("ok-button")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.name("Submit")));
        driver.findElement(By.name("Submit")).click();
        driver.findElement(By.xpath("//*[@id=\"main-panel\"]/div[4]/div/section/ul/li/a")).click();
        driver.findElement(By.id("name")).sendKeys(itemName);
        driver.findElement(By.className("hudson_model_FreeStyleProject")).click();
        driver.findElement(By.id("ok-button")).click();
        driver.findElement(By.name("Submit")).click();

        Assert.assertEquals(
                "Full project name: First folder/First item",
                "Full project name: " + folderName + "/" + itemName
        );
    }

    @Test
    public void testCreateFolderWithoutName() {
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();
        getDriver().findElement(By.id("j-add-item-type-nested-projects")).click();

        WebElement itemNameField = getDriver().findElement(By.id("name"));
        itemNameField.clear();

        getDriver().findElement(By.id("ok-button")).click();

        String errorMessage = getDriver().findElement(By.id("itemname-required")).getText();

        Assert.assertEquals(errorMessage, "» This field cannot be empty, please enter a valid name");

        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        Assert.assertFalse(okButton.isEnabled(), "Ok button is not disabled");
    }

    @Test
    public void testVerifyFolderNameDescriptionConfiguration() {
        final String folderName = "First folder";
        final String displayName = "Display name of the folder";
        final String descriptionField = "Some description here";

        WebDriver driver = getDriver();

        driver.findElement(By.xpath("//*[@id=\"main-panel\"]/div[2]/div/section[1]/ul/li/a")).click();
        driver.findElement(By.id("name")).sendKeys(folderName);
        driver.findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        driver.findElement(By.id("ok-button")).click();
        driver.findElement(By.name("_.displayNameOrNull")).sendKeys(displayName);
        driver.findElement(By.className("jenkins-help-button")).click();
        driver.findElement(By.name("_.description")).sendKeys(descriptionField);
        driver.findElement(By.className("textarea-show-preview")).click();
        driver.findElement(By.className("textarea-hide-preview")).click();
        driver.findElement(By.name("Submit")).click();

        String actualHeader = driver.findElement(By.xpath("//*[@id='main-panel']/h1")).getText();
        Assert.assertEquals(actualHeader, displayName);

        String actualDescription = driver.findElement(By.id("view-message")).getText();
        Assert.assertEquals(actualDescription, descriptionField);
    }
}
