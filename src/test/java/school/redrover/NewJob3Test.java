package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.common.TestUtils;
import school.redrover.component.HeaderComponent;
import school.redrover.page.HomePage;
import school.redrover.page.newitem.NewItemPage;

import java.util.Arrays;
import java.util.List;

public class NewJob3Test extends BaseTest {

    @Test
    public void testEmptyName() {
        String expectedError = "» This field cannot be empty, please enter a valid name";

        NewItemPage newItemPage = new HomePage(getDriver())
                .clickNewItemOnLeftSidePanel();
        newItemPage.selectFreestyleAndClickOk();
        String actualError = newItemPage.getEmptyNameMessage();

        Assert.assertEquals(actualError, expectedError);
    }
    @Ignore
    @Test
    public void testInvalidCharactersInItemName() {
        List<String> invalidNames = Arrays.asList("My $#Job!@#", "Test Job#@$", "Job#12$#@3", "My@Job#$", "Job%Test$#");

        for (String invalidName : invalidNames) {
            NewItemPage newItemPage = new HomePage(getDriver())
                    .clickNewItemOnLeftSidePanel()
                    .sendItemName(invalidName);

            String actualError = newItemPage.getItemNameInvalidMessage();
            Assert.assertTrue(actualError.contains("is an unsafe"), invalidName);
            TestUtils.gotoHomePage(this);
        }
    }
    @Ignore
    @Test
    public void testCreateItemAndNavigateToConfigPage() {
        new HomePage(getDriver())
                .createJob()
                .enterProjectNameAndSelect("My name", "Pipeline");
        new HeaderComponent(getDriver())
                .goToHomePage();

        String actualNameProject = new HomePage(getDriver()).getNameProject();
        Assert.assertEquals(actualNameProject, "My name");
    }

    @Test
    public void testAllItemTypesArePresent() {
        new HomePage(getDriver()).createJob();

        NewItemPage newItemPage = new NewItemPage(getDriver());

        List<String> expectedTitles = Arrays.asList("Freestyle project",
                "Pipeline",
                "Multi-configuration project",
                "Folder",
                "Multibranch Pipeline",
                "Organization Folder");

        List<String> actualTitles = newItemPage.getAllProjectTypeTitles();

        Assert.assertEquals(actualTitles, expectedTitles);
    }

    @Ignore
    @Test
    public void testNewItemCreation() {
        new HomePage(getDriver())
                .createJob()
                .enterProjectNameAndSelect("Freestyle project","Freestyle project");

        NewItemPage newItemPage = new HeaderComponent(getDriver())
                .goToHomePage()
                .clickNewItem();

        String actualCopyFromText = newItemPage.getCopyFromText();

        Assert.assertEquals(actualCopyFromText, "Copy from");
    }

    @Ignore //Error:    NewJob3Test.testNewItemCopyFromAutocomplete:94 » StaleElementReference stale element reference: stale element not found

    @Test
    public void testNewItemCopyFromAutocomplete() {
        new HomePage(getDriver())
                .createJob()
                .enterProjectNameAndSelect("Freestyle project","Freestyle project");
        new HeaderComponent(getDriver())
                .goToHomePage();

        new HomePage(getDriver())
                .clickNewItem()
                .sendTextCopyForm("Freestyle");
        String actualText = new NewItemPage(getDriver())
                .getAutocompleteSuggestionText();

        Assert.assertNotNull(actualText,"Autocomplete suggestion not found.");
    }
    @Ignore
    @Test
    public void testCopyFromNonExistingItem() {
        new HomePage(getDriver())
                .createJob()
                .enterProjectNameAndSelect("Pipeline","Pipeline");
        new HeaderComponent(getDriver())
                .goToHomePage();

        new HomePage(getDriver())
                .clickNewItem()
                .sendTextCopyForm("NonExistingItem");

        String actualText = new NewItemPage(getDriver())
                .getAutocompleteSuggestionText();
        Assert.assertEquals(actualText,"No items");
    }
}

