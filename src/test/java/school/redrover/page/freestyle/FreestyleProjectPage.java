package school.redrover.page.freestyle;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;
import school.redrover.common.TestUtils;
import school.redrover.page.HomePage;

import java.time.Duration;
import java.util.List;

public class FreestyleProjectPage extends BasePage {

    public FreestyleProjectPage(WebDriver driver) {
        super(driver);
    }

    public String getProjectName() {
        return getDriver().findElement(By.className("page-headline")).getText();
    }

    public String getDescription() {
        return getDriver().findElement(By.cssSelector("#description > div")).getText();
    }

    public FreestyleProjectPage clickEditDescriptionButton() {
        getDriver().findElement(By.id("description-link")).click();

        return this;
    }

    public FreestyleProjectPage sendDescription(String text) {
        getDriver().findElement(By.cssSelector("textarea[name='description']")).clear();
        getDriver().findElement(By.cssSelector("textarea[name='description']")).sendKeys(text);

        return this;
    }

    public FreestyleProjectPage clickSave() {
        getDriver().findElement(By.name("Submit")).click();

        return this;
    }

    public FreestyleProjectPage clickLeftSideMenuRename() {
        getDriver().findElement(By.xpath("//span[text()='Rename']/..")).click();

        return this;
    }

    public FreestyleProjectPage sendName(String text) {
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(text);

        return this;
    }

    public FreestyleProjectPage clickRename() {
        getDriver().findElement(By.name("Submit")).click();

        return this;
    }

    public FreestyleProjectPage clickLeftSideMenuDelete() {
        getDriver().findElement(By.xpath("//span[text()='Delete Project']")).click();

        return this;
    }

    public HomePage clickPopUpYesDeleteProject() {
        getDriver().findElement(By.cssSelector("[data-id='ok']")).click();

        return new HomePage(getDriver());
    }

    public FreestyleConfigurationPage clickConfigure() {
        getDriver().findElement(By.cssSelector("a[href*='configure']")).click();

        return new FreestyleConfigurationPage(getDriver());
    }

    public FreestyleProjectPage clickProjectBreadcrumbsDropDownMenu() {

        Actions actions = new Actions(getDriver());
        actions.pause(Duration.ofSeconds(1)).perform();

        WebElement arrow = getWait5().until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(".jenkins-breadcrumbs__list-item:nth-child(3) .jenkins-menu-dropdown-chevron")));

        TestUtils.moveAndClickWithJS(getDriver(), arrow);

        return this;
    }

    public String[] getDropDownMenuItemsText() {
        List<WebElement> menuItems =  getWait5()
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".jenkins-dropdown__item")));

        String[] menuItemsText = new String[menuItems.size()];

        for (int i = 0; i < menuItems.size(); i++) {
            menuItemsText[i] = menuItems.get(i).getText();
        }

        return menuItemsText;
    }

    public String[] getMainMenuItemsText() {
        List<WebElement> menuItems =  getDriver().findElements(By.cssSelector(".task span:nth-of-type(2)"));

        // the first element found with the locator above is Status which is not in the drop-down menu
        // (and is not technically a menu item) so we need to reduce size by one
        String[] menuItemsText = new String[menuItems.size() - 1];

        // start with i = 1 since the first element found is Status (which is not a menu item)
        for (int i = 1; i < menuItems.size(); i++) {
            menuItemsText[i - 1] = menuItems.get(i).getText();
        }

        return menuItemsText;
    }

    public String getDisabledWarningMessageText() {
        String fullText = getDriver().findElement(By.xpath("//div[@class='warning']/form")).getText();

        return fullText.split("\\n")[0];
    }

    public FreestyleProjectPage clickEnableButton() {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();
        getWait5().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='warning']/form")));

        return this;
    }

    public List<WebElement> getWarningMessageList() {
        return getDriver().findElements(By.xpath("//div[@class='warning']/form"));
    }

    public FreestyleProjectPage waitUntilTextNameProjectToBePresentInH1(String text) {
        getWait5().until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), text));

        return this;
    }
}
