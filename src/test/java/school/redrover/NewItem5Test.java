package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;
import school.redrover.page.newitem.NewItemPage;

import java.util.List;

public class NewItem5Test extends BaseTest {

    @Test
    public void testNewItemPageAvailableFromDashboard() {
        final String PAGE_HEADER_TEXT = "New Item";
        final String NEW_ITEM_PAGE_URL = "http://localhost:8080/view/all/newJob";

        NewItemPage newItemPage = new HomePage(getDriver())
                .clickNewItemOnLeftSidePanel();

        Assert.assertEquals(newItemPage.getNewItemPageHeaderText(), PAGE_HEADER_TEXT);
        Assert.assertEquals(newItemPage.getNewItemPageURL(), NEW_ITEM_PAGE_URL);
    }

    @Test
    public void testInputFieldLabelAndAvailability() {
        getDriver().findElement(By.xpath("//span[text()='New Item']/ancestor::span[@class='task-link-wrapper ']")).click();
        boolean isInputFieldDisplayed = getDriver().findElement(By.xpath("//div[@class='add-item-name']/input")).isDisplayed();
        String inputFieldLabelText = getDriver().findElement(By.xpath("//div[@class='add-item-name']/label")).getText();

        Assert.assertTrue(isInputFieldDisplayed, "Input Field isn't displayed");
        Assert.assertEquals(inputFieldLabelText, "Enter an item name");
    }

    @Test
    public void testItemsList() {
        final List<String> EXPECTED_ITEM_TYPES_TEXT_LIST = List.of(
                "Freestyle project",
                "Pipeline",
                "Multi-configuration project",
                "Folder",
                "Multibranch Pipeline",
                "Organization Folder");

        List<String> actualItemTypesTextList = new HomePage(getDriver())
                .clickNewItemOnLeftSidePanel()
                .getItemTypesTextList();

        Assert.assertEquals(actualItemTypesTextList, EXPECTED_ITEM_TYPES_TEXT_LIST);
    }

    @Ignore
    @Test
    public void testItemNameFieldAcceptsAlphanumericsAndSpecialCharacters() {
        final String ACCEPTABLE_CHARACTERS = "Q w1`~()_-+={}'\".,";

        getDriver().findElement(By.xpath("//span[text()='New Item']/ancestor::span[@class='task-link-wrapper ']")).click();
        getDriver().findElement(By.cssSelector(".jenkins-input#name")).sendKeys(ACCEPTABLE_CHARACTERS);
        WebElement invalidCharacterMessage = getDriver().findElement(By.id("itemname-invalid"));

        Assert.assertFalse(invalidCharacterMessage.isDisplayed(), "An unsafe character error message is displayed");
    }

    @Test
    public void testItemNameWithUnsafeSpecialCharacterNotAllowed() {
        final List<String> UNSAFE_CHARACTERS_LIST = List.of("!", "@", "#", "$", "%", "^", "&", "*", "[", "]", "|", "<", ">", "?", "/", ":", ";");

        getDriver().findElement(By.xpath("//span[text()='New Item']/ancestor::span[@class='task-link-wrapper ']")).click();
        WebElement unsafeCharacterMessage = getDriver().findElement(By.id("itemname-invalid"));
        WebElement inputField = getDriver().findElement(By.cssSelector(".jenkins-input#name"));

        for (String character: UNSAFE_CHARACTERS_LIST) {
            getWait5().until(ExpectedConditions.elementToBeClickable(inputField)).sendKeys(character);

            Assert.assertTrue(getWait5().until(ExpectedConditions.visibilityOf(unsafeCharacterMessage)).isDisplayed(), "An unsafe character error message is NOT displayed");
            Assert.assertEquals(unsafeCharacterMessage.getText(), "» ‘" + character + "’ is an unsafe character");

            inputField.clear();
        }
    }
}
