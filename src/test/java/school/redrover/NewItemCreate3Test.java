package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.common.TestUtils;

import java.util.ArrayList;
import java.util.List;

public class NewItemCreate3Test extends BaseTest {

    public void goToNewItemPage() {
        getDriver().findElement(By.linkText("New Item")).click();
    }

    @Test
    public void testCreateNewItemWithoutName() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();

        Assert.assertEquals(
                (getDriver().findElement(By.id("itemname-required")).getText()),
                "» This field cannot be empty, please enter a valid name");

        Assert.assertFalse(getDriver().findElement(By.id("ok-button")).isEnabled());
    }

    @Test
    public void testCreateNewItemNameWithIllegalCharacters() {
        String el;
        goToNewItemPage();

        List<String> specialChars = new ArrayList<>(List.of("@", "<", "#", "&", "?", "!", "/"));
        for (String element : specialChars) {
            el = element;
            String messageError = "» ‘" + el + "’ is an unsafe character";
            getDriver().findElement(By.id("name")).sendKeys(element);
            String text = getWait5()
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("itemname-invalid")))
                    .getText();
            Assert.assertEquals(messageError, text);
            getDriver().findElement(By.id("name")).clear();
        }
    }

    @Ignore
    @Test
    public void testCreateItemNameWithAppropriateCharacters() {
        goToNewItemPage();

        List<String> names = new ArrayList<>(List.of("ABCH", "avbcj", "пренш", "НЫГШ", "125487", "_"));
        for (String element : names) {
            getDriver().findElement(By.id("name")).sendKeys(element);
            Assert.assertFalse(getDriver().findElement(By.id("itemname-required")).isDisplayed());
            getDriver().findElement(By.id("name")).clear();
        }
    }
    @Test
    public  void  testCreateNewItemWithCorrectName() {
        final String newItemName = "New free-style project";
        final String expectedName = "New free-style project";

        goToNewItemPage();
        getDriver().findElement(By.id("name")).sendKeys(newItemName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        Assert.assertEquals(getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"breadcrumbs\"]/li[3]/a"))).getText(), expectedName);

    }

    @Test
    public void testCreateNewItemWithExistingName() {
        goToNewItemPage();
        final String projectName = "New FreeStyleProject";

        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        TestUtils.scrollAndClickWithJS(getDriver(), getDriver().findElement(By.id("ok-button")));

        TestUtils.gotoHomePage(this);
        Assert.assertEquals(
                getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.linkText("New FreeStyleProject")))
                        .getText(), "New FreeStyleProject");

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.linkText("New Item")));
        goToNewItemPage();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        WebElement el = getWait10().until(ExpectedConditions.presenceOfElementLocated(By.id("itemname-invalid")));
        getWait5().until(ExpectedConditions.visibilityOf(el));

        Assert.assertEquals(el.getText(), "» A job already exists with the name ‘New FreeStyleProject’");
    }
}

