package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.common.TestUtils;
import school.redrover.page.HomePage;
import school.redrover.page.newitem.NewItemPage;
import school.redrover.testdata.TestDataProvider;

import java.util.List;
import java.util.Random;

public class NewItemPage2Test extends BaseTest {
    private Actions actions;
    HomePage homePage;

    @BeforeMethod
    void setUp() {
        homePage = new HomePage(getDriver());

        if (getDriver() != null) {
            actions = new Actions(getDriver());
        } else {
            throw new IllegalStateException("WebDriver is null. Cannot initialize Actions.");
        }
    }

    private void clickOnNewItemLink() {
        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/view/all/newJob']")))
                   .click();
    }

    private int  getRandomNumberWithin1And6() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }

    private void enterNonExistingItemValueToCopyFrom(String randomAlphaNumericValue) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String generated;
        Random random = new Random();
        int randomLength = getRandomNumberWithin1And6();

        do {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < randomLength; i++) {
                char c = chars.charAt(random.nextInt(chars.length()));
                sb.append(c);
            }
            generated = sb.toString();
        } while (randomAlphaNumericValue.startsWith(generated));

        WebElement copyFromInput = getDriver().findElement(By.id("from"));
        TestUtils.scrollAndClickWithJS(getDriver(), copyFromInput);
        copyFromInput.sendKeys(generated);

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.className("jenkins-dropdown")));
    }

    private void enterExistingItemValueToCopyFrom(String randomAlphaNumericValue) {
        Random random = new Random();
        int randomLength = random.nextInt(randomAlphaNumericValue.length() + 1);
        String inputValue = randomAlphaNumericValue.substring(0, randomLength);

        getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("name")))
                .sendKeys(TestUtils.generateRandomAlphanumeric());

        WebElement copyFromInput = getDriver().findElement(By.id("from"));
        TestUtils.scrollAndClickWithJS(getDriver(), copyFromInput);
        copyFromInput.sendKeys(inputValue);
    }

    private void openDropdownMenuForJob(String itemName) {
        String buttonSelector = String.format("button[data-href*='%s']", itemName);
        WebElement dropdownChevron = getDriver().findElement(By.cssSelector(buttonSelector));
        TestUtils.moveAndClickWithJS(getDriver(), dropdownChevron);
    }

    private void createNewJob(String name, String itemTypeName) {
        getDriver().findElement(By.id("name")).sendKeys(name);
        WebElement itemType = getDriver().findElement(By.xpath(String.format("//span[text()='%s']", itemTypeName)));
        TestUtils.scrollAndClickWithJS(getDriver(), itemType);
        TestUtils.scrollAndClickWithJS(getDriver(), getDriver().findElement(By.id("ok-button")));
        TestUtils.scrollAndClickWithJS(getDriver(), getDriver().findElement(By.name("Submit")));
    }

    @Test
    public void testIfPageIsAccessibleFromHomePage() {
        NewItemPage newItemPage = new NewItemPage(getDriver());

        homePage.clickNewItemOnLeftSidePanel();

        Assert.assertTrue(newItemPage.getNewItemPageURL().contains("/view/all/newJob"));
        Assert.assertEquals(newItemPage.getNewItemPageHeaderText(),"New Item");
    }

    @Test
    public void testInputPositiveValidation() {
        String randomAlphaNumericValue = TestUtils.generateRandomAlphanumeric() + "_";

        homePage.clickNewItemOnLeftSidePanel()
                .sendItemName(randomAlphaNumericValue);

        Assert.assertEquals(randomAlphaNumericValue, new NewItemPage(getDriver()).getInputValue());
    }

    @Test
    public void testIfAvailableJobsAreDisplayedOnThePage() {
        List<String> expectedLabels = List.of(
                "Freestyle project", "Pipeline", "Multi-configuration project", "Folder", "Multibranch Pipeline",
                "Organization Folder"
        );

        Assert.assertEquals(homePage.clickNewItemOnLeftSidePanel().getAllItemsTypesLabels(),
                            expectedLabels
        );
    }

    @Test(dataProvider = "itemDescriptions", dataProviderClass = TestDataProvider.class)
    public void testItemsDescriptions(List<String> expectedItemDescriptions) {
        List<String> actualItemDescriptions = homePage.clickNewItemOnLeftSidePanel().getJobsDescriptions();

        Assert.assertEquals(actualItemDescriptions, expectedItemDescriptions);
    }

    @Test(dataProvider = "itemTypes", dataProviderClass = TestDataProvider.class)
    public void testIfSelectedItemIsHighlighted(String itemTypeName) {
        NewItemPage newItemPage = homePage.clickNewItemOnLeftSidePanel().clickOnJobItem(itemTypeName);

        Assert.assertTrue(newItemPage.isListItemHighlighted(itemTypeName));
    }

    @Test(dataProvider = "itemTypes", dataProviderClass = TestDataProvider.class)
    public void testOkButtonWhenFieldIsEmpty(String itemTypeName) {
        NewItemPage newItemPage = homePage.clickNewItemOnLeftSidePanel().clickOnJobItem(itemTypeName);

        Assert.assertFalse(newItemPage.isOkButtonEnabled());
    }

    @Test
    public void testIfErrorMessageIsDisplayedWhenFieldIsEmpty() {
        NewItemPage newItemPage = homePage.clickNewItemOnLeftSidePanel().selectFreestyle();

        Assert.assertEquals(
                         newItemPage.getErrorMessageOnEmptyField(),
                "» This field cannot be empty, please enter a valid name"
        );
    }

    @Test
    public void testIfCopyFromOptionIsDisplayed() {
        TestUtils.newItemCreate(this, TestUtils.generateRandomAlphanumeric(), getRandomNumberWithin1And6());
        clickOnNewItemLink();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("p.jenkins-form-label")).getText(),
                "If you want to create a new item from other existing, you can use this option:"
        );
        Assert.assertTrue(getDriver().findElement(By.id("from")).isDisplayed());
    }

    @Test
    public void testIfCopyFromOptionIsNotDisplayed() {
        clickOnNewItemLink();

        Assert.assertTrue(getDriver().findElements(By.id("from")).isEmpty());
    }
    @Ignore //NewItemPage2Test.testAutocompleteOption:227 » Timeout Expected condition failed: waiting for visibility of element located by By.className: jenkins-dropdown__item (tried for 10 second(s) with 500 milliseconds interval)
    @Test
    public void testAutocompleteOption() {
        String randomAlphaNumericValue = TestUtils.generateRandomAlphanumeric();
        TestUtils.newItemCreate(this, randomAlphaNumericValue, getRandomNumberWithin1And6());
        clickOnNewItemLink();

        enterExistingItemValueToCopyFrom(randomAlphaNumericValue);
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.className("jenkins-dropdown__item")));
        WebElement dropdownItem = getDriver().findElement(By.className("jenkins-dropdown__item"));

        Assert.assertTrue(dropdownItem.isDisplayed());
        Assert.assertEquals(dropdownItem.getText(), randomAlphaNumericValue);
    }

    @Test
    public void testIfNoItemsMessageIsDisplayed() {
        String randomAlphaNumericValue = TestUtils.generateRandomAlphanumeric();
        TestUtils.newItemCreate(this, randomAlphaNumericValue, getRandomNumberWithin1And6());
        clickOnNewItemLink();

        enterNonExistingItemValueToCopyFrom(randomAlphaNumericValue);

        Assert.assertEquals(
                getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.className("jenkins-dropdown__placeholder"))).getText(),
                "No items"
        );
    }
    @Ignore //NewItemPage2Test.testCopyFromOptionWhenCreatingNewJob:246 » Timeout Expected condition failed: waiting for visibility of element located by By.className: jenkins-dropdown__item (tried for 10 second(s) with 500 milliseconds interval)
    @Test
    public void testCopyFromOptionWhenCreatingNewJob() {
        String randomAlphaNumericValue = TestUtils.generateRandomAlphanumeric();
        TestUtils.newItemCreate(this, randomAlphaNumericValue, getRandomNumberWithin1And6());
        clickOnNewItemLink();

        enterExistingItemValueToCopyFrom(randomAlphaNumericValue);
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.className("jenkins-dropdown__item"))).click();
        getDriver().findElement(By.id("ok-button")).click();

        getWait5().until(ExpectedConditions.urlContains("/job"));

        Assert.assertTrue(getDriver().findElement(By.id("general")).isDisplayed());
    }

    @Test
    public void testIfUserRedirectedToErrorPage() {
        String randomAlphaNumericValue = TestUtils.generateRandomAlphanumeric();
        TestUtils.newItemCreate(this, randomAlphaNumericValue, getRandomNumberWithin1And6());
        clickOnNewItemLink();

        getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("name")))
                .sendKeys(TestUtils.generateRandomAlphanumeric());
        enterNonExistingItemValueToCopyFrom(randomAlphaNumericValue);
        getDriver().findElement(By.id("ok-button")).click();

        getWait5().until(ExpectedConditions.urlContains("/createItem"));

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), "Error");
    }

    @Test
    public void testIfOriginalItemConfigurationIsCopied() {
        clickOnNewItemLink();

        String randomAlphaNumericValue = TestUtils.generateRandomAlphanumeric();
        getDriver().findElement(By.id("name")).sendKeys(randomAlphaNumericValue);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        TestUtils.scrollToItemWithJS(getDriver(), getDriver().findElement(By.id("environment")));

        List<WebElement> labels = getDriver().findElements(By.xpath("//div[@id='environment']/../descendant::label"));
        for (WebElement label : labels) {
            TestUtils.scrollAndClickWithJS(getDriver(), label);
        }
        TestUtils.scrollAndClickWithJS(getDriver(), getDriver().findElement(By.name("Submit")));
        TestUtils.gotoHomePage(getDriver());

        clickOnNewItemLink();

        getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("name")))
                .sendKeys(TestUtils.generateRandomAlphanumeric());

        WebElement copyFromInput = getDriver().findElement(By.id("from"));
        TestUtils.scrollAndClickWithJS(getDriver(), copyFromInput);
        copyFromInput.sendKeys(randomAlphaNumericValue);
        getWait5().until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("[class^='jenkins-dropdown__item']")))
                  .click();

        getDriver().findElement(By.id("ok-button")).click();

        TestUtils.scrollToItemWithJS(getDriver(), getDriver().findElement(By.id("environment")));
        List<WebElement> checkboxes = getDriver().findElements(By.xpath("//div[@id='environment']/../descendant::input[@type='checkbox']"));

        Assert.assertTrue(checkboxes.stream().allMatch(WebElement::isSelected));
    }

    @Test
    public void testIfNewFolderIsCreatedEmpty() {
        clickOnNewItemLink();

        getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("name")))
                .sendKeys(TestUtils.generateRandomAlphanumeric());

        TestUtils.scrollAndClickWithJS(getDriver(), getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")));
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("h2.h4")).getText(),
                "This folder is empty"
        );
    }

    @Test(dataProvider = "itemTypes", dataProviderClass = TestDataProvider.class)
    public void testJobCreationWithinFolder(String itemTypeName) {
        String randomAlphaNumericValue = TestUtils.generateRandomAlphanumeric();

        TestUtils.newItemCreate(this, TestUtils.generateRandomAlphanumeric(), 4);

        WebElement jobTableLink = getDriver().findElement(By.cssSelector("a[href*='job'].jenkins-table__link"));
        String projectName = jobTableLink.getText();
        actions.moveToElement(jobTableLink).perform();

        TestUtils.moveAndClickWithJS(getDriver(), getDriver().findElement(By.cssSelector(".jenkins-table__link .jenkins-menu-dropdown-chevron")));
        TestUtils.scrollAndClickWithJS(getDriver(), getDriver().findElement(By.cssSelector(".jenkins-dropdown a[href$='/newJob'")));

        createNewJob(randomAlphaNumericValue, itemTypeName);

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("description-link")));
        TestUtils.scrollAndClickWithJS(getDriver(), getDriver().findElement(By.cssSelector(String.format("a[href='/job/%s/']", projectName))));

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("a.jenkins-table__link span")).getText(),
                randomAlphaNumericValue
        );
    }

    @Test(dataProvider = "itemTypes", dataProviderClass = TestDataProvider.class)
    public void testIf(String itemTypeName) {
        String firstFolderProjectName = TestUtils.generateRandomAlphanumeric();
        String secondFolderProjectName = TestUtils.generateRandomAlphanumeric();
        String randomAlphaNumericValue = TestUtils.generateRandomAlphanumeric();

        TestUtils.newItemCreate(this, firstFolderProjectName, 4);

        String firstFolderProjectLink = String.format("a[href*='%s'].jenkins-table__link", firstFolderProjectName);
        actions.moveToElement(getDriver().findElement(By.cssSelector(firstFolderProjectLink))).perform();

        openDropdownMenuForJob(firstFolderProjectName);
        TestUtils.scrollAndClickWithJS(getDriver(), getDriver().findElement(By.cssSelector(".jenkins-dropdown a[href$='/newJob'")));

        createNewJob(randomAlphaNumericValue, itemTypeName);
        TestUtils.gotoHomePage(getDriver());

        TestUtils.newItemCreate(this, secondFolderProjectName, 4);

        String secondFolderProjectLink  = String.format("a[href*='%s'].jenkins-table__link", secondFolderProjectName);
        actions.moveToElement(getDriver().findElement(By.cssSelector(secondFolderProjectLink))).perform();

        openDropdownMenuForJob(secondFolderProjectName);
        TestUtils.scrollAndClickWithJS(getDriver(), getDriver().findElement(By.cssSelector(".jenkins-dropdown a[href$='/newJob'")));

        createNewJob(randomAlphaNumericValue, "Freestyle project");

        Assert.assertEquals(getDriver().findElement(By.className("page-headline")).getText(), randomAlphaNumericValue);
    }
}
